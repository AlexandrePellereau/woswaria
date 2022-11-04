package fr.firedragonalex.showdamage;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.scheduler.BukkitRunnable;



public class tempHologram extends BukkitRunnable {
	
	private int timer;
	private ArmorStand tempHologramArmorStand;

	public tempHologram(Main main,Double nbDamage, String color, Location coordinates) {
		this.timer = 1;
		//Location newLocation = new Location(coordinates.getWorld(), coordinates.getX()+(2*Math.random())-1, coordinates.getY()+(2*Math.random())-21, coordinates.getZ()+(2*Math.random())-1);
		Location newLocation2 = new Location(coordinates.getWorld(), coordinates.getX()+(2*Math.random())-1, coordinates.getY()+(Math.random())-1, coordinates.getZ()+(2*Math.random())-1);
		this.tempHologramArmorStand = (ArmorStand)main.getWorld().spawnEntity(newLocation2, EntityType.ARMOR_STAND);
		this.tempHologramArmorStand.setInvisible(true);
		this.tempHologramArmorStand.setGravity(false);
		this.tempHologramArmorStand.setInvulnerable(true);
		this.tempHologramArmorStand.setCustomName(color+Double.toString(Math.round(nbDamage*100.0)/100.0));
		this.tempHologramArmorStand.setCustomNameVisible(true);
	}
	
	@Override
	public void run() {
		if (timer == 0) {
			tempHologramArmorStand.remove();
			cancel();
		}
		timer--;
	}

}
