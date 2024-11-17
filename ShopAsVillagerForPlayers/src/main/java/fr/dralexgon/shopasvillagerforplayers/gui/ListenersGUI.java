package fr.dralexgon.shopasvillagerforplayers.gui;

import java.util.ArrayList;
import java.util.List;

import fr.dralexgon.shopasvillagerforplayers.database.SaveAndLoadSQLite;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import fr.dralexgon.shopasvillagerforplayers.Main;
import fr.dralexgon.shopasvillagerforplayers.VillagerShop;

public class ListenersGUI implements Listener {

	private final Main main;

	public ListenersGUI(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent event) {
		String inventoryTitle = event.getView().getTitle();
		Player player = (Player)event.getWhoClicked();
		ItemStack item = event.getCurrentItem();
		if (item==null) return;
		if (inventoryTitle.equals(ChatColor.BLUE + Main.getText("gui.mainmenu.title"))) {
			event.setCancelled(true);
			if (!item.hasItemMeta()) return;
			switch (item.getType()) {
			case EMERALD://Main.getText("gui.mainmenu.items.addtrade")
				main.getGui().startNewTrade(player);
				break;
			case REDSTONE://Main.getText("gui.mainmenu.items.deletetrade")
				main.getGui().startDeleteTrade(player);
				break;
			case BONE://Main.getText("gui.mainmenu.items.deletevillagershop")
				main.getGui().startConfirmDelete(player);
				break;
			case VILLAGER_SPAWN_EGG:
				player.sendMessage("§cEn cours de développement...");
				for (List<Object> tempVariable : main.getTempVariables()) {
					if (tempVariable.get(0)==player && tempVariable.get(1).equals("VillagerShopSelected")) {
						VillagerShop villagerShop = (VillagerShop)tempVariable.get(2);
						//Timestamp ts = new Timestamp(System.currentTimeMillis());//+main.getExpirationTime());  
						//Date date = new Date(ts.getTime());  
						//player.getInventory().addItem(main.getGui().customItem(Material.VILLAGER_SPAWN_EGG, villagerShop.getName(), Arrays.asList("§0VillagerShopCustom","Date d'expiration : ",date+" (Ann§es-Jours-Mois)","§0UUID:"+villagerShop.getUUID(),"§0TimeOfCreation:"+System.currentTimeMillis())));
						//main.getListVillagersShopInactive().add(villagerShop);
						main.getListVillagersShop().remove(villagerShop);
						player.sendMessage("§ckill");
						villagerShop.getVillager().setHealth(0);
						player.closeInventory();
					}
				}
				break;
			case NAME_TAG://Main.getText("gui.mainmenu.items.renamevillegershop")
				player.sendMessage(ChatColor.YELLOW + Main.getText("message.renamevillagershop"));
				List<Object> tempList = new ArrayList<>();
				tempList.add(player);
				tempList.add("RenameVillagerShop");
				tempList.add(System.currentTimeMillis());
				main.getTempVariables().add(tempList);
				player.closeInventory();
				break;
			case CHEST://Main.getText("gui.mainmenu.items.storageitemstosell")
				for (List<Object> tempVariable : main.getTempVariables()) {
					if (tempVariable.get(0)==player && tempVariable.get(1).equals("VillagerShopSelected")) {
						VillagerShop villagerShop = (VillagerShop)tempVariable.get(2);
						player.openInventory(villagerShop.getInventoryThingsToSell());
					}
				}
				break;
			case ENDER_CHEST://Main.getText("gui.mainmenu.items.storageitemsreceived")
				for (List<Object> tempVariable : main.getTempVariables()) {
					if (tempVariable.get(0)==player && tempVariable.get(1).equals("VillagerShopSelected")) {
						VillagerShop villagerShop = (VillagerShop)tempVariable.get(2);
						player.openInventory(villagerShop.getInventoryThingsObtained());
					}
				}
				break;
			default:
				return;
			}
		}
		if (inventoryTitle.equals(ChatColor.BLUE + Main.getText("gui.newtrade.title"))) {
			if (!item.hasItemMeta()) return;
			String itemDisplayName = item.getItemMeta().getDisplayName();
			if (itemDisplayName.equals(ChatColor.BLACK.toString()) ||
					itemDisplayName.equals(Main.getText("gui.newtrade.items.toreceive1")) ||
					itemDisplayName.equals(Main.getText("gui.newtrade.items.toreceive2")) ||
					itemDisplayName.equals(Main.getText("gui.newtrade.items.tosell1")) ||
					itemDisplayName.equals(Main.getText("gui.arrow"))) {
				event.setCancelled(true);
				return;
			}
			if (item.getItemMeta().getDisplayName().equals(ChatColor.GREEN + Main.getText("gui.newtrade.items.submit"))) {
				event.setCancelled(true);
				for (List<Object> tempVariable : main.getTempVariables()) {
					if (tempVariable.get(0)==player && tempVariable.get(1).equals("VillagerShopSelected")) {
						VillagerShop villagerShop = (VillagerShop)tempVariable.get(2);
						Inventory inventory = event.getView().getTopInventory();
						ItemStack inputTrade = inventory.getItem(9*2+1);
						ItemStack inputTrade2 = inventory.getItem(9*2+3);
						ItemStack outputTrade = inventory.getItem(9*2+7);
						if (inputTrade != null && outputTrade != null) {
							VillagerShop.addTrade(villagerShop.getVillager(), inputTrade, inputTrade2, outputTrade);
						}
						player.closeInventory();
					}
				}
			}
		}
		if (inventoryTitle.equals(ChatColor.BLUE + Main.getText("gui.confirmdelete.title"))) {
			event.setCancelled(true);
			if (!item.hasItemMeta()) return;
			switch (item.getType()) {
			case GREEN_CONCRETE:
				for (List<Object> tempVariable : main.getTempVariables()) {
					if (tempVariable.get(0)==player && tempVariable.get(1).equals("VillagerShopSelected")) {
						VillagerShop villagerShop = (VillagerShop)tempVariable.get(2);
						if (!villagerShop.hasInfiniteTrade()) {
							for (ItemStack itemstack : villagerShop.getInventoryThingsObtained()) {
								try {
									villagerShop.getVillager().getWorld().dropItem(villagerShop.getVillager().getLocation(), itemstack);
								} catch (Exception e) {
									// TODO: handle exception
								}
							}
							for (ItemStack itemstack : villagerShop.getInventoryThingsToSell()) {
								try {
									villagerShop.getVillager().getWorld().dropItem(villagerShop.getVillager().getLocation(), itemstack);
								} catch (Exception e) {
									// TODO: handle exception
								}
							}
							if (villagerShop.getVillager().getProfession() != Profession.NONE) {
								ItemStack item2 = new ItemStack(Material.LEATHER_CHESTPLATE,1);
								ItemMeta itemMeta2 = item2.getItemMeta();
								itemMeta2.setDisplayName("§9SkinVillagerShop");
								item2.setItemMeta(itemMeta2);
								villagerShop.getVillager().getWorld().dropItem(villagerShop.getVillager().getLocation(), item2);
							}
							if (main.getConfig().getBoolean("give_villagershopegg_when_you_kill_a_villagershop")) {
								ItemStack item3 = new ItemStack(Material.VILLAGER_SPAWN_EGG,1);
								ItemMeta itemMeta3 = item.getItemMeta();
								itemMeta3.setDisplayName("§eVillagerShop");
								item3.setItemMeta(itemMeta3);
								villagerShop.getVillager().getWorld().dropItem(villagerShop.getVillager().getLocation(), item3);
							}
						}
						villagerShop.death();
						player.closeInventory();
					}
				}
			case RED_CONCRETE:
				player.closeInventory();
			default:
				break;
			}
		}
		if (inventoryTitle.equals(ChatColor.BLUE + Main.getText("gui.deletetrade.title"))) {
			event.setCancelled(true);
			if (!item.hasItemMeta()) return;
			if (item.getItemMeta().getDisplayName().equals(Main.getText("gui.deletetrade.items.previoustrade"))) {
				for (List<Object> tempVariable : main.getTempVariables()) {
					if (tempVariable.get(0)==player && tempVariable.get(1).equals("TradeSelected")) {
						int indexOfCurrentRecipe = (int)tempVariable.get(2);
						main.getTempVariables().remove(tempVariable);
						List<Object> tempList = new ArrayList<>();
						tempList.add(player);
						tempList.add("TradeSelected");
						tempList.add(indexOfCurrentRecipe-1);
						main.getTempVariables().add(tempList);
						main.getGui().startDeleteTrade(player);
						return;
					}
				}
			}
			if (item.getItemMeta().getDisplayName().equals(Main.getText("gui.deletetrade.items.nexttrade"))) {
				System.out.println(main.getTempVariables());
				for (List<Object> tempVariable : main.getTempVariables()) {
					if (tempVariable.get(0) == player && tempVariable.get(1).equals("TradeSelected")) {
						int indexOfCurrentRecipe = (int) tempVariable.get(2);
						main.getTempVariables().remove(tempVariable);
						List<Object> tempList = new ArrayList<>();
						tempList.add(player);
						tempList.add("TradeSelected");
						tempList.add(indexOfCurrentRecipe + 1);
						main.getTempVariables().add(tempList);
						main.getGui().startDeleteTrade(player);
						return;
					}
				}
				return;
			}
			if (item.getItemMeta().getDisplayName().equals(Main.getText("gui.deletetrade.items.deletetrade"))) {
				event.setCancelled(true);
				for (List<Object> tempVariable : main.getTempVariables()) {
					if (tempVariable.get(0)==player && tempVariable.get(1).equals("VillagerShopSelected")) {
						VillagerShop villagerShop = (VillagerShop)tempVariable.get(2);
						for (List<Object> tempVariable2 : main.getTempVariables()) {
							if (tempVariable2.get(0)==player && tempVariable2.get(1).equals("TradeSelected")) {
								int indexOfCurrentRecipe = (int)tempVariable2.get(2);
								MerchantRecipe merchantRecipe = villagerShop.getVillager().getRecipe(indexOfCurrentRecipe);
								List<MerchantRecipe> listOfRecipes = new ArrayList<MerchantRecipe>();
								for (MerchantRecipe trade : villagerShop.getVillager().getRecipes()) {
									if (trade != null) {
										if (trade.getIngredients().equals(merchantRecipe.getIngredients()) &&
												trade.getResult().equals(merchantRecipe.getResult())) {
											trade = null;
										} else {
											listOfRecipes.add(trade);
										}
									} else {
										listOfRecipes.add(trade);
									}
								}
								villagerShop.getVillager().setRecipes(listOfRecipes);
								player.closeInventory();
								player.sendMessage("§cL'échange a bien été supprimé !");
							}
						}
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		for (List<Object> tempVariable : main.getTempVariables()) {
			if (tempVariable.get(0)==player && tempVariable.get(1).equals("RenameVillagerShop")) {
				if (System.currentTimeMillis()>=(long)tempVariable.get(2)+60*1000) {
					player.sendMessage(Main.getText("error.timeoutrenamevillegershop"));
				}else{
					int min = 3;
					int max = 20;
					if (main.getConfig().contains("min_name_of_villagershop") && main.getConfig().contains("max_name_of_villagershop")) {
						min = main.getConfig().getInt("min_name_of_villagershop");
						max = main.getConfig().getInt("max_name_of_villagershop");
					}
					if (event.getMessage().length() >= min && event.getMessage().length() <= max) {
						for (List<Object> tempVariable2 : main.getTempVariables()) {
							if (tempVariable2.get(0) == player && tempVariable2.get(1).equals("VillagerShopSelected")) {
								VillagerShop villagerShop = (VillagerShop)tempVariable2.get(2);
								villagerShop.setName(event.getMessage());
								player.sendMessage(ChatColor.YELLOW + Main.getText("message.renamesuccess") + event.getMessage());
								SaveAndLoadSQLite.updateVillagerShop(villagerShop);
								event.setCancelled(true);
								main.getTempVariables().remove(tempVariable);
								main.getTempVariables().remove(tempVariable2);
								return;
							}
						}
					}else {
						player.sendMessage(ChatColor.RED + Main.getText("error.invalidname.part1") + min
								+ Main.getText("error.invalidname.part2") + max + Main.getText("error.invalidname.part3"));
					}
					event.setCancelled(true);
					main.getTempVariables().remove(tempVariable);
					return;
				}
			}
		}
	}

	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event) {
		String inventoryTitle = event.getView().getTitle();
		Player player = (Player)event.getPlayer();
		for (List<Object> tempVariable : main.getTempVariables()) {
			if (tempVariable.get(0)==player && tempVariable.get(1).equals("VillagerShopSelected")) {
				VillagerShop villagerShop = (VillagerShop)tempVariable.get(2);
				if (event.getInventory() == villagerShop.getInventoryThingsToSell() || event.getInventory() == villagerShop.getInventoryThingsObtained()) {
					villagerShop.updateInventories();
					SaveAndLoadSQLite.saveInventory(villagerShop.getVillager().getUniqueId()+"1", villagerShop.getInventoryThingsObtained());
					SaveAndLoadSQLite.saveInventory(villagerShop.getVillager().getUniqueId()+"2", villagerShop.getInventoryThingsToSell());
				}
			}
		}
	}
}
