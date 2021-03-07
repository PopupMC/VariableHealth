package com.popupmc.variablehealth.system.scaling;

import com.popupmc.variablehealth.VariableHealth;
import com.popupmc.variablehealth.system.BaseSystem;
import com.popupmc.variablehealth.system.System;
import com.popupmc.variablehealth.utility.RandomTools;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class ScaleStats extends BaseSystem {

    public ScaleStats(int statNoiseMaxPercent,
                      @NotNull System system,
                      @NotNull VariableHealth plugin) {
        super(system, plugin);

        this.statNoiseMaxPercent = statNoiseMaxPercent;
    }

    public void scaleHealth(LivingEntity livingEntity, int level, boolean isBoss) {
        // Get Scaled Health, genericScale up only if boss
        double health = livingEntity.getMaxHealth();

        health = RandomTools.addNoiseClamp(
                system.scale.basics.genericScale(health, level, isBoss),
                statNoiseMaxPercent,
                0.01d,
                2560d
        );

        // Set new health
        livingEntity.setMaxHealth(health);
        livingEntity.setHealth(health);
    }

    public void scaleAir(LivingEntity livingEntity, int level, boolean isBoss) {
        // Get Scaled Health, genericScale up only if boss
        double air = livingEntity.getMaximumAir();

        air = RandomTools.addNoiseClamp(
                system.scale.basics.genericScale(air, level, isBoss),
                statNoiseMaxPercent,
                1d,
                300d
        );

        // Set new health
        livingEntity.setMaximumAir((int)air);
        livingEntity.setRemainingAir((int)air);
    }

    public final int statNoiseMaxPercent;
}
