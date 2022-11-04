package fr.firedragonalex.spellandweapon.quests.steps.abstracts;

public abstract class AbstractQuestStepInstant extends AbstractQuestStep {
	
	public abstract void execute();
	
	public boolean isFinish() {
		this.execute();
		return true;
	}

}
