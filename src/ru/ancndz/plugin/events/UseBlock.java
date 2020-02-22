package ru.ancndz.plugin.events;

import java.util.regex.Pattern;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import ru.ancndz.plugin.Configuration;
import ru.ancndz.plugin.Util;

public class UseBlock implements Listener{
	
	@EventHandler
    public void onEch(final PlayerInteractEvent e) {
        final Block block = e.getClickedBlock();
        
    	
        if (block == null) return;
        
        if (!block.getBlockData().getMaterial().isInteractable()) return;
        
        String block_name = block.getBlockData().getAsString().split(Pattern.quote("["))[0].replaceAll("minecraft:", "");
        
        
        if (Configuration.disallowedToUse.contains(block_name)) {
        	
        	final Player p = e.getPlayer();
        	
		        if (!p.hasPermission("rpgcraft.use." + block_name) || p.hasPermission("rpgcraft.use.*")) {
			        e.setCancelled(true);
			        Util.sendNoPermUse(p, block_name);
	            }      
        	
	        
	        if (Util.debugMode() && p.hasPermission("rpgcraft.debug")) {
	        	p.sendMessage("§b[rpgCraftPerm][Debug] Нужный доступ: rpgCraft." + block_name);
	        }
        }
    }
}
