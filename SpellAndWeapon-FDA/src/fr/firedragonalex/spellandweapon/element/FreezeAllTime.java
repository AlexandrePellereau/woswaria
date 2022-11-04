package fr.firedragonalex.spellandweapon.element;

import org.bukkit.Bukkit;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.scheduler.BukkitRunnable;

import fr.firedragonalex.spellandweapon.Main;
import fr.firedragonalex.spellandweapon.showdamage.ShowDamage;
import net.minecraft.world.entity.ai.behavior.TryFindWater;


public class FreezeAllTime extends BukkitRunnable{
	
	//private Player player;
	private int timer;
	private double damagePerSnowTick;
	private Entity target;
	private int timeBetweenSnowTick;

	public FreezeAllTime(int time, Entity target, double damagePerSnowTick) {
		//this.player = player;
		this.timer = time;
		this.target = target;
		this.damagePerSnowTick = damagePerSnowTick;
		this.timeBetweenSnowTick = 20;
	}

	@Override
	public void run() {
		target.setFreezeTicks(140);
		if (timer % timeBetweenSnowTick == 0) {
			Damageable targetDamageable = (Damageable)target;
			targetDamageable.damage(damagePerSnowTick);
			ShowDamage.addDamage(damagePerSnowTick, "§b", target.getLocation());
		}
		if (timer == 0) {
			cancel();
		}
		timer--;
	}
}
