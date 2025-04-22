package io.github.derec4.derexDespawn;

import io.github.derec4.derexDespawn.handlers.ConfigHandler;
import org.bukkit.plugin.java.JavaPlugin;

public final class DerexDespawn extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        ConfigHandler configHandler = new ConfigHandler(this);
        configHandler.createConfigFile();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
