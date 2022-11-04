package fr.firedragonalex.areaplugin;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class Selection {
	
	private Location currentLocation;
	private Player owner;
	
	public Selection(Location currentLocation, Player owner) {
		this.currentLocation = currentLocation;
		this.owner = owner;
		//System.out.println("[AreaPlugin-FDA] New selection : "+currentLocation.getBlockX()+" "+currentLocation.getBlockY()+" "+currentLocation.getBlockZ()+" !");
	}
	
	public Location getLocation() {
		return this.currentLocation;
	}
	
	public Player getOwner() {
		return this.owner;
	}
	
}
