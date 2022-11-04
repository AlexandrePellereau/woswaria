package fr.firedragonalex.spellandweapon.custom.code.custommonsterspawner;

import org.bukkit.scheduler.BukkitRunnable;

public class CustomMonsterSpawnerLoopSpawn extends BukkitRunnable{
	
	private CustomMonsterSpawner customMonsterSpawner;
	
	public CustomMonsterSpawnerLoopSpawn(CustomMonsterSpawner customMonsterSpawner) {
		this.customMonsterSpawner = customMonsterSpawner;
	}

	@Override
	public void run() {
		this.customMonsterSpawner.trySpawn();
	}
	
}
