package com.popupmc.variablehealth.mob.specific;

import com.popupmc.variablehealth.VariableHealth;
import com.popupmc.variablehealth.mob.MobLevel;
import org.bukkit.entity.Zombie;

public class ZombieSpecific {
    public static void zombieSetup(Zombie zombie, int level) {
        if(level > MobLevel.percentOfMax(.5f))
            zombie.setCanBreakDoors(VariableHealth.random.nextInt(100 + 1) > 25);

        if(zombie.shouldBurnInDay() && level < MobLevel.percentOfMax(.5f))
            zombie.setShouldBurnInDay(VariableHealth.random.nextInt(100 + 1) > 25);
    }
}
