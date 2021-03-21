package com.popupmc.variablehealth.system.mobs;

import com.popupmc.variablehealth.VariableHealth;
import com.popupmc.variablehealth.system.BaseSystem;
import com.popupmc.variablehealth.system.System;
import com.popupmc.variablehealth.utility.MathTools;
import com.popupmc.variablehealth.utility.RandomTools;
import org.bukkit.entity.Slime;
import org.jetbrains.annotations.NotNull;

public class SlimeSpecific extends BaseSystem {

    public SlimeSpecific(int slimeMaxSize,
                         @NotNull System system,
                         @NotNull VariableHealth plugin) {
        super(system, plugin);

        this.slimeMaxSize = MathTools.createEasyDivider(system.level.data.maxLevel, slimeMaxSize);
    }

    public void slimeSetup(Slime slime, int level) {

        // If we don't include this then slimes will always multiply indefinately leading to a server
        // crash
        if(!RandomTools.getRandomChanceDown(15))
            return;

        slime.setSize(level / slimeMaxSize);
    }

    public final int slimeMaxSize;
}
