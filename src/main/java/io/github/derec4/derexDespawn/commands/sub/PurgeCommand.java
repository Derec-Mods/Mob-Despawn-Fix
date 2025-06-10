package io.github.derec4.derexDespawn.commands.sub;

import io.github.derec4.derexDespawn.DerexDespawn;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;

public class PurgeCommand {
    private final DerexDespawn plugin;

    public PurgeCommand(DerexDespawn plugin) {
        this.plugin = plugin;
    }

    public void execute(CommandSender sender, String[] args) {
        int purged = 0;

        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be used by players.");
        }

        Player player = (Player) sender;
        World world = player.getWorld();

        for (Entity entity : world.getEntities()) {
            if (!(entity instanceof Monster monster)) {
                continue;
            }

            boolean hasMainHand = !monster.getEquipment().getItemInMainHand().getType().isAir();
            boolean hasOffHand = !monster.getEquipment().getItemInOffHand().getType().isAir();
            if (!hasMainHand || !hasOffHand) {
                continue;
            }

            boolean isNamed = monster.isCustomNameVisible() || monster.getCustomName() != null;
            if (isNamed) {
                continue;
            }

            if (!plugin.getConfigHandler().isDespawnBoat()) {
                continue;
            }

            boolean inBoat = false;
            for (Entity vehicle = monster.getVehicle(); vehicle != null; vehicle =
                    vehicle.getVehicle()) {
                if (vehicle instanceof Boat) {
                    inBoat = true;
                    break;
                }
            }

            if (inBoat) {
                continue;
            }

            monster.remove();
            purged++;
        }
        sender.sendMessage("§aPurged " + purged + " monsters");

    }

}