package fr.firedragonalex.spellandweapon.custom.easytoadd;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public enum CustomArmor {
	
	PLUMAGE_DE_PHENIX(
			ChatColor.RED+"Plumage de phénix",
			Arrays.asList(
					"Cette armure est constituée de plume phénix,",
					"inventé par GrendSaje et connue pour la légende",
					"du barbare marchant sur la lave."),
			Material.LEATHER,
			10,50);
	
	private String name;
	private List<String> lore;
	private Material typeItem;
	private int defense;
	private int health;
	private static HashMap<Material, List<Material>> typeItemToMaterial;
	
	static {
		CustomArmor.typeItemToMaterial = new HashMap<>();
		List<Material> leatherItems = new ArrayList<>();
		leatherItems.add(Material.LEATHER_HELMET);
		leatherItems.add(Material.LEATHER_CHESTPLATE);
		leatherItems.add(Material.LEATHER_LEGGINGS);
		leatherItems.add(Material.LEATHER_BOOTS);
		CustomArmor.typeItemToMaterial.put(Material.LEATHER, leatherItems);
		List<Material> chainmailItems = new ArrayList<>();
		leatherItems.add(Material.CHAINMAIL_HELMET);
		leatherItems.add(Material.CHAINMAIL_CHESTPLATE);
		leatherItems.add(Material.CHAINMAIL_LEGGINGS);
		leatherItems.add(Material.CHAINMAIL_BOOTS);
		CustomArmor.typeItemToMaterial.put(Material.IRON_NUGGET, chainmailItems);
		List<Material> ironItems = new ArrayList<>();
		leatherItems.add(Material.IRON_HELMET);
		leatherItems.add(Material.IRON_CHESTPLATE);
		leatherItems.add(Material.IRON_LEGGINGS);
		leatherItems.add(Material.IRON_BOOTS);
		CustomArmor.typeItemToMaterial.put(Material.IRON_INGOT, ironItems);
		List<Material> goldItems = new ArrayList<>();
		leatherItems.add(Material.GOLDEN_HELMET);
		leatherItems.add(Material.GOLDEN_CHESTPLATE);
		leatherItems.add(Material.GOLDEN_LEGGINGS);
		leatherItems.add(Material.GOLDEN_BOOTS);
		CustomArmor.typeItemToMaterial.put(Material.GOLD_INGOT, goldItems);
		List<Material> diamondItems = new ArrayList<>();
		leatherItems.add(Material.DIAMOND_HELMET);
		leatherItems.add(Material.DIAMOND_CHESTPLATE);
		leatherItems.add(Material.DIAMOND_LEGGINGS);
		leatherItems.add(Material.DIAMOND_BOOTS);
		CustomArmor.typeItemToMaterial.put(Material.DIAMOND, diamondItems);
	}
	
	private CustomArmor(String name, List<String> lore, Material typeItem, int defense, int health) {
		this.name = name;
		this.lore = lore;
		this.typeItem = typeItem;
		this.defense = defense;
		this.health = health;
	}
	
	public ItemStack getItem(int number) {
		Material finalTypeItem = CustomArmor.typeItemToMaterial.get(this.typeItem).get(number);
		ItemStack item = new ItemStack(finalTypeItem);
		ItemMeta mItem = item.getItemMeta();
		List<String> finalLore = new ArrayList<String>();
		finalLore.add("");
		finalLore.add(ChatColor.YELLOW+"Défense: "+ChatColor.GRAY+"+"+this.defense);
		finalLore.add(ChatColor.YELLOW+"Vie: "+ChatColor.GREEN+"+"+this.health);
		if (lore.size() > 0) {
			finalLore.add("");
		}
		for (String line : lore) {
			finalLore.add(line);
		}
		mItem.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		mItem.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		mItem.setUnbreakable(true);
		mItem.setDisplayName(name);
		mItem.setLore(finalLore);
		item.setItemMeta(mItem);
		return item;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getDefense() {
		return this.defense;
	}
	
	public int getHealth() {
		return this.health;
	}
	
	public static boolean isAnArmor(ItemStack item) {
		for (CustomArmor customArmor : CustomArmor.values()) {
			for (int i = 0; i < 4; i++) {
				if (customArmor.getItem(i).equals(item)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static int getEquipNumber(Material itemMaterial) {
		for (List<Material> materialList : CustomArmor.typeItemToMaterial.values()) {
			if (materialList.contains(itemMaterial)) {
				return materialList.indexOf(itemMaterial);
			}
		}
		return -1;
	}
	
	public static CustomArmor getCustomArmorByItem(ItemStack item) {
		for (CustomArmor customArmor : CustomArmor.values()) {
			for (int i = 0; i < 4; i++) {
				if (customArmor.getItem(i).equals(item)) {
					return customArmor;
				}
			}
		}
		return null;
	}
	
}
