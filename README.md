# rpgCraftPermissions
A plugin that allows you to create a group of items (name and access level) and permissions that are needed to create items from this group. Example:
groups: 
  Fisherman: 
    0: 
      - oak_door
  Woodman: 
    0: 
      - oak_door
    1: 
      - oak_sign
That config makes two groups: fisherman and woodman with 2 (max 5) access levels. Permission for create oak_door:
   - rpgcraft.allowgroup.all.*
   - rpgCraft.fisherman.0.oak_door
   - rpgCraft.woodman.0.oak_door
   
Items can be added to the "white list" and they will be available for everyone to create.
allowedItems: 
  - chest
  - crafting_table
  - oak_planks
  - stick

Also, this plugin makes it possible to give players permissions to use blocks (furnace, anvil, enchanting table, etc.) that are included in the "black list".
Blacklist example:
disallowedBlocks:
  - furnace
  - smoker
  - enchanting_table

You should have permissions for each of these block:
   - rpgcraft.use.*
   - rpgCraft.use.furnace
   - rpgCraft.use.smoker
   
Commands:
/rcp - get info about plugin
/rcp debug - toggle debug mode (shows needed permission)
/rcp reload - reload configuration
