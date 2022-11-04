package fr.firedragonalex.areaplugin.farmarea;

import org.bukkit.Material;

public class MinableBlock {
	
	private Material farmableBlock;
	private Material tempBlock;
	private int timeInTicks;
	
	public MinableBlock(Material farmableBlock, Material tempBlock, int timeInTicks) {
		this.farmableBlock = farmableBlock;
		this.tempBlock = tempBlock;
		this.timeInTicks = timeInTicks;
	}
	
	public Material getFarmableBlock() {
		return farmableBlock;
	}
	
	public Material getTempBlock() {
		return tempBlock;
	}
	
	public int getTimeInTicks() {
		return timeInTicks;
	}

}
