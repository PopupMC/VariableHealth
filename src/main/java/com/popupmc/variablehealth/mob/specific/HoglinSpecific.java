package com.popupmc.variablehealth.mob.specific;

import com.popupmc.variablehealth.mob.MobLevel;
import org.bukkit.entity.Hoglin;

public class HoglinSpecific {
    public static void hoglinSetup(Hoglin hoglin, int level) {
        hoglin.setIsAbleToBeHunted(MobLevel.levelLTMaxPercent(level, 0.50f));
    }
}
