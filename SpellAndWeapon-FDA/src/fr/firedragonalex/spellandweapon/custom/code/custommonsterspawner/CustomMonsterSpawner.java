package fr.firedragonalex.spellandweapon.custom.code.custommonsterspawner;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

import fr.firedragonalex.spellandweapon.Main;
import fr.firedragonalex.spellandweapon.alexlibrairy.UsefulFunctions;
import fr.firedragonalex.spellandweapon.custom.code.CustomMonster;
import fr.firedragonalex.spellandweapon.custom.easytoadd.CustomMonsterType;
import fr.firedragonalex.spellandweapon.woswaria.Woswaria;

public class CustomMonsterSpawner {
	
	//private UUID uuid;
	private Location location;
	private CustomMonsterType customMonsterType;
	private int level;
	private int nbMonsterMax;
	private int ticksBetweenMonsterSpawn;
	private int spawnRadius;
	private int keepMonsterRadius;
	private CustomMonsterSpawnerLoopSpawn loopSpawn;
	private CustomMonsterSpawnerLoopKeepMonster loopKeepMonster;
	private List<CustomMonster> allCustomMonstersOwned;
	//private ArmorStand centerArmorStand;
	
	public CustomMonsterSpawner(Location location, CustomMonsterType customMonsterType, int level, int nbMonsterMax, int ticksBetweenMonsterSpawn, int spawnRadius, int keepMonsterRadius) {
		//this.uuid = UUID.randomUUID();
		location.setY(location.getY()+customMonsterType.getEntityHeight(this.level));
		location.setY(location.getY()+0.5);
		location.setX(location.getX()+0.5*UsefulFunctions.getSign(location.getX()));
		location.setZ(location.getZ()+0.5*UsefulFunctions.getSign(location.getZ()));
		this.location = location;
//		this.centerArmorStand = (ArmorStand)location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
//		this.centerArmorStand.setInvisible(true);
//		this.centerArmorStand.setCollidable(false);
//		this.centerArmorStand.setSilent(true);
//		this.centerArmorStand.setGravity(false);
//		this.centerArmorStand.setInvulnerable(true);
//		this.centerArmorStand.setVisualFire(false);
//		this.centerArmorStand.setCustomNameVisible(false);
//		this.centerArmorStand.setAI(false);
		
		
		
		this.customMonsterType = customMonsterType;
		this.level = level;
		this.nbMonsterMax = nbMonsterMax;
		this.ticksBetweenMonsterSpawn = ticksBetweenMonsterSpawn;
		this.spawnRadius = spawnRadius;
		this.keepMonsterRadius = keepMonsterRadius;
		this.allCustomMonstersOwned = new ArrayList<>();
		this.loopSpawn = new CustomMonsterSpawnerLoopSpawn(this);
		this.loopSpawn.runTaskTimer(Main.getInstance(), 0, this.ticksBetweenMonsterSpawn);
		this.loopKeepMonster = new CustomMonsterSpawnerLoopKeepMonster(this);
		this.loopKeepMonster.runTaskTimer(Main.getInstance(), 0, 20);
		
		Main.getAllCustomMonsterSpawners().add(this);
	}
	
	public List<CustomMonster> getAllCustomMonstersOwned() {
		return this.allCustomMonstersOwned;
	}
	
	public Location getLocation() {
		return this.location;
	}
	
	public int getKeepMonsterRadius() {
		return this.keepMonsterRadius;
	}
	
//	public ArmorStand getCenterAmorStand() {
//		return this.centerArmorStand;
//	}
	
	public void trySpawn() {
		if (this.allCustomMonstersOwned.size() < this.nbMonsterMax) {
			Location spawnLocation = this.location.clone();
			int randomX = (int)Math.round(spawnRadius*(Math.random()*2-1));
			int randomZ = (int)Math.round(spawnRadius*(Math.random()*2-1));
			//Bukkit.broadcastMessage("randomX:"+randomX);
			//Bukkit.broadcastMessage("randomZ:"+randomZ);
			spawnLocation.setX(spawnLocation.getX()+randomX);
			spawnLocation.setZ(spawnLocation.getZ()+randomZ);
			this.allCustomMonstersOwned.add(new CustomMonster(customMonsterType, level, spawnLocation));
		}
	}
}
