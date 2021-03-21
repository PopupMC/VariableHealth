package com.popupmc.variablehealth.system;

import com.popupmc.variablehealth.VariableHealth;
import com.popupmc.variablehealth.BaseClass;
import com.popupmc.variablehealth.lists.BossMobs;
import com.popupmc.variablehealth.lists.GlowingMobs;
import com.popupmc.variablehealth.lists.NonBossMobs;
import com.popupmc.variablehealth.system.boss.Boss;
import com.popupmc.variablehealth.system.effects.Effects;
import com.popupmc.variablehealth.system.level.Level;
import com.popupmc.variablehealth.system.mobs.Mobs;
import com.popupmc.variablehealth.system.scaling.Scale;
import com.popupmc.variablehealth.system.spawns.Spawns;
import com.popupmc.variablehealth.utility.LocationTools;
import com.popupmc.variablehealth.utility.RandomTools;
import com.popupmc.variablehealth.utility.StringTools;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class System extends BaseClass {
    public System(int maxLevel,
                  int lowLevelPercent,
                  int powerLevelCap,
                  int averageRadius,
                  int levelNoiseMaxPercent,
                  int maxScalePercent,
                  int scaleGranularityCount,
                  int statNoiseMaxPercent,
                  int bossPlayerRadius,
                  int resistanceLevelMax,
                  int hasteLevelMax,
                  int jumpLevelMax,
                  int healthBoostMax,
                  int absorbtionBoostMax,
                  int slownessLevelMax,
                  int fatigueLevelMax,
                  int creeperExplosionMaxRadius,
                  int creeperFuseMaxTime,
                  int llamaStrengthMax,
                  int phantomSizeMax,
                  int villagerExpMax,
                  int villagerLevelMax,
                  int slimeMaxSize,
                  int strengthLevelMax,
                  @NotNull VariableHealth plugin) {
        super(plugin);

        this.level = new Level(maxLevel,
                lowLevelPercent,
                powerLevelCap,
                averageRadius,
                levelNoiseMaxPercent,
                this,
                plugin);

        this.scale = new Scale(maxScalePercent,
                scaleGranularityCount,
                statNoiseMaxPercent,
                this,
                plugin);

        this.boss = new Boss(bossPlayerRadius, this, plugin);

        this.effects = new Effects(resistanceLevelMax,
                hasteLevelMax,
                jumpLevelMax,
                healthBoostMax,
                absorbtionBoostMax,
                slownessLevelMax,
                fatigueLevelMax,
                strengthLevelMax,
                this,
                plugin);

        this.mobs = new Mobs(creeperExplosionMaxRadius,
                creeperFuseMaxTime,
                llamaStrengthMax,
                phantomSizeMax,
                villagerExpMax,
                villagerLevelMax,
                slimeMaxSize,
                this,
                plugin);

        this.spawns = new Spawns(this, plugin);
    }

    public boolean doesApply(LivingEntity entity) {
        return NonBossMobs.hashList.containsKey(entity.getType()) ||
                BossMobs.hashList.containsKey(entity.getType());
    }

    public void setup(LivingEntity entity) {
        if(!doesApply(entity))
            return;

        // Get is boss or not
        boolean isBoss = BossMobs.hashList.containsKey(entity.getType());

        // Retrieve or create level level
        int level;
        boolean setup = false;
        if(this.level.meta.hasLevel(entity)) {
            level = this.level.meta.retrieveLevel(entity);
            setup = true;
        }
        else
            level = this.level.data.getAverageOrRandomLevel(entity.getLocation());

        // Setup new mob
        if(!setup) {
            setupNewMob(entity, isBoss, level);

            // Display title if boss
            if(isBoss)
                boss.displayBossTitle(entity, level);
        }
    }

    public void setupNewMob(LivingEntity entity, boolean isBoss, int level) {
        // Skip all mobs that are tamed
        if(entity instanceof Tameable) {
            if(((Tameable)entity).getOwnerUniqueId() != null && ((Tameable)entity).isTamed()) {
                return;
            }
        }

        // Does it have a custom name?
        boolean hasCustomName = entity.getCustomName() != null && !entity.getCustomName().isEmpty();

        // If not, assign one, else stop here. Dont touch anything with custom names
        if(!hasCustomName) {
            //if(entity.getType() != EntityType.ENDER_DRAGON)
            entity.setCustomName("[" + level + "] " + StringTools.readableMobName(entity.getType()));
        }
        else
            return;

        // Skip all mobs that are leashed
        if(entity.isLeashed())
            return;

        if(GlowingMobs.hashList.containsKey(entity.getType()) && level < this.level.data.maxLowLevel) {
            entity.setGlowing(RandomTools.getRandomChanceUp(25));
        }

        // Setup specific mobs
        mobs.setup(entity, level);

        // If above 175 it's probably learned how to be silent
        if(level > this.level.data.percentOfMax(0.75f))
            entity.setSilent(RandomTools.getRandomChanceUp(25));

        // Apply effect all mobs get
        effects.basic.applyBasicEffects(entity, level, isBoss);
        effects.extra.applyExtraEffects(entity, level);

        // This will auto-skip if not low-level
        if(!isBoss)
            effects.extra.applyLowLevelEffects(entity, level);

        entity.setCanPickupItems(false);

        // ScaleBasics Health
        //if(entity.getType() != EntityType.ENDER_DRAGON)
        scale.stats.scaleHealth(entity, level, isBoss);

        // ScaleBasics Air
        //if(entity.getType() != EntityType.ENDER_DRAGON)
        scale.stats.scaleAir(entity, level, isBoss);

        // Store Level onto entity
        if(!this.level.meta.hasLevel(entity))
            this.level.meta.storeLevel(entity, level);

        // Check to see if we need to spawn mobs/animals
        this.spawns.checkSpawn(entity);
    }

    public final Level level;
    public final Scale scale;
    public final Boss boss;
    public final Effects effects;
    public final Mobs mobs;
    public final Spawns spawns;
}
