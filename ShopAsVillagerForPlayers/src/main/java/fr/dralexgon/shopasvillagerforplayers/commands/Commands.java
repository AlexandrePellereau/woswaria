package fr.dralexgon.shopasvillagerforplayers.commands;

import java.util.Arrays;

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
//		case"giveskinvillagershop":
//			try {
//				Profession.valueOf(arguments[0]);
//				ItemStack item = new ItemStack(Material.LEATHER_CHESTPLATE,1);
//				ItemMeta itemMeta = item.getItemMeta();
//				itemMeta.setDisplayName("§9SkinVillagerShop");
//				itemMeta.setLore(Arrays.asList(arguments[0]));
//				item.setItemMeta(itemMeta);
//				player.getInventory().addItem(item);
//				player.updateInventory();
//				player.sendMessage("§eVous avez bien reçu un skin de "+arguments[0]+" !");
//			} catch (Exception e) {
//				player.sendMessage("§cCe métier n'existe pas !");
//			}
//			break;
		case "givevillagershop":
			ItemStack item = new ItemStack(Material.VILLAGER_SPAWN_EGG,1);
			ItemMeta itemMeta = item.getItemMeta();
			itemMeta.setDisplayName("§eVillagerShop");
			item.setItemMeta(itemMeta);
			player.getInventory().addItem(item);
			player.sendMessage("§eVous avez bien reçu un VillagerShop !");
			break;
		case "givevillagershopinfinitetrade":
			ItemStack item2 = new ItemStack(Material.VILLAGER_SPAWN_EGG,1);
			ItemMeta itemMeta2 = item2.getItemMeta();
			itemMeta2.setDisplayName("§eVillagerShopInfiniteTrade");
			item2.setItemMeta(itemMeta2);
			player.getInventory().addItem(item2);
			player.sendMessage("§eVous avez bien reçu un VillagerShopInfiniteTrade !");
			break;
		case "givevillagershopkiller":
			ItemStack item3 = new ItemStack(Material.BONE,1);
			ItemMeta itemMeta3 = item3.getItemMeta();
			itemMeta3.setDisplayName("§cVillagerShopKiller");
			item3.setItemMeta(itemMeta3);
			player.getInventory().addItem(item3);
			player.sendMessage("§eVous avez bien reçu un §cVillagerShopKiller§e !");
			break;
		}
		return false;
	}
}

