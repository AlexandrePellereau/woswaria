package fr.dralexgon.shopasvillagerforplayers.gui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import fr.dralexgon.shopasvillagerforplayers.Main;
import fr.dralexgon.shopasvillagerforplayers.VillagerShop;

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
		ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD,1);
		SkullMeta playerHeadMeta = (SkullMeta)playerHead.getItemMeta();
		playerHeadMeta.setDisplayName(name);
		playerHeadMeta.setOwner(nameTexture);
		playerHead.setItemMeta(playerHeadMeta);
		return playerHead;
	}

	public void fillInventory(Inventory inventory) {
		for (int i = 0; i < inventory.getSize(); i++) {
			inventory.setItem(i,this.customItem(Material.BLACK_STAINED_GLASS_PANE, ChatColor.BLACK.toString()));
		}
	}
	
	public void startMainMenu(Player player) {
		int size = 5;
		Inventory inventory = Bukkit.createInventory(null, size*9, ChatColor.BLUE + Main.getText("gui.mainmenu.title"));
		fillInventory(inventory);
		
		inventory.setItem(9*1+1,this.customItem(Material.EMERALD, ChatColor.GREEN + Main.getText("gui.mainmenu.items.addtrade")));
		inventory.setItem(9*1+4,this.customItem(Material.NAME_TAG, ChatColor.YELLOW + Main.getText("gui.mainmenu.items.renamevillagershop")));
		inventory.setItem(9*1+7,this.customItem(Material.REDSTONE, ChatColor.RED + Main.getText("gui.mainmenu.items.deletetrade")));
		inventory.setItem(9*3+1,this.customItem(Material.CHEST, ChatColor.GOLD + Main.getText("gui.mainmenu.items.storageitemstosell")));
		inventory.setItem(9*3+4,this.customItem(Material.ENDER_CHEST, ChatColor.GOLD + Main.getText("gui.mainmenu.items.storageitemsreceived")));
		inventory.setItem(9*3+7,this.customItem(Material.BONE, ChatColor.RED + Main.getText("gui.mainmenu.items.deletevillagershop")));

		player.openInventory(inventory);
	}
	
	public void startNewTrade(Player player) {
		int size = 6;
		Inventory inventory = Bukkit.createInventory(null, size*9, ChatColor.BLUE + Main.getText("gui.newtrade.title"));
		fillInventory(inventory);
		
		inventory.setItem(9*1+1,this.customItem(Material.PAPER, Main.getText("gui.newtrade.items.toreceive1")));
		inventory.setItem(9*1+3,this.customItem(Material.PAPER, Main.getText("gui.newtrade.items.toreceive2")));
		inventory.setItem(9*1+7,this.customItem(Material.PAPER, Main.getText("gui.newtrade.items.tosell1")));
		
		inventory.setItem(9*2+1,null);
		inventory.setItem(9*2+3,null);
		inventory.setItem(9*2+5,this.customItem(Material.MAGENTA_GLAZED_TERRACOTTA, Main.getText("gui.arrow")));
		inventory.setItem(9*2+7,null);
		inventory.setItem(9*4+4,this.customItem(Material.GREEN_CONCRETE, ChatColor.GREEN + Main.getText("gui.newtrade.items.submit")));
		
		player.openInventory(inventory);
	}
	
	public void startConfirmDelete(Player player) {
		int size = 3;
		Inventory inventory = Bukkit.createInventory(null, size*9, ChatColor.BLUE + Main.getText("gui.confirmdelete.title"));
		fillInventory(inventory);

		inventory.setItem(9*1+2,this.customItem(Material.GREEN_CONCRETE, Main.getText("gui.confirmdelete.items.delete")));
		inventory.setItem(9*1+6,this.customItem(Material.RED_CONCRETE, Main.getText("gui.confirmdelete.items.cancel")));
		
		player.openInventory(inventory);
	}
	
	public void startDeleteTrade(Player player) {
		int size = 5;
		Inventory inventory = Bukkit.createInventory(null, size*9, ChatColor.BLUE + Main.getText("gui.deletetrade.title"));
		fillInventory(inventory);
		
		for (List<Object> tempVariable : main.getTempVariables()) {
			if (tempVariable.get(0)==player && tempVariable.get(1).equals("VillagerShopSelected")) {
				VillagerShop villagerShop = (VillagerShop)tempVariable.get(2);
				if (villagerShop.getVillager().getRecipeCount()>0) {
					int indexOfCurrentRecipe = 0;
					for (List<Object> tempVariable2 : main.getTempVariables()) {
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
					inventory.setItem(9*1+5,this.customItem(Material.MAGENTA_GLAZED_TERRACOTTA, Main.getText("gui.arrow")));
					inventory.setItem(9*1+7,villagerShop.getVillager().getRecipe(indexOfCurrentRecipe).getResult());
					if (indexOfCurrentRecipe!=0) {
						inventory.setItem(9*3+1,this.customHead(Main.getText("gui.deletetrade.items.previoustrade"),"MHF_ArrowLeft"));
					}
					inventory.setItem(9*3+4,this.customItem(Material.RED_CONCRETE, Main.getText("gui.deletetrade.items.deletetrade")));
					if (indexOfCurrentRecipe!=villagerShop.getVillager().getRecipeCount()-1) {
						inventory.setItem(9*3+7,this.customHead(Main.getText("gui.deletetrade.items.nexttrade"),"MHF_ArrowRight"));
					}
					player.openInventory(inventory);
					List<Object> tempList = new ArrayList<>();
					tempList.add(player);
					tempList.add("TradeSelected");
					tempList.add(indexOfCurrentRecipe);
					main.getTempVariables().add(tempList);
				}else {
					player.sendMessage(ChatColor.RED + Main.getText("error.notrade2"));
				}
				return;
			}
		}
	}
	
}
