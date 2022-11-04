package fr.firedragonalex.areaplugin.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.firedragonalex.areaplugin.MainAreaPlugin;

public class CommandGiveAreaSelectors implements CommandExecutor{
	
	private MainAreaPlugin mainAreaPlugin;
	
	public CommandGiveAreaSelectors(MainAreaPlugin mainAreaPlugin) {
		this.mainAreaPlugin = mainAreaPlugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] arg3) {
		if (sender instanceof Player) {
			Player player  = (Player)sender;
			if (cmd.getName().equalsIgnoreCase("giveareaselector")) {
				ItemStack areaSelector = new ItemStack(Material.WOODEN_HOE,1);
				ItemMeta areaSelectorItemMeta = areaSelector.getItemMeta();
				areaSelectorItemMeta.setDisplayName(ChatColor.BLUE+"AreaSelector");
				areaSelectorItemMeta.setLore(Arrays.asList("Clique droit sur deux bloks pour séléctionner une zone."));
				areaSelectorItemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
				areaSelectorItemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
				areaSelectorItemMeta.setUnbreakable(true);
				areaSelector.setItemMeta(areaSelectorItemMeta);
				player.getInventory().addItem(areaSelector);
				player.updateInventory();
				player.sendMessage("§eVous avez bien reçu l'§9AreaSelector§e !");
				return true;
			}
			if (cmd.getName().equalsIgnoreCase("giveareaselector+")) {
				ItemStack areaSelectorWithY = new ItemStack(Material.WOODEN_HOE,1);
				ItemMeta areaSelectorWithYItemMeta = areaSelectorWithY.getItemMeta();
				areaSelectorWithYItemMeta.setDisplayName(ChatColor.BLUE+"AreaSelector(with height)");
				areaSelectorWithYItemMeta.setLore(Arrays.asList("Clique droit sur deux bloks pour séléctionner une zone."));
				areaSelectorWithYItemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
				areaSelectorWithYItemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
				areaSelectorWithYItemMeta.setUnbreakable(true);
				areaSelectorWithY.setItemMeta(areaSelectorWithYItemMeta);
				player.getInventory().addItem(areaSelectorWithY);
				player.updateInventory();
				player.sendMessage("§eVous avez bien reçu l'§9AreaSelector(with height)§e !");
				return true;
			}
		}
		return false;
	}
}
