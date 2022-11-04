package fr.firedragonalex.cities.gui.chatwaiters;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import fr.firedragonalex.cities.City;
import fr.firedragonalex.cities.Main;

public class CWChangeCityOwner extends ChatWaiter{
	
	public CWChangeCityOwner(Player player) {
		super(player);
	}

	@Override
	public void execute(String message) {
		City city = Main.getCityByPlayerOwner(this.player.getUniqueId());
		OfflinePlayer newOwner = null;
		for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()) {
			try {
				if (offlinePlayer.getName().equals(message)) {
					newOwner = offlinePlayer;
				}
			} catch (Exception e) {}
		}
		if (newOwner != null) {
			if (Main.getCityByPlayerOwner(newOwner.getUniqueId()) == null) {
				city.setOwner(newOwner.getUniqueId());
				player.sendMessage(ChatColor.YELLOW+"Le propriétaire de la ville est maintenant : "+ChatColor.BLUE+message+ChatColor.YELLOW+" !");
			} else {
				player.sendMessage(ChatColor.RED+"Ce joueur est déjà propriétaire d'une ville !");
			}
		} else {
			player.sendMessage(ChatColor.RED+"Ce joueur n'existe pas ou n'a jamais rejoint le serveur !");
		}
	}
	
}
