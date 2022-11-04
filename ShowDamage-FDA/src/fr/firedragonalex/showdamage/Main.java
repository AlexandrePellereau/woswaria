package fr.firedragonalex.showdamage;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.java.JavaPlugin;


public class Main extends JavaPlugin {
	
	private World world;
	private List<tempHologram> allDamages;
	
	@Override
	public void onEnable() {
		this.world = Bukkit.getWorld("world");
		this.allDamages = new ArrayList<tempHologram>();
		getServer().getPluginManager().registerEvents(new Listeners(this), this);
		
		System.out.println("[ShowDamage-FDA] Enabled !");
	}
	
	//tempHologram tempHologramDamage = new FSpawnZombies(this);
	//spawnZombies.runTaskTimer(this, 0, 20);
	//public class FSpawnZombies extends BukkitRunnable{
	public World getWorld() {
		return this.world;
	}
	
	public List<tempHologram> getAllDamages() {
		return this.allDamages;
	}
	
	public void addDamage(Double nbDamage, String color, Location coordinates) {
		tempHologram theHologram = new tempHologram(this, nbDamage, color, coordinates);
		theHologram.runTaskTimer(this, 0, 20);
		this.getAllDamages().add(theHologram);
		return;
	}
	
	
	@Override
	public void onDisable() {
		System.out.println("[ShowDamage-FDA] Disabled !");
	}

}
