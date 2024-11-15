package fr.dralexgon.shopasvillagerforplayers;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import fr.dralexgon.shopasvillagerforplayers.database.SaveSqlite;
import org.bukkit.Material;
import org.bukkit.entity.Villager;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import fr.dralexgon.shopasvillagerforplayers.commands.CommandGiveSkinVillagerShop;
import fr.dralexgon.shopasvillagerforplayers.commands.Commands;
import fr.dralexgon.shopasvillagerforplayers.commands.TabCompletionSkinVillagerShop;
import fr.dralexgon.shopasvillagerforplayers.gui.Gui;
import fr.dralexgon.shopasvillagerforplayers.gui.ListenersGUI;
import fr.dralexgon.shopasvillagerforplayers.saveandload.SaveAndLoad;
import fr.dralexgon.shopasvillagerforplayers.saveandload.UseCsvFiles;

import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;


public class Main extends JavaPlugin {
	
	private List<VillagerShop> listVillagersShop;
	private List<VillagerShop> listVillagersShopInactive;
	private Gui gui;
	private UseCsvFiles useCsvFiles;
	private List<List<Object>> tempVariables;
	private long expirationTime;
	private static Main instance;
	
	@Override
	public void onEnable() {
		saveDefaultConfig();
		Main.instance = this;
		
		this.expirationTime = 1000L *60*60*24*365;
		this.listVillagersShop = new ArrayList<>();
		this.listVillagersShopInactive = new ArrayList<>();
		this.tempVariables = new ArrayList<>();
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
		SaveSqlite.enable();
		
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
	
	public List<List<Object>> getTempVariables() {
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
		List<MerchantRecipe> tempListOfRecipes = new ArrayList<>(villager.getRecipes());
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
		SaveSqlite.disable();
		System.out.println("[ShopPlayerPNJ-FDA] Disabled !");
	}
}
