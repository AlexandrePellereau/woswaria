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


public class Commands implements CommandExecutor{
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] arguments) {
		if (!(sender instanceof Player)) { return false; }
		Player player  = (Player)sender;
		switch (cmd.getName()) {
		case "givevillagershop":
			ItemStack item = new ItemStack(Material.VILLAGER_SPAWN_EGG,1);
			ItemMeta itemMeta = item.getItemMeta();
			itemMeta.setDisplayName("§eVillagerShop");
			item.setItemMeta(itemMeta);
			player.getInventory().addItem(item);
			player.sendMessage(Main.getText("commands.givevillagershop"));
			break;
		case "givevillagershopinfinitetrade":
			ItemStack item2 = new ItemStack(Material.VILLAGER_SPAWN_EGG,1);
			ItemMeta itemMeta2 = item2.getItemMeta();
			itemMeta2.setDisplayName("§eVillagerShopInfiniteTrade");
			item2.setItemMeta(itemMeta2);
			player.getInventory().addItem(item2);
			player.sendMessage(Main.getText("commands.givevillagershopinfinitetrade"));
			break;
		case "givevillagershopkiller":
			ItemStack item3 = new ItemStack(Material.BONE,1);
			ItemMeta itemMeta3 = item3.getItemMeta();
			itemMeta3.setDisplayName("§cVillagerShopKiller");
			itemMeta3.setLore(Arrays.asList(Main.getText("commands.givevillagershopkiller.lore")));
			item3.setItemMeta(itemMeta3);
			player.getInventory().addItem(item3);
			player.sendMessage(Main.getText("commands.givevillagershopinfinitetrade"));
			break;
		}
		return false;
	}
}

