package fr.firedragonalex.spellandweapon.quests.steps.notabstracts.defaults;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import fr.firedragonalex.spellandweapon.Main;
import fr.firedragonalex.spellandweapon.quests.QuestInProgress;
import fr.firedragonalex.spellandweapon.quests.steps.Speech;
import fr.firedragonalex.spellandweapon.quests.steps.abstracts.AbstractQuestStep;
import fr.firedragonalex.spellandweapon.quests.steps.abstracts.AbstractQuestStepDefault;

public class QuestStepTalkVillager extends AbstractQuestStepDefault {
	
	private String villagerName;
	private List<String> listMessages;
	
	public QuestStepTalkVillager(String villagerName, List<String> listMessages) {
		this.villagerName = villagerName;
		this.listMessages = listMessages;
	}
	
	public String getVillagerName() {
		return this.villagerName;
	}
	
	public List<String> getListMessages() {
		return this.listMessages;
	}
	
	public void startSpeech(Player player) {
		Speech speech = new Speech(player, this);
		speech.runTaskTimer(Main.getInstance(), 0, 1*20);
		Main.getAllSpeeches().add(speech);
	}

	@Override
	public AbstractQuestStep clone() {
		return new QuestStepTalkVillager(this.villagerName, this.listMessages);
	}
	
	@Override
	public String toPrint() {
		return "Tu dois parler au villageois nommé : \""+villagerName+ChatColor.YELLOW+"\" !";
	}

	@Override
	public void checkFinish() {}

	@Override
	public int getAdvencementStep() {
		return 0;
	}
	
	@Override
	public void setAdvencementStep(int advencementStep) {}
}
