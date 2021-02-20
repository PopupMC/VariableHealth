package com.popupmc.variablehealth;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Random;

public class VariableHealth extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);

        // Log enabled status
        getLogger().info("VariableHealth is enabled.");

        for(EntityType type : EntityTypesToAdd.typestoAdd) {
            validTypes.put(type, true);
        }
    }

    // Log disabled status
    @Override
    public void onDisable() {
        getLogger().info("VariableHealth is disabled");
    }

    // Lets go after all the other plugins
    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntitySpawn(EntitySpawnEvent event) {

        // Stop here if not valid entity type, event is canceled, or isn't a living entity
        // The ordering here is important, they are ordered based on speed
        // isCanceled is quickest, HashMap is next quickest, and dynamic type checking is 3rd quickest
        // The or operator will stop on the first false so we want quickest first
        if(event.isCancelled() ||
                !validTypes.containsKey(event.getEntityType()) ||
                !(event.getEntity() instanceof LivingEntity))
            return;

        LivingEntity entity = (LivingEntity)event.getEntity();

        // Gets a number between 1-200
        int healthPercentChange = random.nextInt(200 + 1) + 1;

        // 15% chance of adding an additional 1-100
        if(random.nextInt(100 + 1) <= 15) {
            healthPercentChange +=  random.nextInt(100 + 1) + 1;
        }

        // 5% chance of adding an additional 1-200
        if(random.nextInt(100 + 1) <= 5) {
            healthPercentChange +=  random.nextInt(200 + 1) + 1;
        }

        // Convert to an actual percent
        float percentChance = healthPercentChange * 0.01f;

        // Calculate new health
        double newHealth = entity.getHealth() * percentChance;

        // If above max health, set to max health
        if(newHealth > entity.getMaxHealth())
            entity.setMaxHealth(newHealth);

        // Change entities health
        entity.setHealth(newHealth);
    }

    // This may look silly, an ArrayList seems better
    // However we need speed above all else, nothing is faster than a HashMap
    // We don't need to iterate over a long array list every single time we need to check something
    public static final HashMap<EntityType,Boolean> validTypes = new HashMap<>();

    public static final Random random = new Random();
}
