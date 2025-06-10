package io.github.derec4.derexDespawn.commands.sub;

import io.github.derec4.derexDespawn.DerexDespawn;
import org.bukkit.command.CommandSender;

public class PurgeCommand {
    private final DerexDespawn plugin;

    public PurgeCommand(DerexDespawn plugin) {
        this.plugin = plugin;
    }

    public void execute(CommandSender sender, String[] args) {
        sender.sendMessage("§ePurge functionality not implemented yet.");
    }
}