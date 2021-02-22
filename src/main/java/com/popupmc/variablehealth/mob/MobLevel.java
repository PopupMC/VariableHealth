package com.popupmc.variablehealth.mob;

import com.popupmc.variablehealth.VariableHealth;
import org.bukkit.entity.LivingEntity;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import java.util.List;

public class MobLevel {
    public static void storeLevel(LivingEntity livingEntity, int level) {
        livingEntity.setMetadata(metadataKeyLevel, new FixedMetadataValue(VariableHealth.plugin, level));
    }

    public static boolean hasLevel(LivingEntity livingEntity) {
        return livingEntity.hasMetadata(metadataKeyLevel);
    }

    public static int retrieveLevel(LivingEntity livingEntity) {
        int level = 1;
        if(livingEntity.hasMetadata(metadataKeyLevel)) {
            List<MetadataValue> values = livingEntity.getMetadata(metadataKeyLevel);
            if(values.size() > 0) {
                level = values.get(0).asInt();
            }
        }

        return level;
    }

    public static int percentOfMax(float percent) {
        return (int)(maxLevel * percent);
    }

    public static boolean levelGTMaxPercent(int level, float percent) {
        return level > percentOfMax(percent);
    }

    public static boolean levelLTMaxPercent(int level, float percent) {
        return level < percentOfMax(percent);
    }

    public static int randomLevel() {
        return VariableHealth.random.nextInt(maxLevel) + 1;
    }

    public static final String metadataKeyPrefix = "com.popupmc.variablehealth.VariableHealth:";
    public static final String metadataKeyLevel = metadataKeyPrefix + "level";

    // Max Level
    public static final int maxLevel = 100;
}
