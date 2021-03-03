package com.popupmc.variablehealth.lists;

import org.bukkit.entity.EntityType;

import java.util.HashMap;

public class GlowingMobs {
    public static void setup() {
        for(EntityType type : list) {
            hashList.put(type, true);
        }
    }

    public static final EntityType[] list = {
            EntityType.ENDER_DRAGON,
            EntityType.WITHER,
            EntityType.BLAZE,
            EntityType.CREEPER,
            EntityType.DROWNED,
            EntityType.ELDER_GUARDIAN,
            EntityType.ENDERMITE,
            EntityType.EVOKER,
            EntityType.GHAST,
            EntityType.GUARDIAN,
            EntityType.HOGLIN,
            EntityType.HUSK,
            EntityType.MAGMA_CUBE,
            EntityType.PHANTOM,
            EntityType.PIGLIN_BRUTE,
            EntityType.PILLAGER,
            EntityType.RAVAGER,
            EntityType.SHULKER,
            EntityType.SILVERFISH,
            EntityType.SKELETON,
            EntityType.SLIME,
            EntityType.STRAY,
            EntityType.VEX,
            EntityType.VINDICATOR,
            EntityType.WITCH,
            EntityType.WITHER_SKELETON,
            EntityType.ZOGLIN,
            EntityType.ZOMBIE,
            EntityType.ZOMBIE_VILLAGER,
            EntityType.ZOMBIE_HORSE,
            EntityType.ILLUSIONER
    };

    public static final HashMap<EntityType,Boolean> hashList = new HashMap<>();
}
