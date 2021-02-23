package com.popupmc.variablehealth.mob;

import com.popupmc.variablehealth.VariableHealth;
import com.popupmc.variablehealth.lists.BossMobs;
import com.popupmc.variablehealth.lists.NonBossMobs;
import com.popupmc.variablehealth.mob.specific.*;
import org.bukkit.entity.*;

public class Mob {
    public static boolean doesApply(Entity entity) {
        return NonBossMobs.hashList.containsKey(entity.getType()) ||
                BossMobs.hashList.containsKey(entity.getType());
    }

    public static void setup(Entity entity) {
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
            level = MobLevel.randomLevel();

        // Setup new mob
        if(!setup)
            setupNewMob(livingEntity, isBoss, level);

        // Display title if boss
        if(isBoss)
            MobBoss.displayBossTitle(entity, level);
    }

    public static void setupNewMob(Entity entity, boolean isBoss, int level) {
        if(!(entity instanceof LivingEntity))
            return;

        // Get living instance
        LivingEntity livingEntity = (LivingEntity)entity;

        // Skip all mobs that are tamed
        if(entity instanceof Tameable) {
            if(((Tameable)entity).isTamed())
                return;
        }

        // Skip anything that has a custom name
        if(entity.getCustomName() != null && !entity.getCustomName().isEmpty())
            return;

        // Setup specific mobs
        Specific.setup(livingEntity, level);

        // If above 175 it's probably learned how to be silent
        if(level > MobLevel.percentOfMax(0.75f))
            entity.setSilent(VariableHealth.random.nextInt(100 + 1) > 25);

        // Apply effect all mobs get
        MobEffects.applyBasicEffects(livingEntity, level);

        // If above 50% of max level and not a boss, apply extra effects and config
        if(level > MobLevel.percentOfMax(0.50f) && !isBoss) {
            MobEffects.applyExtraEffects(livingEntity, level);
            livingEntity.setCanPickupItems(VariableHealth.random.nextInt(100 + 1) > 25);
        }

        // Scale Health
        MobScaling.scaleHealth(livingEntity, level, isBoss);

        // Scale Air
        MobScaling.scaleAir(livingEntity, level, isBoss);

        // Store Level onto entity
        if(!MobLevel.hasLevel(livingEntity))
            MobLevel.storeLevel(livingEntity, level);
    }
}
