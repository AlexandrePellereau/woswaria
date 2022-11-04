package fr.firedragonalex.uhc.core.runnable;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.firedragonalex.uhc.core.langague.Translate;

public class ShutdownLater extends BukkitRunnable {

	@Override
	public void run() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			player.kickPlayer(Translate.END_GAME.getString());
		}
		Bukkit.shutdown();
	}

}
