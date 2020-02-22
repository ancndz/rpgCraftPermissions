package ru.ancndz.plugin;

import java.util.List;
import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;

import net.milkbowl.vault.permission.Permission;

public class Util
{
    
    public static void setupPerms(Plugin serverplugin) {
        if (serverplugin.getServer().getPluginManager().getPlugin("Vault").isEnabled()) {
            serverplugin.getLogger().log(Level.INFO, "Vault detected...");
            final RegisteredServiceProvider<Permission> rsp = (RegisteredServiceProvider<Permission>)serverplugin.getServer().getServicesManager().getRegistration((Class)Permission.class);
        }
    }
    
    
    public static boolean debugMode() {
        return Configuration.configs.getBoolean("debug");
    }
    
    
    public static void sendNoPermMessage(final Player p, String itemname) {
        if (Configuration.configs.getString("message").isEmpty() || !Configuration.configs.getBoolean("notifyPlayer")) {
            return;
        }
        p.sendMessage(Configuration.configs.getString("message").replaceAll("&", "§").replaceAll("%item%", itemname));
    }
    
    
    public static void sendNoPermMessage(final List<HumanEntity> humans, String itemname) {
    	if (Configuration.configs.getString("message").isEmpty() || !Configuration.configs.getBoolean("notifyPlayer")) {
            return;
        }
        for (HumanEntity p : humans) {
        	sendNoPermMessage((Player)p, itemname);
        }
    }
    

	public static void sendNoPermBrew(Player p) {
		if (!Configuration.configs.getBoolean("notifyPlayer")) {
            return;
        }
		p.sendMessage("&7[rpgCraftPerm] &cВы не умеете варить зелья!");
	}

	
	public static void sendNoPermEnchant(Player p) {
		if (!Configuration.configs.getBoolean("notifyPlayer")) {
            return;
        }
		p.sendMessage("&7[rpgCraftPerm] &cВы не умееть накладывать заклинания!");
	}


	public static void sendNoPermFurnace(Player p, String string) {
		if (!Configuration.configs.getBoolean("notifyPlayer")) {
            return;
        }
		p.sendMessage("&7[rpgCraftPerm] &cВы не умееть использовать печь " + string + "!");
	}


	public static void sendNoPermUse(Player p, String name) {
		if (!Configuration.configs.getBoolean("notifyPlayer")) {
            return;
        }
		p.sendMessage(trans("&7[rpgCraftPerm] &cВы не умееть использовать " + name + "!"));
	}
	
	
	public static String trans(final String arg0) {
        return ChatColor.translateAlternateColorCodes('&', arg0);
    }
	
}
