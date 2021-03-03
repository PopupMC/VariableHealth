package com.popupmc.variablehealth.events;

import com.popupmc.variablehealth.mob.Mob;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

public class OnMobSpawn implements Listener {
    @EventHandler(priority = EventPriority.NORMAL)
    public void onMobSpawn(EntitySpawnEvent event) {
        if(!Mob.doesApply(event.getEntity()))
            return;

        Mob.setup(event.getEntity());
    }
}
