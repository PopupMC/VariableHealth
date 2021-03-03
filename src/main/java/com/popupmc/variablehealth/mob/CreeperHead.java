package com.popupmc.variablehealth.mob;

import com.popupmc.variablehealth.VariableHealth;
import com.popupmc.variablehealth.mob.specific.CreeperSpecific;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class CreeperHead {
    public static boolean doCreeperHeadDrop(Entity entity) {
        if(entity.getType() != EntityType.CREEPER)
            return false;

        return VariableHealth.random.nextInt(100 + 1) > 70;
    }

    public static void dropCreeperHead(Entity entity) {
        if(entity.getType() != EntityType.CREEPER)
            return;

        // Get living instance
        LivingEntity livingEntity = (LivingEntity)entity;

        // Get level
        int level = MobLevel.retrieveLevel(livingEntity);

        // Create creeper head item
        ItemStack item = new ItemStack(Material.CREEPER_HEAD);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName("Explosive Head");

        // Save data into item
        ArrayList<String> lore = new ArrayList<>();
        lore.add("Blast Radius: " + CreeperSpecific.getCreeperExplosionRadius(level));
        lore.add("Fuse: " + CreeperSpecific.getCreeperFuseMaxTime(level));
        lore.add("Powered: " + ((CreeperSpecific.getCreeperPowered(level)) ? "yes" : "no"));
        meta.setLore(lore);
        item.setItemMeta(meta);

        // Drop item
        entity.getLocation().getWorld().dropItemNaturally(entity.getLocation(), item);
    }

    public static boolean isPoweredCreeperHead(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();

        if(lore == null)
            return false;

        if(lore.size() != 3)
            return false;

        String blastRadius = lore.get(0);
        String fuse = lore.get(1);
        String powered = lore.get(2);

        return blastRadius.startsWith("Blast Radius: ") &&
                fuse.startsWith("Fuse: ") &&
                powered.startsWith("Powered: ");
    }

    public static void placePoweredCreeperHead(Location location, ItemStack item, VariableHealth plugin) {

        location = location.toCenterLocation();

        Creeper entity = (Creeper)location.getWorld().spawnEntity(location, EntityType.CREEPER);
        entity.setAI(false);
        entity.setLootTable(null);
        entity.setAware(false);
        entity.setCanPickupItems(false);
        entity.setPersistent(true);

        MobLevel.storeLevel(entity, 1);

        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();

        if(lore == null)
            return;

        if(lore.size() != 3)
            return;

        String blastRadius = lore.get(0);
        String fuse = lore.get(1);
        String powered = lore.get(2);

        if(!blastRadius.startsWith("Blast Radius: ") ||
                !fuse.startsWith("Fuse: ") ||
                !powered.startsWith("Powered: "))
            return;

        blastRadius = blastRadius.substring("Blast Radius: ".length());
        fuse = fuse.substring("Fuse: ".length());
        powered = powered.substring("Powered: ".length());

        int blastRadiusInt;
        int fuseInt;
        boolean poweredBool;

        try {
            blastRadiusInt = Integer.parseInt(blastRadius);
            fuseInt = Integer.parseInt(fuse);
            poweredBool = powered.equalsIgnoreCase("yes");
        }
        catch (NumberFormatException ex) {
            plugin.getLogger().warning("NumberFormatException");
            plugin.getLogger().warning(blastRadius);
            plugin.getLogger().warning(fuse);
            plugin.getLogger().warning(powered);
            return;
        }

        entity.setExplosionRadius(blastRadiusInt);
        entity.setMaxFuseTicks(fuseInt);
        entity.setPowered(poweredBool);
        entity.setIgnited(true);
    }
}
