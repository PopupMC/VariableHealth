package com.popupmc.variablehealth.system.effects;

import com.popupmc.variablehealth.VariableHealth;
import com.popupmc.variablehealth.system.BaseSystem;
import com.popupmc.variablehealth.system.System;
import com.popupmc.variablehealth.utility.MathTools;
import com.popupmc.variablehealth.utility.RandomTools;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

public class EffectsBasic extends BaseSystem {
    public EffectsBasic(int resistanceLevelMax,
                        int strengthLevelMax,
                        @NotNull System system,
                        @NotNull VariableHealth plugin) {
        super(system, plugin);

        this.resistanceLevelMax = MathTools.createEasyDivider(
                system.level.data.maxLevel,
                MathTools.getPotionLevel(resistanceLevelMax));

        this.strengthLevelMax = MathTools.createEasyDivider(
                system.level.data.maxLevel,
                MathTools.getPotionLevel(strengthLevelMax));
    }

    public void applyBasicEffects(LivingEntity entity, int level, boolean isBoss) {
        if(isBoss)
            applyBasicEffectsBoss(entity, level);
        else
            applyBasicEffectsNonBoss(entity, level);
    }

    public void applyBasicEffectsNonBoss(LivingEntity entity, int level) {
        if(RandomTools.getCoinFlip() && level > system.level.data.maxLowLevel)
            system.effects.addPotionEffect(
                    entity,
                    PotionEffectType.INCREASE_DAMAGE,
                    level / strengthLevelMax);

        if(RandomTools.getCoinFlip() && level > system.level.data.maxLowLevel)
            system.effects.addPotionEffect(
                    entity,
                    PotionEffectType.DAMAGE_RESISTANCE,
                    level / resistanceLevelMax);
    }

    public void applyBasicEffectsBoss(LivingEntity entity, int level) {
        system.effects.addPotionEffect(
                entity,
                PotionEffectType.INCREASE_DAMAGE,
                level / strengthLevelMax);

        system.effects.addPotionEffect(
                entity,
                PotionEffectType.DAMAGE_RESISTANCE,
                level / resistanceLevelMax);
    }

    public final int resistanceLevelMax;
    public final int strengthLevelMax;
}
