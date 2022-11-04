package fr.firedragonalex.cities.saveandload;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.firedragonalex.areaplugin.MainAreaPlugin;
import fr.firedragonalex.areaplugin.area.Area;
import fr.firedragonalex.areaplugin.saveandload.UseCsvFiles;
import fr.firedragonalex.cities.City;
import fr.firedragonalex.cities.Main;

public class SaveAndLoadCities {
	
	public static void save() {
		SaveAndLoadCities.saveCities();
		SaveAndLoadCities.saveCitiesAreas();
		SaveAndLoadCities.saveCitiesLocations();
		SaveAndLoadCities.saveCityChests();
		SaveAndLoadCities.saveBanners();
	}
	
	public static void load() {
		SaveAndLoadCities.loadCities();
		SaveAndLoadCities.loadCitiesAreas();
		SaveAndLoadCities.loadCitiesLocations();
		SaveAndLoadCities.loadCityChests();
		SaveAndLoadCities.loadBanners();
	}
	
	private static void saveCities() {
		List<List<String>> listOflines = new ArrayList<List<String>>();
		listOflines.add(Arrays.asList("CityUUID","Owner","Name"));
		for (City city : Main.getAllCities()) {
			List<String> line = new ArrayList<>();
			line.add(city.getUUID().toString());
			line.add(city.getOwner().toString());
			line.add(city.getName());
			listOflines.add(line);
		}
		UseCsvFiles.save("Cities", listOflines, "plugins/Cities-FDA/saves/");
	}
	
	private static void saveCitiesAreas() {
		List<List<String>> listOflines = new ArrayList<List<String>>();
		listOflines.add(Arrays.asList("CityUUID","AreaUUID"));
		for (City city : Main.getAllCities()) {
			for (Area area : city.getListAreas()) {
				List<String> line = new ArrayList<>();
				line.add(city.getUUID().toString());
				line.add(area.getUUID().toString());
				listOflines.add(line);
			}
		}
		UseCsvFiles.save("CitiesAreas", listOflines, "plugins/Cities-FDA/saves/");
	}
	
	private static void saveCitiesLocations() {
		List<List<String>> listOflines = new ArrayList<List<String>>();
		listOflines.add(Arrays.asList("CityUUID","WorldUUID","FirstPointX","FirstPointY","FirstPointZ","SecondPointX","SecondPointY","SecondPointZ"));
		for (City city : Main.getAllCities()) {
			for (Location[] couplePoints : city.getListCouplePoints()) {
				List<String> line = new ArrayList<>();
				line.add(city.getUUID()+"");
				line.add(couplePoints[0].getWorld().getName()+"");
				line.add(couplePoints[0].getBlockX()+"");
				line.add(couplePoints[0].getBlockY()+"");
				line.add(couplePoints[0].getBlockZ()+"");
				line.add(couplePoints[1].getBlockX()+"");
				line.add(couplePoints[1].getBlockY()+"");
				line.add(couplePoints[1].getBlockZ()+"");
				listOflines.add(line);
			}
		}
		UseCsvFiles.save("CitiesLocations", listOflines, "plugins/Cities-FDA/saves/");
	}
	
	private static void loadCities() {
		List<List<String>> lines = UseCsvFiles.load("Cities", "plugins/Cities-FDA/saves/");
		for (List<String> line : lines) {
			UUID uuid = UUID.fromString(line.get(0));
			UUID owner = UUID.fromString(line.get(1));
			String name = line.get(2);
			City city = new City(uuid, name, owner);
			Main.setNationality(owner, city);
			Main.getAllCities().add(city);
		}
	}
	
	private static void loadCitiesAreas() {
		List<List<String>> lines = UseCsvFiles.load("CitiesAreas", "plugins/Cities-FDA/saves/");
		for (List<String> line : lines) {
			UUID cityUUID = UUID.fromString(line.get(0));
			UUID areaUUID = UUID.fromString(line.get(1));
			City city = Main.getCityByUUID(cityUUID);
			Area area = MainAreaPlugin.getThis().getAreaByUUID(areaUUID);
			city.addArea(area);
		}
	}
	
	private static void loadCitiesLocations() {
		List<List<String>> lines = UseCsvFiles.load("CitiesLocations", "plugins/Cities-FDA/saves/");
		for (List<String> line : lines) {
			UUID cityUUID = UUID.fromString(line.get(0));
			World world = Bukkit.getWorld(line.get(1));
			Integer firstpointX = Integer.valueOf(line.get(2));
			Integer firstpointY = Integer.valueOf(line.get(3));
			Integer firstpointZ = Integer.valueOf(line.get(4));
			Integer secondpointX = Integer.valueOf(line.get(5));
			Integer secondpointY = Integer.valueOf(line.get(6));
			Integer secondpointZ = Integer.valueOf(line.get(7));
			Location firstpoint = new Location(world, firstpointX, firstpointY, firstpointZ);
			Location secondpoint = new Location(world, secondpointX, secondpointY, secondpointZ);
			City city = Main.getCityByUUID(cityUUID);
			Location[] couplePoints = {firstpoint,secondpoint};
			city.getListCouplePoints().add(couplePoints);
		}
	}
	
	private static void saveCityChests() {
		System.out.println("[Cities-FDA] Try to save all CityChests...");
		
		File file = new File("plugins/Cities-FDA/saves/" +"saveCityChests"+ ".yml");
		YamlConfiguration config = new YamlConfiguration();
		if (file.exists()) {
			file.delete();
		}
        try {
        	file.createNewFile();
		} catch (Exception e) {}
        
        List<String> allCityUUID = new ArrayList<>();
        for (City city : Main.getAllCities()) {
        	SaveAndLoadCities.saveInventoryKeepOrder(city.getCityChest(), "city_chest."+city.getUUID(), config);
        	allCityUUID.add(city.getUUID().toString());
		}

        config.set("list_city_uuid", allCityUUID.toArray());
        
        try {
        	config.save(file);
		} catch (Exception e) {}
		
		System.out.println("[Cities-FDA] All CityChests save successfully !");
	}
	
	private static void loadCityChests() {
		System.out.println("[Cities-FDA] Try to load all CityChests...");
		
		File file = new File("plugins/Cities-FDA/saves/" +"saveCityChests"+ ".yml");
		if (!file.exists()) {
			System.out.println("[Cities-FDA] [Error] It's normal if it's the first time you launch this plugin.");
			return;
		}
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

		for (String uuidString : config.getStringList("list_city_uuid")) {
			UUID uuid = UUID.fromString(uuidString);
			Main.getCityByUUID(uuid).setCityChest(SaveAndLoadCities.loadInventoryKeepOrder(4, "city_chest."+uuidString, config));
		}
		
		System.out.println("[Cities-FDA] All SellSignInventory load successfully !");
	}
	
	private static void saveBanners() {
		System.out.println("[Cities-FDA] Try to save all banners...");
		
		File file = new File("plugins/Cities-FDA/saves/" +"saveBanners"+ ".yml");
		YamlConfiguration config = new YamlConfiguration();
		if (file.exists()) {
			file.delete();
		}
        try {
        	file.createNewFile();
		} catch (Exception e) {}
        
        List<String> allCityUUID = new ArrayList<>();
        for (City city : Main.getAllCities()) {
        	config.set("banner."+city.getUUID(), city.getBanner());
        	allCityUUID.add(city.getUUID().toString());
		}

        config.set("list_city_uuid", allCityUUID.toArray());
        
        try {
        	config.save(file);
		} catch (Exception e) {}
		
		System.out.println("[Cities-FDA] All banners save successfully !");
	}
	
	private static void loadBanners() {
		System.out.println("[Cities-FDA] Try to load all banners...");
		
		File file = new File("plugins/Cities-FDA/saves/" +"saveBanners"+ ".yml");
		if (!file.exists()) {
			System.out.println("[Cities-FDA] [Error] It's normal if it's the first time you launch this plugin.");
			return;
		}
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

		for (String uuidString : config.getStringList("list_city_uuid")) {
			Main.getCityByUUID(UUID.fromString(uuidString)).setBanner(config.getItemStack("banner."+uuidString));
		}
		
		System.out.println("[Cities-FDA] All banners load successfully !");
	}
	
	public static void saveInventoryKeepOrder(Inventory inventory, String path, YamlConfiguration ymlConfig) {
		int i = 0;
		for (ItemStack itemstack : inventory) {
			if (itemstack != null) {
				ymlConfig.set(path+"."+i, itemstack);
			} else {
				ymlConfig.set(path+"."+i, new ItemStack(Material.AIR));
			}
			i++;
		}

	}
	
	public static Inventory loadInventoryKeepOrder(int inventoryLines, String path, YamlConfiguration ymlConfig) {
		Inventory inventory = Bukkit.createInventory(null, inventoryLines*9);
		
		boolean hasFinish = false;
		int i = 0;
		while (!hasFinish) {
			try {
				inventory.setItem(i, ymlConfig.getItemStack(path+"."+i));
				i++;
			} catch (Exception e) {
				hasFinish = true;
			}
		}
		return inventory;

	}
}
