package com.popupmc.variablehealth.system.mobs;

import com.popupmc.variablehealth.VariableHealth;
import com.popupmc.variablehealth.system.BaseSystem;
import com.popupmc.variablehealth.system.System;
import com.popupmc.variablehealth.utility.RandomTools;
import org.bukkit.entity.Villager;
import org.bukkit.entity.ZombieVillager;
import org.jetbrains.annotations.NotNull;

public class ZombieVillagerSpecific extends BaseSystem {
    public ZombieVillagerSpecific(@NotNull System system,
                                  @NotNull VariableHealth plugin) {
        super(system, plugin);
    }

    public void villagerSetup(ZombieVillager villager, int level) {

        // Do nothing if it's an existing villager
        if(villager.getTicksLived() > 20)
            return;

        // If level 10 and below, 90% chance of being a nitwit
        if(level <= system.level.data.percentOfMax(.10f) && (RandomTools.getRandomChanceDown(90)))
            villager.setVillagerProfession(Villager.Profession.NITWIT);

        // If level 11-25, 50% chance unemployed, 50% employed
        else if(level <= system.level.data.percentOfMax(.25f) && (RandomTools.getRandomChanceUp(50)))
            villager.setVillagerProfession(Villager.Profession.NONE);

        // Random Profession
        else
            randomProfession(villager);
    }

    public void randomProfession(ZombieVillager villager) {
        Villager.Profession[] professions = Villager.Profession.values();
        Villager.Profession profession = professions[RandomTools.getRandomRange0Exclusive(professions.length)];
        villager.setVillagerProfession(profession);
    }
}
