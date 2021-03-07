package com.popupmc.variablehealth.commands;

import com.popupmc.variablehealth.BaseClass;
import com.popupmc.variablehealth.VariableHealth;
import com.popupmc.variablehealth.utility.MathTools;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.jetbrains.annotations.NotNull;

public class OnMobRangeCommand extends BaseClass implements CommandExecutor {
    public OnMobRangeCommand(@NotNull VariableHealth plugin) {
        super(plugin);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(!(commandSender instanceof Player))
            return false;

        Player p = (Player)commandSender;

        if(!p.getWorld().getName().startsWith("insanity")) {
            p.sendMessage(ChatColor.GOLD + "You're not in an insanity world, mobs don't have levels around you");
            return false;
        }

        Objective pwrLevelObj = p.getScoreboard().getObjective("mcmmo_pwrlvl");
        if(pwrLevelObj == null) {
            p.sendMessage("You have no power level, mobs will have random levels around you");
            return true;
        }

        int powerLevelPlayer = pwrLevelObj.getScore(p.getName()).getScore();
        int powerLevelAverage = plugin.system.level.average.getPowerLevelAverage(p.getLocation());

        if(powerLevelAverage > -1)
            statsWAvg(p, powerLevelPlayer, powerLevelAverage);
        else
            statsWOAvg(p, powerLevelPlayer);

        return true;
    }

    public void statsWAvg(Player p, int powerLevelPlayer, int powerLevelAverage) {
        int baseLevelPlayer =
                (int)MathTools.clamp((double)powerLevelPlayer / (double)plugin.system.level.average.powerLevelPerLevel,
                        1,
                        plugin.system.level.data.maxLevel);

        int baseLevelAverage =
                (int)MathTools.clamp((double)powerLevelAverage / (double)plugin.system.level.average.powerLevelPerLevel,
                        1,
                        plugin.system.level.data.maxLevel);

        int maxLevelNoisePlayer = (int)(baseLevelPlayer * (plugin.system.level.average.levelNoiseMaxPercent * 0.01));
        int maxLevelNoiseAverage = (int)(baseLevelAverage * (plugin.system.level.average.levelNoiseMaxPercent * 0.01));

        int minLevelPlayer =
                (int)MathTools.clamp(baseLevelPlayer - maxLevelNoisePlayer, 1, plugin.system.level.data.maxLevel);
        int maxLevelPlayer =
                (int)MathTools.clamp(baseLevelPlayer + maxLevelNoisePlayer, 1, plugin.system.level.data.maxLevel);

        int minLevelAverage =
                (int)MathTools.clamp(baseLevelAverage - maxLevelNoiseAverage, 1, plugin.system.level.data.maxLevel);
        int maxLevelAverage =
                (int)MathTools.clamp(baseLevelAverage + maxLevelNoiseAverage, 1, plugin.system.level.data.maxLevel);

        p.sendMessage(ChatColor.translateAlternateColorCodes('&',
                "&6Your Power Level: &e" + powerLevelPlayer + " &6(Level &e" + minLevelPlayer + "&6 to &e" + maxLevelPlayer));

        p.sendMessage(ChatColor.translateAlternateColorCodes('&',
                "&6Area Power Level: &e" + powerLevelAverage + " &6(Level &e" + minLevelAverage + " &6to &e" + maxLevelAverage));
    }

    public void statsWOAvg(Player p, int powerLevelPlayer) {
        int baseLevelPlayer =
                (int)MathTools.clamp((double)powerLevelPlayer / (double)plugin.system.level.average.powerLevelPerLevel,
                        1,
                        plugin.system.level.data.maxLevel);

        int maxLevelNoisePlayer = (int)(baseLevelPlayer * (plugin.system.level.average.levelNoiseMaxPercent * 0.01));

        int minLevelPlayer =
                (int)MathTools.clamp(baseLevelPlayer - maxLevelNoisePlayer, 1, plugin.system.level.data.maxLevel);

        int maxLevelPlayer =
                (int)MathTools.clamp(baseLevelPlayer + maxLevelNoisePlayer, 1, plugin.system.level.data.maxLevel);

        p.sendMessage(ChatColor.translateAlternateColorCodes('&',
                "&6Your Power Level: &e" + powerLevelPlayer + " &6(Level &e" + minLevelPlayer + "&6 to &e" + maxLevelPlayer));

        p.sendMessage(ChatColor.translateAlternateColorCodes('&',
                "&6Area Power Level: &eUndetermined, mobs will be random level"));
    }
}
