package com.popupmc.variablehealth.system.scaling;

import com.popupmc.variablehealth.VariableHealth;
import com.popupmc.variablehealth.system.BaseSystem;
import com.popupmc.variablehealth.system.System;
import com.popupmc.variablehealth.utility.MathTools;
import com.popupmc.variablehealth.utility.RandomTools;
import org.jetbrains.annotations.NotNull;

public class ScaleBasics extends BaseSystem {
    public ScaleBasics(int maxScalePercent,
                       int scaleGranularityCount,
                       @NotNull System system,
                       @NotNull VariableHealth plugin) {
        super(system, plugin);

        this.scaleMax = MathTools.createEasyDivider(maxScalePercent, system.level.data.maxLevel);
        this.scaleGranularityCount = scaleGranularityCount;
        this.scaleGranularityAmount = system.level.data.maxLevel / scaleGranularityCount;
    }

    public double genericScale(double val, int level, boolean increaseOnly) {

        // Used to countdown levels left
        // Ideally we want a random number for each level
        // But that's computationally expensive
        // So instead we do short bursts, this keeps track of that
        int levelLeft = level;

        // final product
        int percentChange = 0;

        // Begin looping our predefined granularity
        for(int i = 0; i < scaleGranularityCount; i++) {

            // Get a number between 1 and our max genericScale
            int tmp = RandomTools.getRandomRange1(scaleMax);

            // If the short burst is greater than levels left, don't do a short burst, do the remainder levels
            // and stop here
            if(scaleGranularityAmount > levelLeft) {
                percentChange += (tmp * levelLeft);
                break;
            }
            // Otherwise do the full short burst and subtract burst done from levels left
            else {
                percentChange += (tmp * scaleGranularityAmount);
                levelLeft -= scaleGranularityAmount;
            }
        }

        if(increaseOnly)
            percentChange += 100;

        // Convert to an actual percent
        double percentChangeFloat = percentChange * 0.01;

        return val * percentChangeFloat;
    }

    public final int scaleMax;
    public final int scaleGranularityCount;
    public final int scaleGranularityAmount;
}
