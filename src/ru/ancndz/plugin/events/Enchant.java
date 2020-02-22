package ru.ancndz.plugin.events;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import ru.ancndz.plugin.Util;

public class Enchant implements Listener
{
	
    @EventHandler
    public void onEchestopen(final PlayerInteractEvent e) {
        final Block block = e.getClickedBlock();
        final Player p = e.getPlayer();
        //p.sendMessage(block.getBlockData().getAsString());
        //if (block != null && block.getType().equals((Object)Material.ENCHANTING_TABLE)) {
        if (block != null && block.getBlockData().getAsString().equalsIgnoreCase("minecraft:enchanting_table")) {
        	if (Util.debugMode() && p.hasPermission("rpgCraft.debug")) {
        		p.sendMessage("§b[rpgCraftPerm][Debug] Нужный доступ: rpgCraft.enchant");
            }
            if (!p.hasPermission("rpgCraft.enchant")) {
                e.setCancelled(true);
                Util.sendNoPermEnchant(p);
            }
        }
    }
     
    
}
