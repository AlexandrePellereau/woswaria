package fr.firedragonalex.spellandweapon.quests.steps.notabstracts.instants;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import fr.firedragonalex.spellandweapon.quests.steps.abstracts.AbstractQuestStep;
import fr.firedragonalex.spellandweapon.quests.steps.abstracts.AbstractQuestStepInstant;

public class QuestStepTeleport extends AbstractQuestStepInstant {
	
	private Entity entity;
	private Location location;
	
	public QuestStepTeleport(Entity entity, Location location) {
		this.entity = entity;
		this.location = location;
	}
	
	public QuestStepTeleport(Player player, Location location) {
		this.entity = player;
		this.location = location;
	}
	
	@Override
	public AbstractQuestStep clone() {
		return new QuestStepTeleport(this.entity, this.location);
	}

	@Override
	public void execute() {
		this.entity.teleport(location);
	}

}
