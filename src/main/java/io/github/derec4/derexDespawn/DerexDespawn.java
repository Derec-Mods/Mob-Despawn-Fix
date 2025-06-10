package io.github.derec4.derexDespawn;

import io.github.derec4.derexDespawn.commands.CommandHandler;
import io.github.derec4.derexDespawn.handlers.ConfigHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class DerexDespawn extends JavaPlugin {
    private ConfigHandler configHandler;

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        configHandler = new ConfigHandler(this);

        Objects.requireNonNull(getCommand("ddespawn")).setExecutor(new CommandHandler(this));
        Objects.requireNonNull(getCommand("ddespawn")).setTabCompleter(new CommandHandler(this));


        Bukkit.getLogger().info("");
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "  |_______|                             " +
                "  ");
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "  | Derex |     Derex Despawn v1.0.0");
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "  |_______|     Running on " + Bukkit.getName() + " - " + Bukkit.getVersion());
        Bukkit.getLogger().info("");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void reloadConfigValues() {
        configHandler.reload();
    }

    public ConfigHandler getConfigHandler() {
        return configHandler;
    }
}
