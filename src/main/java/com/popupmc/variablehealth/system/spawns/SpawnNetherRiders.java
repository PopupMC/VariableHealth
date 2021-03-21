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

public class SpawnNetherRiders extends BaseSystem {
    public SpawnNetherRiders(@NotNull System system, @NotNull VariableHealth plugin) {
        super(system, plugin);
    }

    public void checkSpawn(LivingEntity entity) {
        if((entity.getType() == EntityType.PIGLIN ||
                entity.getType() == EntityType.PIGLIN_BRUTE) && RandomTools.getRandomChanceDown(8)) {
            Location location = entity.getLocation().clone();

            new BukkitRunnable() {
                @Override
                public void run() {
                    List<Entity> entityList = LocationTools.getEntitiesOfType(
                            location, 100, EntityType.HOGLIN);

                    if(entityList.size() >= 4)
                        return;

                    Hoglin horse = (Hoglin)location.getWorld().spawnEntity(location, EntityType.HOGLIN);
                    horse.addPassenger(entity);
                }
            }.runTaskLater(plugin, 20);
        }

        if((entity.getType() == EntityType.SKELETON ||
                entity.getType() == EntityType.WITHER_SKELETON) && RandomTools.getRandomChanceDown(8)) {
            Location location = entity.getLocation().clone();

            new BukkitRunnable() {
                @Override
                public void run() {
                    List<Entity> entityList = LocationTools.getEntitiesOfType(
                            location, 100, EntityType.SKELETON_HORSE);

                    if(entityList.size() >= 4)
                        return;

                    SkeletonHorse horse = (SkeletonHorse)location.getWorld().spawnEntity(location, EntityType.SKELETON_HORSE);
                    horse.setTrap(true);
                    horse.addPassenger(entity);
                }
            }.runTaskLater(plugin, 20);
        }

        if((entity.getType() == EntityType.ZOMBIFIED_PIGLIN) && RandomTools.getRandomChanceDown(8)) {
            Location location = entity.getLocation().clone();

            if(RandomTools.getCoinFlip())
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        List<Entity> entityList = LocationTools.getEntitiesOfType(
                                location, 100, EntityType.ZOMBIE_HORSE);

                        if(entityList.size() >= 4)
                            return;

                        ZombieHorse horse = (ZombieHorse)location.getWorld().spawnEntity(location, EntityType.ZOMBIE_HORSE);
                        horse.addPassenger(entity);
                    }
                }.runTaskLater(plugin, 20);
            else
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        List<Entity> entityList = LocationTools.getEntitiesOfType(
                                location, 100, EntityType.HOGLIN);

                        if(entityList.size() >= 4)
                            return;

                        Hoglin horse = (Hoglin)location.getWorld().spawnEntity(location, EntityType.HOGLIN);
                        horse.addPassenger(entity);
                    }
                }.runTaskLater(plugin, 20);
        }
    }
}
