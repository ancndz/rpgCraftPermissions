package ru.ancndz.plugin.events;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import ru.ancndz.plugin.Configuration;

public class AnimalFram implements Listener {
	
	@EventHandler
	public void PlayerInteractEntityEvent(final PlayerInteractEntityEvent e) {
		
		EntityType type = e.getRightClicked().getType();
		Player p = e.getPlayer();
		
		//cowboy group
		if (type.equals(EntityType.PIG) || type.equals(EntityType.COW) || 
				type.equals(EntityType.CHICKEN) || type.equals(EntityType.SHEEP) || 
				type.equals(EntityType.TURTLE) || type.equals(EntityType.RABBIT) || 
				type.equals(EntityType.MUSHROOM_COW) || type.equals(EntityType.PANDA) || 
				type.equals(EntityType.BEE)) {
			if (!p.hasPermission("rpgcraft.breed.farm") || !p.hasPermission("rpgcraft.breed.*")) {
				//e.getPlayer().sendMessage("U can't breed animals.");
				e.setCancelled(true); 
			}
		}
		
		//hunter group
		if (type.equals(EntityType.WOLF) || type.equals(EntityType.OCELOT) || 
				type.equals(EntityType.FOX) || type.equals(EntityType.PARROT)) {
			if (!p.hasPermission("rpgcraft.breed.helpers") || !p.hasPermission("rpgcraft.breed.*")) {
				//e.getPlayer().sendMessage("U can't breed little friends.");
				e.setCancelled(true); 
			}
		}
		
		//hunter, cowboy and special skill
		if (type.equals(EntityType.HORSE) || type.equals(EntityType.MULE) || 
				type.equals(EntityType.LLAMA) || type.equals(EntityType.DONKEY)) {
			if ((!p.hasPermission("rpgcraft.breed.horse") || !p.hasPermission("rpgcraft.breed.*")
					) && Configuration.configs.getBoolean("enableHorsePerk")) {
				//e.getPlayer().sendMessage("U can't breed horses.");
				e.setCancelled(true); 
			}
		}
	}

}
