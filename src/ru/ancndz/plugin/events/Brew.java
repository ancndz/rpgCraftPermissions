package ru.ancndz.plugin.events;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import ru.ancndz.plugin.Util;


public class Brew implements Listener
{
    @EventHandler
    public void onBrewing(final PlayerInteractEvent e) {
        final Block block = e.getClickedBlock();
        final Player p = e.getPlayer();
        if (block != null && block.getType().equals((Object)Material.BREWING_STAND)) {
        	
            if (Util.debugMode() && p.hasPermission("rpgCraft.debug")) {
                p.sendMessage("§b[rpgCraftPerm][Debug] Нужный доступ: rpgCraft.brew");
            }
            
            if (!p.hasPermission("rpgCraft.brew")) {
                e.setCancelled(true);
                Util.sendNoPermBrew(p);
            }
        }
    }
}
