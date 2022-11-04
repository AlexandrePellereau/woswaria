package fr.firedragonalex.cities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import fr.firedragonalex.areaplugin.AreaEvent;
import fr.firedragonalex.areaplugin.Selection;
import fr.firedragonalex.areaplugin.area.Area;
import fr.firedragonalex.cities.gui.ListenersGui;
import fr.firedragonalex.cities.gui.chatwaiters.ChatWaiter;
import fr.firedragonalex.cities.gui.chatwaiters.ListenerChatWaiter;
import fr.firedragonalex.cities.saveandload.SaveAndLoadCities;

public class Main extends JavaPlugin {
	
	private static Main instance;
	private static HashMap<UUID, City> allNationalities;
	private static List<Selection> allSelections;
	private static List<City> allCities;
	private static List<ChatWaiter> allChatWaiters;
	
	@Override
	public void onEnable() {
		
		Main.instance = this;
		
		saveDefaultConfig();
		
		Main.allNationalities = new HashMap<UUID, City>();
		Main.allSelections = new ArrayList<Selection>();
		Main.allCities = new ArrayList<City>();
		Main.allChatWaiters = new ArrayList<ChatWaiter>();
		
		AreaEvent.registerEvent(new AreaEventListener());
		
		this.getCommand("givecityselector").setExecutor(new Commands());
		this.getCommand("citysettings").setExecutor(new Commands());
		this.getCommand("nationality").setExecutor(new Commands());
		this.getServer().getPluginManager().registerEvents(new Listeners(), this);
		this.getServer().getPluginManager().registerEvents(new ListenersGui(), this);
		this.getServer().getPluginManager().registerEvents(new ListenerChatWaiter(), this);
		
		SaveAndLoadCities.load();
		
		System.out.println("[Cities-FDA] Enabled !");
	}
	
	public static Main getInstance() {
		return Main.instance;
	}
	
	public static HashMap<UUID, City> getAllNationality() {
		return Main.allNationalities;
	}
	
	public static City getNationality(Player player) {
		return Main.allNationalities.get(player.getUniqueId());
	}
	
	public static City getNationality(UUID uuid) {
		return Main.allNationalities.get(uuid);
	}
	
	public static void setNationality(Player player, City city) {
//		City playerCity = Main.getCityByPlayerOwner(player.getUniqueId());
//		if (playerCity == null) {
//			
//		}
		Main.setNationality(player.getUniqueId(), city);
	}
	
	public static void setNationality(UUID playerUUID, City city) {
		if (Main.allNationalities.containsKey(playerUUID)) {
			Main.allNationalities.replace(playerUUID, city);
		} else {
			Main.allNationalities.put(playerUUID, city);
		}
	}
	
	public static ItemStack getDefaultBanner() {
		ItemStack itemStack = new ItemStack(Material.WHITE_BANNER);
		return itemStack.clone();
	}
	
	public static List<ChatWaiter> getAllChatWaiters() {
		return Main.allChatWaiters;
	}
	
	public static City getCityByUUID(UUID uuid) {
		for (City city : Main.getAllCities()) {
			if (city.getUUID().equals(uuid)) {
				return city;
			}
		}
		return null;
	}
	
	public static City getCityByPlayerOwner(UUID uuid) {
		for (City city : Main.getAllCities()) {
			if (city.getOwner().equals(uuid)) {
				return city;
			}
		}
		return null;
	}
	
	public static City getCityByArea(Area area) {
		for (City city : Main.getAllCities()) {
			for (Area area2 : city.getListAreas()) {
				if (area == area2) {
					return city;
				}
			}
		}
		return null;
	}
	
	public static List<Selection> getAllSelections() {
		return Main.allSelections;
	}
	
	public static List<City> getAllCities() {
		return Main.allCities;
	}
	
	public static boolean isEqualsPlusOrMinus(int a, int b, int n) {
		return (b >= a-n) && (b <= a+n);
	}
	
	@Override
	public void onDisable() {
		SaveAndLoadCities.save();
		
		System.out.println("[Cities-FDA] Disabled !");
	}

}
