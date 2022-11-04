package fr.firedragonalex.cities;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import fr.firedragonalex.areaplugin.AreaEvent;
import fr.firedragonalex.areaplugin.area.Area;

public class AreaEventListener extends AreaEvent{

	@Override
	public void onCreateAreaEvent(Area area) {
		if (Bukkit.getOfflinePlayer(area.getOwner()).isOp()) { return; }
		City city = Main.getCityByPlayerOwner(area.getOwner());
		if (city == null || !city.isInTheCity(area)) {
			AreaEvent.setCancelled(true,"§cTu ne peux créer une maison que dans ta ville ! Pour rejoindre une ville, il te suffit d'acheter une maison dans une ville.");
		} else {
			city.addArea(area);
		}
	}
	
	@Override
	public void onBuyAreaEvent(Area area, Player buyer, Inventory price) {
		if (Main.getNationality(buyer) == null) {
			City city = Main.getCityByArea(area);
			if (city != null) {
				Main.setNationality(buyer, city);
			}
		} else {
			buyer.sendMessage("Tu peux utiliser la commande /nationality pour changer ta nationalité.");
		}
	}

}