# Mob Despawn Fix

A modern, lightweight Minecraft server optimization plugin (Spigot / Paper 1.13.2–1.21+) inspired by *Let Me Despawn* (LMD).

## What Problem Does This Solve?
In vanilla Minecraft, when a mob (such as a zombie, skeleton, or drowned) picks up an item from the ground—like rotten flesh, arrows, or eggs—vanilla sets `persistenceRequired = true` (`setRemoveWhenFarAway(false)`). 
Because of this, mobs hoarded in dark caves never despawn naturally and don't count toward the mob cap, slowly deteriorating server performance and causing significant TPS loss over time.

## How It Works
* **Natural Despawning**: Reverts mobs that pick up whitelist items back to natural despawning rules (`setRemoveWhenFarAway(true)`).
* **Safe by Default**:
  * **Whitelist Mode**: Only junk/cave floor items (`rotten_flesh`, `bone`, `arrow`, `egg`, etc.) trigger despawning. Player valuables (`diamond`, `netherite_*`, `elytra`) keep mobs persistent so gear is never lost upon death.
  * **Name Protection**: Name-tagged mobs and anvil-renamed items are completely ignored and preserved.
  * **Vehicle Safety**: Mobs in boats, minecarts, or other vehicles are protected from despawning.
* **Retroactive Cleanup**: Can scan loaded chunks on startup or load to fix pre-existing cave hoarders from older worlds.

## Commands & Permissions
* `/mobdespawnfix reload` (alias `/mdf reload`, `/ddespawn reload`) — Reloads the configuration.
* `/mobdespawnfix purge` (alias `/mdf purge`) — Safely purges persistent hoarders holding non-protected items.
* `/mobdespawnfix info` (alias `/mdf info`) — Displays live monster counts and persistent hoarder diagnostics.
* **Permission**: `mobdespawnfix.use`
