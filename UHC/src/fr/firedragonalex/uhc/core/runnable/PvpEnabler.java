package fr.firedragonalex.uhc.core.runnable;

import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

import fr.firedragonalex.uhc.core.GameManager;
import fr.firedragonalex.uhc.core.GameState;
import fr.firedragonalex.uhc.core.Options;
import fr.firedragonalex.uhc.core.langague.Translate;

public class PvpEnabler extends BukkitRunnable {
	
	private int timer;
	
	public PvpEnabler() {
		this.timer = Options.PVP_OFF_DURATION+1;
	}
	
	@Override
	public void run() {
		timer--;
		//Bukkit.broadcastMessage("timer:"+timer);
		if (timer == 0) {
			GameManager.setGameState(GameState.PLAYING);
			GameManager.sendTitleToAll(ChatColor.YELLOW+Translate.PVP_ON.getString());
			GameManager.sendMessageToAll(ChatColor.YELLOW+Translate.PVP_ON.getString());
			//PvpEnabler.stop();
			this.cancel();
		}
		
		ChatColor color = ChatColor.BLUE;
		
		if (!Arrays.asList(30*60,20*60,10*60,5*60,2*60,60,30,10,5,3,2,1).contains(timer)) return;
		GameManager.sendMessageToAll(color.toString()+Translate.ANNOUCEMENT_PVP_ON.getString().replace("{number}", ""+timer));
		
		if (!Arrays.asList(3,2,1).contains(timer)) return;
		GameManager.sendTitleToAll(color.toString()+timer);
		
	}

}
