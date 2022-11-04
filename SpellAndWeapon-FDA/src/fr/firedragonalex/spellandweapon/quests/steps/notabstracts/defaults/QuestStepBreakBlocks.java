package fr.firedragonalex.spellandweapon.quests.steps.notabstracts.defaults;

import org.bukkit.Material;

import fr.firedragonalex.spellandweapon.quests.steps.abstracts.AbstractQuestStep;
import fr.firedragonalex.spellandweapon.quests.steps.abstracts.AbstractQuestStepDefault;

public class QuestStepBreakBlocks extends AbstractQuestStepDefault {
	
	private int nbBlocksToBreakAtStart;
	private int nbBlocksToBreak;
	private Material typeOfBlocksToBreak;
	
	public QuestStepBreakBlocks(int nbBlocksToBreak, Material typeOfBlocksToBreak) {
		this.nbBlocksToBreak = nbBlocksToBreak;
		this.nbBlocksToBreakAtStart = nbBlocksToBreak;
		this.typeOfBlocksToBreak = typeOfBlocksToBreak;
	}
	
	public void blockBreak() {
		this.nbBlocksToBreak--;
		this.checkFinish();
	}
	
	public int getNbBlocksToBreak() {
		return this.nbBlocksToBreak;
	}
	
	public Material getTypeOfBlocksToBreak() {
		return this.typeOfBlocksToBreak;
	}

	@Override
	public AbstractQuestStep clone() {
		return new QuestStepBreakBlocks(this.nbBlocksToBreak, this.typeOfBlocksToBreak);
	}
	
	@Override
	public String toPrint() {
		return "Tu dois récupérer "+this.nbBlocksToBreak+" blocks de "+this.typeOfBlocksToBreak.name()+" ("+(this.nbBlocksToBreakAtStart-this.nbBlocksToBreak)+"/"+this.nbBlocksToBreakAtStart+")";
	}

	@Override
	public void checkFinish() {
		if (this.nbBlocksToBreak <= 0) {
			this.finish();
		}
	}

	@Override
	public int getAdvencementStep() {
		return this.nbBlocksToBreakAtStart - this.nbBlocksToBreak;
	}
	
	@Override
	public void setAdvencementStep(int advencementStep) {
		this.nbBlocksToBreak = this.nbBlocksToBreakAtStart - advencementStep;
	}

}
