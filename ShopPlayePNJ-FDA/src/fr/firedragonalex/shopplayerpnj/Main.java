package fr.firedragonalex.shopplayerpnj;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import fr.firedragonalex.shopplayerpnj.commands.CommandGiveSkinVillagerShop;
import fr.firedragonalex.shopplayerpnj.commands.Commands;
import fr.firedragonalex.shopplayerpnj.commands.TabCompletionSkinVillagerShop;
import fr.firedragonalex.shopplayerpnj.gui.Gui;
import fr.firedragonalex.shopplayerpnj.gui.ListenersGUI;
import fr.firedragonalex.shopplayerpnj.saveandload.SaveAndLoad;
import fr.firedragonalex.shopplayerpnj.saveandload.UseCsvFiles;

import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;


public class Main extends JavaPlugin {
	
	private List<VillagerShop> listVillagersShop;
	private List<VillagerShop> listVillagersShopInactive;
	private Gui gui;
	private UseCsvFiles useCsvFiles;
	private List<List> tempVariables;
	private long expirationTime;
	private static Main instance;
	
	@Override
	public void onEnable() {
		saveDefaultConfig();
		Main.instance = this;
		
		this.expirationTime = 1000*60*60*24*365;
		this.listVillagersShop = new ArrayList<VillagerShop>();
		this.listVillagersShopInactive = new ArrayList<VillagerShop>();
		this.tempVariables = new ArrayList<List>();
		this.useCsvFiles = new UseCsvFiles();
		this.gui = new Gui(this);
		
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new Listeners(this), this);
		pm.registerEvents(new ListenersGUI(this), this);
		getCommand("giveskinvillagershop").setExecutor(new CommandGiveSkinVillagerShop());
		getCommand("giveskinvillagershop").setTabCompleter(new TabCompletionSkinVillagerShop());
		getCommand("givevillagershop").setExecutor(new Commands());
		getCommand("givevillagershopinfinitetrade").setExecutor(new Commands());
		getCommand("givevillagershopkiller").setExecutor(new Commands());
		
		SaveAndLoad.load();
		
		System.out.println("[ShopPlayerPNJ-FDA] Enabled !");
	}
	
	public static Main getInstance() {
		return Main.instance;
	}
	
	public List<VillagerShop> getListVillagersShop() {
		return this.listVillagersShop;
	}
	
	public List<VillagerShop> getListVillagersShopInactive() {
		return this.listVillagersShopInactive;
	}
	
	public Gui getGui() {
		return this.gui;
	}
	
	public UseCsvFiles getUseCsvFiles() {
		return this.useCsvFiles;
	}
	
	public List<List> getTempVariables() {
		return this.tempVariables;
	}
	
	public long getExpirationTime() {
		return this.expirationTime;
	}
	
	public boolean removeInventory(Inventory inventory, Material type, int nb) {
		int nbMissing = nb;
		for (ItemStack itemStack : inventory.getContents()) {
			if (itemStack!=null) {
				if (itemStack.getType()==type) {
					if (itemStack.getAmount()>=nbMissing) {
						itemStack.setAmount(itemStack.getAmount()-nb);
						return true;
					}else {
						nbMissing-=itemStack.getAmount();
						itemStack.setAmount(0);
					}
				}
			}
		}
		return false;
	}
	
	public void addTrade(Villager villager, ItemStack inputTrade, ItemStack inputTrade2, ItemStack outputTrade) {
		List<MerchantRecipe> tempListOfRecipes = new ArrayList<MerchantRecipe>();
		for (MerchantRecipe recipe : villager.getRecipes()) {
			tempListOfRecipes.add(recipe);
		}
		MerchantRecipe newTrade = new MerchantRecipe(outputTrade.clone(),0);
		newTrade.addIngredient(inputTrade.clone());
		if (inputTrade2 != null) {
			newTrade.addIngredient(inputTrade2.clone());
		}
		newTrade.setExperienceReward(false);
		tempListOfRecipes.add(newTrade);
		villager.setRecipes(tempListOfRecipes);
	}
	
	
	
	@Override
	public void onDisable() {
		SaveAndLoad.save();
		System.out.println("[ShopPlayerPNJ-FDA] Disabled !");
	}
}
