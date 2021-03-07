package com.popupmc.variablehealth.utility;

import com.popupmc.variablehealth.VariableHealth;

public class RandomTools {
    public static boolean getRandomChanceUp(int val) {
        return (VariableHealth.random.nextInt(100) + 1) >= val;
    }

    public static boolean getRandomChanceDown(int val) {
        return (VariableHealth.random.nextInt(100) + 1) <= val;
    }

    public static boolean getCoinFlip() {
        return getRandomChanceUp(50);
    }

    public static int getRandomRange0Exclusive(int max) {
        return VariableHealth.random.nextInt(max);
    }

    public static int getRandomRange0Inclusive(int max) {
        return VariableHealth.random.nextInt(max + 1);
    }

    public static int getRandomRange1(int max) {
        return VariableHealth.random.nextInt(max) + 1;
    }

    public static double addNoise(double val, double percent) {

        int valPercent = (int)(val * (percent * 0.01));
        valPercent = getRandomRange0Inclusive(valPercent);

        if(getCoinFlip())
            return val + valPercent;

        return val - valPercent;
    }

    public static double addNoise(double val) {
        return addNoise(val, .10d);
    }

    public static double addNoiseClamp(double val, double percent, double min, double max) {
        return MathTools.clamp(addNoise(val, percent), min, max);
    }

    public static double addNoiseClamp(double val, double min, double max) {
        return MathTools.clamp(addNoise(val), min, max);
    }
}
