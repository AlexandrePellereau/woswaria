package fr.firedragonalex.spellandweapon.element;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;

public class WaitBeforeLightning extends BukkitRunnable{
	
	private LivingEntity entity;
	private Element element;
	
	public WaitBeforeLightning(LivingEntity entity, Element element) {
		this.entity = entity;
		this.element = element;
	}
	
	public void strikeLighting() {
		this.entity.getWorld().spawnEntity(this.entity.getLocation(), EntityType.LIGHTNING);
		//this.entity.getWorld().strikeLightning(this.entity.getLocation());
		this.element.setPower(0);
		cancel();
	}

	@Override
	public void run() {
		this.strikeLighting();
	}

}
