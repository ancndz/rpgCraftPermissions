package ru.ancndz.plugin.events;


import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Furnace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import ru.ancndz.plugin.RpgCraftPermission;
import ru.ancndz.plugin.Util;

public class Smelt implements Listener
{
	
    private static Plugin plugin;
    
    static {
        Smelt.plugin = (Plugin)JavaPlugin.getPlugin((Class)ru.ancndz.plugin.RpgCraftPermission.class);
    }
    
    @EventHandler
    public void BlockPlaceEvent(final BlockPlaceEvent e) {
        e.getBlockPlaced().setMetadata("playerSmelt", (MetadataValue)new FixedMetadataValue(Smelt.plugin, (Object)e.getPlayer().getName()));
    }
    
    @EventHandler
    public void FurnaceSmeltEvent(final FurnaceSmeltEvent e) {
        if (e.getBlock().getMetadata("playerSmelt").isEmpty()) {
            return;
        }
        final String playerName = e.getBlock().getMetadata("playerSmelt").get(0).asString();
        final Player p = Smelt.plugin.getServer().getPlayer(playerName);
        if (p != null) {
        	final ItemStack item = e.getSource();
        	final ArrayList<String> perms = RpgCraftPermission.getPermString(item);
            if (!RpgCraftPermission.isAllowed(item) && !RpgCraftPermission.hasperms(item, p)) {
                e.setCancelled(true);
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
    
    @EventHandler
    public void FurnaceBurnEvent(final FurnaceBurnEvent e) {
        if (e.getBlock().getMetadata("playerSmelt").isEmpty()) {
            return;
        }
        final String playerName = e.getBlock().getMetadata("playerSmelt").get(0).asString();
        final Player p = Smelt.plugin.getServer().getPlayer(playerName);
        if (p != null) {
            final Furnace furnace = (Furnace)e.getBlock().getState();
            //final String perm = "ExtraPermissions.smelt." + furnace.getInventory().getSmelting().getType().toString();
            final ItemStack item = furnace.getInventory().getSmelting();
        	//final ArrayList<String> perms = RpgCraftPermission.getPermString(item);
            if (!RpgCraftPermission.isAllowed(item) && !RpgCraftPermission.hasperms(item, p)) {
                e.setCancelled(true);
            }
        }
    }
    
    @EventHandler
    public void InventoryClickEvent(final InventoryClickEvent e) {
        final Player p = (Player)e.getWhoClicked();
        if ((e.getInventory().getType().equals((Object)InventoryType.FURNACE) || e.getInventory().getType().equals((Object)InventoryType.SMOKER) || e.getInventory().getType().equals((Object)InventoryType.BLAST_FURNACE))
        		&& !e.getCursor().getType().equals((Object)Material.AIR) && e.getInventory().getLocation() != null && e.getRawSlot() >= 0 && e.getRawSlot() <= 2) {
        	if (Util.debugMode() && p.hasPermission("rpgCraft.debug")) {
                final String perm = "rpgCraft.allowgroup.group.level." + e.getCursor().getType().toString();
                p.sendMessage("§b[rpgCraftPerm][Debug] Нужный доступ: " + perm);
            }
            final Block furnace = e.getInventory().getLocation().getBlock();
            if (furnace.hasMetadata("playerSmelt")) {
                furnace.removeMetadata("playerSmelt", Smelt.plugin);
            }
            furnace.setMetadata("playerSmelt", (MetadataValue)new FixedMetadataValue(Smelt.plugin, (Object)p.getName()));
        }
    }
    
    @EventHandler
    public void InventoryOpenEvent(final InventoryOpenEvent e) {
        if (e.getInventory().getType().equals((Object)InventoryType.FURNACE) || e.getInventory().getType().equals((Object)InventoryType.SMOKER) || e.getInventory().getType().equals((Object)InventoryType.BLAST_FURNACE)) {
            final Location furnaceloc = e.getInventory().getLocation();
            if (furnaceloc.getBlock().getMetadata("playerSmelt").isEmpty()) {
                return;
            }
            if (furnaceloc != null) {
                final String playerName = furnaceloc.getBlock().getMetadata("playerSmelt").get(0).asString();
                final Player p = Smelt.plugin.getServer().getPlayer(playerName);
                final Furnace furnace = (Furnace)furnaceloc.getBlock().getState();
                if (furnace.getInventory().getSmelting() != null) {
                	final ItemStack item = furnace.getInventory().getSmelting();
                    if (!RpgCraftPermission.isAllowed(item) && !RpgCraftPermission.hasperms(item, p)) {
                        furnace.setCookTime((short)0);
                        furnace.update();
                    }
                }
            }
        }
    }
    
    @EventHandler
    public void BlockBreakEvent(final BlockBreakEvent e) {
        if (e.getBlock().hasMetadata("playerSmelt")) {
            e.getBlock().removeMetadata("playerSmelt", Smelt.plugin);
        }
    }
    
    public static void CancelSmeltProgress() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Smelt.plugin, (Runnable)new Runnable() {
            @Override
            public void run() {
                for (final Player player : Smelt.plugin.getServer().getOnlinePlayers()) {
                    if (player.getOpenInventory().getTopInventory().getType().equals((Object)InventoryType.FURNACE) ||
                    		player.getOpenInventory().getTopInventory().getType().equals((Object)InventoryType.SMOKER) ||
                    		player.getOpenInventory().getTopInventory().getType().equals((Object)InventoryType.BLAST_FURNACE)) {
                        final Location furnaceloc = player.getOpenInventory().getTopInventory().getLocation();
                        if (furnaceloc == null) {
                            continue;
                        }
                        final Furnace furnace = (Furnace)furnaceloc.getBlock().getState();
                        if (furnaceloc.getBlock().getMetadata("playerSmelt").isEmpty()) {
                            return;
                        }
                        if (furnace.getInventory().getSmelting() == null || furnace.getMetadata("playerSmelt").isEmpty()) {
                            continue;
                        }
                        final String playerName = furnaceloc.getBlock().getMetadata("playerSmelt").get(0).asString();
                        final Player p = Smelt.plugin.getServer().getPlayer(playerName);
                        final ItemStack item = furnace.getInventory().getSmelting();
                        if (RpgCraftPermission.isAllowed(item) || RpgCraftPermission.hasperms(item, p)) {
                            continue;
                        }
                        furnace.setCookTime((short)(-10));
                        furnace.update();
                    }
                }
            }
        }, 0L, 15L);
    }
    
    /*
	@EventHandler
    public void PlayerInteractEvent(final PlayerInteractEvent e) {
        final Block block = e.getClickedBlock();
        final Player p = e.getPlayer();
        if (block != null && block.getType().equals((Object)Material.FURNACE)) {
        	if (Util.debugMode() && p.hasPermission("rpgCraft.debug")) {
        		p.sendMessage("§b[rpgCraftPerm][Debug] Нужный доступ: rpgCraft.furnace");
            }
            if (!p.hasPermission("rpgCraft.furnace")) {
                e.setCancelled(true);
                Util.sendNoPermFurnace(p, "");
            }
        } else if (block != null && block.getType().equals((Object)Material.SMOKER)) {
        	if (Util.debugMode() && p.hasPermission("rpgCraft.debug")) {
        		p.sendMessage("§b[rpgCraftPerm][Debug] Нужный доступ: rpgCraft.smoker");
            }
            if (!p.hasPermission("rpgCraft.smoker")) {
                e.setCancelled(true);
                Util.sendNoPermFurnace(p, "для еды");
            }
        } else if (block != null && block.getType().equals((Object)Material.BLAST_FURNACE)) {
        	if (Util.debugMode() && p.hasPermission("rpgCraft.debug")) {
        		p.sendMessage("§b[rpgCraftPerm][Debug] Нужный доступ: rpgCraft.blastfurnace");
            }
            if (!p.hasPermission("rpgCraft.enchant")) {
                e.setCancelled(true);
                Util.sendNoPermFurnace(p, "для руды");
            }
        }
    }
    */
}