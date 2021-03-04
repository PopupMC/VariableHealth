package com.popupmc.variablehealth;

import com.popupmc.variablehealth.commands.OnMobRangeCommand;
import com.popupmc.variablehealth.events.OnCreeperDeath;
import com.popupmc.variablehealth.events.OnCreeperHeadPlace;
import com.popupmc.variablehealth.events.OnMobDeath;
import com.popupmc.variablehealth.events.OnMobSpawn;
import com.popupmc.variablehealth.lists.BossMobs;
import com.popupmc.variablehealth.lists.GlowingMobs;
import com.popupmc.variablehealth.lists.NonBossMobs;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.Random;

public class VariableHealth extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        plugin = this;

        // Pre-cache these lists for quick lookup
        BossMobs.setup();
        NonBossMobs.setup();
        GlowingMobs.setup();

        Bukkit.getPluginManager().registerEvents(new OnMobSpawn(), this);
        Bukkit.getPluginManager().registerEvents(new OnCreeperDeath(), this);
        Bukkit.getPluginManager().registerEvents(new OnCreeperHeadPlace(), this);
        Bukkit.getPluginManager().registerEvents(new OnMobDeath(), this);

        Objects.requireNonNull(this.getCommand("mob-range")).setExecutor(new OnMobRangeCommand());

        // Log enabled status
        getLogger().info("VariableHealth is enabled.");
    }

    // Log disabled status
    @Override
    public void onDisable() {
        getLogger().info("VariableHealth is disabled");
    }

    public static VariableHealth plugin;
    public static final Random random = new Random();
}
