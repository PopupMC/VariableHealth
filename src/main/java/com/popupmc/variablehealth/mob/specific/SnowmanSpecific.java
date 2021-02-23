package com.popupmc.variablehealth.mob.specific;

import com.popupmc.variablehealth.mob.MobLevel;
import org.bukkit.entity.Snowman;

public class SnowmanSpecific {
    public static void snowmanSetup(Snowman snowman, int level) {
        snowman.setDerp(level < MobLevel.percentOfMax(.5f));
    }
}
