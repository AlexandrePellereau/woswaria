package fr.firedragonalex.uhc.core.runnable;

import java.util.Arrays;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class LauchFireworks extends BukkitRunnable {
	
	private static LauchFireworks instance;
	private Player player;
	private int timer;
	
	public LauchFireworks(int timer, Player player) {
		LauchFireworks.instance = this;
		this.player = player;
		this.timer = timer;
	}
	
	public static void stop() {
		LauchFireworks.instance.cancel();
		LauchFireworks.instance = null;
	}

	@Override
	public void run() {
		if (timer == 0) {
			LauchFireworks.stop();
			return;
		}
		
		Color color = Arrays.asList(Color. YELLOW, Color.ORANGE, Color.RED, Color.GREEN, Color.LIME).get((int)Math.round(Math.random()*4));
		
		Firework firework = (Firework)player.getWorld().spawnEntity(player.getLocation(), EntityType.FIREWORK);
		FireworkMeta meta = firework.getFireworkMeta();
		meta.addEffect(FireworkEffect.builder().withColor(color).build());
//        FireworkEffect effect = FireworkEffect.builder().withColor(color).build();
//        metaFw.setEffect(effect);
		firework.setFireworkMeta(meta);
		
		timer--;
	}
}
