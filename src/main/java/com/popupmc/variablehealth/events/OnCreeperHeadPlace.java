package com.popupmc.variablehealth.events;

import com.popupmc.variablehealth.VariableHealth;
import com.popupmc.variablehealth.mob.CreeperHead;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class OnCreeperHeadPlace implements Listener {
    @EventHandler(priority = EventPriority.NORMAL)
    public void onCreeperHeadPlace(BlockPlaceEvent event) {

        if(event.isCancelled() || !CreeperHead.isPoweredCreeperHead(event.getItemInHand()))
            return;

        // Place creeper
        CreeperHead.placePoweredCreeperHead(event.getBlock().getLocation(), event.getItemInHand(), VariableHealth.plugin);

        // Remove placed block on next tick
        Location location = event.getBlock().getLocation().clone();
        new BukkitRunnable() {
            @Override
            public void run() {
                location.getWorld().getBlockAt(location).setType(Material.AIR);
            }
        }.runTaskLater(VariableHealth.plugin, 1);
    }
}
