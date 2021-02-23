package com.popupmc.variablehealth.mob.specific;

import com.popupmc.variablehealth.VariableHealth;
import com.popupmc.variablehealth.mob.MobLevel;
import com.popupmc.variablehealth.mob.MobScaling;
import org.bukkit.entity.Creeper;

public class CreeperSpecific {
    public static int getCreeperExplosionRadius(int level) {
        return MobScaling.addNoise(level / creeperExplosionMaxRadius);
    }

    public static int getCreeperFuseMaxTime(int level) {
        return MobScaling.addNoise(level / creeperFuseMaxTime);
    }

    public static boolean getCreeperPowered(int level) {
        if(level > MobLevel.percentOfMax(.75f))
            return VariableHealth.random.nextInt(100 + 1) > 25;

        return false;
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
