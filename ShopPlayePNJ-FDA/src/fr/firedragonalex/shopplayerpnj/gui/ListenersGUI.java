package fr.firedragonalex.shopplayerpnj.gui;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.loot.Lootable;

import fr.firedragonalex.shopplayerpnj.Main;
import fr.firedragonalex.shopplayerpnj.VillagerShop;


public class ListenersGUI implements Listener {
	private Main main;

	public ListenersGUI(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent event) {
		String inventoryTitle = event.getView().getTitle();
		Player player = (Player)event.getWhoClicked();
		ItemStack item = event.getCurrentItem();
		if (item==null) return;
		if (inventoryTitle.equals("�eShopPlayerPNJ_MainMenu")) {
			event.setCancelled(true);
			if (!item.hasItemMeta()) return;
			switch (item.getItemMeta().getDisplayName()) {
			case "Ajouter un echange":
				main.getGui().startNewTrade(player);
				break;
			case "Supprimer un echange":
				main.getGui().startDeleteTrade(player);
				break;
			case "Supprimer le pnj":
				main.getGui().startConfirmDelete(player);
				break;
			case "Transformer en oeuf":
				player.sendMessage("�cEn cours de d�vellopement...");
				for (List tempVariable : main.getTempVariables()) {
					if (tempVariable.get(0)==player && tempVariable.get(1).equals("VillagerShopSelected")) {
						VillagerShop villagerShop = (VillagerShop)tempVariable.get(2);
						//Timestamp ts = new Timestamp(System.currentTimeMillis());//+main.getExpirationTime());  
						//Date date = new Date(ts.getTime());  
						//player.getInventory().addItem(main.getGui().customItem(Material.VILLAGER_SPAWN_EGG, villagerShop.getName(), Arrays.asList("�0VillagerShopCustom","Date d'expiration : ",date+" (Ann�es-Jours-Mois)","�0UUID:"+villagerShop.getUUID(),"�0TimeOfCreation:"+System.currentTimeMillis())));
						//main.getListVillagersShopInactive().add(villagerShop);
						main.getListVillagersShop().remove(villagerShop);
						player.sendMessage("�ckill");
						villagerShop.getVillager().setHealth(0);
						player.closeInventory();
					}
				}
				break;
			case "Renommer":
				player.sendMessage("�eEcris le nom dans le chat.(avant 60s)");
				List tempList = new ArrayList<>();
				tempList.add(player);
				tempList.add("RenameVillagerShop");
				tempList.add(System.currentTimeMillis());
				main.getTempVariables().add(tempList);
				player.closeInventory();
				break;
			case "Stockage des objets � vendre":
				for (List tempVariable : main.getTempVariables()) {
					if (tempVariable.get(0)==player && tempVariable.get(1).equals("VillagerShopSelected")) {
						VillagerShop villagerShop = (VillagerShop)tempVariable.get(2);
						player.openInventory(villagerShop.getInventoryThingsToSell());
					}
				}
				break;
			case "Stockage des objets re�us":
				for (List tempVariable : main.getTempVariables()) {
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
		if (inventoryTitle.equals("�eShopPlayerPNJ_NewTrade")) {
			if (!item.hasItemMeta()) return;
			switch (item.getItemMeta().getDisplayName()) {
			case "--->":
				event.setCancelled(true);
				break;
			case "�":
				event.setCancelled(true);
				break;
			case "Objet1 � recevoir (Obligatoire)":
				event.setCancelled(true);
				break;
			case "Objet2 � recevoir (Pas Obligatoire)":
				event.setCancelled(true);
				break;
			case "Objet � vendre (Obligatoire)":
				event.setCancelled(true);
				break;
			case "Valider":
				event.setCancelled(true);
				for (List tempVariable : main.getTempVariables()) {
					if (tempVariable.get(0)==player && tempVariable.get(1).equals("VillagerShopSelected")) {
						VillagerShop villagerShop = (VillagerShop)tempVariable.get(2);
						Inventory inventory = event.getView().getTopInventory();
						ItemStack inputTrade = inventory.getItem(9*2+1);
						ItemStack inputTrade2 = inventory.getItem(9*2+3);
						ItemStack outputTrade = inventory.getItem(9*2+7);
						if (inputTrade != null && outputTrade != null) {
							main.addTrade(villagerShop.getVillager(), inputTrade, inputTrade2, outputTrade);
						}
						player.closeInventory();
					}
				}
				break;
			default:
				break;
			}
		}
		if (inventoryTitle.equals("�eShopPlayerPNJ_ConfirmDelete")) {
			event.setCancelled(true);
			if (!item.hasItemMeta()) return;
			switch (item.getItemMeta().getDisplayName()) {
			case "Supprimer":
				for (List tempVariable : main.getTempVariables()) {
					if (tempVariable.get(0)==player && tempVariable.get(1).equals("VillagerShopSelected")) {
						VillagerShop villagerShop = (VillagerShop)tempVariable.get(2);
						if (player.getGameMode()!=GameMode.CREATIVE) {
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
								itemMeta2.setDisplayName("�9SkinVillagerShop");
								item2.setItemMeta(itemMeta2);
								villagerShop.getVillager().getWorld().dropItem(villagerShop.getVillager().getLocation(), item2);
							}
							if (main.getConfig().getBoolean("give_villagershopegg_when_you_kill_a_villagershop")) {
								ItemStack item3 = new ItemStack(Material.VILLAGER_SPAWN_EGG,1);
								ItemMeta itemMeta3 = item.getItemMeta();
								itemMeta3.setDisplayName("�eVillagerShop");
								item3.setItemMeta(itemMeta3);
								villagerShop.getVillager().getWorld().dropItem(villagerShop.getVillager().getLocation(), item3);
							}
						}
						villagerShop.death();
						player.closeInventory();
					}
				}
			case "Annuler":
				player.closeInventory();
			default:
				break;
			}
		}
		if (inventoryTitle.equals("�eShopPlayerPNJ_DeleteTrade")) {
			event.setCancelled(true);
			if (!item.hasItemMeta()) return;
			switch (item.getItemMeta().getDisplayName()) {
			case "Echange pr�c�dent":
				for (List tempVariable : main.getTempVariables()) {
					if (tempVariable.get(0)==player && tempVariable.get(1).equals("TradeSelected")) {
						int indexOfCurrentRecipe = (int)tempVariable.get(2);
						main.getTempVariables().remove(tempVariable);
						List tempList = new ArrayList<>();
						tempList.add(player);
						tempList.add("TradeSelected");
						tempList.add(indexOfCurrentRecipe-1);
						main.getTempVariables().add(tempList);
						main.getGui().startDeleteTrade(player);
						return;
					}
				}
				break;
			case "Echange suivant":
				System.out.println(main.getTempVariables());
				for (List tempVariable : main.getTempVariables()) {
					if (tempVariable.get(0)==player && tempVariable.get(1).equals("TradeSelected")) {
						int indexOfCurrentRecipe = (int)tempVariable.get(2);
						main.getTempVariables().remove(tempVariable);
						List tempList = new ArrayList<>();
						tempList.add(player);
						tempList.add("TradeSelected");
						tempList.add(indexOfCurrentRecipe+1);
						main.getTempVariables().add(tempList);
						main.getGui().startDeleteTrade(player);
						return;
					}
				}
				break;
			case "Supprimer l'�change":
				event.setCancelled(true);
				for (List tempVariable : main.getTempVariables()) {
					if (tempVariable.get(0)==player && tempVariable.get(1).equals("VillagerShopSelected")) {
						VillagerShop villagerShop = (VillagerShop)tempVariable.get(2);
						for (List tempVariable2 : main.getTempVariables()) {
							if (tempVariable2.get(0)==player && tempVariable2.get(1).equals("TradeSelected")) {
								int indexOfCurrentRecipe = (int)tempVariable2.get(2);
								MerchantRecipe merchantRecipe = villagerShop.getVillager().getRecipe(indexOfCurrentRecipe);
								List<MerchantRecipe> listOfRecipes = new ArrayList<MerchantRecipe>();
								for (MerchantRecipe trade : villagerShop.getVillager().getRecipes()) {
									if (trade != null) {
										if (trade.getIngredients().equals(merchantRecipe.getIngredients()) && trade.getResult().equals(merchantRecipe.getResult())) {
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
								player.sendMessage("�cL'�change a bien �t� supprim� !");
							}
						}
					}
				}
				break;
			default:
				
				break;
			}
		}
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		for (List tempVariable : main.getTempVariables()) {
			if (tempVariable.get(0)==player && tempVariable.get(1).equals("RenameVillagerShop")) {
				if (System.currentTimeMillis()>=(long)tempVariable.get(2)+60*1000) {
					player.sendMessage("�cD�lai d�pass� !");
				}else{
					int min = 3;
					int max = 20;
					if (main.getConfig().contains("min_name_of_villagershop") && main.getConfig().contains("max_name_of_villagershop")) {
						min = main.getConfig().getInt("min_name_of_villagershop");
						max = main.getConfig().getInt("max_name_of_villagershop");
					}
					if (event.getMessage().length() >= min && event.getMessage().length() <= max) {
						for (List tempVariable2 : main.getTempVariables()) {
							if (tempVariable2.get(0) == player && tempVariable2.get(1).equals("VillagerShopSelected")) {
								VillagerShop villagerShop = (VillagerShop)tempVariable2.get(2);
								player.sendMessage("�eCe marchant a bien �t� renomm� en "+event.getMessage());
								villagerShop.setName(event.getMessage());
								event.setCancelled(true);
								main.getTempVariables().remove(tempVariable);
								main.getTempVariables().remove(tempVariable2);
								return;
							}
						}
					}else {
						player.sendMessage("�cLe nom d'un marchant doit �tre entre "+min+" et "+max+" caract�res.");
					}
					event.setCancelled(true);
					main.getTempVariables().remove(tempVariable);
					return;
				}
			}
		}
	}
}
