package com.popupmc.variablehealth.system.mobs;

import com.popupmc.variablehealth.VariableHealth;
import com.popupmc.variablehealth.system.BaseSystem;
import com.popupmc.variablehealth.system.System;
import com.popupmc.variablehealth.utility.RandomTools;
import org.bukkit.DyeColor;
import org.bukkit.entity.Shulker;
import org.jetbrains.annotations.NotNull;

public class ShulkerSpecific extends BaseSystem {
    public ShulkerSpecific(@NotNull System system, @NotNull VariableHealth plugin) {
        super(system, plugin);
    }

    public void shulkerSetup(Shulker shulker, int level) {
        DyeColor color = DyeColor.values()[RandomTools.getRandomRange0Exclusive(DyeColor.values().length)];
        shulker.setColor(color);
    }
}
