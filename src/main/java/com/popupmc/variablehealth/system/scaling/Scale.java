package com.popupmc.variablehealth.system.scaling;

import com.popupmc.variablehealth.VariableHealth;
import com.popupmc.variablehealth.system.BaseSystem;
import com.popupmc.variablehealth.system.System;
import org.jetbrains.annotations.NotNull;

public class Scale extends BaseSystem {
    public Scale(int maxScalePercent,
                 int scaleGranularityCount,
                 int statNoiseMaxPercent,
                 @NotNull System system,
                 @NotNull VariableHealth plugin) {
        super(system, plugin);

        this.basics = new ScaleBasics(maxScalePercent, scaleGranularityCount, system, plugin);
        this.stats = new ScaleStats(statNoiseMaxPercent, system, plugin);
    }

    public final ScaleBasics basics;
    public final ScaleStats stats;
}
