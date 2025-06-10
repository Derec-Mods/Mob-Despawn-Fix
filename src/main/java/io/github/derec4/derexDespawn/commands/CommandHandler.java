package io.github.derec4.derexDespawn.commands;

import io.github.derec4.derexDespawn.DerexDespawn;
import io.github.derec4.derexDespawn.commands.sub.ReloadCommand;
import io.github.derec4.derexDespawn.commands.sub.PurgeCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CommandHandler implements TabExecutor {
    private final DerexDespawn plugin;
    private final ReloadCommand reloadCommand;
    private final PurgeCommand purgeCommand;

    public CommandHandler (DerexDespawn plugin) {
        this.plugin = plugin;
        this.reloadCommand = new ReloadCommand(plugin);
        this.purgeCommand = new PurgeCommand(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("§cUsage: /ddespawn <subcommand>");
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "reload":
                reloadCommand.execute(sender, args);
                break;
            case "purge":
                purgeCommand.execute(sender, args);
                break;
            default:
                sender.sendMessage("§cUnknown subcommand. Use /ddespawn <subcommand>");
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return Arrays.asList("reload", "purge");
        }
        return Collections.emptyList();
    }
}