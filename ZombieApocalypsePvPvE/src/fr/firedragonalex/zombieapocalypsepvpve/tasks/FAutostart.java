package fr.firedragonalex.zombieapocalypsepvpve.tasks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.firedragonalex.zombieapocalypsepvpve.FMain;
import fr.firedragonalex.zombieapocalypsepvpve.FState;

public class FAutostart extends BukkitRunnable{
	
	private int timer = 10;
	private FMain main;

	public FAutostart(FMain main) {
		this.main = main;
	}

	@Override
	public void run() {
		
		for (Player pls : main.getPlayers()) {
			pls.setLevel(timer);
		}
		
		if(timer==10 || timer == 5 ||timer == 3||timer == 2||timer == 1) {
			Bukkit.broadcastMessage("Lancement du jeu dans " + timer + "...");
			for (Player pls : main.getPlayers()) {
				pls.sendTitle("§c"+timer,"", 1, 20*3, 1);
			}
			
		}
		
		if (timer == 0) {
			Bukkit.broadcastMessage("C'est parti !");
			Bukkit.broadcastMessage("Bonne chance !");
			main.setState(FState.PLAYING);
			cancel();
		}
		
		timer--;
	}
}
