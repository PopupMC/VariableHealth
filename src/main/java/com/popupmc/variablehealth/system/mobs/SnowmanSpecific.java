package com.popupmc.variablehealth.system.mobs;

import com.popupmc.variablehealth.VariableHealth;
import com.popupmc.variablehealth.system.BaseSystem;
import com.popupmc.variablehealth.system.System;
import com.popupmc.variablehealth.utility.RandomTools;
import org.bukkit.entity.Snowman;
import org.jetbrains.annotations.NotNull;

public class SnowmanSpecific extends BaseSystem {
    public SnowmanSpecific(@NotNull System system, @NotNull VariableHealth plugin) {
        super(system, plugin);
    }

    public void snowmanSetup(Snowman snowman, int level) {
        if(level < system.level.data.percentOfMax(.5f))
            snowman.setDerp(RandomTools.getRandomChanceUp(25));
        else
            snowman.setDerp(RandomTools.getRandomChanceDown(25));
    }
}
