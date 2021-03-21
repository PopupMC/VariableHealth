package com.popupmc.variablehealth.system.spawns;

import com.popupmc.variablehealth.VariableHealth;
import com.popupmc.variablehealth.system.BaseSystem;
import com.popupmc.variablehealth.system.System;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class Spawns extends BaseSystem {
    public Spawns(@NotNull System system, @NotNull VariableHealth plugin) {
        super(system, plugin);

        this.spawnDolphinRider = new SpawnDolphinRider(system, plugin);
        this.spawnEndermanRider = new SpawnEndermanRider(system, plugin);
        this.spawnNetherRiders = new SpawnNetherRiders(system, plugin);
        this.spawnIllusioner = new SpawnIllusioner(system, plugin);
        this.spawnPhantomRider = new SpawnPhantomRider(system, plugin);
        this.spawnRabbits = new SpawnRabbits(system, plugin);
        this.spawnZombieHorse = new SpawnZombieHorse(system, plugin);
    }

    public void checkSpawn(LivingEntity entity) {
        spawnDolphinRider.checkSpawn(entity);
        spawnEndermanRider.checkSpawn(entity);
        spawnNetherRiders.checkSpawn(entity);
        spawnIllusioner.checkSpawn(entity);
        spawnPhantomRider.checkSpawn(entity);
        spawnRabbits.checkSpawn(entity);
        spawnZombieHorse.checkSpawn(entity);
    }

    public final SpawnDolphinRider spawnDolphinRider;
    public final SpawnEndermanRider spawnEndermanRider;
    public final SpawnNetherRiders spawnNetherRiders;
    public final SpawnIllusioner spawnIllusioner;
    public final SpawnPhantomRider spawnPhantomRider;
    public final SpawnRabbits spawnRabbits;
    public final SpawnZombieHorse spawnZombieHorse;
}
