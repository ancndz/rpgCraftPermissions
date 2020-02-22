package ru.ancndz.plugin.events;

import org.bukkit.event.EventHandler;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;

import ru.ancndz.plugin.RpgCraftPermission;
import ru.ancndz.plugin.Util;

import org.bukkit.event.Listener;

public class Craft implements Listener
{
    @EventHandler
    public void onCraft(final CraftItemEvent e) {
        final Player p = (Player)e.getWhoClicked();
        final ItemStack item = e.getInventory().getResult();
        
        final ArrayList<String> perms = RpgCraftPermission.getPermString(item);
        
        if (!RpgCraftPermission.isAllowed(item) && !RpgCraftPermission.hasperms(item, p)) {
            e.setCancelled(true);
            Util.sendNoPermMessage(p, item.getType().name().toLowerCase().replace("legacy_", ""));
        }
        if (Util.debugMode() && p.hasPermission("rpgCraft.debug")) {
        	String msg = "§b[rpgCraftPerm][Debug] Нужный доступ: ";
        	for (String s : perms) {
        		msg += "\n" + s;
        	}
            p.sendMessage(msg);
        }
    }
}
