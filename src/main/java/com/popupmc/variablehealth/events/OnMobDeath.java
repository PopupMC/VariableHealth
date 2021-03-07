package com.popupmc.variablehealth.events;

import com.popupmc.variablehealth.BaseClass;
import com.popupmc.variablehealth.VariableHealth;
import com.popupmc.variablehealth.lists.BossMobs;
import com.popupmc.variablehealth.utility.RandomTools;
import com.popupmc.variablehealth.utility.StringTools;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.jetbrains.annotations.NotNull;

public class OnMobDeath extends BaseClass implements Listener {
    public OnMobDeath(@NotNull VariableHealth plugin) {
        super(plugin);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onMobDeath(EntityDeathEvent event) {
        // Only on applicable mobs
        if(!plugin.system.doesApply(event.getEntity()))
            return;

        if(!event.getEntity().getWorld().getName().startsWith("insanity"))
            return;

        // Get entity
        LivingEntity entity = event.getEntity();

        // Get level
        int level;
        if(plugin.system.level.meta.hasLevel(entity))
            level = plugin.system.level.meta.retrieveLevel(entity);
        else
            level = plugin.system.level.data.getAverageOrRandomLevel(entity.getLocation());

        // Get boss or not
        boolean isBoss = BossMobs.hashList.containsKey(entity.getType());

        // Get XP
        int xp = event.getDroppedExp();

        // Re-adjust if boss to custom baselines
        if(entity.getType() == EntityType.ENDER_DRAGON)
            xp = 6000;
        else if(entity.getType() == EntityType.WITHER)
            xp = 100;

        // Add noise with a minimum of 1
        xp = (int)RandomTools.addNoiseClamp(
                plugin.system.scale.basics.genericScale(xp, level, isBoss), 1, (double)Integer.MAX_VALUE);

        event.setDroppedExp(xp);

        if(isBoss) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "discord broadcast **The level " +
                    level + " " +
                    StringTools.readableMobName(entity.getType()) + " " +
                    "was defeated**, reward is " + xp + "xp");

            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "broadcast " + ChatColor.BOLD + "The level " +
                    level + " " +
                    StringTools.readableMobName(entity.getType()) + " " +
                    "was defeated" + ChatColor.RESET + ", reward is " + xp + "xp");
        }
    }
}
