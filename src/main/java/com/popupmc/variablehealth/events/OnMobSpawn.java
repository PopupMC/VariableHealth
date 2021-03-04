package com.popupmc.variablehealth.events;

import com.destroystokyo.paper.event.entity.EntityAddToWorldEvent;
import com.popupmc.variablehealth.mob.Mob;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.world.ChunkLoadEvent;

public class OnMobSpawn implements Listener {

    // Apparently spawning code is pretty crappy
    // So we have to cover seberal events

    // Only called on chunk loads, but covers all entities
    @EventHandler(priority = EventPriority.NORMAL)
    public void onChunkLoad(ChunkLoadEvent event) {
        for(Entity entity : event.getChunk().getEntities()) {
            if(!Mob.doesApply(entity))
                continue;

            Mob.setup(entity);
        }
    }

    // Only called on new mob spawns, but only covers hostile mobs and squids for some weird reason
    @EventHandler(priority = EventPriority.NORMAL)
    public void onMobSpawn(EntitySpawnEvent event) {
        if(!Mob.doesApply(event.getEntity()))
            return;

        Mob.setup(event.getEntity());
    }

    // Only call on new chunk loads but may also be called for mob spawners and may also be called for passive mobs in
    // loaded chunks although the latter two are unverified
    @EventHandler(priority = EventPriority.NORMAL)
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        if(!Mob.doesApply(event.getEntity()))
            return;

        Mob.setup(event.getEntity());
    }

    // This is supposedly called whenever any entity is added to the world, its very specific to Paper only
    // Because Im unsure how accurate this is Im leaving the others above
    @EventHandler(priority = EventPriority.NORMAL)
    public void onCreatureSpawn(EntityAddToWorldEvent event) {
        if(!Mob.doesApply(event.getEntity()))
            return;

        Mob.setup(event.getEntity());
    }

    // If the above doesnt work on passive mobs then theres nothing we can do for passive mobs
}
