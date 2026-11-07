> If this project helped you, please consider leaving some stars [on the Github](https://github.com/Derec-Mods/Mob-Despawn-Fix)! 

[![Support me on Ko-fi](https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/compact/donate/kofi-singular-alt_vector.svg)](https://ko-fi.com/derexxd) 

# Mob Despawn Fix

A modern, high-performance Spigot/Paper server optimization plugin that eliminates subterranean mob-hoarding lag caused by monsters picking up ground items.

Inspired by [Let Me Despawn](https://github.com/frikinjay/let-me-despawn) by frikinjay and its earlier Paper fork by contionability. This project is a modern, clean-room Spigot/Paper rewrite designed from the ground up to support modern Minecraft versions (1.13.2 through 1.21+) while keeping worlds completely lag-free.

---

## Description

In vanilla Minecraft, whenever a hostile mob (such as a Zombie, Skeleton, or Drowned) walks over and equips or picks up any dropped item from the ground, vanilla sets `PersistenceRequired: 1b` (`setRemoveWhenFarAway(false)`).

Because of this mechanic, when players spend extended periods of time in an area, dark caves and aquifers beneath their bases gradually accumulate dozens or even hundreds of monsters that scooped up mob drops (rotten flesh, arrows, bones), chicken jockey eggs, or broken gravel. 

These monsters:
* **Never despawn naturally** when players move away.
* **Do not count towards the natural mob cap**, allowing new monsters to continuously spawn and join the permanent horde.
* **Constantly consume server CPU ticks** calculating pathfinding, line-of-sight, and collisions, slowly dragging down TPS and causing massive entity lag over long-term survival worlds.

**Mob Despawn Fix** dynamically overrides this persistence, allowing mobs that hoard junk items to despawn naturally like ordinary monsters, without putting player equipment, named mobs, or farms at risk.

---

## Features

* **Natural Despawning:** Hostile mobs picking up ground items remain subject to standard vanilla despawn distances (despawning naturally when players move 32–128+ blocks away) instead of becoming permanently persistent.
* **Safe by Default (Whitelist Mode):** Configured with `mode: whitelist` out of the box. Only natural cave debris and mob drops (`rotten_flesh`, `bone`, `arrow`, `egg`, `string`, `spider_eye`, `gunpowder`, `gravel`, `dirt`, `torch`, etc.) allow despawning.
* **Player Gear Protection:** If a player dies in a cave and a monster picks up their Diamond/Netherite gear, tools, or valuables, the monster retains full vanilla persistence so the player can return and recover their items.
* **Name Tag & Farm Safeguards:** Any monster given a custom name via a Name Tag, or holding an item renamed in an anvil, is strictly preserved.
* **Vehicle Protection:** Mobs contained inside boats, minecarts, or any vehicle are protected from despawning (ideal for iron farms, trading halls, and mob cages).
* **Retroactive Cave Cleanup:** When added to established worlds where hundreds of mobs are already hoarding in caves, the plugin scans loaded chunks and cleanses pre-existing persistence from un-named hoarders.
* **Live In-Game Diagnostics:** Includes built-in scanning tools (`/mdf info`) to diagnose hoarded mobs in loaded chunks, and a manual purge command (`/mdf purge`).
* **Zero NMS / Ultra-Compatible:** Pure Spigot/Paper API implementation with zero bytecode manipulation or volatile NMS dependencies, ensuring broad cross-version stability (1.13.2 to 1.21+).

---

## Commands & Permissions

All commands support the primary `/mobdespawnfix` command as well as the `/mdf` and `/ddespawn` aliases.

| Command | Description | Permission |
| :--- | :--- | :--- |
| `/mdf info` | Scans loaded chunks and reports total monsters, persistent mobs, and hoarders. | `mobdespawnfix.use` |
| `/mdf purge` | Manually purges non-protected persistent hoarders across loaded chunks. | `mobdespawnfix.use` |
| `/mdf reload` | Reloads `config.yml` and updates whitelist/blacklist definitions on the fly. | `mobdespawnfix.use` |

---

## Configuration

```yaml
# Mode can be 'whitelist' (only listed items allow despawn) or 'blacklist' (all items allow despawn except listed ones)
mode: whitelist

# Items mobs pick up that will allow them to despawn (used when mode: whitelist)
whitelist:
  - rotten_flesh
  - bone
  - arrow
  - string
  - spider_eye
  - gunpowder
  - slime_ball
  - glow_ink_sac
  - ink_sac
  - ender_pearl
  - egg
  - feather
  - raw_chicken
  - gravel
  - flint
  - dirt
  - cobblestone
  - stone
  - torch
  - redstone_torch
  - coal
  - raw_copper
  - raw_iron
  - kelp
  - seagrass

# Items mobs pick up that will NEVER despawn (used when mode: blacklist)
blacklist:
  - diamond
  - diamond_sword
  - diamond_helmet
  - diamond_chestplate
  - diamond_leggings
  - diamond_boots
  - netherite_ingot
  - netherite_sword
  - netherite_helmet
  - netherite_chestplate
  - netherite_leggings
  - netherite_boots
  - totem_of_undying
  - elytra

# Despawn mobs in vehicles (boats, minecarts, etc.)
despawn-vehicle: false

# Despawn existing mobs that were marked persistent via holding items
retroactive-despawn-check: false

prevent-pickup-persistence: true
ignore-named-mobs: true
ignore-named-items: true
```

---

## Why was this made?

6 years ago, I made a post on [r/Minecraft](https://www.reddit.com/r/Minecraft/comments/l1sjgv/my_world_is_filled_with_persistent_hostile_mobs/):

> */effect give @e[nbt= {PersistenceRequired:1b}] minecraft:glowing*
> 
> *This returned around 600 targets somehow, and showed a bunch of underground mobs that never despawned either. This is starting to make my world lag and I'm wondering why it's happening.*

For years, survival worlds and servers have suffered from this exact mechanic—zombies hoarding chicken eggs, chicken jockey drops, and stray arrows in unlit caves below bases. 

While the original *Let Me Despawn* mod tackled this on Forge/Fabric, the Paper/Spigot plugin version had not received any updates in over 4 years. We created this modern clean-room implementation to solve the problem for modern servers with clean code, safe defaults, and zero bloat.

---

## ❤️ Credits

* **Original Mod Concept & Inspiration:** [frikinjay](https://github.com/frikinjay) ([Let Me Despawn](https://github.com/frikinjay/let-me-despawn))
* **Paper Port Inspiration:** [contionability](https://github.com/contionability)
* **Author & Maintainer:** [DerexXD / DereC4](https://github.com/DereC4) ([Derec-Mods](https://github.com/Derec-Mods))

This project is open-source and licensed under the **GNU Lesser General Public License v3.0 (LGPL-3.0)**.
