package com.popupmc.variablehealth.mob.specific;

import com.popupmc.variablehealth.mob.MobLevel;
import org.bukkit.entity.Phantom;

public class PhantomSpecific {
    public static void phantomSetup(Phantom phantom, int level) {
        int size = level / phantomSizeMax;
        phantom.setSize(size);
    }

    public static final int phantomSizeMax = (int)Math.ceil((double) MobLevel.maxLevel / 32); // Up to size 32
}
