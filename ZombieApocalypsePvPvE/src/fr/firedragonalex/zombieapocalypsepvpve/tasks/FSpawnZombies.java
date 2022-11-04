package fr.firedragonalex.zombieapocalypsepvpve.tasks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;
import org.bukkit.scheduler.BukkitRunnable;

import fr.firedragonalex.zombieapocalypsepvpve.FMain;
import fr.firedragonalex.zombieapocalypsepvpve.FState;

public class FSpawnZombies extends BukkitRunnable{
	
	private FMain main;

	public FSpawnZombies(FMain main) {
		this.main = main;
	}

	@Override
	public void run() {
		if (main.isState(FState.PLAYING)) {
			//System.out.println("test1");
			if (main.getPeriod().getCooldownBetweenWaves() <= main.getTimeOfTheLastWave()) {
				System.out.println("[ZombieApocalypsePvPvE] trying to create new wave");
				//System.out.println("test2");
				List<Zombie> listOfZombies = new ArrayList<Zombie>();
				List<Entity> listOfEntity = main.getWorld().getEntities();
				//System.out.println("test3");
				for (Entity myEntity : listOfEntity) {
					if (myEntity instanceof Zombie) {
						listOfZombies.add((Zombie)myEntity);
					}
				}
				//Bukkit.broadcastMessage("Nombre de zombies : "+listOfZombies.size());
				System.out.println("[ZombieApocalypsePvPvE] nb of living zombies : "+listOfZombies.size());
				if (listOfZombies.size() <= main.getNbPlayersAtStart()*0.2) {
					if (main.getNbPlayersAtStart()>0) {
						Bukkit.broadcastMessage("Nouvelle vague !");
						System.out.println("[ZombieApocalypsePvPvE] nb of new zombies : "+main.getPeriod().getNbZombies()*main.getNbPlayersAtStart());
						for (int i = 0; i < main.getPeriod().getNbZombies()*main.getNbPlayersAtStart(); i++) {
							Random r = new Random();
							double x = r.nextInt(main.getSizeOfTheMap().get(0));
							double y = r.nextInt(main.getSizeOfTheMap().get(1));
							Zombie zombie = (Zombie)main.getWorld().spawnEntity(new Location(main.getWorld(), x, 35, y), EntityType.ZOMBIE);
							if (!zombie.isAdult()) {
								zombie.setHealth(5);
							}
							//Bukkit.broadcastMessage("Nouveau zombie !");
							//Bukkit.broadcastMessage("Zombie n°"+i);
						}
						main.getBossBarNbZombies().setProgress(1);
					}
				}
				main.setTimeOfTheLastWave(0);
			}
			main.setTimeOfTheLastWave(main.getTimeOfTheLastWave()+1);
		}
	}
}
