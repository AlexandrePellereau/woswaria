package fr.firedragonalex.areaplugin.farmarea;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;

import fr.firedragonalex.areaplugin.MainAreaPlugin;
import fr.firedragonalex.areaplugin.area.Area;

public class FarmArea extends Area{
	
	private List<MinableBlock> farmableBlocks;
	
	public FarmArea(Area area, List<MinableBlock> farmableBlocks) {
		super(area.getFirstPoint(), area.getSecondPoint(), area.getOwner(), area.getMates(), area.getName(), area.getUUID(), MainAreaPlugin.getThis());
		this.farmableBlocks = farmableBlocks;
	}
	
	public void minableBlockActivate(Block block) {
		MultipleMinableBlock multipleMinableBlock = new MultipleMinableBlock(block,this.getMinableBlock(block.getType()).getTempBlock());
		multipleMinableBlock.runTaskTimer(MainAreaPlugin.getThis(), 1, this.getMinableBlock(block.getType()).getTimeInTicks());
	}
	
	public List<MinableBlock> getBlocksFarmable() {
		return farmableBlocks;
	}
	
	public boolean isThisBlockFarmable(Block block) {
		Material blocktype = block.getType();
		return this.isThisTypeOfBlockFarmable(blocktype);
	}
	
	public boolean isThisTypeOfBlockFarmable(Material blocktype) {
		for (MinableBlock minableBlock : this.farmableBlocks) {
			if (minableBlock.getFarmableBlock() == blocktype) {
				return true;
			}
		}
		return false;
	}
	
	public MinableBlock getMinableBlock(Material blocktype) {
		for (MinableBlock minableBlock : this.farmableBlocks) {
			if (minableBlock.getFarmableBlock() == blocktype) {
				return minableBlock;
			}
		}
		return null;
	}

}
