package fr.firedragonalex.areaplugin;

import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.Particle.DustOptions;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.firedragonalex.areaplugin.area.Area;

public class ParticleSpawner extends BukkitRunnable{
	
	private Player player;
	private Area area;
	private int timer;
	private double yPlayer;
	private double x1;
	private double x2;
	private double z1;
	private double z2;
	private DustOptions dustOptions;
	
	public ParticleSpawner(Player player,Area area) {
		this.player = player;
		this.area = area;
		this.yPlayer = player.getLocation().getY()+1;
		this.x1 = Math.min(area.getFirstPoint().getX(), area.getSecondPoint().getX());
		this.x2 = Math.max(area.getFirstPoint().getX(), area.getSecondPoint().getX());
		this.z1 = Math.min(area.getFirstPoint().getZ(), area.getSecondPoint().getZ());
		this.z2 = Math.max(area.getFirstPoint().getZ(), area.getSecondPoint().getZ());
		this.dustOptions = new DustOptions(Color.fromRGB(0, 127, 255), 4.0F);
		timer = 5;
	}
	
	@Override
	public void run() {
		for (double i = x1;i <= x2;i++) {
			player.spawnParticle(Particle.REDSTONE, i, yPlayer, area.getFirstPoint().getZ(), 1, dustOptions);
			player.spawnParticle(Particle.REDSTONE, i, yPlayer, area.getSecondPoint().getZ(), 1, dustOptions);
		}
		for (double i = z1;i <= z2;i++) {
			player.spawnParticle(Particle.REDSTONE, area.getFirstPoint().getX(), yPlayer, i, 1, dustOptions);
			player.spawnParticle(Particle.REDSTONE, area.getSecondPoint().getX(), yPlayer, i, 1, dustOptions);
		}
		if (timer == 0) {
			cancel();
		}
		timer--;
	}

}
