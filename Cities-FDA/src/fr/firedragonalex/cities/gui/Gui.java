package fr.firedragonalex.cities.gui;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.firedragonalex.cities.City;
import fr.firedragonalex.cities.Main;

public class Gui {
	
	public static ItemStack customItem(Material itemType, String name) {
		ItemStack item = new ItemStack(itemType,1);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(name);
		item.setItemMeta(itemMeta);
		return item;
	}
	
	public static ItemStack customItem(Material itemType, String name, List<String> lore) {
		ItemStack item = new ItemStack(itemType,1);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(name);
		itemMeta.setLore(lore);
		item.setItemMeta(itemMeta);
		return item;
	}
	
	public static void startGui_CitySettings(Player player) {
		Inventory gui = Bukkit.createInventory(null, 5*9, "§eCityGUI_MainSettings");
		
		for (int i = 0; i < 5*9; i++) {
			gui.setItem(i, Gui.customItem(Material.BLACK_STAINED_GLASS_PANE, "§"));
		}
		gui.setItem(9*1+1, Gui.customItem(Material.PAPER,"Infos"));
		gui.setItem(9*1+4, Gui.customItem(Material.NAME_TAG,"Renommer"));
		gui.setItem(9*1+7, Gui.customItem(Material.RED_CONCRETE,"Supprimer"));
		gui.setItem(9*3+1, Gui.customItem(Material.WHITE_BANNER,"Changer la bannière"));
		gui.setItem(9*3+4, Gui.customItem(Material.CHEST,"Ouvrir le coffre fort"));
		gui.setItem(9*3+7, Gui.customItem(Material.PLAYER_HEAD,"Nommer un nouveau propriétaire"));
		
		player.openInventory(gui);
	}
	
	public static void startGui_ChangeBanner(Player player) {
		Inventory gui = Bukkit.createInventory(null, 5*9, "§eCityGUI_ChangeBanner");
		
		for (int i = 0; i < 5*9; i++) {
			gui.setItem(i, Gui.customItem(Material.BLACK_STAINED_GLASS_PANE, "§"));
		}
		
		City city = Main.getCityByPlayerOwner(player.getUniqueId());
		gui.setItem(9*2+4, city.getBanner());
		
		player.openInventory(gui);
	}
	
	public static void startGui_Infos(Player player) {
		Inventory gui = Bukkit.createInventory(null, 3*9, "§eCityGUI_Infos");
		
		for (int i = 0; i < 3*9; i++) {
			gui.setItem(i, Gui.customItem(Material.BLACK_STAINED_GLASS_PANE, "§"));
		}
		gui.setItem(9*1+1, Gui.customItem(Material.PAPER,"Informations générales"));
		gui.setItem(9*1+3, Gui.customItem(Material.IRON_NUGGET,"Liste des habitants"));
		gui.setItem(9*1+5, Gui.customItem(Material.GOLD_NUGGET,"Liste des citoyens"));
		gui.setItem(9*1+7, Gui.customItem(Material.OAK_FENCE,"Afficher les frontières"));
		
		player.openInventory(gui);
	}

}
