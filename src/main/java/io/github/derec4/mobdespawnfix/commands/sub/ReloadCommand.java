package io.github.derec4.mobdespawnfix.commands.sub;

import io.github.derec4.mobdespawnfix.MobDespawnFix;
import org.bukkit.command.CommandSender;

public class ReloadCommand {
    private final MobDespawnFix plugin;

    public ReloadCommand(MobDespawnFix plugin) {
        this.plugin = plugin;
    }

    public void execute(CommandSender sender, String[] args) {
        plugin.reloadConfigValues();
        sender.sendMessage("§aMob Despawn Fix config reloaded.");
    }
}

