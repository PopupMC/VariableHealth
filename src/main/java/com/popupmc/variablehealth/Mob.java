package com.popupmc.variablehealth;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Mob {
    public Mob(VariableHealth plugin) {
        this.plugin = plugin;

        for(EntityType type : NonBossMobs.list) {
            nonBossMobs.put(type, true);
        }

        for(EntityType type : BossMobs.list) {
            bossMobs.put(type, true);
        }
    }

    public boolean doesApply(Entity entity) {
        return nonBossMobs.containsKey(entity.getType()) ||
            bossMobs.containsKey(entity.getType());
    }

    public void setup(Entity entity) {
        if(!(entity instanceof LivingEntity))
            return;

        // Get living instance
        LivingEntity livingEntity = (LivingEntity)entity;

        // Get is boss or not
        boolean isBoss = bossMobs.containsKey(entity.getType());

        // Retrieve or create level level
        int level;
        if(hasLevel(livingEntity))
            level = retrieveLevel(livingEntity);
        else
            level = (isBoss) ? getBossLevel() : getNonBossLevel();

        // Scale Health
        scaleHealth(livingEntity, level, isBoss);

        // Store Level onto entity
        if(!hasLevel(livingEntity))
            storeLevel(livingEntity, level);

        // Display title if boss
        if(isBoss)
            displayBossTitle(entity, level);
    }

    public Double getDamageToOthers(Entity entity, double damage) {
        if(!(entity instanceof LivingEntity))
            return null;

        // Get living instance
        LivingEntity livingEntity = (LivingEntity)entity;

        // Get level
        int level = retrieveLevel(livingEntity);

        // Is Boss
        boolean isBoss = bossMobs.containsKey(entity.getType());

        // Scale damage
        return scale(damage, level, isBoss, false);
    }

    public Double getDamageToSelf(Entity entity, double damage) {
        if(!(entity instanceof LivingEntity))
            return null;

        // Get living instance
        LivingEntity livingEntity = (LivingEntity)entity;

        // Get level
        int level = retrieveLevel(livingEntity);

        // Is Boss
        // boolean isBoss = bossMobs.containsKey(entity.getType());

        // Scale damage, prevent it from scaling below 0.10
        double ret = scale(damage, level, false, true);
        ret = Math.max(ret, 0.10d);

        return ret;
    }

    public Integer getExpDrop(Entity entity, int exp) {
        if(!(entity instanceof LivingEntity))
            return null;

        // Get living instance
        LivingEntity livingEntity = (LivingEntity)entity;

        // Get level
        int level = retrieveLevel(livingEntity);

        // Is Boss
        boolean isBoss = bossMobs.containsKey(entity.getType());

        // Make sure the dragon has initial exp amount
        if(entity.getType() == EntityType.ENDER_DRAGON)
            exp = 12000;

        return (int)scale(exp, level, isBoss, false);
    }

    public int getBossLevel() {
        return random.nextInt(100) + 1;
    }

    public int getNonBossLevel() {
        return random.nextInt(100) + 1;
    }

    public double scale(double val, int level, boolean increaseOnly, boolean resistance) {
        int percentChange = random.nextInt(5) + 1;
        percentChange *= level;
        if(increaseOnly)
            percentChange += 100;

        // Convert to an actual percent
        double percentChangeFloat = percentChange * 0.01;

        if(resistance)
            return val / percentChangeFloat;

        return val * percentChangeFloat;
    }

    public void scaleHealth(LivingEntity livingEntity, int level, boolean isBoss) {
        // Get Scaled Health, scale up only if boss
        double health = livingEntity.getHealth();
        health = scale(health, level, isBoss, false);

        // Minecraft Limit
        if(health > 2560d)
            health = 2560d;
        else if(health < 0.01d)
            health = 0.01d;

        // Set new health
        livingEntity.setMaxHealth(health);
        livingEntity.setHealth(health);
    }

    public void displayBossTitle(Entity entity, int level) {
        // Get all players within a hundred radius of boss and send title update
        Collection<Player> players = entity.getLocation().getNearbyPlayers(100, 255);

        for(Player player : players) {
            player.sendTitle("Boss Battle", "Level " + level, 10, 60, 20);
        }
    }

    public void storeLevel(LivingEntity livingEntity, int level) {
        livingEntity.setMetadata(metadataKeyLevel, new FixedMetadataValue(plugin, level));
    }

    public boolean hasLevel(LivingEntity livingEntity) {
        return livingEntity.hasMetadata(metadataKeyLevel);
    }

    public int retrieveLevel(LivingEntity livingEntity) {
        int level = 1;
        if(livingEntity.hasMetadata(metadataKeyLevel)) {
            List<MetadataValue> values = livingEntity.getMetadata(metadataKeyLevel);
            if(values.size() > 0) {
                level = values.get(0).asInt();
            }
        }

        return level;
    }

    // This may look silly, an ArrayList seems better
    // However we need speed above all else, nothing is faster than a HashMap
    // We don't need to iterate over a long array list every single time we need to check something
    public static final HashMap<EntityType,Boolean> nonBossMobs = new HashMap<>();
    public static final HashMap<EntityType,Boolean> bossMobs = new HashMap<>();

    public static final Random random = new Random();

    public static final String metadataKeyPrefix = "com.popupmc.variablehealth.VariableHealth:";
    public static final String metadataKeyLevel = metadataKeyPrefix + "level";

    public final VariableHealth plugin;
}
