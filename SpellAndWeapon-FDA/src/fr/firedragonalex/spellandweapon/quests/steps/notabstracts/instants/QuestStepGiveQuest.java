package fr.firedragonalex.spellandweapon.quests.steps.notabstracts.instants;

import org.bukkit.ChatColor;
import org.bukkit.Location;

import fr.firedragonalex.spellandweapon.custom.code.CustomMonster;
import fr.firedragonalex.spellandweapon.custom.easytoadd.CustomMonsterType;
import fr.firedragonalex.spellandweapon.quests.Quest;
import fr.firedragonalex.spellandweapon.quests.steps.abstracts.AbstractQuestStep;
import fr.firedragonalex.spellandweapon.quests.steps.abstracts.AbstractQuestStepInstant;

public class QuestStepGiveQuest extends AbstractQuestStepInstant {
	
	private Quest quest;
	
	public QuestStepGiveQuest(Quest quest) {
		this.quest = quest;
	}
	
	@Override
	public AbstractQuestStep clone() {
		return new QuestStepGiveQuest(quest);
	}

	@Override
	public void execute() {
		this.getQuestInProgress().getCustomPlayer().addQuest(quest);
	}

}
