package fr.firedragonalex.areaplugin.saveandload;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.firedragonalex.areaplugin.MainAreaPlugin;
import fr.firedragonalex.areaplugin.area.Area;
import fr.firedragonalex.areaplugin.farmarea.FarmArea;
import fr.firedragonalex.areaplugin.farmarea.MinableBlock;

public class SaveAndLoadAreas {
	
	public void loadAllAreas() {
		List<List<String>> lines = UseCsvFiles.load("saveAreas", "plugins/AreaPlugin-FDA/saves/");
		for (List<String> line : lines) {
			List<UUID> mates = new ArrayList<UUID>();
			if (!line.get(2).equals("{Empty}")) {
				String[] matesSplit = line.get(2).split(",");
				for (String mate : matesSplit) {
					mates.add(UUID.fromString(mate));
				}
			}
//			World world = Bukkit.getWorld(UUID.fromString(line.get(4)));
//			if (world == null) {
//				world = Bukkit.getWorld(line.get(4));
//			}
			World world = Bukkit.getWorld(line.get(4));
			
			Location firstPoint = new Location(world, Integer.parseInt(line.get(5)), Integer. parseInt(line.get(6)), Integer. parseInt(line.get(7)));
			Location secondPoint = new Location(world, Integer.parseInt(line.get(8)), Integer. parseInt(line.get(9)), Integer. parseInt(line.get(10)));
			UUID areaUUID = UUID.fromString(line.get(0));
			String name = line.get(1);
			UUID owner = UUID.fromString(line.get(2));
			Area area = new Area( firstPoint, secondPoint, owner, mates, name, areaUUID, MainAreaPlugin.getThis());
			area.setMatesCanBreakAndPlaceBlocks(MainAreaPlugin.getThis().convertStringToBoolean(line.get(11)));
			area.setMatesCanOpenChests(MainAreaPlugin.getThis().convertStringToBoolean(line.get(12)));
			area.setMatesCanOpenDoors(MainAreaPlugin.getThis().convertStringToBoolean(line.get(13)));
			area.setMatesCanUseRedstone(MainAreaPlugin.getThis().convertStringToBoolean(line.get(14)));
			area.setEveryoneCanOpenChests(MainAreaPlugin.getThis().convertStringToBoolean(line.get(15)));
			area.setEveryoneCanOpenDoors(MainAreaPlugin.getThis().convertStringToBoolean(line.get(16)));
			area.setEveryoneCanUseRedstone(MainAreaPlugin.getThis().convertStringToBoolean(line.get(17)));
			area.setIsInvulnerableToExplosion(MainAreaPlugin.getThis().convertStringToBoolean(line.get(18)));
			MainAreaPlugin.getThis().addArea(area);
		}
	}
	
	public void saveAllAreas() {
		System.out.println("[AreaPlugin-FDA] Try to save all areas...");
		
		List<List<String>> listOflines = new ArrayList<List<String>>();
		listOflines.add(Arrays.asList("AreaUUID;Name;Owner;Mates;Wolrd;FirstPointX;FirstPointY;FirstPointZ;SecondPointX;SecondPointY;SecondPointZ;MatesCanBreakAndPlaceBlocks;MatesCanOpenChests;MatesCanOpenDoors;MatesCanUseRedstone;EveryoneCanOpenChests;EveryoneCanOpenDoors;EveryoneCanUseRedstone;IsInvulnerableToExplosion".split(";")));
		for (Area area : MainAreaPlugin.getThis().getAllAreas()) {
			List<String> line = new ArrayList<String>();
			String matesString = "";
			if (area.getMates().size()>=1) {
				int i=0;
				for (UUID mate : area.getMates()) {
					i++;
					matesString+=mate;
					if (!(area.getMates().size()==i)) {
						matesString+=",";
					}
				}
			}else {
				matesString = "{Empty}";
			}
			line.add(area.getUUID()+"");
			line.add(area.getName());
			line.add(area.getOwner()+"");
			line.add(matesString+"");
			//line.add(area.getFirstPoint().getWorld().getUID()+"");
			line.add(area.getFirstPoint().getWorld().getName()+"");
			line.add(area.getFirstPoint().getBlockX()+"");
			line.add(area.getFirstPoint().getBlockY()+"");
			line.add(area.getFirstPoint().getBlockZ()+"");
			line.add(area.getSecondPoint().getBlockX()+"");
			line.add(area.getSecondPoint().getBlockY()+"");
			line.add(area.getSecondPoint().getBlockZ()+"");
			line.add(area.getMatesCanBreakAndPlaceBlocks()+"");
			line.add(area.getMatesCanOpenChests()+"");
			line.add(area.getMatesCanOpenDoors()+"");
			line.add(area.getMatesCanUseRedstone()+"");
			line.add(area.getEveryoneCanOpenChests()+"");
			line.add(area.getEveryoneCanOpenDoors()+"");
			line.add(area.getEveryoneCanUseRedstone()+"");
			line.add(area.isInvulnerableToExplosion()+"");
			//System.out.println(area.getOwner()+";"+matesString+";"+area.getFirstPoint().getWorld().getName()+";"+area.getFirstPoint().getBlockX()+";"+area.getFirstPoint().getBlockY()+";"+area.getFirstPoint().getBlockZ()+";"+area.getSecondPoint().getBlockX()+";"+area.getSecondPoint().getBlockY()+";"+area.getSecondPoint().getBlockZ()+";");
			listOflines.add(line);
		}
		UseCsvFiles.save("saveAreas", listOflines, "plugins/AreaPlugin-FDA/saves/");
		System.out.println("[AreaPlugin-FDA] All areas save successfully !");
    }
	
	public void loadFarmAreas() {
		File farmAreas = new File("plugins/AreaPlugin-FDA/" +"FarmAreas"+ ".yml");
		if (farmAreas.exists()) {
			FileConfiguration farmAreasConfig = YamlConfiguration.loadConfiguration(farmAreas);
			boolean hasCrash;
			List<Area> areaToRemove = new ArrayList<Area>();
			List<Area> farmareaToAdd = new ArrayList<Area>();
			for (Area area : MainAreaPlugin.getThis().getAllAreas()) {
				hasCrash = true;
				List<String> minable = new ArrayList<String>();
				List<String> transform = new ArrayList<String>();
				List<Integer> time = new ArrayList<Integer>();
				minable = farmAreasConfig.getStringList(area.getUUID().toString()+".minable");
				transform = farmAreasConfig.getStringList(area.getUUID().toString()+".transform");
				time = farmAreasConfig.getIntegerList(area.getUUID().toString()+".time");
				hasCrash = false;
				if (!hasCrash) {
					List<MinableBlock> farmableBlocks = new ArrayList<MinableBlock>();
					if (transform.size() == 1) {
						for (int i = 0; i < minable.size(); i++) {
							MinableBlock minableBlock = new MinableBlock(Material.valueOf(minable.get(i)), Material.valueOf(transform.get(0)), time.get(0)*20);
							farmableBlocks.add(minableBlock);
						}
					} else if (minable.size() == transform.size()) {
						for (int i = 0; i < minable.size(); i++) {
							farmableBlocks.add(new MinableBlock(Material.valueOf(minable.get(i)), Material.valueOf(transform.get(i)), time.get(i)*20));
						}
					} else {
						System.out.println("[AreaPlugin-FDA] Error, lenght minable different lenght transform");
						throw new RuntimeException("lenght minable different lenght transform");
					}
					FarmArea farmArea = new FarmArea(area, farmableBlocks);
					areaToRemove.add(area);
					farmareaToAdd.add(farmArea);
				}
			}
			MainAreaPlugin.getThis().getAllAreas().removeAll(areaToRemove);
			MainAreaPlugin.getThis().getAllAreas().addAll(farmareaToAdd);
		} else {
			try {
				farmAreas.createNewFile();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
	
	public void saveSellSign() {
		System.out.println("[AreaPlugin-FDA] Try to save all SellSignInventory...");
		
		File saveSellSignFile = new File("plugins/AreaPlugin-FDA/saves/" +"saveSellSignInventory"+ ".yml");
		YamlConfiguration saveSellSignConfig = new YamlConfiguration();
		if (saveSellSignFile.exists()) {
			saveSellSignFile.delete();
		}
        try {
        	saveSellSignFile.createNewFile();
		} catch (Exception e) {}
        
        for (String signLocation : MainAreaPlugin.getThis().getSignLocationToPrice().keySet()) {
        	this.saveInventory(MainAreaPlugin.getThis().getSignLocationToPrice().get(signLocation), "sell_signs."+signLocation, saveSellSignConfig);
		}
        
        saveSellSignConfig.set("list_sell_signs", MainAreaPlugin.getThis().getSignLocationToPrice().keySet().toArray());
        
        try {
        	saveSellSignConfig.save(saveSellSignFile);
		} catch (Exception e) {}
		
		System.out.println("[AreaPlugin-FDA] All SellSignInventory save successfully !");
	}
	
	public void loadSellSign() {
		System.out.println("[AreaPlugin-FDA] Try to load all SellSignInventory...");
		
		File saveSellSignFile = new File("plugins/AreaPlugin-FDA/saves/" +"saveSellSignInventory"+ ".yml");
		if (!saveSellSignFile.exists()) {
			System.out.println("[AreaPlugin-FDA] [Error] It's normal if it's the first time you launch this plugin.");
			return;
		}
		YamlConfiguration saveSellSignConfig = YamlConfiguration.loadConfiguration(saveSellSignFile);

		for (String signLocation : saveSellSignConfig.getStringList("list_sell_signs")) {
			MainAreaPlugin.getThis().getSignLocationToPrice().put(signLocation, this.loadInventory(4, "sell_signs."+signLocation, saveSellSignConfig));
		}
		
		System.out.println("[AreaPlugin-FDA] All SellSignInventory load successfully !");
	}
	
	public void saveItemstacksToEarn() {
		System.out.println("[AreaPlugin-FDA] Try to save all ItemstacksToEarn...");
		
		File saveFile = new File("plugins/AreaPlugin-FDA/saves/" +"saveItemstacksToEarn"+ ".yml");
		YamlConfiguration ymlConfig = new YamlConfiguration();
		if (saveFile.exists()) {
			saveFile.delete();
		}
        try {
        	saveFile.createNewFile();
		} catch (Exception e) {}
        
        for (UUID playerUUID : MainAreaPlugin.getThis().getNotEarnItemStacks().keySet()) {
        	int i = 0;
    		for (ItemStack itemstack : MainAreaPlugin.getThis().getNotEarnItemStacks().get(playerUUID)) {
    			if (itemstack != null && itemstack.getType() != Material.AIR) {
    				ymlConfig.set("players."+playerUUID+"."+i, itemstack);
    				i++;
    			}
    		}
		}
        List<String> uuidStringList = new ArrayList<>();
        for (UUID uuid : MainAreaPlugin.getThis().getNotEarnItemStacks().keySet()) {
        	uuidStringList.add(uuid.toString());
		}
        ymlConfig.set("list_players",uuidStringList.toArray());
        
        try {
        	ymlConfig.save(saveFile);
		} catch (Exception e) {}
		
		System.out.println("[AreaPlugin-FDA] All ItemstacksToEarn save successfully !");
	}
	
	public void loadItemstacksToEarn() {
		System.out.println("[AreaPlugin-FDA] Try to load all ItemstacksToEarn...");
		
		File saveFile = new File("plugins/AreaPlugin-FDA/saves/" +"saveItemstacksToEarn"+ ".yml");
		
		if (!saveFile.exists()) {
			System.out.println("[AreaPlugin-FDA] [Error] It's normal if it's the first time you launch this plugin.");
			return;
		}
		
		YamlConfiguration ymlConfig = YamlConfiguration.loadConfiguration(saveFile);
		
		for (String stringPlayerUUID : ymlConfig.getStringList("list_players")) {
			List<ItemStack> itemstacksList = new ArrayList<>();
			boolean hasFinish = false;
			int i = 0;
			while (!hasFinish) {
				ItemStack itemstack = ymlConfig.getItemStack("players."+stringPlayerUUID+"."+i); 
				if (itemstack != null) {
					itemstacksList.add(itemstack);
					i++;
				} else {
					hasFinish = true;
				}
			}
			MainAreaPlugin.getThis().getNotEarnItemStacks().put(UUID.fromString(stringPlayerUUID), itemstacksList);
		}
		
		System.out.println("[AreaPlugin-FDA] All ItemstacksToEarn load successfully !");
	}
	
	public void saveInventory(Inventory inventory,String path,YamlConfiguration ymlConfig) {
		int i = 0;
		for (ItemStack itemstack : inventory) {
			if (itemstack!=null) {
				ymlConfig.set(path+"."+i, itemstack);
				i++;
			}
		}

	}
	
	public Inventory loadInventory(int inventoryLines,String path,YamlConfiguration ymlConfig) {
		Inventory inventory = Bukkit.createInventory(null, inventoryLines*9);
		
		boolean hasFinish = false;
		int i = 0;
		while (!hasFinish) {
			try {
				inventory.addItem(ymlConfig.getItemStack(path+"."+i));
				i++;
			} catch (Exception e) {
				hasFinish = true;
			}
		}
		return inventory;

	}
	
	public void saveInventoryKeepOrder(Inventory inventory,String path,YamlConfiguration ymlConfig) {
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
	
	public Inventory loadInventoryKeepOrder(int inventoryLines,String path,YamlConfiguration ymlConfig) {
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
