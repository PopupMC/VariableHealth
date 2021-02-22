package com.popupmc.variablehealth.events;

import com.popupmc.variablehealth.mob.Mob;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

public class OnMobSpawn implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onMobSpawn(EntitySpawnEvent event) {
        if(event.isCancelled() || !Mob.doesApply(event.getEntity()))
            return;

        Mob.setup(event.getEntity());
    }
}
