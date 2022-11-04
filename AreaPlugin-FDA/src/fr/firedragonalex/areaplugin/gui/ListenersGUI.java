package fr.firedragonalex.areaplugin.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Particle;
import org.bukkit.Particle.DustOptions;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.firedragonalex.areaplugin.AreaEvent;
import fr.firedragonalex.areaplugin.MainAreaPlugin;
import fr.firedragonalex.areaplugin.area.Area;

public class ListenersGUI implements Listener {
	private MainAreaPlugin mainAreaPlugin;

	public ListenersGUI(MainAreaPlugin mainAreaPlugin) {
		this.mainAreaPlugin = mainAreaPlugin;
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent event) {
		String inventoryTitle = event.getView().getTitle();
		Player player = (Player)event.getWhoClicked();
		ItemStack item = event.getCurrentItem();
		List tempList;
		if (item==null) return;
		switch (inventoryTitle) {
		case "§1AreaGUI_ChoiceOpOrNot":
			event.setCancelled(true);
			switch (item.getType()) {
			case GRASS_BLOCK:
				mainAreaPlugin.getGui().startAreaGUI_SelectArea(player);
				break;
			case GOLD_BLOCK:
				player.sendMessage("En cours de dev...");
				break;
			default:
				return;
			}
			break;
		case "§1AreaGUI_SelectArea":
			event.setCancelled(true);
			for (Area area : mainAreaPlugin.getAllAreas()) {
				if (area.getOwner().equals(player.getUniqueId()) && area.getName().equals(item.getItemMeta().getDisplayName())) {
					tempList = new ArrayList<>();
					tempList.add(player);
					tempList.add("AreaSelected");
					tempList.add(area);
					mainAreaPlugin.getTempVariables().add(tempList);
					mainAreaPlugin.getGui().startAreaGUI_MainSettings(player);
				}
			}
			break;
		case "§1AreaGUI_AddOrRemoveMates":
			event.setCancelled(true);
			switch (item.getType()) {
			case GREEN_CONCRETE:
				player.sendMessage("§eEcris le nom dans le chat.(avant 60s)");
				tempList = new ArrayList<>();
				tempList.add(player);
				tempList.add("NewMate");
				tempList.add(System.currentTimeMillis());
				mainAreaPlugin.getTempVariables().add(tempList);
				break;
			case RED_CONCRETE:
				player.sendMessage("§eEcris le nom dans le chat.(avant 60s)");
				List tempList2 = new ArrayList<>();
				tempList2.add(player);
				tempList2.add("RemoveMate");
				tempList2.add(System.currentTimeMillis());
				mainAreaPlugin.getTempVariables().add(tempList2);
				break;
			default:
				return;
			}
			player.closeInventory();
			break;
		case"§1AreaGUI_MatesSettings":
			event.setCancelled(true);
			if (item.hasItemMeta()) {
				if (item.getItemMeta().hasLore()) {
					for (List tempVariable : mainAreaPlugin.getTempVariables()) {
						if (tempVariable.get(0)==player && tempVariable.get(1).equals("AreaSelected")) {
							Area area = (Area)tempVariable.get(2);
							switch (item.getItemMeta().getLore().get(1)) {
							case "§0matesCanBreakAndPlaceBlocks":
								if (area.getMatesCanBreakAndPlaceBlocks()) {
									area.setMatesCanBreakAndPlaceBlocks(false);
								}else {
									area.setMatesCanBreakAndPlaceBlocks(true);
								}
								break;
							case "§0matesCanOpenChests":
								if (area.getMatesCanOpenChests()) {
									area.setMatesCanOpenChests(false);;
								}else {
									area.setMatesCanOpenChests(true);;
								}
								break;
							case "§0matesCanOpenDoors":
								if (area.getMatesCanOpenDoors()) {
									area.setMatesCanOpenDoors(false);;
								}else {
									area.setMatesCanOpenDoors(true);;
								}
								break;
							case "§0matesCanUseRedstone":
								if (area.getMatesCanUseRedstone()) {
									area.setMatesCanUseRedstone(false);;
								}else {
									area.setMatesCanUseRedstone(true);;
								}
								break;
							default:
								break;
							}
							mainAreaPlugin.getGui().startAreaGUI_MatesSettings(player);
						}
					}
				}
			}
			break;
		case "§1AreaGUI_EveryoneSettings":
			event.setCancelled(true);
			if (item.hasItemMeta()) {
				if (item.getItemMeta().hasLore()) {
					for (List tempVariable : mainAreaPlugin.getTempVariables()) {
						if (tempVariable.get(0)==player && tempVariable.get(1).equals("AreaSelected")) {
							Area area = (Area)tempVariable.get(2);
							switch (item.getItemMeta().getLore().get(1)) {
							case "§0everyoneCanOpenChests":
								if (area.getEveryoneCanOpenChests()) {
									area.setEveryoneCanOpenChests(false);;
								}else {
									area.setEveryoneCanOpenChests(true);;
								}
								break;
							case "§0everyoneCanOpenDoors":
								if (area.getEveryoneCanOpenDoors()) {
									area.setEveryoneCanOpenDoors(false);;
								}else {
									area.setEveryoneCanOpenDoors(true);;
								}
								break;
							case "§0everyoneCanUseRedstone":
								if (area.getEveryoneCanUseRedstone()) {
									area.setEveryoneCanUseRedstone(false);;
								}else {
									area.setEveryoneCanUseRedstone(true);;
								}
								break;
							default:
								break;
							}
							mainAreaPlugin.getGui().startAreaGUI_EveryoneSettings(player);
						}
					}
				}
			}
			break;
		case"§1AreaGUI_ConfirmDelete":
			event.setCancelled(true);
			switch (item.getItemMeta().getDisplayName()) {
			case "Supprimer":
				for (List tempVariable : mainAreaPlugin.getTempVariables()) {
					if (tempVariable.get(0)==player && tempVariable.get(1).equals("AreaSelected")) {
						Area area = (Area)tempVariable.get(2);
						mainAreaPlugin.getAllAreas().remove(area);
						player.sendMessage("§c"+area.getName()+" supprimé !");
					}
				}
				break;
			case "Annuler":
				break;
			default:
				break;
			}
			player.closeInventory();
			break;
		case "§1AreaGUI_MainSettings":
			event.setCancelled(true);
			switch (item.getItemMeta().getDisplayName()) {
			case "Infos":
				for (List tempVariable : mainAreaPlugin.getTempVariables()) {
					if (tempVariable.get(0)==player && tempVariable.get(1).equals("AreaSelected")) {
						Area area = (Area)tempVariable.get(2);
						String prepareMessages = "";
						for (int i = 0; i < 53; i++) {
							prepareMessages+="-";
						}
						player.sendMessage("§e"+prepareMessages);
						player.sendMessage("§e"+area.getName());
						player.sendMessage("§ePropriétaire : "+Bukkit.getPlayer(area.getOwner()).getName());
						String matesString = "";
						if (area.getMates().size()>=1) {
							int i=0;
							for (UUID mate : area.getMates()) {
								i++;
								matesString+=Bukkit.getOfflinePlayer(mate).getName();
								if (!(area.getMates().size()==i)) {
									matesString+=",";
								}
							}
						}else {
							matesString = "{Vide}";
						}
						player.sendMessage("§eEquipiers : "+matesString);
						player.sendMessage("§eTaille : "+area.getSurfaceString());
						player.sendMessage("§eMonde : "+area.getFirstPoint().getWorld().getName());
						player.sendMessage("§e"+prepareMessages);
						area.showArea(player);
						mainAreaPlugin.getTempVariables().remove(tempVariable);
						player.closeInventory();
						return;
					}
				}
				break;
			case "Gérer les équipiers":
				mainAreaPlugin.getGui().startAreaGUI_AddOrRemoveMates(player);
				return;
			case "Permissions équipiers":
				mainAreaPlugin.getGui().startAreaGUI_MatesSettings(player);
				return;
			case "Permissions autres":
				mainAreaPlugin.getGui().startAreaGUI_EveryoneSettings(player);
				return;
			case "Renommer":
				player.sendMessage("§eEcris le nom dans le chat.(avant 60s)");
				tempList = new ArrayList<>();
				tempList.add(player);
				tempList.add("RenameArea");
				tempList.add(System.currentTimeMillis());
				mainAreaPlugin.getTempVariables().add(tempList);
				break;
			case "Supprimer":
				mainAreaPlugin.getGui().startAreaGUI_ConfirmDelete(player);
				return;
			case "Vendre":
				tempList = new ArrayList<>();
				tempList.add(player);
				tempList.add("InventoryContentBeforeSell");
				ItemStack[] inventoryContent = player.getInventory().getStorageContents();
				for (int i = 0; i < inventoryContent.length; i++) {
					if (inventoryContent[i] == null) {
						inventoryContent[i] = new ItemStack(Material.AIR);
					} else {
						inventoryContent[i] = inventoryContent[i].clone();
					}
				}
				tempList.add(inventoryContent);
				mainAreaPlugin.getTempVariables().add(tempList);
				mainAreaPlugin.getGui().startAreaGUI_SetPriceSellArea(player);
				return;
			default:
				return;
			}
			player.closeInventory();
			break;
		case "§1AreaGUI_SellSign":
			event.setCancelled(true);
			switch (item.getItemMeta().getDisplayName()) {
			case "Prix":
				for (List tempVariable : mainAreaPlugin.getTempVariables()) {
					if (tempVariable.get(0)==player && tempVariable.get(1).equals("SellSignLocation")) {
						Inventory inventoryPrice = (Inventory)mainAreaPlugin.getSignLocationToPrice().get((String)tempVariable.get(2));
						Inventory inventoryPrintPrice = Bukkit.createInventory(null, 4*9, ChatColor.DARK_BLUE+"AreaGUI_SellSignPrintPrice");						
						inventoryPrintPrice.setStorageContents(inventoryPrice.getStorageContents());
						player.openInventory(inventoryPrintPrice);
						mainAreaPlugin.getTempVariables().remove(tempVariable);
						return;
					}
				}
				break;
			case "Infos":
				for (List tempVariable : mainAreaPlugin.getTempVariables()) {
					if (tempVariable.get(0)==player && tempVariable.get(1).equals("SellSignLocation")) {
						String[] stringSplit = ((String)tempVariable.get(2)).split(";");
						Location signLocation = new Location(Bukkit.getWorld(UUID.fromString(stringSplit[3])), Integer.valueOf(stringSplit[0]), Integer.valueOf(stringSplit[1]), Integer.valueOf(stringSplit[2]));
						for (Area area : mainAreaPlugin.getAllAreas()) {
							if (area.isInTheArea(signLocation)) {
								String prepareMessages = "";
								for (int i = 0; i < 53; i++) {
									prepareMessages+="-";
								}
								player.sendMessage("§e"+prepareMessages);
								player.sendMessage("§e"+area.getName());
								player.sendMessage("§ePropriétaire : "+Bukkit.getPlayer(area.getOwner()).getName());
								String matesString = "";
								if (area.getMates().size()>=1) {
									int i=0;
									for (UUID mate : area.getMates()) {
										i++;
										matesString+=Bukkit.getOfflinePlayer(mate).getName();
										if (!(area.getMates().size()==i)) {
											matesString+=",";
										}
									}
								}else {
									matesString = "{Vide}";
								}
								player.sendMessage("§eEquipiers : "+matesString);
								player.sendMessage("§eTaille : "+area.getSurfaceString());
								player.sendMessage("§eMonde : "+area.getFirstPoint().getWorld().getName());
								player.sendMessage("§e"+prepareMessages);
								area.showArea(player);
								mainAreaPlugin.getTempVariables().remove(tempVariable);
							}
						}
					}
				}
				break;
			case "Acheter":
				Area area = new Area();
				Inventory inventoryPrice = Bukkit.createInventory(null, InventoryType.PLAYER);
				Location signLocation = new Location(player.getWorld(), 0, 0, 0);
				for (List tempVariable : mainAreaPlugin.getTempVariables()) {
					if (tempVariable.get(0)==player && tempVariable.get(1).equals("SellSignLocation")) {
						inventoryPrice = (Inventory)mainAreaPlugin.getSignLocationToPrice().get((String)tempVariable.get(2));
						String[] stringSplit = ((String)tempVariable.get(2)).split(";");
						signLocation = new Location(Bukkit.getWorld(UUID.fromString(stringSplit[3])), Integer.valueOf(stringSplit[0]), Integer.valueOf(stringSplit[1]), Integer.valueOf(stringSplit[2]));
						for (Area areaLoop : mainAreaPlugin.getAllAreas()) {
							if (areaLoop.isInTheArea(signLocation)) {
								area = areaLoop;
							}
						}
					}
				}
				
				if (AreaEvent.callBuyAreaEvent(area, player, inventoryPrice)) {
					AreaEvent.sendMessageCancel(player);
					return;
				}
				
				ItemStack[] cloneItemStacks = new ItemStack[player.getInventory().getStorageContents().length];
				int i = 0;
				for (ItemStack itemStack : player.getInventory().getStorageContents()) {
					if (itemStack == null) {
						cloneItemStacks[i] = new ItemStack(Material.AIR);
					} else {
						cloneItemStacks[i] = itemStack.clone();
					}
					i++;
				}
				Inventory virtualPlayerInventory = Bukkit.createInventory(null, player.getInventory().getStorageContents().length);
				virtualPlayerInventory.setStorageContents(cloneItemStacks);
				for (ItemStack itemStack : inventoryPrice.getStorageContents()) {
					if (itemStack != null && itemStack.getType() != Material.AIR) {
						if (virtualPlayerInventory.contains(itemStack.getType(),itemStack.getAmount())) {
							mainAreaPlugin.removeInventory(itemStack, itemStack.getAmount(), virtualPlayerInventory);
						} else {
							player.sendMessage(ChatColor.RED+"[AreaPlugin-FDA] Désolé, tu n'as pas les objets requis.");
							player.closeInventory();
							return;
						}
					}
				}
				player.getInventory().setStorageContents(virtualPlayerInventory.getStorageContents());
				player.updateInventory();
				
				signLocation.getBlock().setType(Material.AIR);
				mainAreaPlugin.getSignLocationToPrice().remove(signLocation.getBlockX()+";"+signLocation.getBlockY()+";"+signLocation.getBlockZ()+";"+signLocation.getWorld().getUID());
				
				UUID seller = area.getOwner();
				MainAreaPlugin.addItemToEarn(seller, Arrays.asList(inventoryPrice.getStorageContents()));
				if (Bukkit.getOfflinePlayer(seller).isOnline()) {
					Bukkit.getPlayer(seller).sendMessage(ChatColor.YELLOW+"[Server-AreaPlugin] Tu as reçu des objets grâce à la vente d'une zone. Utilise la commande /areasellrewards pour les récupérer !");
				}
				
				area.setOwner(player.getUniqueId());
				player.sendMessage(ChatColor.YELLOW+"[AreaPlugin-FDA] L'achat a bien été effectué.");
				break;
			default:
				return;
			}
			player.closeInventory();
			break;
		case "§1AreaGUI_SellSignPrintPrice":
			event.setCancelled(true);
			break;
//		case "§1AreaGUI_SellArea":
//			mainAreaPlugin.getGui().startAreaGUI_SellArea(player);
		default:
			break;
		}
	}
	
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event) {
		if (event.getView().getTitle().equals(ChatColor.DARK_BLUE+"AreaGUI_SetPriceSellArea")) {
			Player player = (Player)event.getPlayer();
			for (List tempVariable : mainAreaPlugin.getTempVariables()) {
				if (tempVariable.get(0)==player && tempVariable.get(1).equals("InventoryContentBeforeSell")) {
					player.getInventory().setStorageContents((ItemStack[])tempVariable.get(2));
				}
			}
			for (List tempVariable : mainAreaPlugin.getTempVariables()) {
				if (tempVariable.get(0)==player && tempVariable.get(1).equals("AreaSelected")) {
					Area area = (Area)tempVariable.get(2);
					Location signLocation = player.getLocation();
					
					MainAreaPlugin.getThis().getSignLocationToPrice().put(
							signLocation.getBlockX()+";"+
							signLocation.getBlockY()+";"+
							signLocation.getBlockZ()+";"+
							signLocation.getWorld().getUID(),
							event.getInventory());
					
					if (area.isInTheArea(signLocation)) {
						signLocation.getBlock().setBlockData(Material.OAK_SIGN.createBlockData());
						Sign sign = (Sign)signLocation.getBlock().getState();
						sign.setLine(0, ChatColor.YELLOW+"A VENDRE PAR");
						sign.setLine(1, ChatColor.YELLOW+""+Bukkit.getOfflinePlayer(area.getOwner()).getName());
						sign.setLine(2, ChatColor.YELLOW+area.getSurfaceString());
						sign.setLine(3, ChatColor.YELLOW+"Prix/info:clique");
						sign.update();
					} else {
						player.sendMessage(ChatColor.RED+"Vous devez vendre votre zone dans votre zone !");
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		Player player = (Player)event.getWhoClicked();
		if (event.getView().getTitle().equals(ChatColor.DARK_BLUE+"AreaGUI_SetPriceSellArea")) {
			if (event.getClickedInventory() == null || !event.getClickedInventory().equals(player.getInventory()) ||
					event.getClick() == ClickType.DOUBLE_CLICK ||
					event.getClick() == ClickType.SHIFT_LEFT ||
					event.getClick() == ClickType.SHIFT_RIGHT) {
				event.setCancelled(true);
				if (event.getCursor() != null) {
					event.getInventory().addItem(event.getCursor());
				} else {
					event.getInventory().addItem(event.getCurrentItem());
				}
				
			}
		}
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		for (List tempVariable : mainAreaPlugin.getTempVariables()) {
			if (tempVariable.get(0)==player && tempVariable.get(1).equals("NewMate")) {
				if (System.currentTimeMillis()>=(long)tempVariable.get(2)+60*1000) {
					player.sendMessage("§cDélai dépassé !");
				}else{
					boolean playerFind = false;
					for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
						if (onlinePlayer.getName().equals(event.getMessage())) {
							playerFind=true;
							break;
						}
					}
					if (!playerFind) {
						for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()) {
							if (offlinePlayer.getName().equals(event.getMessage())) {
								playerFind=true;
								break;
							}
						}
					}
					if (playerFind) {
						for (List tempVariable2 : mainAreaPlugin.getTempVariables()) {
							if (tempVariable2.get(0)==player && tempVariable2.get(1).equals("AreaSelected")) {
								Area area = (Area)tempVariable2.get(2);
								area.getMates().add(Bukkit.getPlayer(event.getMessage()).getUniqueId());
								player.sendMessage("§e"+event.getMessage()+" a bien été ajouté à vos équipiers de "+area.getName());
								event.setCancelled(true);
								mainAreaPlugin.getTempVariables().remove(tempVariable);
								mainAreaPlugin.getTempVariables().remove(tempVariable2);
								return;
							}
						}
					}else {
						player.sendMessage("§cCe joueur n'a jamais rejoint le serveur !");
						for (List tempVariable2 : mainAreaPlugin.getTempVariables()) {
							if (tempVariable2.get(0)==player && tempVariable2.get(1).equals("AreaSelected")) {
								event.setCancelled(true);
								mainAreaPlugin.getTempVariables().remove(tempVariable);
								mainAreaPlugin.getTempVariables().remove(tempVariable2);
								return;
							}
						}
					}
				}
			}
			if (tempVariable.get(0)==player && tempVariable.get(1).equals("RemoveMate")) {
				if (System.currentTimeMillis()>=(long)tempVariable.get(2)+60*1000) {
					player.sendMessage("§cDélai dépassé !");
				}else{
					for (List tempVariable2 : mainAreaPlugin.getTempVariables()) {
						if (tempVariable2.get(0)==player && tempVariable2.get(1).equals("AreaSelected")) {
							Area area = (Area)tempVariable2.get(2);
							for (UUID mate : area.getMates()) {
								if (Bukkit.getOfflinePlayer(mate).getName().equals(event.getMessage())) {
									area.getMates().remove(event.getMessage());
									player.sendMessage("§e"+event.getMessage()+" a bien été supprimé de vos équipiers de "+area.getName());
									event.setCancelled(true);
									mainAreaPlugin.getTempVariables().remove(tempVariable);
									mainAreaPlugin.getTempVariables().remove(tempVariable2);
									return;
								}
							}
							player.sendMessage("§c"+event.getMessage()+" ne fais pas parti des équipiers de "+area.getName()+" !");
							event.setCancelled(true);
							mainAreaPlugin.getTempVariables().remove(tempVariable);
							mainAreaPlugin.getTempVariables().remove(tempVariable2);
							return;
						}
					}
				}
			}
			if (tempVariable.get(0)==player && tempVariable.get(1).equals("RenameArea")) {
				if (System.currentTimeMillis()>=(long)tempVariable.get(2)+60*1000) {
					player.sendMessage("§cDélai dépassé !");
				}else{
					if (event.getMessage().length()>=3 && event.getMessage().length()<=20) {
						for (List tempVariable2 : mainAreaPlugin.getTempVariables()) {
							if (tempVariable2.get(0)==player && tempVariable2.get(1).equals("AreaSelected")) {
								event.setMessage(event.getMessage().replace("&", "§"));
								Area area = (Area)tempVariable2.get(2);
								player.sendMessage("§eLa zone "+area.getName()+" a bien été renommé en "+event.getMessage());
								area.setName(event.getMessage());
								event.setCancelled(true);
								mainAreaPlugin.getTempVariables().remove(tempVariable);
								mainAreaPlugin.getTempVariables().remove(tempVariable2);
								return;
							}
						}
					}else {
						player.sendMessage("§cLe nom d'une zone doit être entre 3 et 20 caractères.");
					}
					event.setCancelled(true);
					mainAreaPlugin.getTempVariables().remove(tempVariable);
					return;
				}
			}
		}
	}
}