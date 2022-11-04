package fr.firedragonalex.spellandweapon.quests.steps.notabstracts.instants;

import org.bukkit.ChatColor;
import org.bukkit.Location;

import fr.firedragonalex.diamoney.Main;
import fr.firedragonalex.diamoney.PlayerBank;
import fr.firedragonalex.spellandweapon.custom.code.CustomMonster;
import fr.firedragonalex.spellandweapon.custom.easytoadd.CustomMonsterType;
import fr.firedragonalex.spellandweapon.quests.Quest;
import fr.firedragonalex.spellandweapon.quests.steps.abstracts.AbstractQuestStep;
import fr.firedragonalex.spellandweapon.quests.steps.abstracts.AbstractQuestStepInstant;

public class QuestStepGiveDiamoney extends AbstractQuestStepInstant {
	
	private int amount;
	
	public QuestStepGiveDiamoney(int amount) {
		this.amount = amount;
	}
	
	@Override
	public AbstractQuestStep clone() {
		return new QuestStepGiveDiamoney(amount);
	}

	@Override
	public void execute() {
		PlayerBank playerBank = Main.getPlayerBank(this.getQuestInProgress().getCustomPlayer().getPlayer());
		playerBank.addMoney(amount);
	}

}
