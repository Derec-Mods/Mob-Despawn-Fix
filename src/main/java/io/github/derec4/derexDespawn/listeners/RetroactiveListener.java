package io.github.derec4.derexDespawn.listeners;

import io.github.derec4.derexDespawn.DerexDespawn;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

public class RetroactiveListener implements Listener {
    private final DerexDespawn plugin;

    public RetroactiveListener(DerexDespawn plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onChunkLoad(ChunkLoadEvent event) {
        if (!plugin.getConfigHandler().isRetroactiveDespawnCheck()) {
            return;
        }
        scanChunk(event.getChunk());
    }

    public void scanAllLoadedChunks() {
        if (!plugin.getConfigHandler().isRetroactiveDespawnCheck()) {
            return;
        }
        for (World world : Bukkit.getWorlds()) {
            for (Chunk chunk : world.getLoadedChunks()) {
                scanChunk(chunk);
            }
        }
    }

    private void scanChunk(Chunk chunk) {
        for (Entity entity : chunk.getEntities()) {
            if (!(entity instanceof Monster)) {
                continue;
            }
            Monster monster = (Monster) entity;
            if (monster.getRemoveWhenFarAway()) {
                continue;
            }

            if (plugin.getConfigHandler().isIgnoreNamedMobs() && (monster.getCustomName() != null || monster.isCustomNameVisible())) {
                continue;
            }

            if (!plugin.getConfigHandler().isDespawnVehicle() && monster.isInsideVehicle()) {
                continue;
            }

            EntityEquipment equipment = monster.getEquipment();
            if (equipment == null || !hasAnyEquipment(equipment)) {
                continue;
            }

            if (hasProtectedItem(equipment)) {
                continue;
            }

            if (plugin.getConfigHandler().isAggressiveDespawn()) {
                monster.remove();
            } else {
                monster.setRemoveWhenFarAway(true);
            }
        }
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

