package fr.firedragonalex.uhc.core.runnable;

import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

import fr.firedragonalex.uhc.core.GameManager;
import fr.firedragonalex.uhc.core.GameState;
import fr.firedragonalex.uhc.core.Options;
import fr.firedragonalex.uhc.core.langague.Translate;

public class StopInvicibility extends BukkitRunnable {

	private static StopInvicibility instance;
	private int timer;
	
	public StopInvicibility() {
		StopInvicibility.instance = this;
		this.timer = Options.INVICIBILITY_DURATION+1;
		
		GameManager.sendTitleToAll(ChatColor.YELLOW+Translate.ANNOUCEMENT_INVICIBILITY.getString().replace("{number}", ""+Options.INVICIBILITY_DURATION));
	}
	
	public static void stop() {
		StopInvicibility.instance.cancel();
		StopInvicibility.instance = null;
	}

	
	@Override
	public void run() {
		timer--;
		if (timer == 0) {
			GameManager.setGameState(GameState.PLAYING_PVP_OFF);
			GameManager.sendTitleToAll(ChatColor.YELLOW+Translate.REMOVE_INVICIBILITY.getString());
			GameManager.sendMessageToAll(ChatColor.YELLOW+Translate.REMOVE_INVICIBILITY.getString());
			StopInvicibility.stop();
		}
		
		ChatColor color = ChatColor.YELLOW;
		
		if (!Arrays.asList(10,5,3,2,1).contains(timer)) return;
		GameManager.sendMessageToAll(color.toString()+Translate.ANNOUCEMENT_REMOVE_INVICIBILITY.getString().replace("{number}", ""+timer));
		
		if (!Arrays.asList(3,2,1).contains(timer)) return;
		GameManager.sendTitleToAll(color.toString()+timer);
	}
}
