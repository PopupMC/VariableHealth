package com.popupmc.variablehealth;

import org.bukkit.entity.*;
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

        // Scale slime body to indicate level
        // Bad idea, slimes never die, they keep multiplying, the server crashes
//        if(entity instanceof Slime)
//            scaleBody((Slime)entity, level);

        if(entity instanceof Creeper)
            creeperSetup((Creeper)entity, level);

        if(entity instanceof Llama)
            llamaSetup((Llama)entity, level);

        if(entity instanceof Phantom)
            phantomSetup((Phantom) entity, level);

        if(entity instanceof Hoglin)
            hoglinSetup((Hoglin) entity, level);

        if(entity instanceof Piglin)
            piglinSetup((Piglin) entity, level);

        if(entity instanceof Snowman)
            snowmanSetup((Snowman) entity, level);

        if(entity instanceof Vindicator)
            vindicatorSetup((Vindicator) entity, level);

        if(entity instanceof Zombie)
            zombieSetup((Zombie) entity, level);

        // If above 175 it's learned how to be silent
        if(level > 175)
            entity.setSilent(true);

        // If it's above 150, set arrows up to a max of 5 depending on level
        // to indicate resilence
        if(level > 150) {
            int tmp = level - 150;
            livingEntity.setArrowsInBody(tmp / 10);
            livingEntity.setArrowsStuck(tmp / 10);
        }

        // Entities greater than level 75 can pickup items
        livingEntity.setCanPickupItems(level > 75);

        // Scale Health
        scaleHealth(livingEntity, level, isBoss);

        // Scale Air
        scaleAir(livingEntity, level, isBoss);

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

//    public void scaleBody(Slime slime, int level) {
//        // There's a max level of 200 for slimes, I want a max height of 10 blocks
//        // So I divide level by 20 to get correct sizing
//        int size = level / 20;
//        slime.setSize(size);
//    }

    public void creeperSetup(Creeper creeper, int level) {
        // There's a max level of 200 for creepers, I want a max radius of 10 blocks
        // So I divide level by 20 to get correct amount
        int explosionRadius = level / 20;
        creeper.setExplosionRadius(explosionRadius);

        // I want a max fuse ticks of 5 seconds so divide by 2 to get that
        int maxFuseTicks = level / 2;
        creeper.setMaxFuseTicks(maxFuseTicks);

        // Power creeper only if level is greater than 100
        creeper.setPowered(level > 100);
    }

    public void llamaSetup(Llama llama, int level) {
        // There's a max level of 200 for llamas, I want a max strength of 4
        // So I divide level by 50 to get correct amount
        int strength = (level / 50) + 1; // 1-5
        llama.setStrength(strength);
    }

    public void phantomSetup(Phantom phantom, int level) {
        // There's a max level of 200 for phantoms, I want a max size of 50 (out of 64)
        // So I divide level by 4 to get correct amount
        int size = level / 4;
        phantom.setSize(size);
    }

    public void hoglinSetup(Hoglin hoglin, int level) {
        // Hoglins below level 100 can be hunted
        hoglin.setIsAbleToBeHunted(level < 100);
    }

    public void piglinSetup(Piglin piglin, int level) {
        // Piglins above level 100 can be hunted
        piglin.setIsAbleToHunt(level > 100);
    }

    public void snowmanSetup(Snowman snowman, int level) {
        // Snowmen below level 100 are in derp mode
        snowman.setDerp(level < 100);
    }

    public void vindicatorSetup(Vindicator vindicator, int level) {
        // Vindactors above level 100 are enemy to most
        vindicator.setJohnny(level > 100);
    }

    public void zombieSetup(Zombie zombie, int level) {
        // Zombies above level 100 can break doors
        zombie.setCanBreakDoors(level > 100);

        // If it would normally burn in day, it won't if it's above level 100
        if(zombie.shouldBurnInDay())
            zombie.setShouldBurnInDay(level < 100);
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
        return random.nextInt(200) + 1;
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

    public void scaleAir(LivingEntity livingEntity, int level, boolean isBoss) {
        // Get Scaled Health, scale up only if boss
        double air = livingEntity.getMaximumAir();
        air = scale(air, level, isBoss, false);

        // Minecraft Limit
        if(air > 300d)
            air = 300d;
        else if(air < 1d)
            air = 1d;

        // Set new health
        livingEntity.setMaximumAir((int)air);
        livingEntity.setRemainingAir((int)air);
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
