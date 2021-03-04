package com.popupmc.variablehealth.mob.specific;

import org.bukkit.entity.*;

public class Specific {
    public static void setup(LivingEntity entity, int level) {
        if(entity instanceof Creeper)
            CreeperSpecific.creeperSetup((Creeper)entity, level);

        if(entity instanceof Llama)
            LlamaSpecific.llamaSetup((Llama)entity, level);

        if(entity instanceof Phantom)
            PhantomSpecific.phantomSetup((Phantom) entity, level);

        if(entity instanceof Hoglin)
            HoglinSpecific.hoglinSetup((Hoglin) entity, level);

        if(entity instanceof Piglin)
            PiglinSpecific.piglinSetup((Piglin) entity, level);

        if(entity instanceof Snowman)
            SnowmanSpecific.snowmanSetup((Snowman) entity, level);

        if(entity instanceof Vindicator)
            VindicatorSpecific.vindicatorSetup((Vindicator) entity, level);

        if(entity instanceof Zombie)
            ZombieSpecific.zombieSetup((Zombie) entity, level);

        if(entity instanceof Villager)
            VillagerSpecific.villagerSetup((Villager) entity, level);
    }
}
