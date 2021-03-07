package com.popupmc.variablehealth.system.mobs;

import com.popupmc.variablehealth.VariableHealth;
import com.popupmc.variablehealth.system.BaseSystem;
import com.popupmc.variablehealth.system.System;
import com.popupmc.variablehealth.utility.RandomTools;
import org.bukkit.entity.Hoglin;
import org.jetbrains.annotations.NotNull;

public class HoglinSpecific extends BaseSystem {
    public HoglinSpecific(@NotNull System system, @NotNull VariableHealth plugin) {
        super(system, plugin);
    }

    public void hoglinSetup(Hoglin hoglin, int level) {
        if(level < system.level.data.percentOfMax(.5f))
            hoglin.setIsAbleToBeHunted(RandomTools.getRandomChanceUp(25));
        else
            hoglin.setIsAbleToBeHunted(RandomTools.getRandomChanceDown(25));
    }
}
