package com.popupmc.variablehealth.system;

import com.popupmc.variablehealth.BaseClass;
import com.popupmc.variablehealth.VariableHealth;
import org.jetbrains.annotations.NotNull;

public class BaseSystem extends BaseClass {
    public BaseSystem(@NotNull System system, @NotNull VariableHealth plugin) {
        super(plugin);
        this.system = system;
    }

    public final System system;
}
