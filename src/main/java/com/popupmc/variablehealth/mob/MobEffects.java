package com.popupmc.variablehealth.mob;

import com.popupmc.variablehealth.VariableHealth;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class MobEffects {
    public static void applyBasicEffectsNonBoss(LivingEntity entity, int level) {
        if(VariableHealth.random.nextInt(100 + 1) > 50 && level > maxLowLevel)
            addPotionEffect(entity, PotionEffectType.INCREASE_DAMAGE, level / strengthLevelMax);

        if(VariableHealth.random.nextInt(100 + 1) > 50 && level > maxLowLevel)
            addPotionEffect(entity, PotionEffectType.DAMAGE_RESISTANCE, level / resistanceLevelMax);
    }

    public static void applyBasicEffectsBoss(LivingEntity entity, int level) {
        addPotionEffect(entity, PotionEffectType.INCREASE_DAMAGE, level / strengthLevelMax);
        addPotionEffect(entity, PotionEffectType.DAMAGE_RESISTANCE, level / resistanceLevelMax);
    }

    public static void applyExtraEffects(LivingEntity entity, int level) {

        // If low level, there's a 25% chance some of these will apply
        if(level <= maxLowLevel) {
            if(VariableHealth.random.nextInt(100 + 1) > 25)
                return;
        }

        // Get random effects for 10 potions
        int enabledEffects = VariableHealth.random.nextInt(0b11111111111);

        // Speed
        if((enabledEffects & 0b0000000001) > 0 && level > maxLowLevel)
            addPotionEffect(entity, PotionEffectType.SPEED, level / speedLevelMax);

        // Haste
        if((enabledEffects & 0b0000000010) > 0 && level > maxLowLevel)
            addPotionEffect(entity, PotionEffectType.FAST_DIGGING, level / hasteLevelMax);

        // Jump
        if((enabledEffects & 0b0000000100) > 0)
            addPotionEffect(entity, PotionEffectType.JUMP, level / jumpLevelMax);

        // Fire Resistance
        if((enabledEffects & 0b0000010000) > 0 && entity.getType() != EntityType.IRON_GOLEM)
            addPotionEffect(entity, PotionEffectType.FIRE_RESISTANCE, 0);

        // Water Breathing
        if((enabledEffects & 0b0000100000) > 0 && entity.getType() != EntityType.IRON_GOLEM)
            addPotionEffect(entity, PotionEffectType.WATER_BREATHING, 0);

        // Health Boost
        if((enabledEffects & 0b0010000000) > 0)
            addPotionEffect(entity, PotionEffectType.HEALTH_BOOST, level / healthBoostMax);

        // Slow Falling
        if((enabledEffects & 0b0100000000) > 0)
            addPotionEffect(entity, PotionEffectType.SLOW_FALLING, 0);

        // Absorbtion
        if((enabledEffects & 0b1000000000) > 0)
            addPotionEffect(entity, PotionEffectType.ABSORPTION, level / absorbtionBoostMax);
    }

    public static void applyLowLevelEffects(LivingEntity entity, int level) {
        // Only apply to low levels
        if(level > maxLowLevel)
            return;

        // Reverse it
        int reversedLevel = maxLowLevel - level;

        // Get random effects for 10 potions
        int enabledEffects = VariableHealth.random.nextInt(0b11111111111);

        // Slowness
        if((enabledEffects & 0b0000000001) > 0)
            addPotionEffect(entity, PotionEffectType.SLOW, reversedLevel / slownessLevelMax);

        // Fatigue
        if((enabledEffects & 0b0000000010) > 0)
            addPotionEffect(entity, PotionEffectType.SLOW_DIGGING, reversedLevel / fatigueLevelMax);

        // Weakness, 50% chance of 0 or 1
        if((enabledEffects & 0b0000000100) > 0)
            addPotionEffect(entity, PotionEffectType.WEAKNESS,
                    (VariableHealth.random.nextInt(100 + 1) > 50) ? 0 : 1);
    }

    public static void addPotionEffect(LivingEntity entity, PotionEffectType type, int level) {
        entity.addPotionEffect(
                new PotionEffect(
                        type,
                        Integer.MAX_VALUE,
                        level,
                        false,
                        false));
    }

    // Bug Fix, they're 0 based. Level 3 is level 4 which explains invulnerable mobs
    public static final int resistanceLevelMax = (int)Math.ceil((double)MobLevel.maxLevel / 3); // 4 = 80%
    public static final int strengthLevelMax = (int)Math.ceil((double)MobLevel.maxLevel / 2); // 3 = 4.5 hearts of damage (9 points)
    public static final int speedLevelMax = (int)Math.ceil((double)MobLevel.maxLevel / 3); // 4 = 80% speed increase
    public static final int hasteLevelMax = (int)Math.ceil((double)MobLevel.maxLevel / 3); // 4 = 80% speed increase
    public static final int jumpLevelMax = (int)Math.ceil((double)MobLevel.maxLevel / 3); // 4 = 4 blocks high
    public static final int healthBoostMax = (int)Math.ceil((double)MobLevel.maxLevel / 2); // 3 = 6 extra hearts
    public static final int absorbtionBoostMax = (int)Math.ceil((double)MobLevel.maxLevel / 2); // 3 = 6 extra hearts

    public static final int maxLowLevel = MobLevel.percentOfMax(.15f);

    public static final int slownessLevelMax = (int)Math.ceil((double)maxLowLevel / 5); // 6 = 90% speed decrease
    public static final int fatigueLevelMax = (int)Math.ceil((double)maxLowLevel / 3);
}
