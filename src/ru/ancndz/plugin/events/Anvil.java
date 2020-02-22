package ru.ancndz.plugin.events;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.ItemStack;

import ru.ancndz.plugin.RpgCraftPermission;
import ru.ancndz.plugin.Util;

public class Anvil implements Listener {
	
	@EventHandler
    public void OnAnvil(final PrepareAnvilEvent e) {
		
		final List<HumanEntity> p = e.getViewers();
		
		        
		final ItemStack item = e.getResult();
		        
		final ArrayList<String> perms = RpgCraftPermission.getPermString(item);
		        
		if (!item.getType().isAir()) {
			
			if (!RpgCraftPermission.isAllowed(item) && !RpgCraftPermission.hasperms(item, p)) {
		            e.setResult(null);;
		            //Util.sendNoPermMessage(p, item.getType().name().toLowerCase().replace("legacy_", ""));
		        }
		        if (Util.debugMode()) {
		        	for (HumanEntity human: p) {
			        	if (human.hasPermission("rpgCraft.debug")) {
				        	String msg = "§b[rpgCraftPerm][Debug] Нужный доступ: ";
				        	for (String s : perms) { msg += "\n" + s; }
				        	((Player)human).sendMessage(msg);
			        	} 
			        } 
		        }
		}
		
    }
	
}
