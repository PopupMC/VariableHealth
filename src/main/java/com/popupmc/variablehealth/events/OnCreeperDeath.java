package com.popupmc.variablehealth.events;

import com.popupmc.variablehealth.mob.CreeperHead;
import com.popupmc.variablehealth.mob.Mob;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class OnCreeperDeath implements Listener {
    @EventHandler(priority = EventPriority.NORMAL)
    public void onCreeperDeath(EntityDeathEvent event) {
        if(event.isCancelled() || !Mob.doesApply(event.getEntity()))
            return;

        if(CreeperHead.doCreeperHeadDrop(event.getEntity()))
            CreeperHead.dropCreeperHead(event.getEntity());
    }
}
