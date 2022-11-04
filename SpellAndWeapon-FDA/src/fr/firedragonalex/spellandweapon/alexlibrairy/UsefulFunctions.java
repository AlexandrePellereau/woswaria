package fr.firedragonalex.spellandweapon.alexlibrairy;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class UsefulFunctions {
	
	public static HashMap<String,String> loreToHashMap(ItemStack item) {
		return UsefulFunctions.loreToHashMap(item, ": ");
	}
	
	public static int getSign(double number) {
		if (number >= 0) {
			return 1;
		} else {
			return -1;
		}
	}
	
	public static HashMap<String,String> loreToHashMap(ItemStack item, String separationSymbole) {
		if (!item.hasItemMeta() || !item.getItemMeta().hasLore()) return null;
		
		HashMap<String, String> hashMapToReturn = new HashMap<>();
		for (String string : item.getItemMeta().getLore()) {
			String[] stringSplit = string.split(separationSymbole);
			hashMapToReturn.put(stringSplit[0], stringSplit[1]);
		}
		return hashMapToReturn;
	}
	
	public static int inventoryTotalAmount(Inventory inventory) {
		int totalAmount = 0;
		for (ItemStack itemStack : inventory) {
			if (itemStack != null) {
				totalAmount += itemStack.getAmount();
			}
		}
		return totalAmount;
	}
	
	public static void giveOrDrop(List<ItemStack> allItemstacks, Player player) {
		for (ItemStack itemstack : allItemstacks) {
			UsefulFunctions.giveOrDrop(itemstack, player);
		}
	}
	
	public static void giveOrDrop(ItemStack itemStack, Player player) {
		int initialAmount = UsefulFunctions.inventoryTotalAmount(player.getInventory());
		player.getInventory().addItem(itemStack);
		int afterGiveAmount = UsefulFunctions.inventoryTotalAmount(player.getInventory());
		if (afterGiveAmount - initialAmount < itemStack.getAmount()) {
			itemStack.setAmount(afterGiveAmount - initialAmount);
			player.getWorld().dropItem(player.getLocation(), itemStack);
		}
	}
	
	public static ItemStack giveOrReturn(ItemStack itemStack, Player player) {
		return UsefulFunctions.giveOrReturn(itemStack, player.getInventory());
	}
	
	public static ItemStack giveOrReturn(ItemStack itemStack, Inventory inventory) {
		if (itemStack == null) return null;
		int initialAmount = UsefulFunctions.inventoryTotalAmount(inventory);
		inventory.addItem(itemStack);
		int afterGiveAmount = UsefulFunctions.inventoryTotalAmount(inventory);
		if (afterGiveAmount - initialAmount < itemStack.getAmount()) {
			itemStack.setAmount(afterGiveAmount - initialAmount);
			return itemStack;
		}
		return null;
	}
	
	public static boolean canAdd(ItemStack itemStack, Inventory inventory) {
		Inventory virtualInventory = UsefulFunctions.copyInventory(inventory);
		int initialAmount = UsefulFunctions.inventoryTotalAmount(virtualInventory);
		virtualInventory.addItem(itemStack);
		int afterGiveAmount = UsefulFunctions.inventoryTotalAmount(virtualInventory);
		return initialAmount - afterGiveAmount == itemStack.getAmount();
	}
	
	public static boolean canAdd(List<ItemStack> allItemStack, Inventory inventory) {
		int totalAmount = 0;
		for (ItemStack itemstack : allItemStack) {
			totalAmount += itemstack.getAmount();
		}
		Inventory virtualInventory = UsefulFunctions.copyInventory(inventory);
		int initialAmount = UsefulFunctions.inventoryTotalAmount(virtualInventory);
		for (ItemStack itemstack : allItemStack) {
			virtualInventory.addItem(itemstack);
		}
		int afterGiveAmount = UsefulFunctions.inventoryTotalAmount(virtualInventory);
		return !(afterGiveAmount - initialAmount < totalAmount);
	}
	
	public static Inventory copyInventory(Inventory inventory) {
		Inventory virtualInventory = Bukkit.createInventory(null, 5*9);
		ItemStack[] allItemsatck = Arrays.copyOf(inventory.getContents(), inventory.getContents().length);
		for (int i = 0; i < allItemsatck.length; i++) {
			if (allItemsatck[i] != null) {
				allItemsatck[i] = allItemsatck[i].clone();
			}
		}
		return virtualInventory;
	}

}
