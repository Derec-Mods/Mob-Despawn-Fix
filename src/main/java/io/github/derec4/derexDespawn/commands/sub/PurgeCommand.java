package io.github.derec4.derexDespawn.commands.sub;

import io.github.derec4.derexDespawn.DerexDespawn;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class PurgeCommand {
    private final DerexDespawn plugin;

    public PurgeCommand(DerexDespawn plugin) {
        this.plugin = plugin;
    }

    public void execute(CommandSender sender, String[] args) {
        List<World> worlds = new ArrayList<>();
        if (sender instanceof Player) {
            Player player = (Player) sender;
            worlds.add(player.getWorld());
        } else {
            worlds.addAll(Bukkit.getWorlds());
        }

        int purged = 0;
        for (World world : worlds) {
            for (Entity entity : world.getEntities()) {
                if (!(entity instanceof Monster)) {
                    continue;
                }
                Monster monster = (Monster) entity;

                if (plugin.getConfigHandler().isIgnoreNamedMobs() && (monster.getCustomName() != null || monster.isCustomNameVisible())) {
                    continue;
                }

                if (!plugin.getConfigHandler().isDespawnBoat() && isInBoat(monster)) {
                    continue;
                }

                EntityEquipment equipment = monster.getEquipment();
                if (equipment == null) {
                    continue;
                }

                boolean hasEquip = hasAnyEquipment(equipment);
                if (!hasEquip && monster.getRemoveWhenFarAway()) {
                    continue;
                }

                if (hasEquip && isBlacklisted(equipment)) {
                    continue;
                }

                monster.remove();
                purged++;
            }
        }

        sender.sendMessage("§aPurged " + purged + " monsters.");
    }

    private boolean isInBoat(LivingEntity entity) {
        for (Entity vehicle = entity.getVehicle(); vehicle != null; vehicle = vehicle.getVehicle()) {
            if (vehicle instanceof Boat) {
                return true;
            }
        }
        return false;
    }

    private boolean hasAnyEquipment(EntityEquipment equipment) {
        return isItemPresent(equipment.getItemInMainHand())
                || isItemPresent(equipment.getItemInOffHand())
                || isItemPresent(equipment.getHelmet())
                || isItemPresent(equipment.getChestplate())
                || isItemPresent(equipment.getLeggings())
                || isItemPresent(equipment.getBoots());
    }

    private boolean isItemPresent(ItemStack item) {
        return item != null && !item.getType().isAir();
    }

    private boolean isBlacklisted(EntityEquipment equipment) {
        if (plugin.getConfigHandler().getBlacklist().isEmpty()) {
            return false;
        }
        return isItemBlacklisted(equipment.getItemInMainHand())
                || isItemBlacklisted(equipment.getItemInOffHand())
                || isItemBlacklisted(equipment.getHelmet())
                || isItemBlacklisted(equipment.getChestplate())
                || isItemBlacklisted(equipment.getLeggings())
                || isItemBlacklisted(equipment.getBoots());
    }

    private boolean isItemBlacklisted(ItemStack item) {
        return item != null && plugin.getConfigHandler().getBlacklist().contains(item.getType());
    }
}