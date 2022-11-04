package fr.firedragonalex.spellandweapon.saveandload;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class SaveAndLoadListeners implements Listener {
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		SaveAndLoad.loadCustomPlayer(event.getPlayer().getUniqueId());
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		SaveAndLoad.saveCustomPlayer(event.getPlayer().getUniqueId());
	}

}
