package io.github.derec4.derexDespawn.listeners;

import io.github.derec4.derexDespawn.DerexDespawn;
import org.bukkit.Bukkit;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
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

        if (plugin.getConfigHandler().getBlacklist().contains(item.getType())) {
            return;
        }

        if (!plugin.getConfigHandler().isDespawnBoat() && isInBoat(entity)) {
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

    private boolean isInBoat(LivingEntity entity) {
        for (Entity vehicle = entity.getVehicle(); vehicle != null; vehicle = vehicle.getVehicle()) {
            if (vehicle instanceof Boat) {
                return true;
            }
        }
        return false;
    }
}

