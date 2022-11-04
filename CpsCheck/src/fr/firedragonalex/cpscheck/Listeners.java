package fr.firedragonalex.cpscheck;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class Listeners implements Listener {
	
	private Main main;
	
	public Listeners(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void onPvp(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Player) {
			Player player = (Player)event.getDamager();
			for (CpsPrinter cpsPrinter : main.getListCpsPrinters()) {
				if (cpsPrinter.getTarget() == player) {
					cpsPrinter.addHit();
				}
			}
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		for (CpsPrinter cpsPrinter : main.getListCpsPrinters()) {
			if (cpsPrinter.getTarget() == event.getPlayer()) {
				cpsPrinter.getPlayer().sendMessage("§cLe joueur ciblé s'est déconnecter !");
				cpsPrinter.cancel();
			}
		}
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
			if (event.getPlayer() instanceof Player) {
				Player player = (Player)event.getPlayer();
				for (CpsPrinter cpsPrinter : main.getListCpsPrinters()) {
					if (cpsPrinter.getTarget() == player) {
						cpsPrinter.addHit();
					}
				}
			}
		}
	}

}
