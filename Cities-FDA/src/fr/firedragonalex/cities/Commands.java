package fr.firedragonalex.cities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.firedragonalex.areaplugin.area.Area;
import fr.firedragonalex.cities.gui.Gui;

public class Commands implements CommandExecutor,TabCompleter {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) return false;
		Player player  = (Player)sender;
		switch (cmd.getName()) {
		case "givecityselector":
			ItemStack item = new ItemStack(Material.WOODEN_HOE,1);
			ItemMeta itemMeta = item.getItemMeta();
			itemMeta.setDisplayName(ChatColor.BLUE+"CitySelector");
			itemMeta.setLore(Arrays.asList("Right clic on two blocks to select a new city or to grow one."));
			itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
			itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
			itemMeta.setUnbreakable(true);
			item.setItemMeta(itemMeta);
			player.getInventory().addItem(item);
			player.updateInventory();
			player.sendMessage("§eVous avez bien reçu l'§9CitySelector§e !");
			return true;
		case "citysettings":
			if (Main.getCityByPlayerOwner(player.getUniqueId()) == null) {
				player.sendMessage(ChatColor.RED+"Tu dois avoir une ville pour faire cette commande.");
			} else {
				Gui.startGui_CitySettings(player);
			}
			return true;
		case "nationality":
			if (args.length == 1) {
				City cityOfThisPLayer = Main.getCityByPlayerOwner(player.getUniqueId());
				if (cityOfThisPLayer != null) {
					player.sendMessage(ChatColor.RED+"Tu ne peux pas changer de nationalité car tu es le propriétaire d'une ville !");
				} else {
					for (City city : Main.getAllCities()) {
						if (city.getName().equals(args[0])) {
							Main.setNationality(player, city);
						}
					}
				}
			} else {
				player.sendMessage(ChatColor.RED+cmd.getUsage());
				return true;
			}
			return true;
		default:
			break;
		}
		return false;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) return null;
		Player player  = (Player)sender;
		if (cmd.getName().equals("nationality") && args.length == 1) {
			List<String> citiesName = new ArrayList<>();
			City cityOfThisPLayer = Main.getCityByPlayerOwner(player.getUniqueId());
			if (cityOfThisPLayer != null) {
				citiesName.add(cityOfThisPLayer.getName());
				return citiesName;
			} else {
				for (City city : Main.getAllCities()) {
					for (Area area : city.getListAreas()) {
						if (area.getOwner().equals(player.getUniqueId())) {
							citiesName.add(city.getName());
							break;
						}
					}
				}
			}
		}
		return null;
	}

}
