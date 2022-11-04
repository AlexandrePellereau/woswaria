package fr.firedragonalex.spellandweapon.custom.code.custommonsterspawner;

import java.nio.file.Path;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Mob;
import org.bukkit.scheduler.BukkitRunnable;

import fr.firedragonalex.spellandweapon.custom.code.CustomMonster;

public class CustomMonsterSpawnerLoopKeepMonster extends BukkitRunnable{
	
	private CustomMonsterSpawner customMonsterSpawner;
	
	public CustomMonsterSpawnerLoopKeepMonster(CustomMonsterSpawner customMonsterSpawner) {
		this.customMonsterSpawner = customMonsterSpawner;
	}

	@Override
	public void run() {
		Location spawnerLocation = this.customMonsterSpawner.getLocation();
		for (CustomMonster customMonster :  this.customMonsterSpawner.getAllCustomMonstersOwned()) {
//			if (Math.abs(spawnerLocation.getX())-Math.abs(customMonster.getEntity().getLocation().getX()) > this.customMonsterSpawner.getKeepMonsterRadius() &&
//				Math.abs(spawnerLocation.getZ())-Math.abs(customMonster.getEntity().getLocation().getZ()) > this.customMonsterSpawner.getKeepMonsterRadius()) {
//				
//				((Mob)customMonster.getEntity()).setTarget(this.customMonsterSpawner.getCenterAmorStand());
//				
//				Bukkit.broadcastMessage("go to center");
//			}
			//((Mob)customMonster.getEntity()).setTarget(this.customMonsterSpawner.getCenterAmorStand());
			
			Bukkit.broadcastMessage("go to center");
			Location loc = this.customMonsterSpawner.getLocation();
			customMonster.moveTo(customMonster.getEntity(), spawnerLocation, 10);

		}
	}
	
}
