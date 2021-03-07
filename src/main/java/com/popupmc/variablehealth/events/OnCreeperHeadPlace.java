package com.popupmc.variablehealth.events;

import com.popupmc.variablehealth.BaseClass;
import com.popupmc.variablehealth.VariableHealth;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

public class OnCreeperHeadPlace extends BaseClass implements Listener {
    public OnCreeperHeadPlace(@NotNull VariableHealth plugin) {
        super(plugin);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onCreeperHeadPlace(BlockPlaceEvent event) {

        if(event.isCancelled())
            return;

        if(plugin.heads.creeper.placePoweredCreeperHead(event.getBlock().getLocation(), event.getItemInHand())) {
            // Remove placed block on next tick
            Location location = event.getBlock().getLocation().clone();
            new BukkitRunnable() {
                @Override
                public void run() {
                    location.getWorld().getBlockAt(location).setType(Material.AIR);
                }
            }.runTaskLater(plugin, 1);
        }
    }
}
