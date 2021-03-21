package com.popupmc.variablehealth.system.spawns;

import com.popupmc.variablehealth.VariableHealth;
import com.popupmc.variablehealth.system.BaseSystem;
import com.popupmc.variablehealth.system.System;
import com.popupmc.variablehealth.utility.LocationTools;
import com.popupmc.variablehealth.utility.RandomTools;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SpawnPhantomRider extends BaseSystem {
    public SpawnPhantomRider(@NotNull System system, @NotNull VariableHealth plugin) {
        super(system, plugin);
    }

    public void checkSpawn(LivingEntity entity) {
        if((entity.getType() == EntityType.PHANTOM) && !entity.getWorld().getName().endsWith("the_end") && RandomTools.getRandomChanceDown(15)) {
            Location location = entity.getLocation().clone();

            new BukkitRunnable() {
                @Override
                public void run() {
                    if(RandomTools.getRandomChanceUp(25)) {
                        List<Entity> entityList = LocationTools.getEntitiesOfType(
                                location, 100, EntityType.SKELETON);

                        if(entityList.size() >= 4)
                            return;

                        Skeleton passenger = (Skeleton)location.getWorld().spawnEntity(location, EntityType.SKELETON);
                        entity.addPassenger(passenger);
                    }
                    else {
                        List<Entity> entityList = LocationTools.getEntitiesOfType(
                                location, 100, EntityType.ILLUSIONER);

                        if(entityList.size() >= 4)
                            return;

                        Illusioner passenger = (Illusioner)location.getWorld().spawnEntity(location, EntityType.ILLUSIONER);
                        entity.addPassenger(passenger);
                    }
                }
            }.runTaskLater(plugin, 20);
        }

        if((entity.getType() == EntityType.ENDERMAN) && RandomTools.getRandomChanceDown(8)) {
            Location location = entity.getLocation().clone();

            new BukkitRunnable() {
                @Override
                public void run() {
                    List<Entity> entityList = LocationTools.getEntitiesOfType(
                            location, 100, EntityType.PHANTOM);

                    if(entityList.size() >= 4)
                        return;

                    Phantom horse = (Phantom) location.getWorld().spawnEntity(location, EntityType.PHANTOM);
                    horse.addPassenger(entity);
                }
            }.runTaskLater(plugin, 20);
        }
    }
}
