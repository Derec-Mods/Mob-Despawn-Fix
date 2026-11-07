package io.github.derec4.derexDespawn.handlers;

import io.github.derec4.derexDespawn.DerexDespawn;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ConfigHandler {
    private final DerexDespawn plugin;
    private boolean despawnBoat;
    private boolean retroactiveDespawnCheck;
    private boolean aggressiveDespawn;
    private boolean preventPickupPersistence;
    private boolean ignoreNamedMobs;
    private boolean ignoreNamedItems;
    private final Set<Material> blacklist = new HashSet<>();

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
        preventPickupPersistence = config.getBoolean("prevent-pickup-persistence", true);
        ignoreNamedMobs = config.getBoolean("ignore-named-mobs", true);
        ignoreNamedItems = config.getBoolean("ignore-named-items", true);

        blacklist.clear();
        List<String> rawBlacklist = config.getStringList("blacklist");
        for (String itemName : rawBlacklist) {
            Material material = Material.matchMaterial(itemName.trim());
            if (material != null) {
                blacklist.add(material);
            }
        }
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

    public boolean isPreventPickupPersistence() {
        return preventPickupPersistence;
    }

    public boolean isIgnoreNamedMobs() {
        return ignoreNamedMobs;
    }

    public boolean isIgnoreNamedItems() {
        return ignoreNamedItems;
    }

    public Set<Material> getBlacklist() {
        return blacklist;
    }
}
