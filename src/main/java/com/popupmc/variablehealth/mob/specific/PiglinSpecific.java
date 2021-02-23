package com.popupmc.variablehealth.mob.specific;

import com.popupmc.variablehealth.mob.MobLevel;
import org.bukkit.entity.Piglin;

public class PiglinSpecific {
    public static void piglinSetup(Piglin piglin, int level) {
        piglin.setIsAbleToHunt(level > MobLevel.percentOfMax(.5f));
    }
}
