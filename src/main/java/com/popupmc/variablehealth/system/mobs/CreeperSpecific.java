package com.popupmc.variablehealth.system.mobs;

import com.popupmc.variablehealth.VariableHealth;
import com.popupmc.variablehealth.system.BaseSystem;
import com.popupmc.variablehealth.system.System;
import com.popupmc.variablehealth.utility.MathTools;
import com.popupmc.variablehealth.utility.RandomTools;
import org.bukkit.entity.Creeper;
import org.jetbrains.annotations.NotNull;

public class CreeperSpecific extends BaseSystem {
    public CreeperSpecific(int creeperExplosionMaxRadius,
                           int creeperFuseMaxTime,
                           @NotNull System system,
                           @NotNull VariableHealth plugin) {
        super(system, plugin);

        this.creeperExplosionMaxRadius = MathTools.createEasyDivider(system.level.data.maxLevel, creeperExplosionMaxRadius);
        this.creeperFuseMaxTime = MathTools.createEasyDivider(system.level.data.maxLevel, creeperFuseMaxTime);
    }

    public int getCreeperExplosionRadius(int level) {
        return (int)RandomTools.addNoiseClamp((double)level / (double)creeperExplosionMaxRadius, 0, creeperExplosionMaxRadius);
    }

    public int getCreeperFuseMaxTime(int level) {
        return (int)RandomTools.addNoiseClamp((double)level / (double)creeperFuseMaxTime, 0, creeperFuseMaxTime);
    }

    public boolean getCreeperPowered(int level) {
        if(level > system.level.data.percentOfMax(.75f))
            return RandomTools.getRandomChanceUp(25);

        return false;
    }

    public void creeperSetup(Creeper creeper, int level) {
        int explosionRadius = getCreeperExplosionRadius(level);
        creeper.setExplosionRadius(explosionRadius);

        int maxFuseTicks = getCreeperFuseMaxTime(level);
        creeper.setMaxFuseTicks(maxFuseTicks);

        creeper.setPowered(getCreeperPowered(level));
    }

    public final int creeperExplosionMaxRadius;
    public final int creeperFuseMaxTime;
}
