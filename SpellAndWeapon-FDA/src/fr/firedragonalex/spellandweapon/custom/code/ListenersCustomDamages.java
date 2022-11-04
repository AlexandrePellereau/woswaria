package fr.firedragonalex.spellandweapon.custom.code;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrowableProjectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.projectiles.ProjectileSource;

import fr.firedragonalex.spellandweapon.Main;
import fr.firedragonalex.spellandweapon.custom.easytoadd.CustomWeapon;
import fr.firedragonalex.spellandweapon.element.Element;
import fr.firedragonalex.spellandweapon.element.ElementType;
import fr.firedragonalex.spellandweapon.showdamage.ShowDamage;
import fr.firedragonalex.spellandweapon.woswaria.Woswaria;

public class ListenersCustomDamages implements Listener {
	
	@EventHandler(priority = EventPriority.LOW)
	public void onDamageColor(EntityDamageEvent event) {
		if (!event.isCancelled() && event.getEntityType() != EntityType.DROPPED_ITEM) {
			if (Woswaria.damageCauseToElementType.containsKey(event.getCause())) {
				ShowDamage.addDamage(event.getDamage(), Woswaria.damageCauseToElementType.get(event.getCause()).getColor(), event.getEntity().getLocation());
			}
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onDamageCustomWeapon(EntityDamageByEntityEvent event) {
		if (event.getEntity().isInvulnerable()) {
			event.setCancelled(true);
			return;
		}
		if (!event.isCancelled()) {
			Entity damager = event.getDamager();
			CustomPlayer customPlayer = null;
			String colorElement = ElementType.PHYSICAL.getColor();
			int damage = 0;
			if (damager instanceof Player) {
				Player player = (Player)event.getDamager();
				ItemStack item = player.getInventory().getItemInMainHand();
				customPlayer = Main.getCustomPlayerByPlayer(player);
				damage = customPlayer.getAttack();
				Element elementApplied = null;
				if (item.hasItemMeta()) {
					for(CustomWeapon customWeapon : CustomWeapon.values()) {
						if (customWeapon.getType() == CustomWeaponType.MELEE && item.getItemMeta().getDisplayName().equals(customWeapon.getName())) {
							damage += customWeapon.getDamage();
							elementApplied = customWeapon.getElementApplied();
							if (customWeapon.getElementDamage() != null) {
								colorElement = customWeapon.getElementDamage().getColor();
							}
						}
					}
				}
				event.setDamage(damage);
				if (event.getEntity() instanceof LivingEntity) {
					CustomEntity customEntity = Main.getCustomEntityByEntity((LivingEntity)event.getEntity());
					if (customEntity != null) {
						customEntity.addElement(elementApplied);
						customEntity.damage((int)damage,player);
						event.setDamage(0);
					}
				}
				ShowDamage.addDamage((double)damage, colorElement, event.getEntity().getLocation());
			}
			else if (damager instanceof Arrow) {
				Arrow arrow = (Arrow)damager;
				ProjectileSource arrowShooter = arrow.getShooter();
				damage = (int)Math.round(event.getDamage());
				if (arrowShooter instanceof Player) {
					customPlayer = Main.getCustomPlayerByPlayer((Player)arrowShooter);
					damage = customPlayer.getAttack();
					Element element = null;
					if (arrow.getCustomName() != null && !arrow.getCustomName().equals("Arrow") && !arrow.getCustomName().equals("")) {
						for(CustomWeapon customWeapon : CustomWeapon.values()) {
							if (customWeapon.getType() == CustomWeaponType.BOW && arrow.getCustomName().equals(customWeapon.getName())) {
								damage += customWeapon.getDamage();
								element = customWeapon.getElementApplied();
								if (customWeapon.getElementDamage() != null) {
									colorElement = customWeapon.getElementDamage().getColor();
								}
							}
						}
					}
					damage *= (int)Math.round(event.getDamage());
					event.setDamage(damage);
					if (event.getEntity() instanceof LivingEntity) {
						CustomEntity customEntity = Main.getCustomEntityByEntity((LivingEntity)event.getEntity());
						if (customEntity != null) {
							customEntity.damage(damage, (Player)arrowShooter);
							if (element != null) {
								customEntity.addElement(element);
							}
							event.setDamage(0);
						}
					}
				}
				ShowDamage.addDamage((double)damage, colorElement, event.getEntity().getLocation());
			}
			else if (damager instanceof ThrowableProjectile) {
				ThrowableProjectile projectile = (ThrowableProjectile)damager;
				if (!projectile.getItem().hasItemMeta()) return;
				for(CustomWeapon customWeapon : CustomWeapon.values()) {
					if (customWeapon.getType() == CustomWeaponType.PROJECTILE && projectile.getItem().getItemMeta().getDisplayName().equals(customWeapon.getName())) {
						damage = customWeapon.getDamage();
						if (customWeapon.getElementDamage() != null) {
							colorElement = customWeapon.getElementDamage().getColor();
						}
						event.setDamage(damage);
						if (event.getEntity() instanceof LivingEntity) {
							CustomEntity customEntity = Main.getCustomEntityByEntity((LivingEntity)event.getEntity());
							if (customEntity != null) {
								customEntity.addElement(customWeapon.getElementApplied());
								customEntity.damage((int)customWeapon.getDamage(),(LivingEntity)projectile.getShooter());
								event.setDamage(0);
							}
						}
						ShowDamage.addDamage((double)damage, colorElement, event.getEntity().getLocation());
					}
				}
			}
			if (customPlayer != null) {
				if (customPlayer.hasElement(ElementType.DARK)) {
					customPlayer.heal((int)Math.round(damage));
				}
				if (customPlayer.hasElement(ElementType.VEGETAL)) {
					int damageFinal = (int)Math.round(((customPlayer.getElement(ElementType.VEGETAL).getPower()*1.0)/200)*damage);
					customPlayer.damage(damageFinal);
					ShowDamage.addDamage((double)damageFinal, "§2", customPlayer.getPlayer().getLocation());
				}
			}
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onDamageByCustomMob(EntityDamageByEntityEvent event) {
		if (event.getEntity() instanceof Player) {
			CustomPlayer customPlayer = Main.getCustomPlayerByPlayer((Player)event.getEntity());
			if (customPlayer != null) {
				if (event.getEntity() instanceof LivingEntity && event.getDamager() instanceof LivingEntity) {
					CustomEntity customEntity = Main.getCustomEntityByEntity((LivingEntity)event.getDamager());
					if (customEntity != null && customEntity instanceof CustomMonster) {
						CustomMonster customMonster = (CustomMonster)customEntity;
						customPlayer.damage(customMonster.getAttack(),customMonster.getEntity());
						event.setDamage(0);
						customPlayer.addElement(customMonster.getCustomMonsterType().getElementApplied());
						return;    
					}
				}
				customPlayer.damage((int)Math.round(event.getDamage()));
				event.setDamage(0);
			}
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onDamage(EntityDamageEvent event) {
		if (!event.isCancelled() && event.getCause() != DamageCause.ENTITY_ATTACK) {
			if (event.getEntity() instanceof Player) {
				CustomPlayer customPlayer = Main.getCustomPlayerByPlayer((Player)event.getEntity());
				if (customPlayer != null) {
					customPlayer.damage((int)Math.round(event.getDamage()));
					event.setDamage(0);
				}
			}
		}
	}
}
