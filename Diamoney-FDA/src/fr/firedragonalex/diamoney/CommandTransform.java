package fr.firedragonalex.diamoney;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CommandTransform implements CommandExecutor {
	
	private Main main;

	public CommandTransform(Main main) {
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] arg3) {
		if (!(sender instanceof Player)) return false;
		Player player = (Player)sender;
		ItemStack itemStack = player.getInventory().getItemInMainHand();
		if (itemStack.getType() == Material.AIR) {
			player.sendMessage("§cDésolé, tu n'as rien dans ta main !");
			return false;
		}
		if (itemStack.getType() == Material.DIAMOND) {
			ItemStack itemStackMoney = main.getItemByValue(100);
			player.getInventory().setItemInMainHand(null);
			main.give(player, itemStackMoney, itemStack.getAmount());
			player.sendMessage("§eVous avez bien reçu §b"+itemStack.getAmount()*100+"@ §e!");
			return true;
		}
		List<ItemStack> allPossibleItemStackMoney = new ArrayList<>();
		for (Integer integer : main.getValueToMaterialMoney().keySet()) {
			allPossibleItemStackMoney.add(main.getItemByValue(integer));
		}
		boolean isMoney = false;
		if (!(itemStack.getItemMeta().getLore() == null)) {
			if (!(itemStack.getItemMeta().getLore().get(0) == null)) {
				for (ItemStack itemStack2 : allPossibleItemStackMoney) {
					if (itemStack.getItemMeta().getDisplayName().equals(itemStack2.getItemMeta().getDisplayName()) && itemStack.getItemMeta().getLore().get(0).equals(itemStack2.getItemMeta().getLore().get(0)) && itemStack.getType() == itemStack2.getType()) {
						isMoney = true;
					}
//					System.out.println("item1 : "+itemStack);
//					System.out.println("item2 : "+itemStack2);
//					System.out.println("isMoney : "+isMoney);
				}
			}
		}
		if (isMoney) {
			Integer value = Integer.valueOf(itemStack.getItemMeta().getDisplayName().replace("@", ""));
			if (value >= 100) {
				player.getInventory().setItemInMainHand(null);
				main.give(player, new ItemStack(Material.DIAMOND,1),Math.floorDiv(value, 100)*itemStack.getAmount());
				player.sendMessage("§eVous avez bien reçu §b"+Math.floorDiv(value, 100)*itemStack.getAmount()+" dimant(s) §e!");
				return true;
			} else {
				player.sendMessage("§cDésolé, ces pieces sont trop petites.");
				return false;
			}
		}
		player.sendMessage("§cDésolé, tu n'as ni diamoney ni diamant dans ta main !");
		return false;
	}

}
