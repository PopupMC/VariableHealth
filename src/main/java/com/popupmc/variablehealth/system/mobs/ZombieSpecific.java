package com.popupmc.variablehealth.system.mobs;

import com.popupmc.variablehealth.VariableHealth;
import com.popupmc.variablehealth.system.BaseSystem;
import com.popupmc.variablehealth.system.System;
import com.popupmc.variablehealth.utility.RandomTools;
import org.bukkit.entity.Zombie;
import org.jetbrains.annotations.NotNull;

public class ZombieSpecific extends BaseSystem {
    public ZombieSpecific(@NotNull System system, @NotNull VariableHealth plugin) {
        super(system, plugin);
    }

    public void zombieSetup(Zombie zombie, int level) {
        if(level > system.level.data.percentOfMax(.5f))
            zombie.setCanBreakDoors(RandomTools.getRandomChanceUp(25));
        else
            zombie.setCanBreakDoors(RandomTools.getRandomChanceDown(25));

        if(zombie.shouldBurnInDay() && level < system.level.data.percentOfMax(.5f))
            zombie.setShouldBurnInDay(RandomTools.getRandomChanceUp(25));
        else if(zombie.shouldBurnInDay() && level >= system.level.data.percentOfMax(.5f))
            zombie.setShouldBurnInDay(RandomTools.getRandomChanceDown(25));
    }
}
