package fr.firedragonalex.cities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.PermissionAttachment;

import fr.firedragonalex.areaplugin.area.Area;
import fr.firedragonalex.areaplugin.Selection;

public class Listeners implements Listener{
	
	@EventHandler
	public void onInteractSelection(PlayerInteractEvent event) throws Exception {
		Action action = event.getAction();
		if (action==Action.RIGHT_CLICK_BLOCK) {
			Player player = event.getPlayer();
			Block block = event.getClickedBlock();
			ItemStack it = event.getItem();
			if(it == null) return;
			if(!it.hasItemMeta()) return;
			if(it.getItemMeta().getDisplayName().equals(ChatColor.BLUE+"CitySelector")) {
				event.setCancelled(true);
				
				for (City city : Main.getAllCities()) {
					if (city.isInTheCity(block.getLocation())) {
						player.sendMessage("§cTu ne peux pas faire une séléction dans une ville!");
						return;
					}
				}
				
				for (Selection selection : Main.getAllSelections()) {
					if (selection.getOwner() == player) {
						if (block.getWorld() == selection.getLocation().getWorld()) {
							//System.out.println("[Cities-FDA] New selection : "+block.getLocation().getBlockX()+" "+block.getLocation().getBlockY()+" "+block.getLocation().getBlockZ()+" !");
							player.sendMessage(ChatColor.BLUE+"Nouvelle séléction !");
							
							for (City city : Main.getAllCities()) {
								if (city.getOwner().equals(player.getUniqueId())) {
									
									if (city.isNextToABorderOfTheCity(block.getLocation()) || city.isNextToABorderOfTheCity(selection.getLocation())) {
										city.growCity(selection.getLocation(), block.getLocation());
									} else {
										player.sendMessage(ChatColor.RED+"Une des séléctions doit être prêt de la bordure de la ville !");
										Main.getAllSelections().remove(selection);
									}
									return;
								}
							}
							
							Location[] couplePoints = {selection.getLocation(), block.getLocation()};
							couplePoints[0].setY(0);
							couplePoints[1].setY(256);
							City city = new City("Ville_de_"+player.getName(), player.getUniqueId(), couplePoints);
							Main.getAllCities().add(city);
							Main.setNationality(player, city);

							Main.getAllSelections().remove(selection);
							player.sendMessage(ChatColor.BLUE+"La ville a bien été créée !");
							return;
						}else {
							Main.getAllSelections().remove(selection);
							player.sendMessage("§c[Erreur] Les deux séléctions doivent être dans le même monde !");
							player.sendMessage("§cLes deux séléctions ont été supprimées !");
							return;
						}

					}
				}
				player.sendMessage(ChatColor.BLUE+"Nouvelle séléction !");
				//System.out.println("[AreaPlugin-FDA] New selection : "+block.getLocation().getBlockX()+" "+block.getLocation().getBlockY()+" "+block.getLocation().getBlockZ()+" !");
				Main.getAllSelections().add(new Selection(block.getLocation(), player));
			}
		}
	}
	
	@EventHandler
	public void onInteractTests(PlayerInteractEvent event) {
		Action action = event.getAction();
		Player player = event.getPlayer();
		if (!player.isOp()) return;
		if (action==Action.RIGHT_CLICK_AIR || action==Action.RIGHT_CLICK_BLOCK) {
			ItemStack item = event.getItem();
			if (item != null && item.getType() == Material.PAPER && item.hasItemMeta() && item.getItemMeta().getDisplayName() != null) {
				switch (item.getItemMeta().getDisplayName()) {
				case "nationality":
					player.sendMessage("nationality :"+Main.getNationality(player).getName());
					for (UUID uuid : Main.getAllNationality().keySet()) {
						player.sendMessage(Bukkit.getPlayer(uuid).getName()+" :"+Main.getNationality(uuid).getName());
					}
					break;
				case "spawnSandAtLocationCity":
					for (City city : Main.getAllCities()) {
						if (city.getOwner().equals(player.getUniqueId())) {
							for (Location[] couplePoints : city.getListCouplePoints()) {
								Location location1 = couplePoints[0];
								Location location2 = couplePoints[1];
								location1.setY(player.getLocation().getY()+1);
								location2.setY(player.getLocation().getY()+1);
								player.getWorld().spawnFallingBlock(location1, Material.SAND.createBlockData());
								player.getWorld().spawnFallingBlock(location2, Material.SAND.createBlockData());
							}
						}
					}
					break;
				case "isInTheCity":
					if (action==Action.RIGHT_CLICK_BLOCK) {
						boolean isInTheCity = false;
						for (City city : Main.getAllCities()) {
							if (city.isInTheCity(event.getClickedBlock().getLocation())) {
								isInTheCity = true;
								Bukkit.broadcastMessage("true;owner:"+Bukkit.getOfflinePlayer(city.getOwner()));
							}
						}
						if (!isInTheCity) {
							Bukkit.broadcastMessage("false");
						}
					}
					
					break;
				default:
					break;
				}
				if (item.getItemMeta().getDisplayName().startsWith("plusOrMinus:")) {
					String[] args = item.getItemMeta().getDisplayName().split(":")[1].split(",");
					Bukkit.broadcastMessage("plusOrMinus:"+Main.isEqualsPlusOrMinus(Integer.valueOf(args[0]), Integer.valueOf(args[1]), Integer.valueOf(args[2])));
				}
			}
		}
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		PermissionAttachment attachment = event.getPlayer().addAttachment(Main.getInstance());
		attachment.setPermission("givecityselector.use", true);
		attachment.setPermission("citysettings.use", true);
		attachment.setPermission("nationality.use", true);
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		for (Selection selection : Main.getAllSelections()) {
			if (selection.getOwner()==event.getPlayer()) {
				Main.getAllSelections().remove(selection);
				return;
			}
		}
	}
}
