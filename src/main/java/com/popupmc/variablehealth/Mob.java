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
        boolean setup = false;
        if(hasLevel(livingEntity)) {
            level = retrieveLevel(livingEntity);
            setup = true;
        }
        else
            level = getLevel();

        // Setup new mob
        if(!setup)
            setupNewMob(livingEntity, isBoss, level);

        // Display title if boss
        if(isBoss)
            displayBossTitle(entity, level);
    }

    public void setupNewMob(Entity entity, boolean isBoss, int level) {
        if(!(entity instanceof LivingEntity))
            return;

        // Get living instance
        LivingEntity livingEntity = (LivingEntity)entity;

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
        if(level > percent_75)
            entity.setSilent(true);

        livingEntity.setCanPickupItems(level > percent_50);

        // Scale Health
        scaleHealth(livingEntity, level, isBoss);

        // Scale Air
        scaleAir(livingEntity, level, isBoss);

        // Store Level onto entity
        if(!hasLevel(livingEntity))
            storeLevel(livingEntity, level);
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
        int explosionRadius = level / creeperExplosionMaxRadius;
        creeper.setExplosionRadius(explosionRadius);

        int maxFuseTicks = level / creeperFuseMaxTime;
        creeper.setMaxFuseTicks(maxFuseTicks);

        creeper.setPowered(level > percent_75);
    }

    public void llamaSetup(Llama llama, int level) {
        int strength = (level / llamaStrengthMax) + 1; // 1-5
        llama.setStrength(strength);
    }

    public void phantomSetup(Phantom phantom, int level) {
        int size = level / phantomSizeMax;
        phantom.setSize(size);
    }

    public void hoglinSetup(Hoglin hoglin, int level) {
        hoglin.setIsAbleToBeHunted(level < percent_50);
    }

    public void piglinSetup(Piglin piglin, int level) {
        piglin.setIsAbleToHunt(level > percent_50);
    }

    public void snowmanSetup(Snowman snowman, int level) {
        snowman.setDerp(level < percent_50);
    }

    public void vindicatorSetup(Vindicator vindicator, int level) {
        vindicator.setJohnny(level > percent_50);
    }

    public void zombieSetup(Zombie zombie, int level) {
        zombie.setCanBreakDoors(level > percent_50);

        if(zombie.shouldBurnInDay())
            zombie.setShouldBurnInDay(level < percent_50);
    }

    public Double getDamageToSelf(Entity entity, double damage) {
        if(!(entity instanceof LivingEntity))
            return null;

        // Get living instance
        LivingEntity livingEntity = (LivingEntity)entity;

        // Get level
        int level = retrieveLevel(livingEntity);

        // Is Boss
//         boolean isBoss = bossMobs.containsKey(entity.getType());

        // Scale damage, prevent it from scaling below 0.10
        double ret = scale(damage, level, true, true);
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

    public int getLevel() {
        return random.nextInt(maxLevel) + 1;
    }

    public double scale(double val, int level, boolean increaseOnly, boolean resistance) {

        // Used to countdown levels left
        // Ideally we want a random number for each level
        // But that's computationally expensive
        // So instead we do short bursts, this keeps track of that
        int levelLeft = level;

        // final product
        int percentChange = 0;

        // Begin looping our predefined granularity
        for(int i = 0; i < scaleGranularityCount; i++) {

            // Get a number between 1 and our max scale
            int tmp = random.nextInt(scaleMax) + 1;

            // If the short burst is greater than levels left, don't do a short burst, do the remainder levels
            // and stop here
            if(scaleGranularityAmount > levelLeft) {
                percentChange += (tmp * levelLeft);
                break;
            }
            // Otherwise do the full short burst and subtract burst done from levels left
            else {
                percentChange += (tmp * scaleGranularityAmount);
                levelLeft -= scaleGranularityAmount;
            }
        }

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
        double health = livingEntity.getMaxHealth();
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

    // Max Level
    public static final int maxLevel = 100;

    // public static final int percent_25 = (int)(maxLevel * 0.25);
    public static final int percent_50 = (int)(maxLevel * 0.50);
    public static final int percent_75 = (int)(maxLevel * 0.75);

    // We do math ceil because these are division factors and we want the highest number if decimal
    // Higher number means lower division result
    // Some of these have a specific range they can't go over
    public static final int creeperExplosionMaxRadius = (int)Math.ceil((double)maxLevel / 8); // 10 block radius
    public static final int creeperFuseMaxTime = (int)Math.ceil((double)maxLevel / (20 * 5)); // 5 second fuse
    public static final int llamaStrengthMax = (int)Math.ceil((double)maxLevel / 4); // 4 Strength
    public static final int phantomSizeMax = (int)Math.ceil((double)maxLevel / 32); // Up to size 32

    public static final int scaleMax = (int)Math.ceil(200 / (double)maxLevel); // Up to 250% max scaling
    public static final int scaleGranularityCount = 15; // 10 granularity count in scaling
    public static final int scaleGranularityAmount = maxLevel / scaleGranularityCount; // How many in each granularity

    public final VariableHealth plugin;
}
