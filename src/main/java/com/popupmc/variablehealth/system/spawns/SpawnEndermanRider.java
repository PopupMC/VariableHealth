package com.popupmc.variablehealth.system.spawns;

import com.popupmc.variablehealth.VariableHealth;
import com.popupmc.variablehealth.system.BaseSystem;
import com.popupmc.variablehealth.system.System;
import com.popupmc.variablehealth.utility.LocationTools;
import com.popupmc.variablehealth.utility.RandomTools;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Shulker;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SpawnEndermanRider extends BaseSystem {
    public SpawnEndermanRider(@NotNull System system, @NotNull VariableHealth plugin) {
        super(system, plugin);
    }

    public void checkSpawn(LivingEntity entity) {
        if((entity.getType() == EntityType.ENDERMAN) && RandomTools.getRandomChanceDown(8)) {
            Location location = entity.getLocation().clone();

            new BukkitRunnable() {
                @Override
                public void run() {
                    List<Entity> entityList = LocationTools.getEntitiesOfType(
                            location, 100, EntityType.SHULKER);

                    if(entityList.size() >= 4)
                        return;

                    Shulker passenger = (Shulker)location.getWorld().spawnEntity(location, EntityType.SHULKER);
                    entity.addPassenger(passenger);
                }
            }.runTaskLater(plugin, 20);
        }
    }
}
