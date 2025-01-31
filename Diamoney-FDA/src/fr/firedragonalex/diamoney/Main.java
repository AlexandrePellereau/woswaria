package fr.firedragonalex.diamoney;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.management.loading.PrivateClassLoader;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{
	
	private static List<PlayerBank> listPlayerBank;
	private Map<Integer, Material> valueToMaterialMoney;
	private UseCsvFiles useCsvFiles;
	
	@Override
	public void onEnable() {
		
		saveDefaultConfig();
		
		Main.listPlayerBank = new ArrayList<>();
		this.valueToMaterialMoney = new HashMap<Integer, Material>();
		this.valueToMaterialMoney.put(1,Material.ACACIA_BUTTON);
		this.valueToMaterialMoney.put(2,Material.IRON_NUGGET);
		this.valueToMaterialMoney.put(5,Material.GOLD_NUGGET);
		this.valueToMaterialMoney.put(10,Material.COPPER_INGOT);
		this.valueToMaterialMoney.put(20,Material.IRON_INGOT);
		this.valueToMaterialMoney.put(50,Material.GOLD_INGOT);
		this.valueToMaterialMoney.put(100,Material.COPPER_BLOCK);
		this.valueToMaterialMoney.put(200,Material.IRON_BLOCK);
		this.valueToMaterialMoney.put(500,Material.GOLD_BLOCK);
		this.valueToMaterialMoney.put(1000,Material.RAW_GOLD_BLOCK);
		this.valueToMaterialMoney.put(2000,Material.CHEST);
		this.valueToMaterialMoney.put(5000,Material.ENDER_CHEST);
		this.valueToMaterialMoney.put(10000,Material.SMALL_AMETHYST_BUD);
		this.valueToMaterialMoney.put(20000,Material.MEDIUM_AMETHYST_BUD);
		this.valueToMaterialMoney.put(50000,Material.LARGE_AMETHYST_BUD);
		this.valueToMaterialMoney.put(100000,Material.AMETHYST_CLUSTER);
		this.valueToMaterialMoney.put(200000,Material.AMETHYST_BLOCK);
		this.valueToMaterialMoney.put(500000,Material.END_CRYSTAL);
		
		this.useCsvFiles = new UseCsvFiles();
//		ShapedRecipe diamondToDiamoneyRecipe = new ShapedRecipe(null, this.getItemByValue(100));
//		diamondToDiamoneyRecipe.shape("  "," * ","   ");
//		 
//		diamondToDiamoneyRecipe.setIngredient('*',Material.DIAMOND);
//		diamondToDiamoneyRecipe.setIngredient(' ', Material.AIR);
//
//		getServer().addRecipe(diamondToDiamoneyRecipe);
		
		getCommand("diamoneytransform").setExecutor(new CommandTransform(this));
		getCommand("diamoneybank").setExecutor(new CommandBank(this));
		
		getServer().getPluginManager().registerEvents(new Listeners(this), this);
		this.load();
		System.out.println("[Diamoney-FDA] Enabled !");
	}
	
	public void openGui(Player player) {
		int size = 6;
		Inventory inventory = Bukkit.createInventory(null, size*9, "§eDiamoney_Bank:"+Main.getPlayerBank(player).getMoney());			
		for (int i = 9*2; i < 3*9; i++) {
			inventory.setItem(i,this.customItem(Material.BLACK_STAINED_GLASS_PANE,"§"));
		}
		
		int i = 0;
		for (int value : Arrays.asList(1,2,5,10,20,50,100,200,500)) {
			inventory.setItem(i,this.getItemByValue(value));
			i++;
		}
		i = 0;
		for (int value : Arrays.asList(1000,2000,5000,10000,20000,50000,100000,200000,500000)) {
			inventory.setItem(9+i,this.getItemByValue(value));
			i++;
		}
		
		player.openInventory(inventory);
	}
	
	public ItemStack getItemByValue(int value) {
		return this.customItemEnchant(this.valueToMaterialMoney.get(value), value+"@", Arrays.asList("Valeur en diamant : "+(1.0*value/100))) ;
	}
	
	public ItemStack customItem(Material itemType, String name) {
		ItemStack item = new ItemStack(itemType,1);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(name);
		item.setItemMeta(itemMeta);
		return item;
	}
	
	public ItemStack customItem(Material itemType, String name, List<String> lore) {
		ItemStack item = new ItemStack(itemType,1);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(name);
		itemMeta.setLore(lore);
		item.setItemMeta(itemMeta);
		return item;
	}
	
	public ItemStack customItemEnchant(Material itemType, String name, List<String> lore) {
		ItemStack item = new ItemStack(itemType,1);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(name);
		itemMeta.setLore(lore);
		itemMeta.addEnchant(Enchantment.DURABILITY, 0, true);
		itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		item.setItemMeta(itemMeta);
		return item;
	}
	
	public static PlayerBank getPlayerBank(Player player) {
		for (PlayerBank playerBank : Main.listPlayerBank) {
			if (playerBank.getPlayer() == Bukkit.getPlayer(player.getUniqueId())) {
				return playerBank;
			}
		}
		return null;
	}
	
	public PlayerBank addPlayerBank(int startMoney, Player player) {
		Main.listPlayerBank.add(new PlayerBank(startMoney, Bukkit.getPlayer(player.getUniqueId()), this));
		return null;
	}
	
	public Map<Integer, Material> getValueToMaterialMoney() {
		return this.valueToMaterialMoney;
	}
	
	public int sumListItemStackAmount(ItemStack[] list) {
		int sum = 0;
		for(ItemStack item : list) {
			if(item!=null ) {
				sum += item.getAmount();
			}
		}
		return sum;
	}
	
	public void give(Player player,ItemStack itemStack, int integer) {
		itemStack.setAmount(1);
		int tempInventorySize;
		int counter = integer;
		while (counter > 0) {
			tempInventorySize = sumListItemStackAmount(player.getInventory().getContents());
			player.getInventory().addItem(itemStack);
			if (sumListItemStackAmount(player.getInventory().getContents()) == tempInventorySize) {
				player.getLocation().getWorld().dropItem(player.getLocation(), itemStack);
			}
			counter--;
		}
	}
	
	public UseCsvFiles getUseCsvFiles() {
		return this.useCsvFiles;
	}
	
	public void save() {
		System.out.println("[Diamoney-FDA] Try to save all PlayerBank...");
		
		List<List<String>> listOfLines = new ArrayList<List<String>>();
		List<String> line = new ArrayList<String>();
		
		line.add("UUIDPlayer");
		line.add("Dimoney");
		listOfLines.add(line);
		
		for (PlayerBank playerBank : listPlayerBank) {
			
			line = new ArrayList<String>();
			line.add(playerBank.getPlayer().getUniqueId()+"");
			line.add(playerBank.getMoney()+"");
			listOfLines.add(line);
		}
		
		//this.getUseCsvFiles().save("bank", listOfLines, "plugins/Diamoney-FDA_saves/");
		UseCsvFiles.save("bank", listOfLines, "plugins/Diamoney-FDA/saves/");
		System.out.println("[Diamoney-FDA] All PlayerBank save successfully !");
	}
	
	public void load() {
		System.out.println("[Diamoney-FDA] Try to load all PlayerBank...");
		//List<List<String>> listOfLines = this.getUseCsvFiles().load("bank",  "plugins/Diamoney-FDA_saves/");
		List<List<String>> listOfLines = UseCsvFiles.load("bank",  "plugins/Diamoney-FDA/saves/");
		if (listOfLines==null) {
			return;
		}
		for (List<String> line : listOfLines) {
			UUID uuidPlayer = UUID.fromString(line.get(0));
			int money = Integer.valueOf(line.get(1));
			Main.listPlayerBank.add(new PlayerBank(money, Bukkit.getOfflinePlayer(uuidPlayer), this));
		}
	}
	
	@Override
	public void onDisable() {
		this.save();
		System.out.println("[Diamoney-FDA] Disabled !");
	}


}
