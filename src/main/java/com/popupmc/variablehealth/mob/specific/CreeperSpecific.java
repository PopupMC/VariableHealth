package com.popupmc.variablehealth.mob.specific;

import com.popupmc.variablehealth.mob.MobLevel;
import org.bukkit.entity.Creeper;

public class CreeperSpecific {
    public static int getCreeperExplosionRadius(int level) {
        return level / creeperExplosionMaxRadius;
    }

    public static int getCreeperFuseMaxTime(int level) {
        return level / creeperFuseMaxTime;
    }

    public static boolean getCreeperPowered(int level) {
        return level > MobLevel.percentOfMax(.75f);
    }

    public static void creeperSetup(Creeper creeper, int level) {
        int explosionRadius = getCreeperExplosionRadius(level);
        creeper.setExplosionRadius(explosionRadius);

        int maxFuseTicks = getCreeperFuseMaxTime(level);
        creeper.setMaxFuseTicks(maxFuseTicks);

        creeper.setPowered(getCreeperPowered(level));
    }

    public static final int creeperExplosionMaxRadius = (int)Math.ceil((double)MobLevel.maxLevel / 10); // 10 block radius
    public static final int creeperFuseMaxTime = (int)Math.ceil((double)MobLevel.maxLevel / (20 * 5)); // 5 second fuse
}
