package ru.ancndz.plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

import org.bukkit.Material;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.Listener;
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
            l.add(toItemStack(s));
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
}
