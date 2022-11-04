package fr.firedragonalex.shopplayerpnj.gui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import fr.firedragonalex.shopplayerpnj.Main;
import fr.firedragonalex.shopplayerpnj.VillagerShop;

public class Gui {
	
	private Main main;

	public Gui(Main main) {
		this.main = main;
	}
	
	public ItemStack customItem(Material itemType) {
		ItemStack item = new ItemStack(itemType,1);
		return item;
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
	
	public ItemStack customHead(String name,String nameTexture) {
		ItemStack playerHead = new ItemStack(Material.SKULL,1);
		SkullMeta playerHeadMeta = (SkullMeta)playerHead.getItemMeta();
		playerHeadMeta.setDisplayName(name);
		playerHeadMeta.setOwner(nameTexture);
		playerHead.setItemMeta(playerHeadMeta);
		return playerHead;
	}
	
	public void startMainMenu(Player player) {
		int size = 5;
		Inventory inventory = Bukkit.createInventory(null, size*9, "§eShopPlayerPNJ_MainMenu");
		
		inventory.setItem(9*1+1,this.customItem(Material.EMERALD,"Ajouter un echange"));
		inventory.setItem(9*1+4,this.customItem(Material.NAME_TAG,"Renommer"));
		inventory.setItem(9*1+7,this.customItem(Material.REDSTONE,"Supprimer un echange"));
		inventory.setItem(9*3+1,this.customItem(Material.CHEST,"Stockage des objets à vendre"));
		inventory.setItem(9*3+4,this.customItem(Material.ENDER_CHEST,"Stockage des objets reçus"));
		inventory.setItem(9*3+7,this.customItem(Material.BONE,"Supprimer le pnj"));
		
		player.openInventory(inventory);
	}
	
	public void startNewTrade(Player player) {
		int size = 6;
		Inventory inventory = Bukkit.createInventory(null, size*9, "§eShopPlayerPNJ_NewTrade");
		
		for (int i = 0; i < size*9; i++) {
			inventory.setItem(i,this.customItem(Material.STAINED_GLASS_PANE,"§"));
		}
		
		inventory.setItem(9*1+1,this.customItem(Material.PAPER,"Objet1 à recevoir (Obligatoire)"));
		inventory.setItem(9*1+3,this.customItem(Material.PAPER,"Objet2 à recevoir (Pas Obligatoire)"));
		inventory.setItem(9*1+7,this.customItem(Material.PAPER,"Objet à vendre (Obligatoire)"));
		
		inventory.setItem(9*2+1,null);
		inventory.setItem(9*2+3,null);
		inventory.setItem(9*2+5,this.customItem(Material.MAGENTA_GLAZED_TERRACOTTA,"--->"));
		inventory.setItem(9*2+7,null);
		inventory.setItem(9*4+4,this.customItem(Material.EMERALD_BLOCK,"Valider"));
		
		player.openInventory(inventory);
	}
	
	public void startConfirmDelete(Player player) {
		int size = 3;
		Inventory inventory = Bukkit.createInventory(null, size*9, "§eShopPlayerPNJ_ConfirmDelete");
		
		inventory.setItem(9*1+2,this.customItem(Material.EMERALD_BLOCK,"Supprimer"));
		inventory.setItem(9*1+6,this.customItem(Material.REDSTONE_BLOCK,"Annuler"));
		
		player.openInventory(inventory);
	}
	
	public void startDeleteTrade(Player player) {
		int size = 5;
		Inventory inventory = Bukkit.createInventory(null, size*9, "§eShopPlayerPNJ_DeleteTrade");
		
		for (List tempVariable : main.getTempVariables()) {
			if (tempVariable.get(0)==player && tempVariable.get(1).equals("VillagerShopSelected")) {
				VillagerShop villagerShop = (VillagerShop)tempVariable.get(2);
				if (villagerShop.getVillager().getRecipeCount()>0) {
					int indexOfCurrentRecipe = 0;
					for (List tempVariable2 : main.getTempVariables()) {
						if (tempVariable2.get(0)==player && tempVariable2.get(1).equals("TradeSelected")) {
							indexOfCurrentRecipe = (int)tempVariable2.get(2);
							main.getTempVariables().remove(tempVariable2);
							break;
						}
					}
					inventory.setItem(9*1+1,villagerShop.getVillager().getRecipe(indexOfCurrentRecipe).getIngredients().get(0));
					try {
						inventory.setItem(9*1+3,villagerShop.getVillager().getRecipe(indexOfCurrentRecipe).getIngredients().get(1));
					} catch (Exception e) {
						inventory.setItem(9*1+3,null);
					}
					inventory.setItem(9*1+5,this.customItem(Material.MAGENTA_GLAZED_TERRACOTTA,"--->"));
					inventory.setItem(9*1+7,villagerShop.getVillager().getRecipe(indexOfCurrentRecipe).getResult());
					if (indexOfCurrentRecipe!=0) {
						inventory.setItem(9*3+1,this.customHead("Echange précédent","MHF_ArrowLeft"));
					}
					inventory.setItem(9*3+4,this.customItem(Material.REDSTONE_BLOCK,"Supprimer l'échange"));
					if (indexOfCurrentRecipe!=villagerShop.getVillager().getRecipeCount()-1) {
						inventory.setItem(9*3+7,this.customHead("Echange suivant","MHF_ArrowRight"));
					}
					player.openInventory(inventory);
					List tempList = new ArrayList<>();
					tempList.add(player);
					tempList.add("TradeSelected");
					tempList.add(indexOfCurrentRecipe);
					main.getTempVariables().add(tempList);
				}else {
					player.sendMessage("§cCe VillagerShop n'a pas de trade !");
				}
				return;
			}
		}
	}
	
}
