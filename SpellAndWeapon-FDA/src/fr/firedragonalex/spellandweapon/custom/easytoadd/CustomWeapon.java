package fr.firedragonalex.spellandweapon.custom.easytoadd;

import java.util.ArrayList;
import java.util.Arrays;
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

import fr.firedragonalex.spellandweapon.custom.code.CustomWeaponType;
import fr.firedragonalex.spellandweapon.element.Element;
import fr.firedragonalex.spellandweapon.element.ElementType;

public enum CustomWeapon {
	
	HACHE_PUISSANTE_DES_FLAMMES_ETERNELLES(
			"§cHache Puissante Des Flammes Eternelles",
			Arrays.asList(),
			Material.GOLDEN_AXE,
			100,ElementType.FIRE,
			new Element(ElementType.FIRE,20*20),
			true,
			CustomWeaponType.MELEE),
    CROC_DU_BLIZZARD_NORDIQUE(
    		ChatColor.AQUA+"Croc du Blizzard Nordique",
    		Arrays.asList(),
    		Material.DIAMOND_SWORD,
    		50,ElementType.ICE,
    		new Element(ElementType.ICE,20*10),
    		true,
    		CustomWeaponType.MELEE),
    EPEE_ECLAIR(
    		ChatColor.YELLOW+"Epee eclair",
    		Arrays.asList(),
    		Material.GOLDEN_SWORD,
    		20,ElementType.ELECTRICITY,
    		new Element(ElementType.ELECTRICITY,20*10),
    		true,
    		CustomWeaponType.MELEE),
    LAME_DU_BRASIER_MORTEL(
    		ChatColor.RED+"Lame du Brasier Mortel",
    		Arrays.asList(),
    		Material.GOLDEN_SWORD,
    		50,ElementType.FIRE,
    		new Element(ElementType.FIRE,20*10),
    		true,
    		CustomWeaponType.MELEE),
	FIRE_STICK(
			"§cBaton de feu",
			Arrays.asList(),
			Material.BLAZE_ROD,
			5,ElementType.FIRE,
			new Element(ElementType.FIRE,20*10),
			true,
			CustomWeaponType.MELEE),
	DAGUE_EMPOISONNEE(
			ChatColor.GREEN+"Dague empoisonnee",
			Arrays.asList(),
			Material.IRON_SWORD,
			10,ElementType.VEGETAL,
			new Element(ElementType.VEGETAL,20*20),
			true,
			CustomWeaponType.MELEE),
	CATALYSEUR_CYCLONE(
			ChatColor.WHITE+"Catalyseur Cyclone",
			Arrays.asList(),
			Material.HEART_OF_THE_SEA,
			25,ElementType.WIND,
			new Element(ElementType.WIND,20*30),
			true,
			CustomWeaponType.MELEE),
	EPEE_ROUILLEE_DES_OCEANS(
			ChatColor.DARK_BLUE+"Epee rouillee des Oceans",
			Arrays.asList(),
			Material.IRON_SWORD,
			32,ElementType.WATER,
			new Element(ElementType.WATER,20*15),
			true,
			CustomWeaponType.MELEE),
	TENEBRAX(
			ChatColor.BLACK+""+ChatColor.BOLD+"Tenebrax lame des enfers",
			Arrays.asList(
					"Par odre du diable, le grand chevalier",
					"Xanabrus détruit Saint Machin. Pour",
					"le récompenser de sa bravoure, Satan",
					"lui offrit Tenebrax. Avec son épée,",
					"Xanabrus vaincu plusieurs bienfaiteurs",
					"Et, plus les combats passaient, plus",
					"il se sentait fort. Un jour, il ",
					"combatit le diable en duel. Il perdit",
					"le combat. Le diable scella son âme dans",
					"l'épée. Xanabrus est maitenant la bille",
					"violette au milieu de l'épée. ",
					"Prêt a servire son prochain maitre... "
					),
			Material.IRON_SWORD,
			32,ElementType.DARK,
			new Element(ElementType.DARK,20*7),
			false,
			CustomWeaponType.MELEE),
	SHURIKEN_EN_DIAMANT(
			"§bShuriken en diamant",
			Arrays.asList(),
			Material.SNOWBALL,
			200,ElementType.PHYSICAL,
			null,
			true,
			CustomWeaponType.PROJECTILE),
	BATON_DE_DIEU(
			"§fBaton de dieu",
			Arrays.asList(),
			Material.BLAZE_ROD,
			5,ElementType.LIGHT,
			new Element(ElementType.LIGHT,20*10),
			true,
			CustomWeaponType.MELEE),
	ARC_EN_CIEL(
			ChatColor.WHITE+""+ChatColor.ITALIC+"Arc en ciel",
			Arrays.asList(),
			Material.BOW,
			5,ElementType.LIGHT,
			new Element(ElementType.LIGHT,20*10),
			true,
			CustomWeaponType.BOW),
	;
		
	private String name;
	private List<String> lore;
	private Material typeItem;
	private int damage;
	private ElementType elementDamage;
	private Element elementApplied;
	private boolean isLookedEnchanted;
	private CustomWeaponType customWeaponType;
	
	private CustomWeapon(String name, List<String> lore, Material typeItem, int damage, ElementType elementDamage, Element elementApplied, boolean isLookedEnchanted, CustomWeaponType customWeaponType) {
		this.name = name;
		this.lore = lore;
		this.typeItem = typeItem;
		this.damage = damage;
		this.elementDamage = elementDamage;
		this.elementApplied = elementApplied;
		this.isLookedEnchanted = isLookedEnchanted;
		this.customWeaponType = customWeaponType;
	}
	
	public ItemStack getItem() {
		ItemStack item = new ItemStack(typeItem);
		ItemMeta mItem = item.getItemMeta();
		List<String> finalLore = new ArrayList<String>();
		finalLore.add("");
		finalLore.add(ChatColor.YELLOW+"Dégâts: "+ChatColor.RED+"+"+this.damage+ChatColor.YELLOW+"("+this.elementDamage.getColor()+this.elementDamage.getName()+ChatColor.YELLOW+")");
		if (lore.size() > 0) {
			finalLore.add("");
		}
		for (String line : lore) {
			finalLore.add(line);
		}
		if (this.isLookedEnchanted) {
			mItem.addEnchant(Enchantment.DURABILITY, 0, true);
			mItem.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		}
		mItem.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		mItem.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		mItem.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED,new AttributeModifier(UUID.randomUUID(),"test", 0.1, AttributeModifier.Operation.ADD_NUMBER));
		mItem.setUnbreakable(true);
		mItem.setDisplayName(name);
		mItem.setLore(finalLore);
		item.setItemMeta(mItem);
		return item;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getDamage() {
		return this.damage;
	}
	
	public Element getElementApplied() {
		if (this.elementApplied == null) {
			return null;
		} else {
			return this.elementApplied.clone();
		}
	}
	
	public ElementType getElementDamage() {
		return this.elementDamage;
	}
	
	public CustomWeaponType getType() {
		return this.customWeaponType;
	}
	
	public static CustomWeapon getCustomWeponByItem(ItemStack item) {
		if (item == null || !item.hasItemMeta()) return null;
		for (CustomWeapon customWeapon : CustomWeapon.values()) {
			if (customWeapon.getItem().getItemMeta().getDisplayName().equals(item.getItemMeta().getDisplayName())) {
				return customWeapon;
			}
		}
		return null;
	}
}
