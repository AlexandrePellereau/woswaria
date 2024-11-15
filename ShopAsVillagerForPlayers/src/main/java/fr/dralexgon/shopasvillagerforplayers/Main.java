package fr.dralexgon.shopasvillagerforplayers;


import java.util.ArrayList;
import java.util.List;

import fr.dralexgon.shopasvillagerforplayers.database.SaveSqlite;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import fr.dralexgon.shopasvillagerforplayers.commands.CommandGiveSkinVillagerShop;
import fr.dralexgon.shopasvillagerforplayers.commands.Commands;
import fr.dralexgon.shopasvillagerforplayers.commands.TabCompletionSkinVillagerShop;
import fr.dralexgon.shopasvillagerforplayers.gui.Gui;
import fr.dralexgon.shopasvillagerforplayers.gui.ListenersGUI;
import fr.dralexgon.shopasvillagerforplayers.saveandload.SaveAndLoad;
import fr.dralexgon.shopasvillagerforplayers.saveandload.UseCsvFiles;

public class Main extends JavaPlugin {

	private static Main instance;
	private static FileConfiguration languageConfig;

	private List<VillagerShop> listVillagersShop;
	private List<VillagerShop> listVillagersShopInactive;
	private Gui gui;
	private UseCsvFiles useCsvFiles;
	private List<List<Object>> tempVariables;
	private long expirationTime;

	
	@Override
	public void onEnable() {
		Main.instance = this;

		saveDefaultConfig();
		languageConfig = MultiLanguage.enable();

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
		
		log(Main.getText("log.enabled"));
	}

	public static Main getInstance() {
		return Main.instance;
	}

	public static void log(String message) {
		System.out.println("[" + Main.getInstance().getDescription().getName() + "] " + message);
	}

	public static String getText(String key) {
		String text = languageConfig.getString(key);
		if (text == null || text.isEmpty() || text.equals("null")) {
			text = ChatColor.RED + "[" + key + " not found]";
		}
		return text;
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

	@Override
	public void onDisable() {
		SaveAndLoad.save();
		SaveSqlite.disable();

		log(Main.getText("log.disabled"));
	}
}
