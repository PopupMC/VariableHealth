package com.popupmc.variablehealth.system.mobs;

import com.popupmc.variablehealth.VariableHealth;
import com.popupmc.variablehealth.system.BaseSystem;
import com.popupmc.variablehealth.system.System;
import com.popupmc.variablehealth.utility.RandomTools;
import org.bukkit.entity.Vindicator;
import org.jetbrains.annotations.NotNull;

public class VindicatorSpecific extends BaseSystem {
    public VindicatorSpecific(@NotNull System system, @NotNull VariableHealth plugin) {
        super(system, plugin);
    }

    public void vindicatorSetup(Vindicator vindicator, int level) {
        if(level > system.level.data.percentOfMax(.5f))
            vindicator.setJohnny(RandomTools.getRandomChanceUp(25));
        else
            vindicator.setJohnny(RandomTools.getRandomChanceDown(25));
    }
}
