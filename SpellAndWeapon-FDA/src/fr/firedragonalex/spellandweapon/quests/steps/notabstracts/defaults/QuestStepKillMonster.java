package fr.firedragonalex.spellandweapon.quests.steps.notabstracts.defaults;

import org.bukkit.Material;

import fr.firedragonalex.spellandweapon.custom.easytoadd.CustomMonsterType;
import fr.firedragonalex.spellandweapon.quests.steps.abstracts.AbstractQuestStep;
import fr.firedragonalex.spellandweapon.quests.steps.abstracts.AbstractQuestStepDefault;

public class QuestStepKillMonster extends AbstractQuestStepDefault {
	
	private int nbMonsterToKillAtStart;
	private int nbMonsterToKill;
	private CustomMonsterType typeOfMonsterToKill;
	
	public QuestStepKillMonster(int nbMonsterToKill, CustomMonsterType typeOfMonsterToKill) {
		this.nbMonsterToKillAtStart = nbMonsterToKill;
		this.nbMonsterToKill = nbMonsterToKill;
		this.typeOfMonsterToKill = typeOfMonsterToKill;
	}
	
	public void killMonster() {
		this.nbMonsterToKill--;
		this.checkFinish();
	}
	
	public int getNbMonsterToKill() {
		return this.nbMonsterToKill;
	}
	
	public CustomMonsterType getTypeOfMonsterToKill() {
		return this.typeOfMonsterToKill;
	}

	@Override
	public AbstractQuestStep clone() {
		return new QuestStepKillMonster(this.nbMonsterToKill, this.typeOfMonsterToKill);
	}

	@Override
	public String toPrint() {
		return "Tu dois tuer "+this.nbMonsterToKill+" "+this.typeOfMonsterToKill.getName()+" ("+(this.nbMonsterToKillAtStart-this.nbMonsterToKill)+"/"+this.nbMonsterToKillAtStart+")";
	}

	@Override
	public void checkFinish() {
		if (this.nbMonsterToKill <= 0) {
			this.finish();
		}
	}

	@Override
	public int getAdvencementStep() {
		return this.nbMonsterToKillAtStart - this.nbMonsterToKill;
	}
	
	@Override
	public void setAdvencementStep(int advencementStep) {
		this.nbMonsterToKill = this.nbMonsterToKillAtStart - advencementStep;
	}
}
