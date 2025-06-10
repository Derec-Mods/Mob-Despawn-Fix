package io.github.derec4.derexDespawn.commands.sub;

import io.github.derec4.derexDespawn.DerexDespawn;
import org.bukkit.command.CommandSender;

public class ReloadCommand {
    private final DerexDespawn plugin;

    public ReloadCommand(DerexDespawn plugin) {
        this.plugin = plugin;
    }

    public void execute(CommandSender sender, String[] args) {
        plugin.reloadConfigValues();
        sender.sendMessage("§aDerex Despawn config reloaded.");
    }}
