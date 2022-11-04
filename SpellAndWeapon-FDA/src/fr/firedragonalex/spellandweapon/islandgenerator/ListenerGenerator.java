package fr.firedragonalex.spellandweapon.islandgenerator;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;

import fr.firedragonalex.spellandweapon.Main;
import fr.firedragonalex.spellandweapon.alexlibrairy.UsefulFunctions;

public class ListenerGenerator implements Listener {
	
	@EventHandler
	public void onProjectileHit(ProjectileHitEvent event) {
		if (event.getHitBlock() != null && event.getEntityType() == EntityType.SNOWBALL) {
			Snowball snowballProjectile = (Snowball)event.getEntity();
			ItemStack itemStackSnowball = snowballProjectile.getItem();
			if (itemStackSnowball.hasItemMeta() && itemStackSnowball.getItemMeta().getDisplayName().equals(ChatColor.LIGHT_PURPLE+"IslandGenerator")) {
				Block block = event.getHitBlock();
				HashMap<String, String> lore = UsefulFunctions.loreToHashMap(itemStackSnowball);
				int size = Integer.valueOf(lore.get("Size"));
				int height = Integer.valueOf(lore.get("Height"));
				int frequency = Integer.valueOf(lore.get("Frequency"));
				int gap = Math.round(size/2);
				Location locationSpawnIsland = block.getLocation().clone();
				locationSpawnIsland.setX(locationSpawnIsland.getBlockX()-gap);
				locationSpawnIsland.setZ(locationSpawnIsland.getBlockZ()-gap);
				IslandGenereator spawnIsland = new IslandGenereator((Player)event.getEntity().getShooter(), locationSpawnIsland, size, height);
				spawnIsland.runTaskTimer(Main.getInstance(), 0, 20);
				try {
					Process process = Runtime.getRuntime().exec("py plugins/SpellAndWeapon-FDA/PerlinNoiseGenerator.py "+size+" "+frequency);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}

}
