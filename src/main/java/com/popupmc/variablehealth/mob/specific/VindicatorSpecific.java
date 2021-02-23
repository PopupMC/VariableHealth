package com.popupmc.variablehealth.mob.specific;

import com.popupmc.variablehealth.mob.MobLevel;
import org.bukkit.entity.Vindicator;

public class VindicatorSpecific {
    public static void vindicatorSetup(Vindicator vindicator, int level) {
        vindicator.setJohnny(level > MobLevel.percentOfMax(.5f));
    }
}
