package com.popupmc.variablehealth.system.effects;

import com.popupmc.variablehealth.VariableHealth;
import com.popupmc.variablehealth.system.BaseSystem;
import com.popupmc.variablehealth.system.System;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

public class Effects extends BaseSystem {
    public Effects(int resistanceLevelMax,
                   int hasteLevelMax,
                   int jumpLevelMax,
                   int healthBoostMax,
                   int absorbtionBoostMax,
                   int slownessLevelMax,
                   int fatigueLevelMax,
                   int strengthLevelMax,
                   @NotNull System system,
                   @NotNull VariableHealth plugin) {
        super(system, plugin);

        this.basic = new EffectsBasic(resistanceLevelMax, strengthLevelMax, system, plugin);
        this.extra = new EffectsExtra(
                hasteLevelMax,
                jumpLevelMax,
                healthBoostMax,
                absorbtionBoostMax,
                slownessLevelMax,
                fatigueLevelMax,
                system,
                plugin);
    }

    public void addPotionEffect(LivingEntity entity, PotionEffectType type, int level) {
        entity.addPotionEffect(
                new PotionEffect(
                        type,
                        Integer.MAX_VALUE,
                        level,
                        false,
                        false));
    }

    public final EffectsBasic basic;
    public final EffectsExtra extra;
}
