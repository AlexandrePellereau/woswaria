package fr.firedragonalex.spellandweapon.quests.steps.notabstracts.instants;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import fr.firedragonalex.diamoney.Main;
import fr.firedragonalex.diamoney.PlayerBank;
import fr.firedragonalex.spellandweapon.custom.code.CustomMonster;
import fr.firedragonalex.spellandweapon.custom.easytoadd.CustomMonsterType;
import fr.firedragonalex.spellandweapon.quests.Quest;
import fr.firedragonalex.spellandweapon.quests.steps.abstracts.AbstractQuestStep;
import fr.firedragonalex.spellandweapon.quests.steps.abstracts.AbstractQuestStepInstant;

public class QuestStepGiveItem extends AbstractQuestStepInstant {
	
	private ItemStack item;
	
	public QuestStepGiveItem(ItemStack item) {
		this.item = item;
	}
	
	@Override
	public AbstractQuestStep clone() {
		return new QuestStepGiveItem(item);
	}

	@Override
	public void execute() {
		this.getQuestInProgress().getCustomPlayer().getPlayer().getInventory().addItem(item);
	}

}
