package com.popupmc.variablehealth.system.mobs;

import com.popupmc.variablehealth.VariableHealth;
import com.popupmc.variablehealth.system.BaseSystem;
import com.popupmc.variablehealth.system.System;
import com.popupmc.variablehealth.utility.RandomTools;
import org.bukkit.entity.Piglin;
import org.jetbrains.annotations.NotNull;

public class PiglinSpecific extends BaseSystem {
    public PiglinSpecific(@NotNull System system, @NotNull VariableHealth plugin) {
        super(system, plugin);
    }

    public void piglinSetup(Piglin piglin, int level) {
        if(level > system.level.data.percentOfMax(.5f))
            piglin.setIsAbleToHunt(RandomTools.getRandomChanceUp(25));
        else
            piglin.setIsAbleToHunt(RandomTools.getRandomChanceDown(25));
    }
}
