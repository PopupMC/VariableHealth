package com.popupmc.variablehealth.events;

import com.popupmc.variablehealth.BaseClass;
import com.popupmc.variablehealth.VariableHealth;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.jetbrains.annotations.NotNull;

public class OnCreeperDeath extends BaseClass implements Listener {
    public OnCreeperDeath(@NotNull VariableHealth plugin) {
        super(plugin);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onCreeperDeath(EntityDeathEvent event) {
        if(!event.getEntity().getWorld().getName().startsWith("insanity"))
            return;

        plugin.heads.creeper.dropCreeperHead(event.getEntity());
    }
}
