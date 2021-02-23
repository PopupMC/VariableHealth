package com.popupmc.variablehealth.mob.specific;

import com.popupmc.variablehealth.mob.MobLevel;
import org.bukkit.entity.Zombie;

public class ZombieSpecific {
    public static void zombieSetup(Zombie zombie, int level) {
        zombie.setCanBreakDoors(level > MobLevel.percentOfMax(.5f));

        if(zombie.shouldBurnInDay())
            zombie.setShouldBurnInDay(level < MobLevel.percentOfMax(.5f));
    }
}
