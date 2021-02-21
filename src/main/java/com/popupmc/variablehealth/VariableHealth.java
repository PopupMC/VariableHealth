package com.popupmc.variablehealth;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class VariableHealth extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        mob = new Mob(this);

        Bukkit.getPluginManager().registerEvents(this, this);

        // Log enabled status
        getLogger().info("VariableHealth is enabled.");
    }

    // Log disabled status
    @Override
    public void onDisable() {
        getLogger().info("VariableHealth is disabled");
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onMobSpawn(EntitySpawnEvent event) {
        if(event.isCancelled() || !mob.doesApply(event.getEntity()))
            return;

        mob.setup(event.getEntity());
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onMobDamageToOthers(EntityDamageByEntityEvent event) {
        if(event.isCancelled() || !mob.doesApply(event.getDamager()))
            return;

        Double damage = mob.getDamageToOthers(event.getDamager(), event.getDamage());
        if(damage == null)
            return;

        event.setDamage(damage);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onMobDamageToSelf(EntityDamageEvent event) {
        if(event.isCancelled() || !mob.doesApply(event.getEntity()))
            return;

        Double damage = mob.getDamageToSelf(event.getEntity(), event.getDamage());
        if(damage == null)
            return;

        event.setDamage(damage);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onExpDrop(EntityDeathEvent event) {
        if(event.isCancelled() || !mob.doesApply(event.getEntity()))
            return;

        Integer exp = mob.getExpDrop(event.getEntity(), event.getDroppedExp());
        if(exp == null)
            return;

        event.setDroppedExp(exp);
    }

    public Mob mob;
}
