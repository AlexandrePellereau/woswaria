package fr.firedragonalex.areaplugin.area;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Particle.DustOptions;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fr.firedragonalex.areaplugin.MainAreaPlugin;
import fr.firedragonalex.areaplugin.ParticleSpawner;

public class Area {
	
	private MainAreaPlugin mainAreaPlugin;
	private UUID areaUUID;
	private Location firstPoint;
	private Location secondPoint;
	private UUID owner;
	private List<UUID> mates;
	private String name;
	private AreaType areaType;
	private boolean matesCanBreakAndPlaceBlocks;
	private boolean matesCanOpenChests;
	private boolean matesCanOpenDoors;
	private boolean matesCanUseRedstone;
	private boolean everyoneCanOpenChests;
	private boolean everyoneCanOpenDoors;
	private boolean everyoneCanUseRedstone;
	private boolean isInvulnerableToExplosion;
	
	public Area(Location firstPoint, Location secondPoint, UUID owner, List<UUID> mates, String name, UUID areaUUID, MainAreaPlugin mainAreaPlugin) {
		//System.out.println("[AreaPlugin-FDA] Create new area...");
		if (areaUUID == null) {
			this.areaUUID = UUID.randomUUID();
		} else {
			this.areaUUID = areaUUID;
		}
		this.areaType = AreaType.NORMAL;
		this.name = name;
		this.firstPoint = firstPoint;
		this.secondPoint = secondPoint;
		this.owner = owner;
		if (mates == null) {
			this.mates = new ArrayList<UUID>();
		} else {
			this.mates = mates;
		}
		this.mainAreaPlugin = mainAreaPlugin;
		this.matesCanBreakAndPlaceBlocks = mainAreaPlugin.getConfig().getBoolean("default_settings.mates_can.place_and_break_blocs");
		this.matesCanOpenChests = mainAreaPlugin.getConfig().getBoolean("default_settings.mates_can.open_chest");
		this.matesCanOpenDoors = mainAreaPlugin.getConfig().getBoolean("default_settings.mates_can.open_doors");
		this.matesCanUseRedstone = mainAreaPlugin.getConfig().getBoolean("default_settings.mates_can.use_redstone");
		this.everyoneCanOpenChests = mainAreaPlugin.getConfig().getBoolean("default_settings.everyone_can.open_chest");
		this.everyoneCanOpenDoors = mainAreaPlugin.getConfig().getBoolean("default_settings.everyone_can.open_doors");
		this.everyoneCanUseRedstone = mainAreaPlugin.getConfig().getBoolean("default_settings.everyone_can.use_redstone");
		this.isInvulnerableToExplosion = mainAreaPlugin.getConfig().getBoolean("default_settings.is_invulnerable_to_explosion");
		//System.out.println("[AreaPlugin-FDA] New area created.");
	}
	
	public Area() {}
	
	public UUID getUUID() {
		return this.areaUUID;
	}
	
	public Location getFirstPoint() {
		//System.out.println("first point given");
		return this.firstPoint;
	}
	
	public Location getSecondPoint() {
		//System.out.println("second point given");
		return this.secondPoint;
	}
	
	public UUID getOwner() {
		return this.owner;
	}
	
	public void setOwner(UUID newOwnerUUID) {
		this.owner = newOwnerUUID;
	}
	
	public List<UUID> getMates() {
		return this.mates;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public AreaType getType() {
		return this.areaType;
	}
	
	public void setType(AreaType areaType) {
		this.areaType = areaType;
	}
	
	public boolean getMatesCanBreakAndPlaceBlocks() {
		return this.matesCanBreakAndPlaceBlocks;
	}
	
	public boolean getMatesCanOpenChests() {
		return this.matesCanOpenChests;
	}
	
	public boolean getMatesCanOpenDoors() {
		return this.matesCanOpenDoors;
	}
	
	public boolean getMatesCanUseRedstone() {
		return this.matesCanUseRedstone;
	}
	
	public boolean getEveryoneCanOpenChests() {
		return this.everyoneCanOpenChests;
	}
	
	public boolean getEveryoneCanOpenDoors() {
		return this.everyoneCanOpenDoors;
	}
	
	public boolean getEveryoneCanUseRedstone() {
		return this.everyoneCanUseRedstone;
	}
	
	public boolean isInvulnerableToExplosion() {
		return this.isInvulnerableToExplosion;
	}
	
	public void setMatesCanBreakAndPlaceBlocks(boolean input) {
		this.matesCanBreakAndPlaceBlocks = input;
	}
	
	public void setMatesCanOpenChests(boolean input) {
		this.matesCanOpenChests = input;
	}
	
	public void setMatesCanOpenDoors(boolean input) {
		this.matesCanOpenDoors = input;
	}
	
	public void setMatesCanUseRedstone(boolean input) {
		this.matesCanUseRedstone = input;
	}
	
	public void setEveryoneCanOpenChests(boolean input) {
		this.everyoneCanOpenChests = input;
	}
	
	public void setEveryoneCanOpenDoors(boolean input) {
		this.everyoneCanOpenDoors = input;
	}
	
	public void setEveryoneCanUseRedstone(boolean input) {
		this.everyoneCanUseRedstone = input;
	}
	
	public void setIsInvulnerableToExplosion(boolean input) {
		this.isInvulnerableToExplosion = input;
	}
	
	public String getSurfaceString() {
		int lenght = Math.abs(this.getFirstPoint().getBlockX()-this.getSecondPoint().getBlockX())+1;
		int width = Math.abs(this.getFirstPoint().getBlockZ()-this.getSecondPoint().getBlockZ())+1;
		return lenght+"x"+width+" ("+lenght*width+" blocs)";
	}
	
	public ItemStack getItemSellArea() {
		String typeOfWorld = this.getFirstPoint().getWorld().getEnvironment().toString();
		typeOfWorld = typeOfWorld.substring(0,1).toUpperCase() + typeOfWorld.substring(1).toLowerCase();
		List<String> lore = Arrays.asList(  "AreaUUID : "+this.areaUUID,
											"Nom : "+this.getName(),
											"Taille : "+(Math.abs(this.getFirstPoint().getBlockX()-this.getSecondPoint().getBlockX())+1)+"x"+(Math.abs(this.getFirstPoint().getBlockZ()-this.getSecondPoint().getBlockZ())+1),
											"Coordonnées : "+this.getFirstPoint().getBlockX() + "," + this.getFirstPoint().getBlockZ()+";"+this.getSecondPoint().getBlockX() + "," + this.getSecondPoint().getBlockZ(),
											"Monde : "+this.getFirstPoint().getWorld().getName()+" ("+typeOfWorld+")",
											"Date limite d'utilisation : "+new Timestamp(Math.round(System.currentTimeMillis()+mainAreaPlugin.getConfig().getInt("expiration_date_paper_sell_area"))),
											"",
											"Un fois la date limite d'utilisation écoulée, la zone deviendra inconquise.");
		return mainAreaPlugin.getGui().customItem(Material.PAPER, ChatColor.YELLOW+"Certificat de propriété de la zone \""+this.getName()+"\"", lore);
	}
	
	public void showArea(Player player) {
		ParticleSpawner particleSpawner = new ParticleSpawner(player,this);
		particleSpawner.runTaskTimer(this.mainAreaPlugin, 0, 20);
	}
	
	public boolean isDistinct(Area area) {
		return ((
				Math.max(this.getFirstPoint().getBlockX(), this.getSecondPoint().getBlockX()) < Math.min(area.getFirstPoint().getBlockX(), area.getSecondPoint().getBlockX()) ||
				Math.min(this.getFirstPoint().getBlockX(), this.getSecondPoint().getBlockX()) > Math.max(area.getFirstPoint().getBlockX(), area.getSecondPoint().getBlockX())) || (
				Math.max(this.getFirstPoint().getBlockY(), this.getSecondPoint().getBlockY()) < Math.min(area.getFirstPoint().getBlockY(), area.getSecondPoint().getBlockY()) ||
				Math.min(this.getFirstPoint().getBlockY(), this.getSecondPoint().getBlockY()) > Math.max(area.getFirstPoint().getBlockY(), area.getSecondPoint().getBlockY())) || (
				Math.max(this.getFirstPoint().getBlockZ(), this.getSecondPoint().getBlockZ()) < Math.min(area.getFirstPoint().getBlockZ(), area.getSecondPoint().getBlockZ()) ||
				Math.min(this.getFirstPoint().getBlockZ(), this.getSecondPoint().getBlockZ()) > Math.max(area.getFirstPoint().getBlockZ(), area.getSecondPoint().getBlockZ())));
	}
	
	public boolean isInTheArea(Location newPoint) {
		return  ((firstPoint.getBlockX()-newPoint.getBlockX())*(secondPoint.getBlockX()-newPoint.getBlockX())<=0) &&
				((firstPoint.getBlockY()-newPoint.getBlockY())*(secondPoint.getBlockY()-newPoint.getBlockY())<=0) &&
				((firstPoint.getBlockZ()-newPoint.getBlockZ())*(secondPoint.getBlockZ()-newPoint.getBlockZ())<=0);
	}
}
