package fr.firedragonalex.spellandweapon.quests.steps.notabstracts.instants;

import org.bukkit.ChatColor;
import org.bukkit.Location;

import fr.firedragonalex.spellandweapon.custom.code.CustomMonster;
import fr.firedragonalex.spellandweapon.custom.easytoadd.CustomMonsterType;
import fr.firedragonalex.spellandweapon.quests.steps.abstracts.AbstractQuestStep;
import fr.firedragonalex.spellandweapon.quests.steps.abstracts.AbstractQuestStepInstant;

public class QuestStepSpawnMonster extends AbstractQuestStepInstant {
	
	private int nbMonster;
	private int level;
	private CustomMonsterType customMonsterType;
	private Location location;
	
	public QuestStepSpawnMonster(int nbMonster, int level, CustomMonsterType customMonsterType, Location location) {
		this.nbMonster = nbMonster;
		this.level = level;
		this.customMonsterType = customMonsterType;
		this.location = location;
	}
	
	@Override
	public AbstractQuestStep clone() {
		return new QuestStepSpawnMonster(this.nbMonster, this.level, this.customMonsterType, this.location);
	}

	@Override
	public void execute() {
		for (int i = 0; i < this.nbMonster; i++) {
			new CustomMonster(this.customMonsterType, this.level, this.location);
		}
	}

}
