package com.popupmc.variablehealth.mob.specific;

import com.popupmc.variablehealth.mob.MobLevel;
import org.bukkit.entity.Zombie;

public class ZombieSpecific {
    public static void zombieSetup(Zombie zombie, int level) {
        zombie.setCanBreakDoors(MobLevel.levelGTMaxPercent(level, 0.50f));

        if(zombie.shouldBurnInDay())
            zombie.setShouldBurnInDay(MobLevel.levelLTMaxPercent(level, 0.50f));
    }
}
