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
import org.bukkit.entity.ZombieHorse;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SpawnZombieHorse extends BaseSystem {
    public SpawnZombieHorse(@NotNull System system, @NotNull VariableHealth plugin) {
        super(system, plugin);
    }

    public void checkSpawn(LivingEntity entity) {
        if(entity.getType() == EntityType.ZOMBIE && RandomTools.getRandomChanceDown(15)) {
            Location location = entity.getLocation().clone();

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
        }
    }
}
