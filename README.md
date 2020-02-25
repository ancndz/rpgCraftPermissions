# rpgCraftPermission

Version 1.3.0!

Harvest
Now you can give player permission to harvest: food and ingredients (so far only Nether Wart). The player will be able to break the crop at any stage: but if he does not have permissions, he will not receive anything (note that if the crop destroys by the piston or water, there will be a drop). Also, a crop cannot be harvested without a hoe.

   - rpgcraft.collect.*
   - rpgCraft.collect.food
   - rpgCraft.collect.ing

Breeding
Now you can give players permission to breed animals. There are three categories - farming pets (cows, pigs, etc.), help pets (wolves, ocelots, etc.) and mounts. Each of these groups needs permission, without which it will not be possible to interact with the animal. Also, by the requests, the setting of the last group has been added: when you turn off this setting, the restrictions on interaction would be removed.
You can still ride a pig :)

   - rpgcraft.breed.farm
   - rpgcraft.breed.helpers
   - rpgcraft.breed.horse

Help items
There is new setting - helpChestCords. This is the coordinates of the help chest (x y z). Put a chest (or another container) in the world, put items in it and each player who type /rcp help will receive these items!

   - /rcp help (works only with non-zero cords.)

Legacy

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