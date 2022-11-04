package fr.firedragonalex.spellandweapon.custom.code;

import java.util.Iterator;

import org.bukkit.Bukkit;
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
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.projectiles.ProjectileSource;

import fr.firedragonalex.spellandweapon.Main;
import fr.firedragonalex.spellandweapon.custom.easytoadd.CustomArmor;
import fr.firedragonalex.spellandweapon.custom.easytoadd.CustomWeapon;
import fr.firedragonalex.spellandweapon.element.Element;
import fr.firedragonalex.spellandweapon.element.ElementType;
import fr.firedragonalex.spellandweapon.showdamage.ShowDamage;
import fr.firedragonalex.spellandweapon.woswaria.Woswaria;

public class ListenersCustom implements Listener {
	
	@EventHandler(priority = EventPriority.LOW)
	public void onJoin(PlayerJoinEvent event) {
		CustomPlayer customPlayer = new CustomPlayer(event.getPlayer());
		Main.getAllCustomPlayers().add(customPlayer);
		Main.getAllCustomEntities().add((CustomEntity)customPlayer);
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onQuit(PlayerQuitEvent event) {
		for (CustomPlayer customPlayer : Main.getAllCustomPlayers()) {
			if (customPlayer.getPlayer() == event.getPlayer()) {
				Main.getAllCustomPlayers().remove(customPlayer);
				return;
			}
		}
	}
	
	@EventHandler
	public void onProjetcileLaunch(ProjectileLaunchEvent event) {
		ItemStack item = null;
		Entity entity = event.getEntity();
		
		if (entity instanceof Arrow) {
			ProjectileSource shooter = ((Arrow)entity).getShooter();
			if (shooter instanceof Player) {
				item = ((Player)shooter).getItemInUse();
			}
		}
		else if (entity instanceof ThrowableProjectile) {
			item = ((ThrowableProjectile)entity).getItem();
		}
		
		if (item != null) {
			CustomWeapon customWeapon = CustomWeapon.getCustomWeponByItem(item);
			if (customWeapon != null) {
				entity.setCustomNameVisible(false);
				entity.setCustomName(customWeapon.getName());
			}
		}
	}
	
	@EventHandler
	public void onRegeneration(EntityRegainHealthEvent event) {
		if (event.getEntity() instanceof Player) {
			event.setCancelled(true);
			Player player = (Player)event.getEntity();
			Main.getCustomPlayerByPlayer(player).regeneration();
		}
	}
	
	@EventHandler
	public void onEntityDeath(EntityDeathEvent event) {
		CustomEntity customEntity = Main.getCustomEntityByEntity(event.getEntity());
		if (customEntity != null) {
			event.setDroppedExp(0);
			if (customEntity instanceof CustomMonster) {
				CustomMonster customMonster = (CustomMonster)customEntity;
				event.getDrops().clear();
				event.getDrops().addAll(customMonster.getCustomMonsterType().getLoots());
				if (customMonster.getKiller() instanceof Player) {
					CustomPlayer customPlayer = Main.getCustomPlayerByPlayer((Player)customMonster.getKiller());
					if (customPlayer != null) { 
						customPlayer.addXp(customMonster.getXpDrop());
					}
				}
				Main.getAllCustomEntities().remove(customMonster);
			}
		}
	}
	
	
	@EventHandler
	public void onSlimeSplit(SlimeSplitEvent event) {
		event.setCancelled(true);
	}

	
	@EventHandler
	public void onCloseInventory(InventoryCloseEvent event) {
		if (event.getView().getTitle().equals("§1WoswariaGui_Armor")) {
			Inventory inventory = event.getView().getTopInventory();
			CustomPlayer customPlayer = Main.getCustomPlayerByPlayer((Player)event.getPlayer());
			for (int i = 0; i < 4; i++) {
				if (inventory.getItem(9+1+i*2) != null) {
					if (CustomArmor.getCustomArmorByItem(inventory.getItem(9+1+i*2)) != null) {
						customPlayer.getCustomArmorEquipements()[i] = CustomArmor.getCustomArmorByItem(inventory.getItem(9+1+i*2));
					} else {
						customPlayer.getPlayer().getInventory().addItem(inventory.getItem(9+1+i*2));
					}
				} else {
					customPlayer.getCustomArmorEquipements()[i] = null;
				}
			}
			customPlayer.updateStats();
		}
	}
	
}