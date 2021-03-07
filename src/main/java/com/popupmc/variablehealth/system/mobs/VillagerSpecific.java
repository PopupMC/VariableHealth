package com.popupmc.variablehealth.system.mobs;

import com.popupmc.variablehealth.VariableHealth;
import com.popupmc.variablehealth.system.BaseSystem;
import com.popupmc.variablehealth.system.System;
import com.popupmc.variablehealth.utility.MathTools;
import com.popupmc.variablehealth.utility.RandomTools;
import org.bukkit.entity.Villager;
import org.jetbrains.annotations.NotNull;

public class VillagerSpecific extends BaseSystem {
    public VillagerSpecific(int villagerExpMax,
                            int villagerLevelMax,
                            @NotNull System system,
                            @NotNull VariableHealth plugin) {
        super(system, plugin);

        this.villagerExpMax = MathTools.createEasyDivider(system.level.data.maxLevel, villagerExpMax);
        this.villagerLevelMax = MathTools.createEasyDivider(system.level.data.maxLevel, villagerLevelMax - 1);
    }

    public void villagerSetup(Villager villager, int level) {

        // Do nothing if it's an existing villager
        if(villager.getTicksLived() > 20)
            return;

        // If level 10 and below, 90% chance of being a nitwit
        if(level <= system.level.data.percentOfMax(.10f) && (RandomTools.getRandomChanceDown(90)))
            villager.setProfession(Villager.Profession.NITWIT);

        // If level 11-25, 50% chance unemployed, 50% employed
        else if(level <= system.level.data.percentOfMax(.25f) && (RandomTools.getRandomChanceUp(50)))
            villager.setProfession(Villager.Profession.NONE);

        // Random Profession
        else
            randomProfession(villager);

        // Get the chosen profession
        Villager.Profession chosenProfession = villager.getProfession();

        if(chosenProfession != Villager.Profession.NITWIT && chosenProfession != Villager.Profession.NONE)
            hasProfession(villager, level);

        villager.resetOffers();
    }

    public void randomProfession(Villager villager) {
        Villager.Profession[] professions = Villager.Profession.values();
        Villager.Profession profession = professions[RandomTools.getRandomRange0Exclusive(professions.length)];
        villager.setProfession(profession);
    }

    public void hasProfession(Villager villager, int level) {
        villager.setVillagerExperience((int)RandomTools.addNoise((double)level / (double) villagerExpMax));

        // Prevents a level: 0 crash that can happen
        int villagerLevel = level / villagerLevelMax;
        villagerLevel++;

        villagerLevel = (int)MathTools.clamp(villagerLevel, 1, 5);
        villager.setVillagerLevel(villagerLevel);
    }

    public final int villagerExpMax;
    public final int villagerLevelMax;
}
