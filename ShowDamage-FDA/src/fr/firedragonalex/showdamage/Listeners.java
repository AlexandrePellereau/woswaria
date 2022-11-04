package fr.firedragonalex.showdamage;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;

public class Listeners implements Listener {
	
	private Main main;

	public Listeners(Main main) {
		this.main = main;
	}
	
	
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		Entity damager = event.getDamager();
		if (damager instanceof Player) {
			main.addDamage(event.getDamage(), "", event.getEntity().getLocation());
			//Player player = (Player)damager;
		}
		if (damager instanceof Arrow) {
			Arrow arrowDamager = (Arrow)damager;
			ProjectileSource arrowShooter = arrowDamager.getShooter();
			if (arrowShooter instanceof Player) {
				main.addDamage(event.getDamage(), "", event.getEntity().getLocation());
			}
		}
	}
	
	@EventHandler
	public void onDamage(EntityDamageEvent event) {
		DamageCause cause = event.getCause();
		if (cause==DamageCause.FIRE_TICK) {
			main.addDamage(event.getDamage(), "§c",event.getEntity().getLocation());
		}
		if (cause==DamageCause.FIRE) {
			main.addDamage(event.getDamage(), "§c",event.getEntity().getLocation());
		}
		if (cause==DamageCause.LAVA) {
			main.addDamage(event.getDamage(), "§c",event.getEntity().getLocation());
		}
		if (cause==DamageCause.FREEZE) {
			main.addDamage(event.getDamage(), "§b",event.getEntity().getLocation());
		}
		if (cause==DamageCause.SUFFOCATION) {
			main.addDamage(event.getDamage(), "§6",event.getEntity().getLocation());
		}
		if (cause==DamageCause.FALL) {
			main.addDamage(event.getDamage(), "§6",event.getEntity().getLocation());
		}
		if (cause==DamageCause.POISON) {
			main.addDamage(event.getDamage(), "§2",event.getEntity().getLocation());
		}
		if (cause==DamageCause.CONTACT) {
			main.addDamage(event.getDamage(), "§a",event.getEntity().getLocation());
		}
		if (cause==DamageCause.DROWNING) {
			main.addDamage(event.getDamage(), "§1",event.getEntity().getLocation());
		}
		if (cause==DamageCause.LIGHTNING) {
			main.addDamage(event.getDamage(), "§e",event.getEntity().getLocation());
		}
	}
}
