package fr.firedragonalex.uhc.core;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fr.firedragonalex.uhc.specific.ModdedItem;
import fr.firedragonalex.uhc.specific.Role;
import net.md_5.bungee.api.ChatColor;

public class Commands implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String arg2, String[] arg3) {
		if (! (sender instanceof Player)) return false;
		Player player = (Player)sender;
		if (command.getName().equals("role")) {
			Role role = GameManager.getRole(player);
			if (role == null) {
				player.sendMessage(ChatColor.RED + "Vous n'avez pas encore de role !");
				return false;
			}
			role.sendRoleDescription(player);

			//for test
			player.getInventory().addItem(ModdedItem.ARMURE_BLINDEE.getItem());
			player.getInventory().addItem(new ItemStack(Material.MILK_BUCKET));
		}
		return false;
	}

}
