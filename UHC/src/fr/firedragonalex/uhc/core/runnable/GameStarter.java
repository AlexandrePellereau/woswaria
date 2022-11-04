package fr.firedragonalex.uhc.core.runnable;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

import fr.firedragonalex.uhc.core.GameManager;
import fr.firedragonalex.uhc.core.GameState;
import fr.firedragonalex.uhc.core.Main;
import fr.firedragonalex.uhc.core.Options;
import fr.firedragonalex.uhc.core.langague.Translate;

public class GameStarter extends BukkitRunnable {
	
	private static GameStarter instance;
	private int timer;
	
	public GameStarter() {
		GameStarter.instance = this;
		this.timer = Options.BEFORE_STARTING_DURATION+1;
	}
	
	public static void stop() {
		GameStarter.instance.cancel();
		GameStarter.instance = null;
	}

	
	@Override
	public void run() {
		timer--;
		//Bukkit.broadcastMessage("timer:"+timer);
		if (timer == 0) {
			GameManager.setGameState(GameState.PLAYING_INVICIBLE);
			GameManager.tpPlayersToSpawn();
			Bukkit.getWorld("UHC_game_map").setTime(1000);
			GameManager.sendTitleToAll(ChatColor.YELLOW+Translate.START_PLAYING.getString());
			new StopInvicibility().runTaskTimer(Main.getInstance(), 0, 20);
			new PvpEnabler().runTaskTimer(Main.getInstance(), 0, 20);
			if (Options.IS_THERE_ROLE) new RoleAnnouncement().runTaskLater(Main.getInstance(), Options.ROLE_ANNOUCEMENT_DURATION * 20);
			if (Options.IS_THERE_WORLDBORDER) new WorldBorderManager(Bukkit.getWorld("UHC_game_map")).runTaskTimer(
					Main.getInstance(), 0, (Options.WORLDBORDER_MOVING_DURATION + Options.DURATION_BETWEEN_WORLDBORDER_MOVING) * 20);
			GameStarter.stop();
		}
		
		ChatColor color = ChatColor.YELLOW;
		if (!Arrays.asList(10,5,3,2,1).contains(timer)) return;
		
		if (Arrays.asList(3,2,1).contains(timer)) color = ChatColor.RED;
		
		GameManager.sendTitleToAll(color.toString()+timer);
		
	}
	
}
