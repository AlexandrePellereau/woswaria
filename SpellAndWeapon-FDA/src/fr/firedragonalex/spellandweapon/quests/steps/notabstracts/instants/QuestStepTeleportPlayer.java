package fr.firedragonalex.spellandweapon.quests.steps.notabstracts.instants;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import fr.firedragonalex.spellandweapon.quests.steps.abstracts.AbstractQuestStep;
import fr.firedragonalex.spellandweapon.quests.steps.abstracts.AbstractQuestStepInstant;

public class QuestStepTeleportPlayer extends AbstractQuestStepInstant {
	
	private Location location;
	
	public QuestStepTeleportPlayer(Location location) {
		this.location = location;
	}
	
	@Override
	public AbstractQuestStep clone() {
		return new QuestStepTeleportPlayer(this.location);
	}

	@Override
	public void execute() {
		this.getQuestInProgress().getCustomPlayer().getPlayer().teleport(location);
	}

}
