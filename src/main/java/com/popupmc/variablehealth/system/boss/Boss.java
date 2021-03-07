package com.popupmc.variablehealth.system.boss;

import com.popupmc.variablehealth.VariableHealth;
import com.popupmc.variablehealth.system.BaseSystem;
import com.popupmc.variablehealth.system.System;
import com.popupmc.variablehealth.utility.LocationTools;
import com.popupmc.variablehealth.utility.StringTools;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Boss extends BaseSystem {
    public Boss(int bossPlayerRadius,
                @NotNull System system,
                @NotNull VariableHealth plugin) {
        super(system, plugin);

        this.bossPlayerRadius = bossPlayerRadius;
    }

    public void displayBossTitle(Entity entity, int level) {
        // Get all players within a hundred radius of boss and send title update
        List<Player> players = LocationTools.getPlayers(entity.getLocation(), 100, false);

        StringBuilder names = new StringBuilder();

        for(Player player : players) {
            names.append(player.getDisplayName()).append(" ");
            player.sendTitle("Boss Battle", "Level " + level, 10, 60, 20);
        }

        executeBossCommands(entity, level, names.toString());
    }

    public void executeBossCommands(Entity entity, int level, String playerNames) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "discord broadcast **Level " +
                level + " " +
                StringTools.readableMobName(entity.getType()) + " " +
                "Boss Battle** started by: " + playerNames);

        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "broadcast " + ChatColor.BOLD + "Level " +
                level + " " +
                StringTools.readableMobName(entity.getType()) + " " +
                "Boss Battle" + ChatColor.RESET + " started by: " + playerNames);
    }

    public final int bossPlayerRadius;
}
