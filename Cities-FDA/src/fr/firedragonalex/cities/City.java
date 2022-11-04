package fr.firedragonalex.cities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.firedragonalex.areaplugin.area.Area;

public class City {
	
	private UUID uuid;
	private UUID owner;
	private String name;
	private ItemStack banner;
	private Inventory cityChest;
	private List<Location[]> listCouplePoints;
	private List<Area> listAreas;
	
	public City(String name,UUID owner,Location[] firstCouplePoints) throws Exception {
		this.name = name;
		this.owner = owner;
		this.uuid = UUID.randomUUID();
		this.banner = Main.getDefaultBanner();
		this.cityChest = Bukkit.createInventory(null, 6*9, "§eCityGUI_CityChest");
		this.listCouplePoints = new ArrayList<Location[]>();
		if (firstCouplePoints.length != 2) throw new Exception("[Unexpected Lenght] Couple point arrays must be long 2. (actual lenght : "+firstCouplePoints.length+" )"); 
		this.listCouplePoints.add(firstCouplePoints);
		this.listAreas = new ArrayList<Area>();
	}
	
	public City(UUID uuid,String name,UUID owner){
		//for the save ONLY
		this.name = name;
		this.owner = owner;
		this.uuid = uuid;
		this.banner = Main.getDefaultBanner();
		this.cityChest = Bukkit.createInventory(null, 6*9, "§eCityGUI_CityChest");
		this.listCouplePoints = new ArrayList<Location[]>();
		this.listAreas = new ArrayList<Area>();
	}
	
	public void growCity(Location firstPoint, Location secondPoint) {
		Location[] couplePoints = {firstPoint,secondPoint};
		this.listCouplePoints.add(couplePoints);
		Bukkit.getPlayer(this.getOwner()).sendMessage(ChatColor.BLUE+"La ville a bien été agrandie !");
	}
	
	public boolean isNextToABorderOfTheCity(Location point) {
		for (Location[] couplePoints : this.listCouplePoints) {
			if (this.isNextToABorderOfThisCoupleOfPoint(point, couplePoints[0], couplePoints[1])) {
				return true;
			}
		}
		return false;
	}
	
	private boolean isNextToABorderOfThisCoupleOfPoint(Location point,Location firstPoint,Location secondPoint) {
		return  (Main.isEqualsPlusOrMinus(point.getBlockX(), firstPoint.getBlockX(), 1) && Main.isEqualsPlusOrMinus(point.getBlockZ(), firstPoint.getBlockZ(), 1)) ||
				(Main.isEqualsPlusOrMinus(point.getBlockX(), secondPoint.getBlockX(), 1) && Main.isEqualsPlusOrMinus(point.getBlockZ(), secondPoint.getBlockZ(), 1));
	}
	
	public UUID getUUID() {
		return this.uuid;
	}
	
	public UUID getOwner() {
		return this.owner;
	}
	
	public String getName() {
		return this.name;
	}
	
	public ItemStack getBanner() {
		return this.banner;
	}
	
	public Inventory getCityChest() {
		return this.cityChest;
	}
	
	public List<Area> getListAreas() {
		return this.listAreas;
	}
	
	public List<Location[]> getListCouplePoints() {
		return this.listCouplePoints;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setOwner(UUID owner) {
		this.owner = owner;
	}
	
	public void setBanner(ItemStack banner) {
		this.banner = banner;
	}
	
	public void setCityChest(Inventory inventory) {
		this.cityChest = inventory;
	}
	
	public void addArea(Area area) {
		this.listAreas.add(area);
	}
	
	public boolean isInTheCityWithoutArea(Location newPoint) {
		if (this.isInTheCity(newPoint)) {
			for (Area area : this.listAreas) {
				if (area.isInTheArea(newPoint)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean isInTheCity(Location newPoint) {
		for (Location[] locations : this.listCouplePoints) {
			Location firstPoint = locations[0];
			Location secondPoint = locations[1];
			if (((firstPoint.getBlockX()-newPoint.getBlockX())*(secondPoint.getBlockX()-newPoint.getBlockX())<=0) &&
				((firstPoint.getBlockY()-newPoint.getBlockY())*(secondPoint.getBlockY()-newPoint.getBlockY())<=0) &&
				((firstPoint.getBlockZ()-newPoint.getBlockZ())*(secondPoint.getBlockZ()-newPoint.getBlockZ())<=0)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isInTheCity(Area area) {
		return this.isInTheCity(area.getFirstPoint()) && this.isInTheCity(area.getSecondPoint());
//		for (Location[] locations : this.listCouplePoints) {
//			Location firstPoint = locations[0];
//			Location secondPoint = locations[1];
//			if (
//				((firstPoint.getBlockX()-area.getFirstPoint().getBlockX())*(secondPoint.getBlockX()-area.getFirstPoint().getBlockX())<=0) &&
//				((firstPoint.getBlockY()-area.getFirstPoint().getBlockY())*(secondPoint.getBlockY()-area.getFirstPoint().getBlockY())<=0) &&
//				((firstPoint.getBlockZ()-area.getFirstPoint().getBlockZ())*(secondPoint.getBlockZ()-area.getFirstPoint().getBlockZ())<=0) &&
//				((firstPoint.getBlockX()-area.getSecondPoint().getBlockX())*(secondPoint.getBlockX()-area.getSecondPoint().getBlockX())<=0) &&
//				((firstPoint.getBlockY()-area.getSecondPoint().getBlockY())*(secondPoint.getBlockY()-area.getSecondPoint().getBlockY())<=0) &&
//				((firstPoint.getBlockZ()-area.getSecondPoint().getBlockZ())*(secondPoint.getBlockZ()-area.getSecondPoint().getBlockZ())<=0)
//				) {
//				return true;
//			}
//		}
//		return false;
	}
	
	public int getNbTotalBlocks() {
		int nbTotalBlocks = 0;
		for (Location[] locations : this.listCouplePoints) {
			Location firstPoint = locations[0];
			Location secondPoint = locations[1];
			int lenght = Math.abs(firstPoint.getBlockX()-secondPoint.getBlockX())+1;
			int width = Math.abs(firstPoint.getBlockZ()-secondPoint.getBlockZ())+1;
			nbTotalBlocks += lenght * width;
		}
		return nbTotalBlocks;
	}
	
	public List<UUID> getAllResident() {
		List<UUID> residentsList = new ArrayList<>();
		for (Area area : this.listAreas) {
			if (!residentsList.contains(area.getOwner())) {
				residentsList.add(area.getOwner());
			}
		}
		return residentsList;
	}
	
	public List<UUID> getAllCitizens() {
		List<UUID> citizensList = new ArrayList<>();
		for (Area area : this.listAreas) {
			if (Main.getNationality(area.getOwner()) == this && !citizensList.contains(area.getOwner())) {
				citizensList.add(area.getOwner());
			}
		}
		return citizensList;
	}
	
}
