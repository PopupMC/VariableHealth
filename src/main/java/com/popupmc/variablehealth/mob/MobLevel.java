package com.popupmc.variablehealth.mob;

import com.popupmc.variablehealth.VariableHealth;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.scoreboard.Objective;

import java.util.Collection;
import java.util.List;

public class MobLevel {
    public static void storeLevel(LivingEntity livingEntity, int level) {
        livingEntity.setMetadata(metadataKeyLevel, new FixedMetadataValue(VariableHealth.plugin, level));
    }

    public static boolean hasLevel(LivingEntity livingEntity) {
        return livingEntity.hasMetadata(metadataKeyLevel);
    }

    public static int retrieveLevel(LivingEntity livingEntity) {
        int level = 1;
        if(livingEntity.hasMetadata(metadataKeyLevel)) {
            List<MetadataValue> values = livingEntity.getMetadata(metadataKeyLevel);
            if(values.size() > 0) {
                level = values.get(0).asInt();
            }
        }

        return level;
    }

    public static int percentOfMax(float percent) {
        return (int)(maxLevel * percent);
    }

    public static int getLevel(Location location) {
        int powerLevelAverage = Math.max(getPowerLevelAverage(location), 1);

        if(powerLevelAverage <= -1)
            return VariableHealth.random.nextInt(maxLevel) + 1;

        int level = powerLevelAverage / powerLevelPerLevel;
        level = MobScaling.addNoise(level, noiseMaxPercent);

        if(level > maxLevel)
            level = maxLevel;
        else if(level < 1)
            level = 1;

        return level;
    }

    // Gets the average power level in an area
    public static int getPowerLevelAverage(Location location) {
        Collection<Player> players = location.getWorld().getNearbyPlayers(location, averageRadius, 255);

        int powerLevel = 0;
        int count = players.size();

        for(Player player : players) {
            if(player.getGameMode() == GameMode.SPECTATOR || player.isOp()) {
                count -= 1;
                continue;
            }

            Objective pwrLevelObj = player.getScoreboard().getObjective("mcmmo_pwrlvl");
            if(pwrLevelObj == null)
                continue;

            powerLevel += pwrLevelObj.getScore(player.getName()).getScore();
        }

        if(count > 0 && powerLevel > 0) {
            powerLevel /= count;
        }
        else if(count <= 0) {
            return -1;
        }

        return Math.min(powerLevel, powerLevelCap);
    }

    public static final String metadataKeyPrefix = "com.popupmc.variablehealth.VariableHealth:";
    public static final String metadataKeyLevel = metadataKeyPrefix + "level";

    // Max Level
    public static final int maxLevel = 100;

    // Power Level Cap
    public static final int powerLevelCap = 1500;

    // 1 Mob Level = X amount of power levels
    public static final int powerLevelPerLevel = (int)Math.ceil((double)powerLevelCap / maxLevel);

    // Radius to look for and average level of nearby players
    public static final int averageRadius = 75;

    // Max level variance
    public static final int noiseMaxPercent = 25;
}
