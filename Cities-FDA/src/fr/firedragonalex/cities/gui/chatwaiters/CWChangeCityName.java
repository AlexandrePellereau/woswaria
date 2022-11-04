package fr.firedragonalex.cities.gui.chatwaiters;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import fr.firedragonalex.cities.City;
import fr.firedragonalex.cities.Main;

public class CWChangeCityName extends ChatWaiter{
	
	public CWChangeCityName(Player player) {
		super(player);
	}

	@Override
	public void execute(String message) {
		City city = Main.getCityByPlayerOwner(this.player.getUniqueId());
		for (City otherCity : Main.getAllCities()) {
			if (otherCity.getName().equals(message)) {
				this.player.sendMessage(ChatColor.RED+"Une autre ville a d�j� ce nom !");
				return;
			}
		}
		city.setName(message);
		this.player.sendMessage(ChatColor.YELLOW+"Ta ville a bien �t� renomm�e en "+message+ChatColor.YELLOW+" !");
	}
	
}
