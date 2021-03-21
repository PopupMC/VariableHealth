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
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SpawnIllusioner extends BaseSystem {
    public SpawnIllusioner(@NotNull System system, @NotNull VariableHealth plugin) {
        super(system, plugin);
    }

    public void checkSpawn(LivingEntity entity) {
        if((entity.getType() == EntityType.EVOKER ||
                entity.getType() == EntityType.VINDICATOR ||
                entity.getType() == EntityType.PILLAGER) &&
                RandomTools.getRandomChanceDown(15)) {

            Location location = entity.getLocation().clone();

            new BukkitRunnable() {
                @Override
                public void run() {
                    List<Entity> entityList = LocationTools.getEntitiesOfType(
                            location, 100, EntityType.ILLUSIONER);

                    if(entityList.size() >= 4)
                        return;

                    location.getWorld().spawnEntity(location, EntityType.ILLUSIONER);
                }
            }.runTaskLater(plugin, 20);
        }
    }
}
