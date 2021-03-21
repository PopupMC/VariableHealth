package com.popupmc.variablehealth.system.effects;

import com.popupmc.variablehealth.VariableHealth;
import com.popupmc.variablehealth.system.BaseSystem;
import com.popupmc.variablehealth.system.System;
import com.popupmc.variablehealth.utility.MathTools;
import com.popupmc.variablehealth.utility.RandomTools;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

public class EffectsExtra extends BaseSystem {
    public EffectsExtra(int hasteLevelMax,
                        int jumpLevelMax,
                        int healthBoostMax,
                        int absorbtionBoostMax,
                        int slownessLevelMax,
                        int fatigueLevelMax,
                        @NotNull System system,
                        @NotNull VariableHealth plugin) {
        super(system, plugin);

        this.hasteLevelMax = MathTools.createEasyDivider(system.level.data.maxLevel, MathTools.getPotionLevel(hasteLevelMax));
        this.jumpLevelMax = MathTools.createEasyDivider(system.level.data.maxLevel, MathTools.getPotionLevel(jumpLevelMax));
        this.healthBoostMax = MathTools.createEasyDivider(system.level.data.maxLevel, MathTools.getPotionLevel(healthBoostMax));
        this.absorbtionBoostMax = MathTools.createEasyDivider(system.level.data.maxLevel, MathTools.getPotionLevel(absorbtionBoostMax));

        this.slownessLevelMax = MathTools.createEasyDivider(system.level.data.maxLowLevel, MathTools.getPotionLevel(slownessLevelMax));
        this.fatigueLevelMax = MathTools.createEasyDivider(system.level.data.maxLowLevel, MathTools.getPotionLevel(fatigueLevelMax));
    }

    public void applyExtraEffects(LivingEntity entity, int level) {

        // If low level, there's a 25% chance some of these will apply
        if(level <= system.level.data.maxLowLevel) {
            if(!RandomTools.getRandomChanceDown(20))
                return;
        }

        // Get random effects for 8 potions
        int enabledEffects = RandomTools.getRandomRange0Inclusive(0b111111111);

        // Skipping bit 0

        // Speed
        if((enabledEffects & 0b000000010) > 0 && level > system.level.data.maxLowLevel)
            system.effects.addPotionEffect(
                    entity,
                    PotionEffectType.SPEED,
                    MathTools.getPotionLevel(RandomTools.getRandomRange1(3)));

        // Haste
        if((enabledEffects & 0b000000100) > 0 && level > system.level.data.maxLowLevel)
            system.effects.addPotionEffect(
                    entity,
                    PotionEffectType.FAST_DIGGING,
                    level / hasteLevelMax);

        // Jump
        if((enabledEffects & 0b000001000) > 0)
            system.effects.addPotionEffect(
                    entity,
                    PotionEffectType.JUMP,
                    level / jumpLevelMax);

        // Fire Resistance
        if((enabledEffects & 0b000010000) > 0 && entity.getType() != EntityType.IRON_GOLEM)
            system.effects.addPotionEffect(
                    entity,
                    PotionEffectType.FIRE_RESISTANCE,
                    MathTools.getPotionLevel(1));

        // Water Breathing
        if((enabledEffects & 0b000100000) > 0 && entity.getType() != EntityType.IRON_GOLEM)
            system.effects.addPotionEffect(
                    entity,
                    PotionEffectType.WATER_BREATHING,
                    MathTools.getPotionLevel(1));

        // Health Boost
        if((enabledEffects & 0b001000000) > 0)
            system.effects.addPotionEffect(
                    entity,
                    PotionEffectType.HEALTH_BOOST,
                    level / healthBoostMax);

        // Slow Falling
        if((enabledEffects & 0b010000000) > 0)
            system.effects.addPotionEffect(
                    entity,
                    PotionEffectType.SLOW_FALLING,
                    MathTools.getPotionLevel(1));

        // Absorbtion
        if((enabledEffects & 0b100000000) > 0)
            system.effects.addPotionEffect(
                    entity,
                    PotionEffectType.ABSORPTION,
                    level / absorbtionBoostMax);
    }

    public void applyLowLevelEffects(LivingEntity entity, int level) {
        // Only apply to low levels
        if(level > system.level.data.maxLowLevel)
            return;

        // Reverse it
        int reversedLevel = system.level.data.maxLowLevel - level;

        // Get random effects for 10 potions
        int enabledEffects = RandomTools.getRandomRange0Inclusive(0b1111);

        // Skip bit 0

        // Slowness
        if((enabledEffects & 0b0010) > 0)
            system.effects.addPotionEffect(entity, PotionEffectType.SLOW, reversedLevel / slownessLevelMax);

        // Fatigue
        if((enabledEffects & 0b0100) > 0)
            system.effects.addPotionEffect(entity, PotionEffectType.SLOW_DIGGING, reversedLevel / fatigueLevelMax);

        // Weakness, 50% chance of 0 or 1
        if((enabledEffects & 0b1000) > 0)
            system.effects.addPotionEffect(entity, PotionEffectType.WEAKNESS,
                    MathTools.getPotionLevel((RandomTools.getCoinFlip()) ? 1 : 2));
    }

    public final int hasteLevelMax;
    public final int jumpLevelMax;
    public final int healthBoostMax;
    public final int absorbtionBoostMax;

    public final int slownessLevelMax;
    public final int fatigueLevelMax;
}
