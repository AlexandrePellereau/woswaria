package fr.dralexgon.shopasvillagerforplayers;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;

import java.util.ArrayList;
import java.util.List;

public class VillagerShop {

	private UUID owner;
	private Villager villager;
	private String name;
	private Inventory inventoryThingsToSell;
	private Inventory inventoryThingsObtained;
	private long lastTimeUse;
	private List<Integer> listLastMaxeUses;
	private boolean hasInfiniteTrade;
	private boolean dead;
	
	public VillagerShop(UUID owner, Villager villager, boolean hasInfiniteTrade) {
		this.owner = owner;
		this.villager = villager;
		this.villager.setAI(false);
		this.villager.setGravity(true);
		this.villager.setSilent(true);
		//this.villager.setInvulnerable(true);
		this.name = "§eMarchant de "+Bukkit.getPlayer(owner).getName();
		this.getVillager().setCustomName("§eMarchant de "+Bukkit.getPlayer(owner).getName());
		this.getVillager().setCustomNameVisible(true);
		this.lastTimeUse = System.currentTimeMillis();
		this.listLastMaxeUses = new ArrayList<Integer>();
		this.inventoryThingsToSell = Bukkit.createInventory(null, 3*9, "§1InventoryThingsToSell");
		this.inventoryThingsObtained = Bukkit.createInventory(null, 3*9, "§1InventoryThingsObtained");
		this.hasInfiniteTrade = hasInfiniteTrade;
		this.dead = false;
	}

	public VillagerShop(UUID owner, String name, Villager villager, boolean hasInfiniteTrade) {
		/*Only used for the save*/
		this.owner = owner;
		this.villager = villager;
		this.villager.setAI(false);
		this.villager.setGravity(true);
		this.villager.setSilent(true);
		this.name = name;
		this.getVillager().setCustomName(name);
		this.getVillager().setCustomNameVisible(true);
		this.lastTimeUse = System.currentTimeMillis();
		this.listLastMaxeUses = new ArrayList<Integer>();
		this.inventoryThingsToSell = Bukkit.createInventory(null, 3*9, "§1InventoryThingsToSell");
		this.inventoryThingsObtained = Bukkit.createInventory(null, 3*9, "§1InventoryThingsObtained");
		this.hasInfiniteTrade = hasInfiniteTrade;
		this.dead = false;
	}
	
	public boolean isDead() {
		return this.dead;
	}
	
	public void death() {
		this.dead = true;
		this.getVillager().setHealth(0);
	}
	
	public UUID getOwner() {
		return this.owner;
	}
	
	public Villager getVillager() {
		return this.villager;
	}
	
	public Inventory getInventoryThingsToSell() {
		return this.inventoryThingsToSell;
	}
	
	public Inventory getInventoryThingsObtained() {
		return this.inventoryThingsObtained;
	}
	
	public String getName() {
		return this.name;
	}
	
	public List<Integer> getListLastMaxeUses() {
		return this.listLastMaxeUses;
	}
	
	public boolean hasInfiniteTrade() {
		return this.hasInfiniteTrade;
	}
	
	public void setListLastMaxeUses(List<Integer> listLastMaxeUses) {
		this.listLastMaxeUses = listLastMaxeUses;
	}
	
	public void setInventoryThingsToSell(Inventory inventory) {
		this.inventoryThingsToSell = inventory;
	}
	
	public void setInventoryThingsObtained(Inventory inventory) {
		this.inventoryThingsObtained = inventory;
	}
	
	public void setVillager(Villager villager) {
		this.villager = villager;
	}
	
	public void setName(String name) {
		this.name = name;
		this.getVillager().setCustomName(name);
		this.getVillager().setCustomNameVisible(true);
	}
	
	public void updateMaxUses() {
		this.updateInventories();
		this.setListLastMaxeUses(new ArrayList<>());
		for (MerchantRecipe trade : this.getVillager().getRecipes()) {
			if (hasInfiniteTrade) {
				trade.setMaxUses(64*4*9);
			} else {
				int nbItem = 0;
				for (ItemStack  itemStack : this.getInventoryThingsToSell().getContents()) {
					if (itemStack!=null) {
						if (itemStack.getType()==trade.getResult().getType()) {
							nbItem+=itemStack.getAmount();
						}
					}
				}
				this.getListLastMaxeUses().add(Math.floorDiv(nbItem, trade.getResult().getAmount()));
				trade.setMaxUses(Math.floorDiv(nbItem, trade.getResult().getAmount()));
				trade.setUses(0);
			}
		}
	}
	
	public void updateInventories() {
		int i = 0;
		for (MerchantRecipe trade : this.getVillager().getRecipes()) {
			try {
				int nbTrade = trade.getUses();
				for (int j = 0; j < nbTrade; j++) {
					Main.getInstance().removeInventory(this.getInventoryThingsToSell(), trade.getResult().getType(), trade.getResult().getAmount());
					this.getInventoryThingsObtained().addItem(trade.getIngredients().get(0));
					try {
						this.getInventoryThingsObtained().addItem(trade.getIngredients().get(1));
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			i++;
		}
	}

}
