package fr.firedragonalex.spellandweapon.custom.easytoadd;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import fr.firedragonalex.spellandweapon.Main;

public class Craft {
	
	public static void loadRecipes() {
		HashMap<String, ItemStack> stringToItemStack = new HashMap<String, ItemStack>();

		stringToItemStack.clear();
		stringToItemStack.put("/", Craft.customItem(Material.STICK));
		Craft.newRecipe("Long_baton_craft",Craft.customItem(Material.STICK, ChatColor.GRAY+"Long baton"), 
				" / ", 
				" / ", 
				" / ", stringToItemStack);//!\ pas de espace dans le nom du craft!
		
		stringToItemStack.clear();
		stringToItemStack.put("*", Craft.customItem(Material.DIAMOND,ChatColor.AQUA+"Givralite"));
		stringToItemStack.put("/", Craft.customItem(Material.STICK));
		Craft.newRecipeCustomWeapon(CustomWeapon.CROC_DU_BLIZZARD_NORDIQUE, 
				" * ", 
				" * ", 
				" / ", stringToItemStack);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void newRecipe(String nameOfTheRecipe,ItemStack item,String line1,String line2,String line3, HashMap<String, ItemStack> stringToItemStack) {
		NamespacedKey key = new NamespacedKey(Main.getInstance(), nameOfTheRecipe);
		ShapedRecipe recipe = new ShapedRecipe(key, item);
		recipe.shape(line1,line2,line3);
		
		for (String string : stringToItemStack.keySet()) {
			//recipe.setIngredient(string.charAt(0), stringToItemStack.get(string).getType());
			recipe.setIngredient(string.charAt(0), new RecipeChoice.ExactChoice(stringToItemStack.get(string)));
		}

		Main.getInstance().getServer().addRecipe(recipe);
	}
	
	public static void newRecipeCustomWeapon(CustomWeapon customWeapon,String line1,String line2,String line3, HashMap<String, ItemStack> stringToItemStack) {
		String name = (customWeapon.name()+"_craft");
		NamespacedKey key = new NamespacedKey(Main.getInstance(), name);
		ShapedRecipe recipe = new ShapedRecipe(key, customWeapon.getItem());
		recipe.shape(line1,line2,line3);
		
		for (String string : stringToItemStack.keySet()) {
			//recipe.setIngredient(string.charAt(0), stringToItemStack.get(string).getType());
			recipe.setIngredient(string.charAt(0), new RecipeChoice.ExactChoice(stringToItemStack.get(string)));
		}

		Main.getInstance().getServer().addRecipe(recipe);
	}
	
	public static ItemStack customItem(Material itemType) {
		ItemStack item = new ItemStack(itemType,1);
		return item;
	}
	
	public static ItemStack customItem(Material itemType, String name) {
		ItemStack item = new ItemStack(itemType,1);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(name);
		item.setItemMeta(itemMeta);
		return item;
	}
	
	public static ItemStack customItem(Material itemType, String name, List<String> lore) {
		ItemStack item = new ItemStack(itemType,1);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(name);
		itemMeta.setLore(lore);
		item.setItemMeta(itemMeta);
		return item;
	}
	
	public static ItemStack customItem(int amount, Material itemType) {
		ItemStack item = new ItemStack(itemType,amount);
		return item;
	}
	
	public static ItemStack customItem(int amount, Material itemType, String name) {
		ItemStack item = new ItemStack(itemType,amount);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(name);
		item.setItemMeta(itemMeta);
		return item;
	}
	
	public static ItemStack customItem(int amount, Material itemType, String name, List<String> lore) {
		ItemStack item = new ItemStack(itemType,amount);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(name);
		itemMeta.setLore(lore);
		item.setItemMeta(itemMeta);
		return item;
	}
	
}
