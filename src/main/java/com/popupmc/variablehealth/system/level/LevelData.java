package com.popupmc.variablehealth.system.level;

import com.popupmc.variablehealth.VariableHealth;
import com.popupmc.variablehealth.system.BaseSystem;
import com.popupmc.variablehealth.system.System;
import com.popupmc.variablehealth.utility.RandomTools;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

public class LevelData extends BaseSystem {
    public LevelData(int maxLevel,
                     int lowLevelPercent,
                     @NotNull System system,
                     @NotNull VariableHealth plugin) {
        super(system, plugin);

        this.maxLevel = maxLevel;
        this.maxLowLevel = percentOfMax((float)lowLevelPercent * 0.01f);
    }

    public int percentOfMax(float percent) {
        return (int)(maxLevel * percent);
    }
    public int getRandomLevel() {
        return VariableHealth.random.nextInt(maxLevel) + 1;
    }

    public int getAverageOrRandomLevel(Location location) {
        int powerLevelAverage = system.level.average.getPowerLevelAverage(location);

        if(powerLevelAverage <= -1)
            return getRandomLevel();

        // DarkPrincess, you idiot, Using Math.max on the first line of the method prents it from ever being -1
        // Leading to the annoying bug you created
        // Sincerely ~DarkPrincess
        powerLevelAverage = Math.max(powerLevelAverage, 1);

        int level = powerLevelAverage / system.level.average.powerLevelPerLevel;
        level = (int)RandomTools.addNoiseClamp(level, system.level.average.levelNoiseMaxPercent, 1, maxLevel);

        return level;
    }

    public final int maxLevel;
    public final int maxLowLevel;
}
