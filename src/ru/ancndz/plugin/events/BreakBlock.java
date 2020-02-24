package ru.ancndz.plugin.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BreakBlock implements Listener{
	
	@EventHandler
	public void OnBreak(BlockBreakEvent e) {
		
		Player p = e.getPlayer();
		Material block = e.getBlock().getBlockData().getMaterial();
		
		if (block.equals(Material.WHEAT) || block.equals(Material.BEETROOTS) ||
				block.equals(Material.POTATOES) || block.equals(Material.CARROTS)) {
			
			if (!p.hasPermission("rpgcraft.collect.food") || !p.hasPermission("rpgcraft.collect.*")) {
				e.setDropItems(false);
				p.sendMessage("it was your food...");
			}
		}
		
		
		if (block.equals(Material.NETHER_WART)) {
			if (!p.hasPermission("rpgcraft.collect.ing") || !p.hasPermission("rpgcraft.collect.*")) {
				e.setDropItems(false);
				p.sendMessage("it was your ingridients...");
			}
		}
	}

}
