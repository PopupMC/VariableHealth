package com.popupmc.variablehealth.mob;

import com.popupmc.variablehealth.VariableHealth;
import org.bukkit.entity.LivingEntity;

public class MobScaling {
    public static double scale(double val, int level, boolean increaseOnly) {

        // Used to countdown levels left
        // Ideally we want a random number for each level
        // But that's computationally expensive
        // So instead we do short bursts, this keeps track of that
        int levelLeft = level;

        // final product
        int percentChange = 0;

        // Begin looping our predefined granularity
        for(int i = 0; i < scaleGranularityCount; i++) {

            // Get a number between 1 and our max scale
            int tmp = VariableHealth.random.nextInt(scaleMax) + 1;

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

    public static int addNoise(int val) {
        return addNoise(val, noiseMaxPercent);
    }

    public static int addNoise(int val, int percent) {

        int valPercent = (int)(val * (percent * 0.01));
        valPercent = VariableHealth.random.nextInt(valPercent + 1);

        if(VariableHealth.random.nextInt(100 + 1) > 50)
            return val + valPercent;

        return val - valPercent;
    }

    public static void scaleHealth(LivingEntity livingEntity, int level, boolean isBoss) {
        // Get Scaled Health, scale up only if boss
        double health = livingEntity.getMaxHealth();
        health = scale(health, level, isBoss);

        // Minecraft Limit
        if(health > 2560d)
            health = 2560d;
        else if(health < 0.01d)
            health = 0.01d;

        // Set new health
        livingEntity.setMaxHealth(health);
        livingEntity.setHealth(health);
    }

    public static void scaleAir(LivingEntity livingEntity, int level, boolean isBoss) {
        // Get Scaled Health, scale up only if boss
        double air = livingEntity.getMaximumAir();
        air = scale(air, level, isBoss);

        // Minecraft Limit
        if(air > 300d)
            air = 300d;
        else if(air < 1d)
            air = 1d;

        // Set new health
        livingEntity.setMaximumAir((int)air);
        livingEntity.setRemainingAir((int)air);
    }

    public static final int scaleMax = (int)Math.ceil(250 / (double)MobLevel.maxLevel); // Up to 250% max scaling
    public static final int scaleGranularityCount = 15; // 10 granularity count in scaling
    public static final int scaleGranularityAmount = MobLevel.maxLevel / scaleGranularityCount; // How many in each granularity
    public static final int noiseMaxPercent = 10;
}
