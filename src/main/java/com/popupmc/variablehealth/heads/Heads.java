package com.popupmc.variablehealth.heads;

import com.popupmc.variablehealth.BaseClass;
import com.popupmc.variablehealth.VariableHealth;
import org.jetbrains.annotations.NotNull;

public class Heads extends BaseClass {
    public Heads(@NotNull VariableHealth plugin) {
        super(plugin);

        this.creeper = new CreeperHead(plugin);
    }

    public final CreeperHead creeper;
}
