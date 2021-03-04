package com.popupmc.variablehealth.mob.specific;

import com.popupmc.variablehealth.VariableHealth;
import com.popupmc.variablehealth.mob.MobLevel;
import org.bukkit.entity.Hoglin;

public class HoglinSpecific {
    public static void hoglinSetup(Hoglin hoglin, int level) {
        if(level < MobLevel.percentOfMax(.5f))
            hoglin.setIsAbleToBeHunted(VariableHealth.random.nextInt(100 + 1) > 25);
        else
            hoglin.setIsAbleToBeHunted(VariableHealth.random.nextInt(100 + 1) < 25);
    }
}
