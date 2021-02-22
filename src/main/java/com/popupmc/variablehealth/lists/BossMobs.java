package com.popupmc.variablehealth.lists;

import org.bukkit.entity.EntityType;

import java.util.HashMap;

public class BossMobs {
    public static void setup() {
        for(EntityType type : list) {
            hashList.put(type, true);
        }
    }

    public static final EntityType[] list = {
            EntityType.ENDER_DRAGON,
            EntityType.WITHER,
    };

    public static final HashMap<EntityType,Boolean> hashList = new HashMap<>();
}
