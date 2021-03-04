package com.popupmc.variablehealth.mob.specific;

import com.popupmc.variablehealth.VariableHealth;
import com.popupmc.variablehealth.mob.MobLevel;
import org.bukkit.entity.Vindicator;

public class VindicatorSpecific {
    public static void vindicatorSetup(Vindicator vindicator, int level) {
        if(level > MobLevel.percentOfMax(.5f))
            vindicator.setJohnny(VariableHealth.random.nextInt(100 + 1) > 25);
        else
            vindicator.setJohnny(VariableHealth.random.nextInt(100 + 1) < 25);
    }
}
