package io.github.derec4.derexDespawn.listeners;

import io.github.derec4.derexDespawn.DerexDespawn;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PickupListener implements Listener {
    private final DerexDespawn plugin;

    public PickupListener(DerexDespawn plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEntityPickup(EntityPickupItemEvent event) {
        if (!plugin.getConfigHandler().isPreventPickupPersistence()) {
            return;
        }

        LivingEntity entity = event.getEntity();
        if (!(entity instanceof Monster)) {
            return;
        }

        if (plugin.getConfigHandler().isIgnoreNamedMobs() && (entity.getCustomName() != null || entity.isCustomNameVisible())) {
            return;
        }

        ItemStack item = event.getItem().getItemStack();
        if (plugin.getConfigHandler().isIgnoreNamedItems()) {
            ItemMeta meta = item.getItemMeta();
            if (meta != null && meta.hasDisplayName()) {
                return;
            }
        }

        if (!plugin.getConfigHandler().isItemAllowedToDespawn(item.getType())) {
            return;
        }

        if (!plugin.getConfigHandler().isDespawnVehicle() && entity.isInsideVehicle()) {
            return;
        }

        if (plugin.getConfigHandler().isAggressiveDespawn()) {
            entity.remove();
            return;
        }

        Bukkit.getScheduler().runTask(plugin, () -> {
            if (entity.isValid()) {
                entity.setRemoveWhenFarAway(true);
            }
        });
    }
}

