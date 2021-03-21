package com.popupmc.variablehealth.system.mobs;

import com.popupmc.variablehealth.VariableHealth;
import com.popupmc.variablehealth.system.BaseSystem;
import com.popupmc.variablehealth.system.System;
import com.popupmc.variablehealth.utility.RandomTools;
import org.bukkit.entity.Rabbit;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class RabbitSpecific extends BaseSystem {
    public RabbitSpecific(@NotNull System system, @NotNull VariableHealth plugin) {
        super(system, plugin);

        for(Rabbit.Type type : Rabbit.Type.values()) {
            if(type != Rabbit.Type.THE_KILLER_BUNNY)
                safeTypes.add(type);
        }
    }

    public void setupRabbit(Rabbit rabbit, int level) {
        if(level >= system.level.data.percentOfMax(.90f) && RandomTools.getRandomChanceDown(10))
            rabbit.setRabbitType(Rabbit.Type.THE_KILLER_BUNNY);
        else if(level >= system.level.data.percentOfMax(.85f) && RandomTools.getRandomChanceDown(15))
            randomAnyTexture(rabbit);
        else
            randomSafeTexture(rabbit);
    }

    public void randomSafeTexture(Rabbit rabbit) {
        Rabbit.Type type = safeTypes.get(RandomTools.getRandomRange0Exclusive(safeTypes.size()));
        rabbit.setRabbitType(type);
    }

    public void randomAnyTexture(Rabbit rabbit) {
        Rabbit.Type type = Rabbit.Type.values()[RandomTools.getRandomRange0Exclusive(Rabbit.Type.values().length)];
        rabbit.setRabbitType(type);
    }

    ArrayList<Rabbit.Type> safeTypes = new ArrayList<>();
}
