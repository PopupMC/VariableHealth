package com.popupmc.variablehealth.system.level;

import com.popupmc.variablehealth.VariableHealth;
import com.popupmc.variablehealth.system.BaseSystem;
import com.popupmc.variablehealth.system.System;
import org.jetbrains.annotations.NotNull;

public class Level extends BaseSystem {
    public Level(int maxLevel,
                 int lowLevelPercent,
                 int powerLevelCap,
                 int averageRadius,
                 int levelNoiseMaxPercent,
                 @NotNull System system,
                 @NotNull VariableHealth plugin) {
        super(system, plugin);

        this.meta = new LevelMeta(system, plugin);
        this.average = new LevelAverage(powerLevelCap, averageRadius, levelNoiseMaxPercent, system, plugin);
        this.data = new LevelData(maxLevel, lowLevelPercent, system, plugin);
    }

    public final LevelMeta meta;
    public final LevelAverage average;
    public final LevelData data;
}
