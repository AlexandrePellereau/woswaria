package fr.firedragonalex.spellandweapon.quests.steps.abstracts;

public abstract class AbstractQuestStepDefault extends AbstractQuestStep {
	
	public abstract void checkFinish();
	
	public abstract int getAdvencementStep();
	
	public abstract void setAdvencementStep(int advencementStep);
	
	public abstract String toPrint();
	
	public boolean isFinish() {
		this.checkFinish();
		return this.finish;
	}
}
