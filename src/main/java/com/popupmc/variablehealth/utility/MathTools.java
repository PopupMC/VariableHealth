package com.popupmc.variablehealth.utility;

public class MathTools {
    public static double clamp(double val, double min, double max) {
        if(val > max)
            val = max;
        else if(val < min)
            val = min;

        return val;
    }

    public static int createEasyDivider(int toBeDividedAgainst, int maxVal) {
        return (int)Math.ceil((double)toBeDividedAgainst / maxVal);
    }

    public static int getPotionLevel(int level) {
        return (int)clamp(level - 1, 0, Integer.MAX_VALUE);
    }
}
