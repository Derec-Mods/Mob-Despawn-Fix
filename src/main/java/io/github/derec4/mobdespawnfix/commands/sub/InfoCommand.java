package io.github.derec4.mobdespawnfix.commands.sub;

import io.github.derec4.mobdespawnfix.MobDespawnFix;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class InfoCommand {
    private final MobDespawnFix plugin;

    public InfoCommand(MobDespawnFix plugin) {
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

        int totalMonsters = 0;
        int persistentMonsters = 0;
        int holdingItemHoarders = 0;
        int namedMonsters = 0;
        int vehicleMonsters = 0;

        for (World world : worlds) {
            for (Entity entity : world.getEntities()) {
                if (!(entity instanceof Monster)) {
                    continue;
                }
                Monster monster = (Monster) entity;
                totalMonsters++;

                boolean isNamed = monster.getCustomName() != null || monster.isCustomNameVisible();
                if (isNamed) {
                    namedMonsters++;
                }

                if (monster.isInsideVehicle()) {
                    vehicleMonsters++;
                }

                if (!monster.getRemoveWhenFarAway()) {
                    persistentMonsters++;

                    EntityEquipment equipment = monster.getEquipment();
                    if (equipment != null && hasAnyEquipment(equipment)) {
                        holdingItemHoarders++;
                    }
                }
            }
        }

        sender.sendMessage("§6=== Mob Despawn Fix Info ===");
        sender.sendMessage("§7Total Monsters: §f" + totalMonsters);
        sender.sendMessage("§7Persistent Monsters: §e" + persistentMonsters);
        sender.sendMessage("§7Holding Items (Hoarders): §c" + holdingItemHoarders);
        sender.sendMessage("§7Named Monsters: §a" + namedMonsters);
        sender.sendMessage("§7In Vehicles: §a" + vehicleMonsters);
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
        return item != null && item.getType() != Material.AIR && !item.getType().name().endsWith("_AIR");
    }
}

