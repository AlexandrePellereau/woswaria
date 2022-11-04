package fr.firedragonalex.spellandweapon.quests;

import org.bukkit.Bukkit;

import fr.firedragonalex.spellandweapon.custom.code.CustomPlayer;
import fr.firedragonalex.spellandweapon.quests.steps.abstracts.AbstractQuestStep;
import fr.firedragonalex.spellandweapon.quests.steps.abstracts.AbstractQuestStepInstant;

public class QuestInProgress {
	
	private Quest quest;
	private int advancement;
	private AbstractQuestStep step;
	private CustomPlayer customPlayer;
	
	public QuestInProgress(Quest quest, CustomPlayer customPlayer) {
		this.quest = quest;
		this.advancement = 0;
		this.customPlayer = customPlayer;
		this.setStep(0);
	}
	
	public int getAdvancement() {
		return this.advancement;
	}
	
	public CustomPlayer getCustomPlayer() {
		return this.customPlayer;
	}
	
	public void setStep(int nbStep) {
		this.step = this.quest.getStep(nbStep).startStep(this);
		if (this.step instanceof AbstractQuestStepInstant) {
			((AbstractQuestStepInstant)this.step).execute();
			this.nextStep();
		}
	}
	
	public Quest getQuest() {
		return this.quest;
	}
	
	public AbstractQuestStep getStep() {
		return this.step;
	}
	
	public void nextStep() {
		this.advancement++;
		if (this.advancement < this.quest.getAllSteps().size()) {
			this.setStep(this.advancement);
		} else {
			this.customPlayer.finishQuest(this.quest);
		}
	}

}
