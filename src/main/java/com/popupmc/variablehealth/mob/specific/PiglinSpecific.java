package com.popupmc.variablehealth.mob.specific;

import com.popupmc.variablehealth.VariableHealth;
import com.popupmc.variablehealth.mob.MobLevel;
import org.bukkit.entity.Piglin;

public class PiglinSpecific {
    public static void piglinSetup(Piglin piglin, int level) {
        if(level > MobLevel.percentOfMax(.5f))
            piglin.setIsAbleToHunt(VariableHealth.random.nextInt(100 + 1) > 25);
    }
}
