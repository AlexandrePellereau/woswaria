package fr.firedragonalex.areaplugin.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fr.firedragonalex.areaplugin.MainAreaPlugin;

public class CommandAreaSellRewards implements CommandExecutor {
	
	private MainAreaPlugin mainAreaPlugin;

	public CommandAreaSellRewards(MainAreaPlugin mainAreaPlugin) {
		this.mainAreaPlugin = mainAreaPlugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] arg3) {
		if (!(sender instanceof Player)) return false;
		Player player = (Player)sender;
		if (mainAreaPlugin.getNotEarnItemStacks().containsKey(player.getUniqueId())) {
			List<ItemStack> itemstackToRemove = new ArrayList<>();
			for (ItemStack itemstack : mainAreaPlugin.getNotEarnItemStacks().get(player.getUniqueId())) {
				if (MainAreaPlugin.giveOrReturn(itemstack, player) != null) {
					mainAreaPlugin.getNotEarnItemStacks().get(player.getUniqueId()).removeAll(itemstackToRemove);
					player.sendMessage(ChatColor.RED+"Tu n'as pas pu tout récupérer car ton inventaire est plein.");
					return false;
				} else {
					itemstackToRemove.add(itemstack);
				}
			}
			mainAreaPlugin.getNotEarnItemStacks().remove(player.getUniqueId());
		} else {
			player.sendMessage(ChatColor.RED+"Tu n'as pas d'items à récupérer !");
		}
		return false;
	}

}
