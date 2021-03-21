package com.popupmc.variablehealth.system.mobs;

import com.popupmc.variablehealth.VariableHealth;
import com.popupmc.variablehealth.system.BaseSystem;
import com.popupmc.variablehealth.system.System;
import com.popupmc.variablehealth.utility.RandomTools;
import org.bukkit.entity.Phantom;
import org.jetbrains.annotations.NotNull;

public class PhantomSpecific extends BaseSystem {
    public PhantomSpecific(int phantomSizeMax,
                           @NotNull System system,
                           @NotNull VariableHealth plugin) {
        super(system, plugin);

        this.phantomSizeMaxRaw = phantomSizeMax;
    }

    public void phantomSetup(Phantom phantom, int level) {
        int size = RandomTools.getRandomRange0Inclusive(phantomSizeMaxRaw);
        phantom.setSize(size);
    }

    public final int phantomSizeMaxRaw;
}
