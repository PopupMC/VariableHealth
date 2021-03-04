package com.popupmc.variablehealth.mob;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.Collection;

public class MobBoss {
    public static void displayBossTitle(Entity entity, int level) {
        // Get all players within a hundred radius of boss and send title update
        Collection<Player> players = entity.getLocation().getNearbyPlayers(100, 255);

        StringBuilder names = new StringBuilder();

        for(Player player : players) {
            names.append(player.getDisplayName()).append(" ");
            player.sendTitle("Boss Battle", "Level " + level, 10, 60, 20);
        }

        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "discord broadcast **Level " +
                level + " " +
                Mob.readableMobName(entity.getType()) + " " +
                "Boss Battle** started by: " + names.toString());

        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "broadcast " + ChatColor.BOLD + "Level " +
                level + " " +
                Mob.readableMobName(entity.getType()) + " " +
                "Boss Battle" + ChatColor.RESET + " started by: " + names.toString());
    }
}
