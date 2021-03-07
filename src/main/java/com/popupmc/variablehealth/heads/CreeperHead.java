package com.popupmc.variablehealth.heads;

import com.popupmc.variablehealth.BaseClass;
import com.popupmc.variablehealth.VariableHealth;
import com.popupmc.variablehealth.utility.RandomTools;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CreeperHead extends BaseClass {
    public CreeperHead(@NotNull VariableHealth plugin) {
        super(plugin);
    }

    public void dropCreeperHead(LivingEntity entity) {
        if((entity.getType() != EntityType.CREEPER) ||
                !RandomTools.getRandomChanceDown(30))
            return;

        // Get level
        int level = plugin.system.level.meta.retrieveLevel(entity);

        // Create creeper head item
        ItemStack item = new ItemStack(Material.CREEPER_HEAD);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName("Explosive Head");

        String blastRadiusMsg = "Blast Radius";

        // 25% chance obfuscated
        if(RandomTools.getRandomChanceDown(25))
            blastRadiusMsg += ChatColor.MAGIC;
        else
            blastRadiusMsg += ": ";

        // 25% chance level 75+ is randomized
        if(level > plugin.system.level.data.percentOfMax(.75f) && RandomTools.getRandomChanceDown(25)) {
            blastRadiusMsg += "???";
        }
        else {
            blastRadiusMsg += plugin.system.mobs.creeperSpecific.getCreeperExplosionRadius(level);
        }

        String poweredMsg = "Powered: ";

        // 25% chance randomized
        if(RandomTools.getRandomChanceDown(25)) {
            poweredMsg += "maybe?";
        }
        else {
            poweredMsg += ((plugin.system.mobs.creeperSpecific.getCreeperPowered(level)) ? "yes" : "no");
        }

        // Save data into item
        ArrayList<String> lore = new ArrayList<>();
        lore.add(blastRadiusMsg);
        lore.add("Fuse: " + plugin.system.mobs.creeperSpecific.getCreeperFuseMaxTime(level));
        lore.add(poweredMsg);
        meta.setLore(lore);
        item.setItemMeta(meta);

        // Drop item
        entity.getLocation().getWorld().dropItemNaturally(entity.getLocation(), item);
    }

    public boolean isPoweredCreeperHead(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();

        if(lore == null)
            return false;

        if(lore.size() != 3)
            return false;

        String blastRadius = lore.get(0);
        String fuse = lore.get(1);
        String powered = lore.get(2);

        return blastRadius.startsWith("Blast Radius") &&
                fuse.startsWith("Fuse: ") &&
                powered.startsWith("Powered: ");
    }

    public boolean placePoweredCreeperHead(Location location, ItemStack item) {

        if(!isPoweredCreeperHead(item))
            return false;

        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();

        if(lore == null)
            return false;

        location = location.toCenterLocation();

        Creeper entity = (Creeper)location.getWorld().spawnEntity(location, EntityType.CREEPER);
        entity.setAI(false);
        entity.setLootTable(null);
        entity.setAware(false);
        entity.setCanPickupItems(false);
        entity.setPersistent(true);

        String blastRadius = lore.get(0);
        String fuse = lore.get(1);
        String powered = lore.get(2);

        blastRadius = blastRadius.substring("Blast Radius: ".length());
        fuse = fuse.substring("Fuse: ".length());
        powered = powered.substring("Powered: ".length());

        int blastRadiusInt = 0;
        int fuseInt = 20;
        boolean poweredBool = false;

        try {
            if(blastRadius.equalsIgnoreCase("???")) {
                int maxLevelDif = plugin.system.level.data.maxLevel - plugin.system.level.data.percentOfMax(.75f);

                blastRadiusInt = plugin.system.mobs.creeperSpecific.getCreeperExplosionRadius(
                        RandomTools.getRandomRange0Inclusive(maxLevelDif) + plugin.system.level.data.percentOfMax(.75f)
                );
            }
            else
                blastRadiusInt = Integer.parseInt(blastRadius);

            fuseInt = Integer.parseInt(fuse);

            if(powered.equalsIgnoreCase("maybe?"))
                poweredBool = RandomTools.getCoinFlip();
            else
                poweredBool = powered.equalsIgnoreCase("yes");
        }
        catch (NumberFormatException ex) {
            plugin.getLogger().warning("NumberFormatException");
            plugin.getLogger().warning(blastRadius);
            plugin.getLogger().warning(fuse);
            plugin.getLogger().warning(powered);
        }

        entity.setExplosionRadius(blastRadiusInt);
        entity.setMaxFuseTicks(fuseInt);
        entity.setPowered(poweredBool);
        entity.setIgnited(true);
        return true;
    }
}
