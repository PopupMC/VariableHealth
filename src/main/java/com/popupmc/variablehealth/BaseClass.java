package com.popupmc.variablehealth;

import org.jetbrains.annotations.NotNull;

abstract public class BaseClass {
    public BaseClass(@NotNull VariableHealth plugin) {
        this.plugin = plugin;
    }

    public final VariableHealth plugin;
}
