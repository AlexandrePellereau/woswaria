package fr.firedragonalex.cities.gui;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;

import fr.firedragonalex.cities.City;
import fr.firedragonalex.cities.Main;
import fr.firedragonalex.cities.gui.chatwaiters.ChatWaiter;
import fr.firedragonalex.cities.gui.chatwaiters.CWChangeCityName;
import fr.firedragonalex.cities.gui.chatwaiters.CWChangeCityOwner;

public class ListenersGui implements Listener {
	
	private List<Material> allBanners = Arrays.asList(
			Material.WHITE_BANNER,
			Material.BLACK_BANNER,
			Material.BLUE_BANNER,
			Material.BROWN_BANNER,
			Material.CYAN_BANNER,
			Material.GRAY_BANNER,
			Material.GREEN_BANNER,
			Material.LIGHT_BLUE_BANNER,
			Material.LIGHT_GRAY_BANNER,
			Material.LIME_BANNER,
			Material.MAGENTA_BANNER,
			Material.ORANGE_BANNER,
			Material.PINK_BANNER,
			Material.PURPLE_BANNER,
			Material.RED_BANNER,
			Material.YELLOW_BANNER
			);
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		Player player = (Player)event.getWhoClicked();
		ItemStack item = event.getCurrentItem();
		if (item == null) return;
		if (event.getView().getTitle().equals("§eCityGUI_MainSettings")) {
			event.setCancelled(true);
			if (item.hasItemMeta()) {
				City city = Main.getCityByPlayerOwner(player.getUniqueId());
				switch (item.getItemMeta().getDisplayName()) {
				case "Renommer":
					new CWChangeCityName(player);
					player.sendMessage(ChatColor.YELLOW+"Tu dois écrire le nom de la ville dans le chat.(avant 60s)");
					player.closeInventory();
					break;
				case "Nommer un nouveau propriétaire":
					new CWChangeCityOwner(player);
					player.sendMessage(ChatColor.YELLOW+"Tu dois écrire le nom du joueur dans le chat.(avant 60s)");
					player.closeInventory();
					break;
				case "Changer la bannière":
					Gui.startGui_ChangeBanner(player);
					break;
				case "Ouvrir le coffre fort":
					player.openInventory(city.getCityChest());
					break;
				case "Infos":
					player.closeInventory();
					break;
				case "Supprimer":
					Main.getAllCities().remove(city);
					player.sendMessage(ChatColor.RED+"La ville a bien été supprimée !");
					player.closeInventory();
					break;
				default:
					break;
				}
			}
		}
		if (event.getView().getTitle().equals("§eCityGUI_ChangeBanner")) {
			event.setCancelled(true);
			if (item != null && allBanners.contains(item.getType())) {
				City city = Main.getCityByPlayerOwner(player.getUniqueId());
				city.setBanner(item);
				Gui.startGui_ChangeBanner(player);
			}
		}
		if (event.getView().getTitle().equals("§eCityGUI_Infos")) {
			event.setCancelled(true);
			if (item.hasItemMeta()) {
				City city = Main.getCityByPlayerOwner(player.getUniqueId());
				switch (item.getItemMeta().getDisplayName()) {
				case "Informations générales":
					player.sendMessage(ChatColor.YELLOW+"--------Informations------");
					player.sendMessage(ChatColor.YELLOW+"Nom : "+city.getName());
					player.sendMessage(ChatColor.YELLOW+"Nombre de blocks : "+city.getNbTotalBlocks());
					player.sendMessage(ChatColor.YELLOW+"Nombre de citoyens : "+city.getAllCitizens().size());
					player.sendMessage(ChatColor.YELLOW+"Nombre d'habitants : "+city.getAllResident().size());
					player.sendMessage(ChatColor.YELLOW+"Nombre de maisons : "+city.getListAreas().size());
					player.sendMessage(ChatColor.YELLOW+"--------------------------");
					break;
				case "Liste des habitants":
					player.sendMessage(ChatColor.YELLOW+"---------Habitants--------");
					for (UUID uuid : city.getAllCitizens()) {
						player.sendMessage(ChatColor.YELLOW+"- "+Bukkit.getOfflinePlayer(uuid).getName());
					}
					player.sendMessage(ChatColor.YELLOW+"--------------------------");
					break;
				case "Liste des citoyens":
					player.sendMessage(ChatColor.YELLOW+"---------Citoyens---------");
					for (UUID uuid : city.getAllCitizens()) {
						player.sendMessage(ChatColor.YELLOW+"- "+Bukkit.getOfflinePlayer(uuid).getName());
					}
					player.sendMessage(ChatColor.YELLOW+"--------------------------");
					break;
				case "Afficher les frontières":
					break;
				default:
					break;
				}
			}
		}
	}

}
