package ru.ancndz.plugin;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import java.util.UUID;
import java.util.HashMap;
import org.bukkit.command.CommandExecutor;

public class Verbose implements CommandExecutor
{
    public static HashMap<UUID, Boolean> Verbose;
    
    static {
    	ru.ancndz.plugin.Verbose.Verbose = new HashMap<UUID, Boolean>();
    }

	private Plugin plugin;
    
    public Verbose(Plugin plugin) {
		this.plugin = plugin;
	}

	public boolean onCommand(final CommandSender sender, final Command cmd, final String id, final String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("[rpgCraftPerm] Команда не может быть выполнена сервером!");
            return true;
        }
        final Player p = (Player)sender;
        
        if (args.length == 0) {
        	sender.sendMessage("#####");
            sender.sendMessage(this.trans("&e&lАвтор плагина: ancndz."));
            sender.sendMessage(this.trans("&7Специально для проекта &l&6pcserver&r&7."));
            sender.sendMessage(this.trans("&7Have fun!"));
            sender.sendMessage("#####");
            if (p.hasPermission("rpgcraft.debug")) { sender.sendMessage(this.trans("&7Use [/rpgcraft debug] for toggle debug mode.")); }
            return false;
        }
        
        if (args.length > 0 && args[0].equalsIgnoreCase("debug") && p.hasPermission("rpgcraft.debug")) {
            if (!Configuration.configs.getBoolean("debug")) {
                p.sendMessage(this.trans("&a[rpgCraftPerm] Подсказки доступа активированы."));
                Configuration.configs.set("debug", true);
            }
            else {
                p.sendMessage(this.trans("&c[rpgCraftPerm] Подсказки доступа деактивированы."));
                Configuration.configs.set("debug", false);
            }
            this.plugin.saveConfig();
            return true;
        }
        
        if (args.length > 0 && args[0].equalsIgnoreCase("reload") && p.hasPermission("rpgcraft.reload")) {
        	plugin.reloadConfig();
            return true;
        }
        
        return false;
    }
    
    public String trans(final String arg0) {
        return ChatColor.translateAlternateColorCodes('&', arg0);
    }
}
