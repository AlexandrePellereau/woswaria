package fr.firedragonalex.spellandweapon.quests.steps.abstracts;

import java.util.List;

import fr.firedragonalex.spellandweapon.quests.QuestInProgress;

public abstract class AbstractQuestStep {
	
	protected boolean finish;
	protected QuestInProgress questInProgress;
	
	public AbstractQuestStep() {
		this.finish = false;
		this.questInProgress = null;
	}
	
	public abstract AbstractQuestStep clone();
	
	public abstract boolean isFinish();
	
	public AbstractQuestStep startStep(QuestInProgress questInProgress) {
		AbstractQuestStep questStep = this.clone();
		questStep.setQuestInProgess(questInProgress);
		return questStep;
	}
	
	public QuestInProgress getQuestInProgress() {
		return this.questInProgress;
	}
	
	public void setQuestInProgess(QuestInProgress questInProgress) {
		this.questInProgress = questInProgress;
	}
	
	public void finish() {
		this.finish = true;
		this.questInProgress.nextStep();
	}

}
