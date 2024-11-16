package fr.dralexgon.shopasvillagerforplayers.commands;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;


public class Commands implements CommandExecutor{
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] arguments) {
		if (!(sender instanceof Player)) { return false; }
		Player player  = (Player)sender;
		switch (cmd.getName()) {
		case "test":
			player.sendMessage("Test");
			ItemStack iitem = player.getInventory().getItemInMainHand();
			ByteArrayOutputStream io = new ByteArrayOutputStream();
            BukkitObjectOutputStream os = null;
            try {
                os = new BukkitObjectOutputStream(io);
				os.writeObject(iitem);
				os.flush();

				byte[] buf = io.toByteArray();
				player.sendMessage("Test");
				player.sendMessage(buf.toString());

				ByteArrayInputStream in = new ByteArrayInputStream(buf);
				BukkitObjectInputStream is = new BukkitObjectInputStream(in);

				ItemStack newItem = (ItemStack) is.readObject();
				player.getInventory().addItem(newItem);


			} catch (Exception e) {
                throw new RuntimeException(e);
            }

            player.sendMessage("Test2");
			break;
		case "givevillagershop":
			ItemStack item = new ItemStack(Material.VILLAGER_SPAWN_EGG,1);
			ItemMeta itemMeta = item.getItemMeta();
			itemMeta.setDisplayName("§eVillagerShop");
			item.setItemMeta(itemMeta);
			player.getInventory().addItem(item);
			player.sendMessage(Main.getText("commands.givevillagershop.success"));
			break;
		case "givevillagershopinfinitetrade":
			ItemStack item2 = new ItemStack(Material.VILLAGER_SPAWN_EGG,1);
			ItemMeta itemMeta2 = item2.getItemMeta();
			itemMeta2.setDisplayName("§eVillagerShopInfiniteTrade");
			item2.setItemMeta(itemMeta2);
			player.getInventory().addItem(item2);
			player.sendMessage(Main.getText("commands.givevillagershopinfinitetrade.success"));
			break;
		case "givevillagershopkiller":
			ItemStack item3 = new ItemStack(Material.BONE,1);
			ItemMeta itemMeta3 = item3.getItemMeta();
			itemMeta3.setDisplayName("§cVillagerShopKiller");
			itemMeta3.setLore(Arrays.asList(Main.getText("commands.givevillagershopkiller.lore")));
			item3.setItemMeta(itemMeta3);
			player.getInventory().addItem(item3);
			player.sendMessage(Main.getText("commands.givevillagershopinfinitetrade.success"));
			break;
		}
		return false;
	}
}

