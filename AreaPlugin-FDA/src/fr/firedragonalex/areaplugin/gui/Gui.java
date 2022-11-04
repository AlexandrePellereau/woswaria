package fr.firedragonalex.areaplugin.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World.Environment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.firedragonalex.areaplugin.MainAreaPlugin;
import fr.firedragonalex.areaplugin.area.Area;

public class Gui {
	
	private MainAreaPlugin mainAreaPlugin;
	
	public Gui(MainAreaPlugin mainAreaPlugin) {
		this.mainAreaPlugin = mainAreaPlugin;
	}
	
	public ItemStack customItem(Material itemType, String name) {
		ItemStack item = new ItemStack(itemType,1);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(name);
		item.setItemMeta(itemMeta);
		return item;
	}
	
	public ItemStack customItem(Material itemType, String name, List<String> lore) {
		ItemStack item = new ItemStack(itemType,1);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(name);
		itemMeta.setLore(lore);
		item.setItemMeta(itemMeta);
		return item;
	}
	
	public void startAreaGUI_ChoiceOpOrNot(Player player) {
		this.startAreaGUI_SelectArea(player);
		/*
		if (player.isOp()) {
			Inventory areaGUI_ChoiceOpOrNot = Bukkit.createInventory(null, 3*9, "§1AreaGUI_ChoiceOpOrNot");
			
			for (int i = 0; i < 3*9; i++) {
				areaGUI_ChoiceOpOrNot.setItem(i,this.customItem(Material.BLACK_STAINED_GLASS_PANE, "§0"));
			}
			
			areaGUI_ChoiceOpOrNot.setItem(11,this.customItem(Material.GRASS_BLOCK, "Voir comme un joueur normal."));
			areaGUI_ChoiceOpOrNot.setItem(15,this.customItem(Material.GOLD_BLOCK, "Voir comme un joueur opérateur."));
			
			player.openInventory(areaGUI_ChoiceOpOrNot);
		}else {
			this.startAreaGUI_SelectArea(player);
		}
		*/
	}
	
	public void startAreaGUI_ConfirmDelete(Player player) {
		Inventory areaGUI_ConfirmDelete = Bukkit.createInventory(null, 3*9, "§1AreaGUI_ConfirmDelete");
		
		for (int i = 0; i < 3*9; i++) {
			areaGUI_ConfirmDelete.setItem(i,this.customItem(Material.BLACK_STAINED_GLASS_PANE, "§"));
		}
		areaGUI_ConfirmDelete.setItem(9*1+2,this.customItem(Material.GREEN_CONCRETE,"Supprimer"));
		areaGUI_ConfirmDelete.setItem(9*1+6,this.customItem(Material.RED_CONCRETE,"Annuler"));
		
		player.openInventory(areaGUI_ConfirmDelete);
	}
	
	public void startAreaGUI_AddOrRemoveMates(Player player) {
		Inventory areaGUI_MainSettings = Bukkit.createInventory(null, 3*9, "§1AreaGUI_AddOrRemoveMates");
		
		for (int i = 0; i < 3*9; i++) {
			areaGUI_MainSettings.setItem(i,this.customItem(Material.BLACK_STAINED_GLASS_PANE, "§"));
		}
		
		
		areaGUI_MainSettings.setItem(9*1+2,this.customItem(Material.GREEN_CONCRETE,"Ajouter un équipier"));
		areaGUI_MainSettings.setItem(9*1+6,this.customItem(Material.RED_CONCRETE,"Enlever un équipier"));
		player.openInventory(areaGUI_MainSettings);
	}
	
	public void startAreaGUI_SelectArea(Player player) {
		Inventory areaGUI_SelectArea = Bukkit.createInventory(null, 6*9, "§1AreaGUI_SelectArea");
		for (int i = 0; i < 6*9; i++) {
			areaGUI_SelectArea.setItem(i,this.customItem(Material.BLACK_STAINED_GLASS_PANE, "§"));
		}
		for (int i = 0; i < 9; i++) {
			areaGUI_SelectArea.setItem(i,this.customItem(Material.WHITE_STAINED_GLASS_PANE,"Selectionne la zone."));
		}
		int place = 9;
		for (Area area : mainAreaPlugin.getAllAreas()) {
			if (player.getUniqueId().equals(area.getOwner())) {
				Material block = Material.STONE;
				if (area.getFirstPoint().getWorld().getEnvironment() == Environment.NORMAL) {
					block = Material.GRASS_BLOCK;
				}
				if (area.getFirstPoint().getWorld().getEnvironment() == Environment.NETHER) {
					block = Material.NETHERRACK;
				}
				if (area.getFirstPoint().getWorld().getEnvironment() == Environment.THE_END) {
					block = Material.END_STONE;
				}
				if (area.getFirstPoint().getWorld().getEnvironment() == Environment.CUSTOM) {
					block = Material.GRASS_BLOCK;
				}
				areaGUI_SelectArea.setItem(place,this.customItem(block,area.getName()));
				place++;
			}
		}
		player.openInventory(areaGUI_SelectArea);
	}
	
	public void startAreaGUI_MainSettings(Player player) {
		Inventory areaGUI_MainSettings = Bukkit.createInventory(null, 5*9, "§1AreaGUI_MainSettings");
		
		for (int i = 0; i < 5*9; i++) {
			areaGUI_MainSettings.setItem(i,this.customItem(Material.BLACK_STAINED_GLASS_PANE, "§"));
		}
		
		areaGUI_MainSettings.setItem(9*2+4,this.customItem(Material.PAPER,"Infos"));
		areaGUI_MainSettings.setItem(9*3+7,this.customItem(Material.GOLD_NUGGET,"Vendre"));
		areaGUI_MainSettings.setItem(9*0+4,this.customItem(Material.PLAYER_HEAD,"Gérer les équipiers"));
		areaGUI_MainSettings.setItem(9*1+7,this.customItem(Material.NAME_TAG,"Renommer"));
		areaGUI_MainSettings.setItem(9*1+1,this.customItem(Material.GOLD_INGOT,"Permissions équipiers"));
		areaGUI_MainSettings.setItem(9*3+1,this.customItem(Material.IRON_INGOT,"Permissions autres"));
		areaGUI_MainSettings.setItem(9*4+4,this.customItem(Material.RED_CONCRETE,"Supprimer"));
		
		player.openInventory(areaGUI_MainSettings);
	}
	
	public void startAreaGUI_MatesSettings(Player player) {
		Inventory areaGUI_MainSettings = Bukkit.createInventory(null, 5*9, "§1AreaGUI_MatesSettings");
		
		for (int i = 0; i < 5*9; i++) {
			areaGUI_MainSettings.setItem(i,this.customItem(Material.BLACK_STAINED_GLASS_PANE, "§"));
		}
		
		areaGUI_MainSettings.setItem(9*1+2,this.customItem(Material.BRICKS,"Les équipiers peuvent casser et poser des blocs."));
		areaGUI_MainSettings.setItem(9*1+6,this.customItem(Material.CHEST,"Les équipiers peuvent ouvrir les coffres.", Arrays.asList("Mais aussi :","les fours","les entonnoirs","les distributeurs","les droppers","les shulkers box)")));
		areaGUI_MainSettings.setItem(9*3+2,this.customItem(Material.OAK_DOOR,"Les équipiers peuvent ouvrir les portes.",Arrays.asList("et les trappes")));
		areaGUI_MainSettings.setItem(9*3+6,this.customItem(Material.REDSTONE,"Les équipiers peuvent utiliser la redstone.",Arrays.asList("bouton","levier","coffre piégé")));
		for (List tempVariable : mainAreaPlugin.getTempVariables()) {
			if (tempVariable.get(0)==player && tempVariable.get(1).equals("AreaSelected")) {
				Area area = (Area)tempVariable.get(2);
				if (area.getMatesCanBreakAndPlaceBlocks()) {
					areaGUI_MainSettings.setItem(9*1+1,this.customItem(Material.GREEN_CONCRETE,ChatColor.DARK_GREEN+"Activé",Arrays.asList("Cliquer pour activer/désactiver.","§0matesCanBreakAndPlaceBlocks")));
				}else {
					areaGUI_MainSettings.setItem(9*1+1,this.customItem(Material.RED_CONCRETE,ChatColor.RED+"Désactivé",Arrays.asList("Cliquer pour activer/désactiver.","§0matesCanBreakAndPlaceBlocks")));
				}
				if (area.getMatesCanOpenChests()) {
					areaGUI_MainSettings.setItem(9*1+7,this.customItem(Material.GREEN_CONCRETE,ChatColor.DARK_GREEN+"Activé",Arrays.asList("Cliquer pour activer/désactiver.","§0matesCanOpenChests")));
				}else {
					areaGUI_MainSettings.setItem(9*1+7,this.customItem(Material.RED_CONCRETE,ChatColor.RED+"Désactivé",Arrays.asList("Cliquer pour activer/désactiver.","§0matesCanOpenChests")));
				}
				if (area.getMatesCanOpenDoors()) {
					areaGUI_MainSettings.setItem(9*3+1,this.customItem(Material.GREEN_CONCRETE,ChatColor.DARK_GREEN+"Activé",Arrays.asList("Cliquer pour activer/désactiver.","§0matesCanOpenDoors")));
				}else {
					areaGUI_MainSettings.setItem(9*3+1,this.customItem(Material.RED_CONCRETE,ChatColor.RED+"Désactivé",Arrays.asList("Cliquer pour activer/désactiver.","§0matesCanOpenDoors")));
				}
				if (area.getMatesCanUseRedstone()) {
					areaGUI_MainSettings.setItem(9*3+7,this.customItem(Material.GREEN_CONCRETE,ChatColor.DARK_GREEN+"Activé",Arrays.asList("Cliquer pour activer/désactiver.","§0matesCanUseRedstone")));
				}else {
					areaGUI_MainSettings.setItem(9*3+7,this.customItem(Material.RED_CONCRETE,ChatColor.RED+"Désactivé",Arrays.asList("Cliquer pour activer/désactiver.","§0matesCanUseRedstone")));
				}
			}
		}
		
		player.openInventory(areaGUI_MainSettings);
	}
	
	public void startAreaGUI_EveryoneSettings(Player player) {
		Inventory inventory = Bukkit.createInventory(null, 4*9, "§1AreaGUI_EveryoneSettings");
		
		for (int i = 0; i < 4*9; i++) {
			inventory.setItem(i,this.customItem(Material.BLACK_STAINED_GLASS_PANE, "§"));
		}
		inventory.setItem(9*1+1,this.customItem(Material.CHEST,"Tous les joueurs peuvent ouvrir les coffres.", Arrays.asList("Mais aussi :","les fours","les entonnoirs","les distributeurs","les droppers","les shulkers box)")));
		inventory.setItem(9*1+4,this.customItem(Material.OAK_DOOR,"Tous les joueurs peuvent ouvrir les portes.",Arrays.asList("et les trappes")));
		inventory.setItem(9*1+7,this.customItem(Material.REDSTONE,"Tous les joueurs peuvent utiliser la redstone.",Arrays.asList("bouton","levier","coffre piégé")));
		for (List tempVariable : mainAreaPlugin.getTempVariables()) {
			if (tempVariable.get(0)==player && tempVariable.get(1).equals("AreaSelected")) {
				Area area = (Area)tempVariable.get(2);
				if (area.getEveryoneCanOpenChests()) {
					inventory.setItem(9*2+1,this.customItem(Material.GREEN_CONCRETE,ChatColor.DARK_GREEN+"Activé",Arrays.asList("Cliquer pour activer/désactiver.","§0everyoneCanOpenChests")));
				}else {
					inventory.setItem(9*2+1,this.customItem(Material.RED_CONCRETE,ChatColor.RED+"Désactivé",Arrays.asList("Cliquer pour activer/désactiver.","§0everyoneCanOpenChests")));
				}
				if (area.getEveryoneCanOpenDoors()) {
					inventory.setItem(9*2+4,this.customItem(Material.GREEN_CONCRETE,ChatColor.DARK_GREEN+"Activé",Arrays.asList("Cliquer pour activer/désactiver.","§0everyoneCanOpenDoors")));
				}else {
					inventory.setItem(9*2+4,this.customItem(Material.RED_CONCRETE,ChatColor.RED+"Désactivé",Arrays.asList("Cliquer pour activer/désactiver.","§0everyoneCanOpenDoors")));
				}
				if (area.getEveryoneCanUseRedstone()) {
					inventory.setItem(9*2+7,this.customItem(Material.GREEN_CONCRETE,ChatColor.DARK_GREEN+"Activé",Arrays.asList("Cliquer pour activer/désactiver.","§0everyoneCanUseRedstone")));
				}else {
					inventory.setItem(9*2+7,this.customItem(Material.RED_CONCRETE,ChatColor.RED+"Désactivé",Arrays.asList("Cliquer pour activer/désactiver.","§0everyoneCanUseRedstone")));
				}
			}
		}
		
		player.openInventory(inventory);
	}
	
	public void startAreaGUI_SellSign(Player player) {
		Inventory inventory = Bukkit.createInventory(null, 3*9, ChatColor.DARK_BLUE+"AreaGUI_SellSign");
		
		for (int i = 0; i < 3*9; i++) {
			inventory.setItem(i,this.customItem(Material.BLACK_STAINED_GLASS_PANE, "§"));
		}
		inventory.setItem(9+1,this.customItem(Material.GOLD_NUGGET, "Prix"));
		inventory.setItem(9+4,this.customItem(Material.PAPER, "Infos"));
		inventory.setItem(9+7,this.customItem(Material.GREEN_CONCRETE, "Acheter"));
		
		player.openInventory(inventory);
	}
	
	public void startAreaGUI_SetPriceSellArea(Player player) {
		Inventory inventory = Bukkit.createInventory(null, 4*9, ChatColor.DARK_BLUE+"AreaGUI_SetPriceSellArea");
		player.openInventory(inventory);
	}
}
