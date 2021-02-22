package com.popupmc.variablehealth.mob;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.Collection;

public class MobBoss {
    public static void displayBossTitle(Entity entity, int level) {
        // Get all players within a hundred radius of boss and send title update
        Collection<Player> players = entity.getLocation().getNearbyPlayers(100, 255);

        for(Player player : players) {
            player.sendTitle("Boss Battle", "Level " + level, 10, 60, 20);
        }
    }
}
