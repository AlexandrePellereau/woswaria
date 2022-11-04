package fr.firedragonalex.uhc.core.runnable;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.scheduler.BukkitRunnable;

import fr.firedragonalex.uhc.core.Options;
import fr.firedragonalex.uhc.core.langague.Translate;

public class WorldBorderManager extends BukkitRunnable {
	
	private World world;
	private WorldBorder worldBorder;
	
	public WorldBorderManager(World world) {
		this.world = world;
		this.worldBorder = this.world.getWorldBorder();
		this.worldBorder.setCenter(new Location(world, 0, 0, 0));
		this.worldBorder.setSize(Options.STARTING_WORLDBORDER_SIZE);
	}

	@Override
	public void run() {
		double newSize = this.worldBorder.getSize()*(Options.WORLDBORDER_CHANGING_SIZE_POURCENTAGE/100.0);
		this.worldBorder.setSize(newSize, Options.WORLDBORDER_MOVING_DURATION);
		Bukkit.broadcastMessage(ChatColor.GREEN+Translate.WORLDBOREDER_MOVING.getString().replace("{number}", newSize+""));
	}

}
