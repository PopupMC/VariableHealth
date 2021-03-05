package com.popupmc.variablehealth.mob;

import com.popupmc.variablehealth.VariableHealth;
import com.popupmc.variablehealth.lists.BossMobs;
import com.popupmc.variablehealth.lists.GlowingMobs;
import com.popupmc.variablehealth.lists.NonBossMobs;
import com.popupmc.variablehealth.mob.specific.*;
import org.bukkit.entity.*;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Mob {
    public static boolean doesApply(Entity entity) {
        return NonBossMobs.hashList.containsKey(entity.getType()) ||
                BossMobs.hashList.containsKey(entity.getType());
    }

    public static void setup(Entity entity) {

        // Don't proceed if this isn't a new mob
        // Disabled because I forgot passive mobs only spawn once, this means
        // all animals would not be "upgraded" per say to a level system
        // Only new animals in new chunks
//        if(entity.getTicksLived() > 20)
//            return;

        // Don't proceed if it isn't a living entity
        if(!(entity instanceof LivingEntity))
            return;

        // Get living instance
        LivingEntity livingEntity = (LivingEntity)entity;

        // Get is boss or not
        boolean isBoss = BossMobs.hashList.containsKey(entity.getType());

        // Retrieve or create level level
        int level;
        boolean setup = false;
        if(MobLevel.hasLevel(livingEntity)) {
            level = MobLevel.retrieveLevel(livingEntity);
            setup = true;
        }
        else
            level = MobLevel.getLevel(entity.getLocation());

        // Setup new mob
        if(!setup) {
            setupNewMob(livingEntity, isBoss, level);

            // Display title if boss
            if(isBoss)
                MobBoss.displayBossTitle(entity, level);
        }
    }

    public static void setupNewMob(Entity entity, boolean isBoss, int level) {
        if(!(entity instanceof LivingEntity))
            return;

        // Get living instance
        LivingEntity livingEntity = (LivingEntity)entity;

        // Skip all mobs that are tamed
        if(entity instanceof Tameable) {
            if(((Tameable)entity).getOwnerUniqueId() != null && ((Tameable)entity).isTamed()) {
                return;
            }
        }

        // Does it have a custom name?
        boolean hasCustomName = entity.getCustomName() != null && !entity.getCustomName().isEmpty();

        // If not, assign one, else stop here. Dont touch anything with custom names
        if(!hasCustomName) {
            //if(entity.getType() != EntityType.ENDER_DRAGON)
                entity.setCustomName("[" + level + "] " + readableMobName(entity.getType()));
        }
        else
            return;

        // Skip all mobs that are leashed
        if(livingEntity.isLeashed())
            return;

        if(GlowingMobs.hashList.containsKey(entity.getType()) && level < MobEffects.maxLowLevel) {
            livingEntity.setGlowing(VariableHealth.random.nextInt(100 + 1) > 25);
        }

        // Setup specific mobs
        Specific.setup(livingEntity, level);

        // If above 175 it's probably learned how to be silent
        if(level > MobLevel.percentOfMax(0.75f) && entity.getType() != EntityType.ENDER_DRAGON)
            entity.setSilent(VariableHealth.random.nextInt(100 + 1) > 25);

        // Apply effect all mobs get
        if(isBoss)
            MobEffects.applyBasicEffectsBoss(livingEntity, level);
        else
            MobEffects.applyBasicEffectsNonBoss(livingEntity, level);

        MobEffects.applyExtraEffects(livingEntity, level);

        // This will auto-skip if not low-level
        if(!isBoss)
            MobEffects.applyLowLevelEffects(livingEntity, level);

        // If above 50% of max level and not a boss, apply extra effects and config
//        if(level > MobLevel.percentOfMax(0.50f) && !isBoss) {
//            livingEntity.setCanPickupItems(VariableHealth.random.nextInt(100 + 1) > 25);
//        }

        livingEntity.setCanPickupItems(false);

        // Scale Health
        //if(entity.getType() != EntityType.ENDER_DRAGON)
            MobScaling.scaleHealth(livingEntity, level, isBoss);

        // Scale Air
        //if(entity.getType() != EntityType.ENDER_DRAGON)
            MobScaling.scaleAir(livingEntity, level, isBoss);

        // Store Level onto entity
        if(!MobLevel.hasLevel(livingEntity))
            MobLevel.storeLevel(livingEntity, level);
    }

    public static String readableMobName(EntityType type) {
        return convertToTitleCaseSplitting(
                type.name().toLowerCase().replaceAll("_", " "));
    }

    public static String convertToTitleCaseSplitting(String text) {
        final String WORD_SEPARATOR = " ";

        if (text == null || text.isEmpty()) {
            return text;
        }

        return Arrays
                .stream(text.split(WORD_SEPARATOR))
                .map(word -> word.isEmpty()
                        ? word
                        : Character.toTitleCase(word.charAt(0)) + word
                        .substring(1)
                        .toLowerCase())
                .collect(Collectors.joining(WORD_SEPARATOR));
    }
}
