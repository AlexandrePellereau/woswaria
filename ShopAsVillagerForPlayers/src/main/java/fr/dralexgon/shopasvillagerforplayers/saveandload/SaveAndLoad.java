package fr.dralexgon.shopasvillagerforplayers.saveandload;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.dralexgon.shopasvillagerforplayers.Main;
import fr.dralexgon.shopasvillagerforplayers.VillagerShop;

public class SaveAndLoad {
	
	public static void save() {
		SaveAndLoad.saveInventoriesVillagerShop();
		SaveAndLoad.saveVillagerShop();
	}
	
	public static void load() {
		SaveAndLoad.loadVillagerShop();
	}
	
	private static void saveVillagerShop() {
		System.out.println("[ShopPlayerPNJ-FDA] Try to save all VillagerShop...");
		
		List<List<String>> listOfLines = new ArrayList<List<String>>();
		List<String> line = new ArrayList<String>();
		
		line.add("UUIDVillagerShop");
		line.add("UUIDOwner");
		line.add("NameVillagerShop");
		line.add("NameWorld");
		line.add("X(Location)");
		line.add("Y(Location)");
		line.add("Z(Location)");
		line.add("X(LocationDirection)");
		line.add("Z(LocationDirection)");
		line.add("IDInventoryThingsObtained");
		line.add("IDInventoryThingsToSell");
		line.add("Skin");
		line.add("HasInfiniteTrade");
		listOfLines.add(line);
		
		for (VillagerShop villagerShop : Main.getInstance().getListVillagersShop()) {
			
			line = new ArrayList<String>();
			line.add(villagerShop.getVillager().getUniqueId()+"");
			line.add(villagerShop.getOwner()+"");
			line.add(villagerShop.getName()+"");
			line.add(villagerShop.getVillager().getWorld().getUID()+"");
			line.add(villagerShop.getVillager().getLocation().getChunk().getX()+"");
			line.add(villagerShop.getVillager().getLocation().getChunk().getZ()+"");
			line.add(villagerShop.getVillager().getUniqueId()+"1");
			line.add(villagerShop.getVillager().getUniqueId()+"2");
			line.add(villagerShop.hasInfiniteTrade()+"");
			listOfLines.add(line);
		}
		UseCsvFiles.save("villagershop", listOfLines, "plugins/ShopPlayerPNJ-FDA/saves/");
		System.out.println("[ShopPlayerPNJ-FDA] All VillagerShop save successfully !");
	}
	
	private static void saveInventoriesVillagerShop() {
		System.out.println("[ShopPlayerPNJ-FDA] Try to save all InventoriesVillagerShop...");
		File saveInventory = new File("plugins/ShopPlayerPNJ-FDA/saves/" +"saveItemstacks"+ ".yml");
		FileConfiguration saveInventoryConfig = new YamlConfiguration();
		if (saveInventory.exists()) {
			saveInventory.delete();
		}
        try {
			saveInventory.createNewFile();
		} catch (Exception e) {}
		
		int i;
		for (VillagerShop villagerShop : Main.getInstance().getListVillagersShop()) {
			i = 0;
			for (ItemStack itemstack : villagerShop.getInventoryThingsObtained().getContents()) {
				if (itemstack!=null) {
					saveInventoryConfig.set(villagerShop.getVillager().getUniqueId()+"1"+"."+i, itemstack);
					i++;
				}
			}
			i = 0;
			for (ItemStack itemstack : villagerShop.getInventoryThingsToSell().getContents()) {
				if (itemstack!=null) {
					saveInventoryConfig.set(villagerShop.getVillager().getUniqueId()+"2"+"."+i, itemstack);
					i++;
				}
			}
		}
        try {
        	saveInventoryConfig.save(saveInventory);
		} catch (Exception e) {}
        
		System.out.println("[ShopPlayerPNJ-FDA] All InventoriesVillagerShop save successfully !");
	}
	
	private static void loadVillagerShop() {
		System.out.println("[ShopPlayerPNJ-FDA] Try to load all VillagerShop...");
		List<List<String>> listOfLines = UseCsvFiles.load("villagershop",  "plugins/ShopPlayerPNJ-FDA/saves/");
		if (listOfLines == null) {
			return;
		}
		for (List<String> line : listOfLines) {
			
			UUID uuidVillagerShop = UUID.fromString(line.get(0));
			UUID owner = UUID.fromString(line.get(1));
			String nameVillagerShop = line.get(2);
			World world = Bukkit.getWorld(UUID.fromString(line.get(3)));
			Chunk chunk = world.getChunkAt(Integer.valueOf(line.get(4)),Integer.valueOf(line.get(5)));
			String uuidInventoryThingsObtained = line.get(6);
			String uuidInventoryThingsToSell = line.get(7);
			boolean hasInfiniteTrade = Boolean.valueOf(line.get(8));

			Villager villager = (Villager)Bukkit.getEntity(uuidVillagerShop);
			if (villager == null) {
				for(Entity entity : chunk.getEntities()) {
					if (entity.getUniqueId().equals(uuidVillagerShop)) {
						villager = (Villager)entity;
					}
				}
			}
			if (villager != null) {
				VillagerShop villagerShop = new VillagerShop(owner, nameVillagerShop, villager, hasInfiniteTrade);

				Main.getInstance().getListVillagersShop().add(villagerShop);
				
				
				Inventory inventoryThingsObtained = Bukkit.createInventory(null, 3*9);
				Inventory inventoryThingsToSell = Bukkit.createInventory(null, 3*9);
				
				File saveInventory = new File("plugins/ShopPlayerPNJ-FDA/saves/" +"saveItemstacks"+ ".yml");
				FileConfiguration saveInventoryConfig = YamlConfiguration.loadConfiguration(saveInventory);
				boolean hasFinish = false;
				int i = 0;
				while (!hasFinish) {
					try {
						inventoryThingsObtained.addItem(saveInventoryConfig.getItemStack(uuidInventoryThingsObtained+"."+i));
						i++;
					} catch (Exception e) {
						hasFinish = true;
					}
				}
				hasFinish = false;
				i = 0;
				while (!hasFinish) {
					try {
						inventoryThingsToSell.addItem(saveInventoryConfig.getItemStack(uuidInventoryThingsToSell+"."+i));
						i++;
					} catch (Exception e) {
						hasFinish = true;
					}
				}
				
				villagerShop.setInventoryThingsObtained(inventoryThingsObtained);
				villagerShop.setInventoryThingsToSell(inventoryThingsToSell);
				
			} else {
				System.out.println("[ERROR] Villager not found !");
			}
		}
		System.out.println("[ShopPlayerPNJ-FDA] All VillagerShop load successfully !");
	}
}
