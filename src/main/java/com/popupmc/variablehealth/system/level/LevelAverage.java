package com.popupmc.variablehealth.system.level;

import com.popupmc.variablehealth.VariableHealth;
import com.popupmc.variablehealth.system.BaseSystem;
import com.popupmc.variablehealth.system.System;
import com.popupmc.variablehealth.utility.LocationTools;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class LevelAverage extends BaseSystem {
    public LevelAverage(int powerLevelCap,
                        int averageRadius,
                        int levelNoiseMaxPercent,
                        @NotNull Level parent,
                        @NotNull System system,
                        @NotNull VariableHealth plugin) {
        super(system, plugin);

        this.powerLevelCap = powerLevelCap;
        this.averageRadius = averageRadius;
        this.levelNoiseMaxPercent = levelNoiseMaxPercent;

        powerLevelPerLevel = (int)Math.ceil((double)powerLevelCap / parent.data.maxLevel);
    }

    // Gets the average power level in an area
    public int getPowerLevelAverage(Location location) {
        List<Player> players = LocationTools.getPlayers(location, averageRadius, false);

        int powerLevel = 0;
        int count = players.size();

        for(Player player : players) {
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

    public final int powerLevelCap;

    // 1 Mob Level = X amount of power levels
    public final int powerLevelPerLevel;

    // Radius to look for and average level of nearby players
    public final int averageRadius;

    // Max level variance
    public final int levelNoiseMaxPercent;
}
