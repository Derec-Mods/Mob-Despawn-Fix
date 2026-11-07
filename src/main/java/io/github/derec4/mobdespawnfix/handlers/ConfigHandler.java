package io.github.derec4.mobdespawnfix.handlers;

import io.github.derec4.mobdespawnfix.MobDespawnFix;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ConfigHandler {
    private final MobDespawnFix plugin;
    private boolean despawnVehicle;
    private boolean retroactiveDespawnCheck;
    private boolean aggressiveDespawn;
    private boolean preventPickupPersistence;
    private boolean ignoreNamedMobs;
    private boolean ignoreNamedItems;
    private String mode;
    private final Set<Material> whitelist = new HashSet<>();
    private final Set<Material> blacklist = new HashSet<>();

    public ConfigHandler(MobDespawnFix plugin) {
        this.plugin = plugin;
        reload(); // Load config values on creation
    }

    public void reload() {
        plugin.reloadConfig();
        FileConfiguration config = plugin.getConfig();
        despawnVehicle = config.getBoolean("despawn-vehicle", config.getBoolean("despawn-boat", false));
        retroactiveDespawnCheck = config.getBoolean("retroactive-despawn-check", false);
        aggressiveDespawn = config.getBoolean("aggressive-despawn", false);
        preventPickupPersistence = config.getBoolean("prevent-pickup-persistence", true);
        ignoreNamedMobs = config.getBoolean("ignore-named-mobs", true);
        ignoreNamedItems = config.getBoolean("ignore-named-items", true);
        mode = config.getString("mode", "whitelist").trim().toLowerCase();

        whitelist.clear();
        List<String> rawWhitelist = config.getStringList("whitelist");
        for (String itemName : rawWhitelist) {
            Material material = Material.matchMaterial(itemName.trim());
            if (material != null) {
                whitelist.add(material);
            }
        }

        blacklist.clear();
        List<String> rawBlacklist = config.getStringList("blacklist");
        for (String itemName : rawBlacklist) {
            Material material = Material.matchMaterial(itemName.trim());
            if (material != null) {
                blacklist.add(material);
            }
        }
    }

    public boolean isDespawnVehicle() {
        return despawnVehicle;
    }

    public boolean isDespawnBoat() {
        return despawnVehicle;
    }

    public boolean isRetroactiveDespawnCheck() {
        return retroactiveDespawnCheck;
    }

    public boolean isAggressiveDespawn() {
        return aggressiveDespawn;
    }

    public boolean isPreventPickupPersistence() {
        return preventPickupPersistence;
    }

    public boolean isIgnoreNamedMobs() {
        return ignoreNamedMobs;
    }

    public boolean isIgnoreNamedItems() {
        return ignoreNamedItems;
    }

    public String getMode() {
        return mode;
    }

    public Set<Material> getWhitelist() {
        return whitelist;
    }

    public Set<Material> getBlacklist() {
        return blacklist;
    }

    public boolean isItemAllowedToDespawn(Material material) {
        if (material == null || material == Material.AIR || material.name().endsWith("_AIR")) {
            return false;
        }
        if ("blacklist".equals(mode)) {
            return !blacklist.contains(material);
        }
        return whitelist.contains(material);
    }
}

