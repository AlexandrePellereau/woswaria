package fr.firedragonalex.spellandweapon.islandgenerator;

import java.awt.image.BufferedImage;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;

import fr.firedragonalex.spellandweapon.Main;

public class IslandCreator extends BukkitRunnable {
	
	private BufferedImage image;
	private IslandGenereator genereator;
	private int i;
	private int j;
	private int minValue;
	private int maxValue;
	private int[][] pixels;
	
	public IslandCreator(BufferedImage image, IslandGenereator genereator) {
		this.image = image;
		this.genereator = genereator;
		this.i = 0;
		this.j = 0;
		
		this.pixels = IslandGenereator.convertTo2DWithoutUsingGetRGB(image);
		
		this.minValue = pixels[0][0];
		this.maxValue = 0;
		
		for (int i = 0; i < pixels.length; i++) {
			for (int j = 0; j < pixels[0].length; j++) {
				int onePixel = pixels[i][j];
				if (onePixel < minValue) {
					minValue = onePixel;
				}
				if (onePixel > maxValue) {
					maxValue = onePixel;
				}
			}
		}
		this.runTaskTimer(Main.getInstance(), 0, 1);
	}
	
	@Override
	public void run() {
		int onePixel = pixels[i][j];
		double newValue = onePixel + (0 - minValue);
		newValue = newValue*(1/(double)(maxValue-minValue));
		
		Location location = genereator.getLocation();
		Block block = location.getWorld().getBlockAt(location.getBlockX()+i, (int)Math.round(newValue*genereator.getHeight())+location.getBlockY(), location.getBlockZ()+j);
		block.setType(Material.DIRT);
		
		int size = genereator.getSize();
		i++;
		if (i >= size) {
			i = 0;
			j++;
		}
		if (j >= size) {
			j = 0;
		}
		if (i == 0 && j == 0) {
			this.cancel();
		}
	}

}
