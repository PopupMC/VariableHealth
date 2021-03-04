package com.popupmc.variablehealth.events;

import com.popupmc.variablehealth.lists.BossMobs;
import com.popupmc.variablehealth.mob.Mob;
import com.popupmc.variablehealth.mob.MobLevel;
import com.popupmc.variablehealth.mob.MobScaling;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class OnMobDeath implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onMobDeath(EntityDeathEvent event) {
        // Only on applicable mobs
        if(!Mob.doesApply(event.getEntity()))
            return;

        // Get entity
        LivingEntity entity = event.getEntity();

        // Get level
        int level;
        if(MobLevel.hasLevel(entity))
            level = MobLevel.retrieveLevel(entity);
        else
            level = MobLevel.getLevel(entity.getLocation());

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
        xp = Math.max(MobScaling.addNoise((int)MobScaling.scale(xp, level, isBoss)), 1);

        // Remove normal exp drops
        // Theres some plugin thats blocking normal exp drops, idk why yet
        event.setDroppedExp(0);

        // Get exp drop location, just the entity location unless ender dragon where we pick a custom location
        // We do this because, inconviniently, the enderdragon drops most of it in the end portal
        Location expDropLocation = entity.getLocation();
        if(entity.getType() == EntityType.ENDER_DRAGON)
            expDropLocation = new Location(Bukkit.getWorld("main_the_end"), -6, 64, 0);

        // Drop exp orbs
        ExperienceOrb orbs = (ExperienceOrb)event.getEntity().getWorld().spawnEntity(expDropLocation, EntityType.EXPERIENCE_ORB);
        orbs.setExperience(xp);

        if(isBoss) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "discord broadcast **The level " +
                    level + " " +
                    Mob.readableMobName(entity.getType()) + " " +
                    "was defeated**, reward is " + xp + "xp");

            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "broadcast " + ChatColor.BOLD + "The level " +
                    level + " " +
                    Mob.readableMobName(entity.getType()) + " " +
                    "was defeated" + ChatColor.RESET + ", reward is " + xp + "xp");
        }
    }
}
