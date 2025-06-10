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

    public ConfigHandler(DerexDespawn plugin) {
        this.plugin = plugin;
    }

    public void createConfigFile() {
        File configFile = new File(plugin.getDataFolder(), "config.yml");

        if (!configFile.exists()) {
            plugin.getDataFolder().mkdirs();

            try {
                configFile.createNewFile();
                FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
                config.options().setHeader(Arrays.asList(
                        "--------------------------------------------------",
                        "DerexDespawn by Derex",
                        "Report all ur troubles at the link below!",
                        "coming soon",
                        "--------------------------------------------------"));
                config.set("retroactive-despawn-check", false);
                config.save(configFile);
            } catch (IOException e) {
                Bukkit.getLogger().severe("Could not create config.yml for DerexDespawn!");
                e.printStackTrace();
            }
        }
    }



}
