package com.popupmc.variablehealth.utility;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class LocationTools {
    public static List<Player> getPlayers(Location location, int radius, boolean includeHidden) {
        Collection<Player> players = location.getWorld().getNearbyPlayers(location, radius, 255);
        ArrayList<Player> ret = new ArrayList<>();

        for(Player player : players) {
            if(!includeHidden && (player.getGameMode() == GameMode.SPECTATOR || player.isOp())) {
                continue;
            }

            ret.add(player);
        }

        return ret;
    }
}
