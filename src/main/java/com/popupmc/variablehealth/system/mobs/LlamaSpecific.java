package com.popupmc.variablehealth.system.mobs;

import com.popupmc.variablehealth.VariableHealth;
import com.popupmc.variablehealth.system.BaseSystem;
import com.popupmc.variablehealth.system.System;
import com.popupmc.variablehealth.utility.MathTools;
import org.bukkit.entity.Llama;
import org.jetbrains.annotations.NotNull;

public class LlamaSpecific extends BaseSystem {
    public LlamaSpecific(int llamaStrengthMax,
                         @NotNull System system,
                         @NotNull VariableHealth plugin) {
        super(system, plugin);

        this.llamaStrengthMax = MathTools.createEasyDivider(system.level.data.maxLevel, llamaStrengthMax - 1);
    }

    public void llamaSetup(Llama llama, int level) {
        int strength = (level / llamaStrengthMax) + 1;
        llama.setStrength(strength);
    }

    public final int llamaStrengthMax;
}
