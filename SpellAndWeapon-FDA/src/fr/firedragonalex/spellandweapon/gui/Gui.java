package fr.firedragonalex.spellandweapon.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import fr.firedragonalex.spellandweapon.Main;
import fr.firedragonalex.spellandweapon.custom.code.CustomPlayer;
import fr.firedragonalex.spellandweapon.custom.easytoadd.Craft;

public class Gui {
	
	public static void startGui_Armor(Player player) {
		CustomPlayer customPlayer = Main.getCustomPlayerByPlayer(player);
		Inventory inventory = Bukkit.createInventory(null, 3*9, "§1WoswariaGui_Armor");
		
		for (int i = 0; i < 3*9; i++) {
			inventory.setItem(i,Craft.customItem(Material.BLACK_STAINED_GLASS_PANE, "§"));
		}
		
		for (int i = 0; i < 4; i++) {
			if (customPlayer.getCustomArmorEquipements()[i] != null) {
				inventory.setItem(9+1+i*2, customPlayer.getCustomArmorEquipements()[i].getItem(i));
			} else {
				inventory.setItem(9+1+i*2, null);
			}
		}
		
		player.openInventory(inventory);
	}

}
