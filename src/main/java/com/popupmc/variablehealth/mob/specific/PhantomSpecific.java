package com.popupmc.variablehealth.mob.specific;

import com.popupmc.variablehealth.mob.MobLevel;
import com.popupmc.variablehealth.mob.MobScaling;
import org.bukkit.entity.Phantom;

public class PhantomSpecific {
    public static void phantomSetup(Phantom phantom, int level) {
        int size = MobScaling.addNoise(level / phantomSizeMax);

        if(size > phantomSizeMaxRaw)
            size = phantomSizeMaxRaw;
        if(size < 0)
            size = 0;

        phantom.setSize(size);
    }

    public static final int phantomSizeMaxRaw = 32;
    public static final int phantomSizeMax = (int)Math.ceil((double) MobLevel.maxLevel / phantomSizeMaxRaw); // Up to size 32
}
