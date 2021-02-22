package com.popupmc.variablehealth.mob.specific;

import com.popupmc.variablehealth.mob.MobLevel;
import org.bukkit.entity.Llama;

public class LlamaSpecific {
    public static void llamaSetup(Llama llama, int level) {
        int strength = (level / llamaStrengthMax) + 1; // 1-5
        llama.setStrength(strength);
    }

    public static final int llamaStrengthMax = (int)Math.ceil((double) MobLevel.maxLevel / 4); // 4 Strength
}
