package ru.ancndz.plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.BlockInventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import ru.ancndz.plugin.events.AnimalFram;
import ru.ancndz.plugin.events.BreakBlock;
import ru.ancndz.plugin.events.Craft;
import ru.ancndz.plugin.events.UseBlock;

public class RpgCraftPermission extends JavaPlugin {
	public void onEnable() {
        this.getLogger().log(Level.INFO, "[rpgCraftPerm] rpgCraftPermission has been enabled!");
        
        this.getServer().getPluginManager().registerEvents((Listener)new Craft(), (Plugin)this);
        //this.getServer().getPluginManager().registerEvents((Listener)new Enchant(), (Plugin)this);
        //this.getServer().getPluginManager().registerEvents((Listener)new Brew(), (Plugin)this);
        //this.getServer().getPluginManager().registerEvents((Listener)new Smelt(), (Plugin)this);
        //this.getServer().getPluginManager().registerEvents((Listener)new Anvil(), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new UseBlock(), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new AnimalFram(), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new BreakBlock(), (Plugin)this);
        //Smelt.CancelSmeltProgress();
        this.getCommand("rpgcraft").setExecutor((CommandExecutor)new Verbose((Plugin)this));
        Util.setupPerms((Plugin)this);
        Configuration.load((Plugin)this);
    }
	

    public static ArrayList<String> getPermString(final ItemStack item) {
		Set<String> groupNames = Configuration.groups.getKeys(false); 
		ArrayList<String> answer = new ArrayList<String>();
		answer.add("rpgcraft.allowgroup.all.*");
		for(String name: groupNames) {
			for (int i = 0; i < 5; i++) {
				if (Configuration.groups.getStringList(name + "." + Integer.toString(i)).isEmpty()) { continue; }
				else {
					if (Configuration.groups.getStringList(name + "." + Integer.toString(i)).contains(item.getType().name().toLowerCase().replace("legacy_", ""))) {
						String permission = "rpgcraft.allowgroup." + name + "." + Integer.toString(i);
						answer.add(permission);
					}
				}
			}
		}
		return answer;
	}
	
	
	public static boolean isAllowed(final ItemStack item) {
        final ArrayList<ItemStack> items = getAllowedListToCraft();
        if (items.contains(item)) {
            return true;
        }
        for (final ItemStack i : items) {
            if (item.getType() == i.getType()) {
                return true;
            }
        }
        return false;
    }
	
	
	public static ArrayList<ItemStack> getAllowedListToCraft() {
		if (Configuration.allowedToCraft.isEmpty()) {
            return new ArrayList<ItemStack>();
        }
		final ArrayList<ItemStack> l = new ArrayList<ItemStack>();
        for (final String s : Configuration.allowedToCraft) {
        	String[] split = s.split("_", 2);
        	if (split[0].equalsIgnoreCase("wood")) {
        		l.add(toItemStack("oak_" + split[1]));
        		l.add(toItemStack("spruce_" + split[1]));
        		l.add(toItemStack("birch_" + split[1]));
        		l.add(toItemStack("jungle_" + split[1]));
        		l.add(toItemStack("acacia_" + split[1]));
        		l.add(toItemStack("dark_oak_" + split[1]));
        	} else l.add(toItemStack(s));
        }
		return l;
	}
	
	
	public static ArrayList<String> getDisallowedListToUse() {
		if (Configuration.disallowedToUse.isEmpty()) {
            return new ArrayList<String>();
        }
		final ArrayList<String> l = new ArrayList<String>();
        for (final String s : Configuration.disallowedToUse) {
            l.add(s);
        }
		return l;
	}
	
	
	public static ItemStack toItemStack(String arg0) {
        arg0 = arg0.toUpperCase();
        return new ItemStack(Material.getMaterial(arg0));
    }
	
	
	public static boolean hasperms(final ItemStack item, final HumanEntity human) {
     	for (final String s : RpgCraftPermission.getPermString(item)) {
             if (human.hasPermission(s)) { return true; }
         }
     	return false;
	 }
	
	
	public static boolean hasperms(final ItemStack item, final List<HumanEntity> humans)  {
		for (HumanEntity h : humans) {
			 if (!hasperms(item, h)) { return false; }
		 }
		return true;
	 }
	
	
	public static void giveHelpBook(Player p) {
    	
    	List<Double> cords = Configuration.configs.getDoubleList("helpChestCords");
    	
    	
    	if (cords.get(0) == 0 && cords.get(1) == 0 && cords.get(2) == 0) return;
    	
    	Location location = new Location(p.getWorld(), cords.get(0), cords.get(1), cords.get(2));
    	Block raw_block = location.getBlock();
    	BlockState state = raw_block.getState();
    	
    	if (state instanceof BlockInventoryHolder) {
    		
    		BlockInventoryHolder container = (BlockInventoryHolder) state;
    		
    		for (ItemStack item : container.getInventory().getContents()) {
    			if (item != null) p.getInventory().addItem(item);
    		}
        	p.updateInventory();
        	
    	} else {
    		if (Util.debugMode() && p.hasPermission("rpgcraft.debug")) {
    			p.sendMessage("Block in config is not container!");
    		}
    		p.sendMessage("Whops, no help books on server! Contact mods or admins.");
    	} 

	}
	
}
