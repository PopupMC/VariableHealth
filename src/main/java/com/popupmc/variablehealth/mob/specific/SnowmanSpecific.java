package com.popupmc.variablehealth.mob.specific;

import com.popupmc.variablehealth.VariableHealth;
import com.popupmc.variablehealth.mob.MobLevel;
import org.bukkit.entity.Snowman;

public class SnowmanSpecific {
    public static void snowmanSetup(Snowman snowman, int level) {
        if(level < MobLevel.percentOfMax(.5f))
            snowman.setDerp(VariableHealth.random.nextInt(100 + 1) > 25);
    }
}
