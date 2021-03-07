package com.popupmc.variablehealth.system.mobs;

import com.popupmc.variablehealth.VariableHealth;
import com.popupmc.variablehealth.system.BaseSystem;
import com.popupmc.variablehealth.system.System;
import org.bukkit.entity.*;
import org.jetbrains.annotations.NotNull;

public class Mobs extends BaseSystem {
    public Mobs(int creeperExplosionMaxRadius,
                int creeperFuseMaxTime,
                int llamaStrengthMax,
                int phantomSizeMax,
                int villagerExpMax,
                int villagerLevelMax,
                int slimeMaxSize,
                @NotNull System system,
                @NotNull VariableHealth plugin) {
        super(system, plugin);

        this.creeperSpecific = new CreeperSpecific(creeperExplosionMaxRadius, creeperFuseMaxTime, system, plugin);
        this.hoglinSpecific = new HoglinSpecific(system, plugin);
        this.llamaSpecific = new LlamaSpecific(llamaStrengthMax, system, plugin);
        this.phantomSpecific = new PhantomSpecific(phantomSizeMax, system, plugin);
        this.piglinSpecific = new PiglinSpecific(system, plugin);
        this.snowmanSpecific = new SnowmanSpecific(system, plugin);
        this.villagerSpecific = new VillagerSpecific(villagerExpMax, villagerLevelMax, system, plugin);
        this.vindicatorSpecific = new VindicatorSpecific(system, plugin);
        this.zombieSpecific = new ZombieSpecific(system, plugin);
        this.zombieVillagerSpecific = new ZombieVillagerSpecific(system, plugin);
        this.slimeSpecific = new SlimeSpecific(slimeMaxSize, system, plugin);
    }

    public void setup(LivingEntity entity, int level) {
        if(entity instanceof Creeper)
            creeperSpecific.creeperSetup((Creeper)entity, level);

        if(entity instanceof Llama)
            llamaSpecific.llamaSetup((Llama)entity, level);

        if(entity instanceof Phantom)
            phantomSpecific.phantomSetup((Phantom) entity, level);

        if(entity instanceof Hoglin)
            hoglinSpecific.hoglinSetup((Hoglin) entity, level);

        if(entity instanceof Piglin)
            piglinSpecific.piglinSetup((Piglin) entity, level);

        if(entity instanceof Snowman)
            snowmanSpecific.snowmanSetup((Snowman) entity, level);

        if(entity instanceof Vindicator)
            vindicatorSpecific.vindicatorSetup((Vindicator) entity, level);

        if(entity instanceof Zombie)
            zombieSpecific.zombieSetup((Zombie) entity, level);

        if(entity instanceof Villager)
            villagerSpecific.villagerSetup((Villager) entity, level);

        if(entity instanceof ZombieVillager)
            zombieVillagerSpecific.villagerSetup((ZombieVillager) entity, level);

        if(entity instanceof Slime)
            slimeSpecific.slimeSetup((Slime) entity, level);
    }

    public final CreeperSpecific creeperSpecific;
    public final HoglinSpecific hoglinSpecific;
    public final LlamaSpecific llamaSpecific;
    public final PhantomSpecific phantomSpecific;
    public final PiglinSpecific piglinSpecific;
    public final SnowmanSpecific snowmanSpecific;
    public final VillagerSpecific villagerSpecific;
    public final VindicatorSpecific vindicatorSpecific;
    public final ZombieSpecific zombieSpecific;
    public final ZombieVillagerSpecific zombieVillagerSpecific;
    public final SlimeSpecific slimeSpecific;
}
