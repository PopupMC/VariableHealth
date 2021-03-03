package com.popupmc.variablehealth.commands;

import com.popupmc.variablehealth.VariableHealth;
import com.popupmc.variablehealth.mob.MobLevel;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.jetbrains.annotations.NotNull;

public class OnMobRangeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(!(commandSender instanceof Player))
            return false;

        Player p = (Player)commandSender;

        Objective pwrLevelObj = p.getScoreboard().getObjective("mcmmo_pwrlvl");
        if(pwrLevelObj == null) {
            p.sendMessage("You have no power level, mobs will have random levels around you");
            return true;
        }

        int powerLevelPlayer = pwrLevelObj.getScore(p.getName()).getScore();
        int powerLevelAverage = MobLevel.getPowerLevelAverage(p.getLocation());

        if(powerLevelAverage > -1)
            statsWAvg(p, powerLevelPlayer, powerLevelAverage);
        else
            statsWOAvg(p, powerLevelPlayer);

        return true;
    }

    public void statsWAvg(Player p, int powerLevelPlayer, int powerLevelAverage) {
        int baseLevelPlayer = clamp(powerLevelPlayer / MobLevel.powerLevelPerLevel, 1, MobLevel.maxLevel);
        int baseLevelAverage = clamp(powerLevelAverage / MobLevel.powerLevelPerLevel, 1, MobLevel.maxLevel);

        int maxLevelNoisePlayer = (int)(baseLevelPlayer * (MobLevel.noiseMaxPercent * 0.01));
        int maxLevelNoiseAverage = (int)(baseLevelAverage * (MobLevel.noiseMaxPercent * 0.01));

        int minLevelPlayer = clamp(baseLevelPlayer - maxLevelNoisePlayer, 1, MobLevel.maxLevel);
        int maxLevelPlayer = clamp(baseLevelPlayer + maxLevelNoisePlayer, 1, MobLevel.maxLevel);

        int minLevelAverage = clamp(baseLevelAverage - maxLevelNoiseAverage, 1, MobLevel.maxLevel);
        int maxLevelAverage = clamp(baseLevelAverage + maxLevelNoiseAverage, 1, MobLevel.maxLevel);

        p.sendMessage(ChatColor.translateAlternateColorCodes('&',
                "&6Your Power Level: &e" + powerLevelPlayer + " &6(Level &e" + minLevelPlayer + "&6 to &e" + maxLevelPlayer));

        p.sendMessage(ChatColor.translateAlternateColorCodes('&',
                "&6Area Power Level: &e" + powerLevelAverage + " &6(Level &e" + minLevelAverage + " &6to &e" + maxLevelAverage));
    }

    public int clamp(int val, int min, int max) {
        if(val < min)
            val = min;
        else if(val > max)
            val = max;

        return val;
    }

    public void statsWOAvg(Player p, int powerLevelPlayer) {
        int baseLevelPlayer = clamp(powerLevelPlayer / MobLevel.powerLevelPerLevel, 1, MobLevel.maxLevel);

        int maxLevelNoisePlayer = (int)(baseLevelPlayer * (MobLevel.noiseMaxPercent * 0.01));

        int minLevelPlayer = clamp(baseLevelPlayer - maxLevelNoisePlayer, 1, MobLevel.maxLevel);
        int maxLevelPlayer = clamp(baseLevelPlayer + maxLevelNoisePlayer, 1, MobLevel.maxLevel);

        p.sendMessage(ChatColor.translateAlternateColorCodes('&',
                "&6Your Power Level: &e" + powerLevelPlayer + " &6(Level &e" + minLevelPlayer + "&6 to &e" + maxLevelPlayer));

        p.sendMessage(ChatColor.translateAlternateColorCodes('&',
                "&6Area Power Level: &eUndetermined, mobs will be random level"));
    }
}
