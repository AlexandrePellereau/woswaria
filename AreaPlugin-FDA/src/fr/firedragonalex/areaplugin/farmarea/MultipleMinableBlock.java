package fr.firedragonalex.areaplugin.farmarea;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;

public class MultipleMinableBlock extends BukkitRunnable{
	
	private Block block;
	private Material tempBlockType;
	private Material type;
	private int step;
	
	public MultipleMinableBlock(Block block,Material tempBlockType) {
		this.block = block;
		this.tempBlockType = tempBlockType;
		this.type = block.getType();
		this.step = 0;
	}
	
	@Override
	public void run() {
		if (this.step == 0) {
			this.block.setType(tempBlockType);
			this.step = 1;
		} else {
			this.block.setType(type);
			this.cancel();
		}
	}

}
