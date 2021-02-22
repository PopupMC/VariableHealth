package com.popupmc.variablehealth.mob.specific;

import com.popupmc.variablehealth.mob.MobLevel;
import org.bukkit.entity.Piglin;

public class PiglinSpecific {
    public static void piglinSetup(Piglin piglin, int level) {
        piglin.setIsAbleToHunt(MobLevel.levelGTMaxPercent(level, 0.50f));
    }
}
