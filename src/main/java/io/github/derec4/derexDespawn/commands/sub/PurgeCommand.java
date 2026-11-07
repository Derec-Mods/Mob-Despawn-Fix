package io.github.derec4.derexDespawn.commands.sub;

import io.github.derec4.derexDespawn.DerexDespawn;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
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

                if (!plugin.getConfigHandler().isDespawnVehicle() && monster.isInsideVehicle()) {
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

                if (hasEquip && hasProtectedItem(equipment)) {
                    continue;
                }

                monster.remove();
                purged++;
            }
        }

        sender.sendMessage("§aPurged " + purged + " monsters.");
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

    private boolean hasProtectedItem(EntityEquipment equipment) {
        return isItemProtected(equipment.getItemInMainHand())
                || isItemProtected(equipment.getItemInOffHand())
                || isItemProtected(equipment.getHelmet())
                || isItemProtected(equipment.getChestplate())
                || isItemProtected(equipment.getLeggings())
                || isItemProtected(equipment.getBoots());
    }

    private boolean isItemProtected(ItemStack item) {
        if (!isItemPresent(item)) {
            return false;
        }
        if (plugin.getConfigHandler().isIgnoreNamedItems() && item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
            return true;
        }
        return !plugin.getConfigHandler().isItemAllowedToDespawn(item.getType());
    }
}