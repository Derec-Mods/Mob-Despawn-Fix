package io.github.derec4.mobdespawnfix;

import io.github.derec4.mobdespawnfix.commands.CommandHandler;
import io.github.derec4.mobdespawnfix.handlers.ConfigHandler;
import io.github.derec4.mobdespawnfix.listeners.PickupListener;
import io.github.derec4.mobdespawnfix.listeners.RetroactiveListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class MobDespawnFix extends JavaPlugin {
    private ConfigHandler configHandler;
    private RetroactiveListener retroactiveListener;

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        configHandler = new ConfigHandler(this);

        Objects.requireNonNull(getCommand("mobdespawnfix")).setExecutor(new CommandHandler(this));
        Objects.requireNonNull(getCommand("mobdespawnfix")).setTabCompleter(new CommandHandler(this));

        getServer().getPluginManager().registerEvents(new PickupListener(this), this);
        retroactiveListener = new RetroactiveListener(this);
        getServer().getPluginManager().registerEvents(retroactiveListener, this);
        retroactiveListener.scanAllLoadedChunks();

        Bukkit.getLogger().info("");
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "  |_______|                             " +
                "  ");
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "  | Derex |     Mob Despawn Fix v1.0.0");
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "  |_______|     Running on " + Bukkit.getName() + " - " + Bukkit.getVersion());
        Bukkit.getLogger().info("");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void reloadConfigValues() {
        configHandler.reload();
        if (retroactiveListener != null) {
            retroactiveListener.scanAllLoadedChunks();
        }
    }

    public ConfigHandler getConfigHandler() {
        return configHandler;
    }
}

