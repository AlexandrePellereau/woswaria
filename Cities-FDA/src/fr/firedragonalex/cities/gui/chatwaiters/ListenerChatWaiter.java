package fr.firedragonalex.cities.gui.chatwaiters;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import fr.firedragonalex.cities.Main;


public class ListenerChatWaiter implements Listener {
	
	@EventHandler(priority = EventPriority.LOW)
	public void onChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		for (ChatWaiter chatWaiter : Main.getAllChatWaiters()) {
			if (chatWaiter.getPlayer() == player) {
				event.setCancelled(true);
				chatWaiter.totalExecute(event.getMessage());
				return;
			}
		}
	}

}
