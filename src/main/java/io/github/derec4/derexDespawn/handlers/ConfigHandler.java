package io.github.derec4.derexDespawn.handlers;

import io.github.derec4.derexDespawn.DerexDespawn;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class ConfigHandler {
    private final DerexDespawn plugin;
    private boolean despawnBoat;
    private boolean retroactiveDespawnCheck;
    private boolean aggressiveDespawn;

    public ConfigHandler(DerexDespawn plugin) {
        this.plugin = plugin;
        reload(); // Load config values on creation
    }

    public void reload() {
        plugin.reloadConfig();
        FileConfiguration config = plugin.getConfig();
        despawnBoat = config.getBoolean("despawn-boat", false);
        retroactiveDespawnCheck = config.getBoolean("retroactive-despawn-check", false);
        aggressiveDespawn = config.getBoolean("aggressive-despawn", false);
    }

    public boolean isDespawnBoat() {
        return despawnBoat;
    }

    public boolean isRetroactiveDespawnCheck() {
        return retroactiveDespawnCheck;
    }

    public boolean isAggressiveDespawn() {
        return aggressiveDespawn;
    }
}
