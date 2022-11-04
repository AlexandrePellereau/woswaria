package fr.firedragonalex.spellandweapon.custom.easytoadd;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import fr.firedragonalex.spellandweapon.Main;
import fr.firedragonalex.spellandweapon.custom.code.CustomMonster;
import fr.firedragonalex.spellandweapon.element.Element;
import fr.firedragonalex.spellandweapon.element.ElementType;

public enum CustomMonsterType {
	
	ZOMBIE_VENIMEUX(
			"Zombie venimeux",
			EntityType.ZOMBIE,
			1,1,100,100,
			null, new Element(ElementType.VEGETAL,20*5),
			Arrays.asList(),
			2,Arrays.asList(
					new ItemStack(Material.SLIME_BALL,1))),
	BLOB_EAU(
			"Blob d'eau",
			EntityType.SLIME,
			10,0,0,250,
			ElementType.WATER, new Element(ElementType.WATER,20*5),
			Arrays.asList(ElementType.WATER),
			1,Arrays.asList(
					Craft.customItem(Material.SLIME_BALL,ChatColor.BLUE+"Gélatine de blob")
					)),
	BLOB_DE_FEU(
			"Blob de feu",
			EntityType.SLIME,
			1,0,0,25,
			ElementType.FIRE, new Element(ElementType.FIRE,20*5),
			Arrays.asList(ElementType.FIRE),
			1,Arrays.asList()),
	BLOB_DE_GLACE(
			"Blob de glace",
			EntityType.SLIME,
			1,0,0,25,
			ElementType.ICE, new Element(ElementType.ICE,20*5),
			Arrays.asList(ElementType.ICE),
			1,Arrays.asList(
					Craft.customItem(Material.DIAMOND,ChatColor.AQUA+"Givralite"))),
	BLOB_ELECTRIQUE(
			"Blob d'electricite",
			EntityType.SLIME,
			1,0,0,25,
			ElementType.ELECTRICITY, new Element(ElementType.ELECTRICITY,20*5),
			Arrays.asList(ElementType.ELECTRICITY),
			1,Arrays.asList()),
	BLOB_DE_LUMIERE(
			"Blob de lumiere",
			EntityType.SLIME,
			1,0,0,25,
			ElementType.LIGHT, new Element(ElementType.LIGHT,20*5),
			Arrays.asList(ElementType.LIGHT),
			1,Arrays.asList()),
	BLOB_SOMBRE(
			"Blob sombre",
			EntityType.SLIME,
			1,0,0,25,
			ElementType.DARK, new Element(ElementType.DARK,20*5),
			Arrays.asList(ElementType.DARK),
			1,Arrays.asList()),
	BLOB_DE_TERRE(
			"Blob de terre",
			EntityType.SLIME,
			1,0,0,25,
			ElementType.STONE, new Element(ElementType.STONE,20*5),
			Arrays.asList(ElementType.STONE),
			1,Arrays.asList()),
	BLOB_DE_VENT(
			"Blob de vent",
			EntityType.SLIME,
			10,0,0,250,
			ElementType.WIND, new Element(ElementType.WIND,20*5),
			Arrays.asList(ElementType.WIND),
			1,Arrays.asList()),
	BLOB_VENIMEUX(
			"Blob venimeux",
			EntityType.SLIME,
			10,0,0,250,ElementType.VEGETAL,
			new Element(ElementType.VEGETAL,20*5),
			Arrays.asList(ElementType.VEGETAL),
			1,Arrays.asList()),
	GOLEM_DE_PIERRE(
			"Golem de pierre",
			EntityType.POLAR_BEAR,
			20,0,0,75,ElementType.STONE,new Element(ElementType.STONE,20*5),
			Arrays.asList(),
			1,Arrays.asList(
					new ItemStack(Material.COBBLESTONE,5),
					new ItemStack(Material.MOSSY_COBBLESTONE,1))),
	GOLEM_DE_ROCHE(
			"Golem de roche",
			EntityType.IRON_GOLEM,
			20,0,0,75,ElementType.STONE,
			null,
			Arrays.asList(),
			1,Arrays.asList(
					new ItemStack(Material.COBBLESTONE,5),
					new ItemStack(Material.MOSSY_COBBLESTONE,1))),
	LOUP_DES_ABYSSES(
			"loup des abysses",
			EntityType.WOLF,
			2,0,0,10,
			ElementType.DARK, new Element(ElementType.DARK,20*15),
			Arrays.asList(),
			1,Arrays.asList(
					new ItemStack(Material.OBSIDIAN,3))),
	TOURELLE_SACREE(
			"tourelle sacree",
			EntityType.BLAZE,
			10,0,0,60,
			ElementType.LIGHT,new Element(ElementType.LIGHT,20*10),
			Arrays.asList(),
			1,Arrays.asList(
					new ItemStack(Material.GLOWSTONE,3))),
	RAT(
			"rat",
			EntityType.SILVERFISH,
			5,0,0,15,
			null,new Element(ElementType.DARK,20*1),
			Arrays.asList(),
			1,Arrays.asList(
					new ItemStack(Material.ROTTEN_FLESH,1))),
	SCARAB(
			"scarab",
			EntityType.ENDERMITE,
			3,0,0,20,
			null,new Element(ElementType.VEGETAL,20*1),
			Arrays.asList(),
			1,Arrays.asList(
					new ItemStack(Material.NAUTILUS_SHELL,1))),
	ELECTRHOMME(
			"electrhomme",
			EntityType.ENDERMAN,
			20,0,0,60,
			ElementType.ELECTRICITY,new Element(ElementType.ELECTRICITY,20*5),
			Arrays.asList(),
			1,Arrays.asList(
					new ItemStack(Material.NETHER_BRICKS,2))),
	SANGLIER_MARIN(
			"sanglier marin",
			EntityType.ZOGLIN,
			15,0,0,70,
			ElementType.WATER,new Element(ElementType.WATER,20*5),
			Arrays.asList(),
			1,Arrays.asList(
					new ItemStack(Material.KELP,7))),
	VENINSECTE(
			"veninsecte",
			EntityType.SILVERFISH,
			10,0,0,40,
			ElementType.VEGETAL, new Element(ElementType.VEGETAL,20*5),
			Arrays.asList(),
			1,Arrays.asList(new ItemStack(Material.ROTTEN_FLESH,7))),
	PIGLACE(
			"piglace",
			EntityType.PIGLIN,
			10,0,0,40,
			ElementType.ICE, new Element(ElementType.ICE,20*5),
			Arrays.asList(),
			1,Arrays.asList(new ItemStack(Material.ROTTEN_FLESH,7))),
	PETIT_DEMON(
			"petit demon",
			EntityType.VEX,
			20,0,0,20,
			ElementType.DARK, new Element(ElementType.DARK,20*5),
			Arrays.asList(),
			1,Arrays.asList(
					new ItemStack(Material.CRYING_OBSIDIAN,3),
					new ItemStack(Material.WITHER_ROSE,1))),
	INFERNUM_PORCUS(
			"infernum porcus",
			EntityType.ZOGLIN,
			60,0,0,20,
			ElementType.FIRE, null,
			Arrays.asList(),
			1,Arrays.asList(
					Craft.customItem(Material.BONE, "Corne de porcus"),
					Craft.customItem(1, Material.PORKCHOP, "Cotelette de porcus")))
	;
	
	private String name;
	private EntityType entityType;
	private int attack;
	private int regeneration;
	private int defense;
	private int maxHealth;
	private Element elementApplied;
	private List<ElementType> listElementsImmune;
	private int xpGiven;
	private List<ItemStack> loots;
	
	private CustomMonsterType(
			String name, 
			EntityType entityType,
			int attack, int regeneration, int defense, int maxHealth,
			ElementType elementDamage,Element elementApplied,
			List<ElementType> listElementsImmune, 
			int xpGiven, List<ItemStack> loots) {
		
		this.name = name;
		this.entityType = entityType;
		this.attack = attack;
		this.regeneration = regeneration;
		this.defense = defense;
		this.maxHealth = maxHealth;
		this.elementApplied = elementApplied;
		this.listElementsImmune = listElementsImmune;
		this.xpGiven = xpGiven;
		this.loots = loots;
	}
	
	public String getName() {
		return this.name;
	}
	
	public EntityType getEntityType() {
		return this.entityType;
	}
	
	public int getAttack() {
		return this.attack;
	}
	
	public int getRegeneration() {
		return this.regeneration;
	}
	
	public int getDefense() {
		return this.defense;
	}
	
	public int getMaxHealth() {
		return this.maxHealth;
	}
	
	public Element getElementApplied() {
		if (this.elementApplied == null) {
			return null;
		} else {
			return this.elementApplied.clone();
		}
	}
	
	public List<ElementType> getListElementsImmune() {
		return this.listElementsImmune;
	}
	
	public int getXpGiven() {
		return this.xpGiven;
	}
	
	public List<ItemStack> getLoots() {
		return this.loots;
	}
	
	public double getEntityHeight(int level) {
		CustomMonster customMonster = new CustomMonster(this, level, new Location(Bukkit.getWorlds().get(0), 0, 0, 0));
		customMonster.remove();
		return customMonster.getEntity().getHeight();
	}
	
//	public double getEntityMaxHeight() {
//		CustomMonster customMonster = new CustomMonster(this, 100, new Location(Bukkit.getWorlds().get(0), 0, 0, 0));
//		customMonster.remove();
//		return customMonster.getEntity().getHeight();
//	}
}
