package fr.dralexgon.shopasvillagerforplayers.commands;

import java.util.Arrays;

import fr.dralexgon.shopasvillagerforplayers.Main;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CommandGiveSkinVillagerShop implements CommandExecutor{
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] arguments) {
		if (sender instanceof Player) {
			Player player  = (Player)sender;
			try {
				Profession.valueOf(arguments[0]);
				ItemStack item = new ItemStack(Material.LEATHER_CHESTPLATE,1);
				ItemMeta itemMeta = item.getItemMeta();
				itemMeta.setDisplayName("§9SkinVillagerShop");
				itemMeta.setLore(Arrays.asList(arguments[0]));
				item.setItemMeta(itemMeta);
				player.getInventory().addItem(item);
				player.updateInventory();
				player.sendMessage(Main.getText("commands.giveskinvillagershop.success.part1") + arguments[0] +
						Main.getText("commands.giveskinvillagershop.success.part2"));
			} catch (Exception e) {
				player.sendMessage(Main.getText("commands.giveskinvillagershop.error"));
			}
		}
		return false;
	}
}
