package fr.firedragonalex.spellandweapon.gui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.ThrowableProjectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.entity.SlimeSplitEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.projectiles.ProjectileSource;

import fr.firedragonalex.spellandweapon.Main;
import fr.firedragonalex.spellandweapon.alexlibrairy.UsefulFunctions;
import fr.firedragonalex.spellandweapon.custom.code.CustomPlayer;
import fr.firedragonalex.spellandweapon.custom.easytoadd.CustomArmor;
import fr.firedragonalex.spellandweapon.element.Element;
import fr.firedragonalex.spellandweapon.element.ElementType;

public class ListenersGui implements Listener {
	
	@EventHandler
	public void onClick(InventoryClickEvent event) {
		String inventoryTitle = event.getView().getTitle();
		Player player = (Player)event.getWhoClicked();
		if (inventoryTitle.equals("§1WoswariaGui_Armor")) {
			event.setCancelled(true);
			int slot = event.getSlot();
			Inventory topInventory = event.getView().getTopInventory();
			Inventory bottomInventory = event.getView().getBottomInventory();
			boolean isTopInventory = event.getClickedInventory().equals(topInventory);
			ItemStack item;
			if (isTopInventory) {
				item = topInventory.getItem(slot);
			} else {
				item = bottomInventory.getItem(slot);
			}
			if (CustomArmor.isAnArmor(item)) {
				if (isTopInventory) {
					topInventory.remove(item);
					bottomInventory.addItem(item);
				} else {
					int indexToBeforeEquipArmor = 10+CustomArmor.getEquipNumber(item.getType())*2;
					ItemStack beforeEquipArmor = topInventory.getItem(indexToBeforeEquipArmor);
					if (beforeEquipArmor != null) {
						UsefulFunctions.giveOrDrop(beforeEquipArmor, player);
					}
					topInventory.setItem(indexToBeforeEquipArmor, item);
					bottomInventory.remove(item);
				}
			}
		}
	}
	
}