package com.popupmc.variablehealth.mob.specific;

import com.popupmc.variablehealth.VariableHealth;
import com.popupmc.variablehealth.mob.MobLevel;
import com.popupmc.variablehealth.mob.MobScaling;
import org.bukkit.entity.Villager;

public class VillagerSpecific {
    public static void villagerSetup(Villager villager, int level) {

        // Do nothing if it's an existing villager
        if(villager.getTicksLived() > 20)
            return;

        // If level 10 and below, 90% chance of being a nitwit
        if(level <= MobLevel.percentOfMax(.10f) && (VariableHealth.random.nextInt(100 + 1) <= 90))
            villager.setProfession(Villager.Profession.NITWIT);

        // If level 11-25, 50% chance unemployed, 50% employed
        else if(level <= MobLevel.percentOfMax(.25f) && (VariableHealth.random.nextInt(100 + 1) > 50))
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

    public static void randomProfession(Villager villager) {
        Villager.Profession[] professions = Villager.Profession.values();
        Villager.Profession profession = professions[VariableHealth.random.nextInt(professions.length)];
        villager.setProfession(profession);
    }

    public static void hasProfession(Villager villager, int level) {
        villager.setVillagerExperience(MobScaling.addNoise(level / expMax));

        // Prevents a level: 0 crash that can happen
        int villagerLevel = level / levelMax;
        if(villagerLevel > 5)
            villagerLevel = 5;
        else if(villagerLevel < 1)
            villagerLevel = 1;

        villager.setVillagerLevel(villagerLevel);
    }

    public static final int expMax = (int)Math.ceil((double) MobLevel.maxLevel / 250); // 250 Max
    public static final int levelMax = (int)Math.ceil((double) MobLevel.maxLevel / 5); // 5 Max
}
