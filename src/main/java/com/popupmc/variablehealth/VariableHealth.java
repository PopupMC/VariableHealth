package com.popupmc.variablehealth;

import com.popupmc.variablehealth.commands.OnMobRangeCommand;
import com.popupmc.variablehealth.events.OnCreeperDeath;
import com.popupmc.variablehealth.events.OnCreeperHeadPlace;
import com.popupmc.variablehealth.events.OnMobDeath;
import com.popupmc.variablehealth.events.OnMobSpawn;
import com.popupmc.variablehealth.heads.Heads;
import com.popupmc.variablehealth.lists.BossMobs;
import com.popupmc.variablehealth.lists.GlowingMobs;
import com.popupmc.variablehealth.lists.NonBossMobs;
import com.popupmc.variablehealth.system.System;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.Random;

public class VariableHealth extends JavaPlugin {
    @Override
    public void onEnable() {
        // Pre-cache these lists for quick lookup
        BossMobs.setup();
        NonBossMobs.setup();
        GlowingMobs.setup();

        // Create System
        this.system = new System(
                100,
                15,
                1500,
                100,
                25,
                250,
                15,
                10,
                100,
                4,
                4,
                4,
                3,
                3,
                4,
                4,
                10,
                5*20,
                5,
                64,
                250,
                5,
                10,
                this
        );

        this.heads = new Heads(this);

        Bukkit.getPluginManager().registerEvents(new OnMobSpawn(this), this);
        Bukkit.getPluginManager().registerEvents(new OnCreeperDeath(this), this);
        Bukkit.getPluginManager().registerEvents(new OnCreeperHeadPlace(this), this);
        Bukkit.getPluginManager().registerEvents(new OnMobDeath(this), this);

        Objects.requireNonNull(this.getCommand("mob-range")).setExecutor(new OnMobRangeCommand(this));
    }

    @Override
    public void onDisable() {
        getLogger().info("VariableHealth is disabled");
    }

    public static final Random random = new Random();
    public System system;
    public Heads heads;
}
