package com.popupmc.variablehealth.system.level;

import com.popupmc.variablehealth.VariableHealth;
import com.popupmc.variablehealth.system.BaseSystem;
import com.popupmc.variablehealth.system.System;
import org.bukkit.entity.LivingEntity;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class LevelMeta extends BaseSystem {
    public LevelMeta(@NotNull System system, @NotNull VariableHealth plugin) {
        super(system, plugin);
    }

    public void storeLevel(LivingEntity livingEntity, int level) {
        livingEntity.setMetadata(metadataKey, new FixedMetadataValue(plugin, level));
    }

    public boolean hasLevel(LivingEntity livingEntity) {
        return livingEntity.hasMetadata(metadataKey);
    }

    public int retrieveLevel(LivingEntity livingEntity) {
        int level = 1;
        if(livingEntity.hasMetadata(metadataKey)) {
            List<MetadataValue> values = livingEntity.getMetadata(metadataKey);
            if(values.size() > 0) {
                level = values.get(0).asInt();
            }
        }

        return level;
    }

    public static final String metadataKey = "com.popupmc.variablehealth.level";
}
