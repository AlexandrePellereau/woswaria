package fr.firedragonalex.spellandweapon.quests;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import fr.firedragonalex.areaplugin.MainAreaPlugin;
import fr.firedragonalex.areaplugin.area.Area;
import fr.firedragonalex.spellandweapon.Main;
import fr.firedragonalex.spellandweapon.custom.code.CustomPlayer;
import fr.firedragonalex.spellandweapon.quests.steps.Speech;
import fr.firedragonalex.spellandweapon.quests.steps.abstracts.AbstractQuestStep;
import fr.firedragonalex.spellandweapon.quests.steps.notabstracts.defaults.QuestStepBreakBlocks;
import fr.firedragonalex.spellandweapon.quests.steps.notabstracts.defaults.QuestStepGoToLocation;
import fr.firedragonalex.spellandweapon.quests.steps.notabstracts.defaults.QuestStepTalkVillager;

public class ListenersQuest implements Listener {

	private Main main;

	public ListenersQuest() {
		this.main = Main.getInstance();
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBreakBock(BlockBreakEvent event) {
		if (!event.isCancelled()) {
			CustomPlayer customPlayer = Main.getCustomPlayerByPlayer(event.getPlayer());
			if (customPlayer.getCurrentQuest() == null) return;
			AbstractQuestStep step = customPlayer.getCurrentQuest().getStep();
			if (step instanceof QuestStepBreakBlocks) {
				QuestStepBreakBlocks stepBreakBlocks = (QuestStepBreakBlocks)step;
				if (event.getBlock().getType() == stepBreakBlocks.getTypeOfBlocksToBreak()) {
					stepBreakBlocks.blockBreak();
				}
			}
		}
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onInteractAtEntityGiveQuest(PlayerInteractAtEntityEvent event) {
		if (event.isCancelled()) return;
		if (!(event.getRightClicked() instanceof Villager)) return;
		CustomPlayer customPlayer = Main.getCustomPlayerByPlayer(event.getPlayer());
		Villager villager = (Villager)event.getRightClicked();
		for (Quest quest : customPlayer.getLockQuests()) {
			if (villager.getCustomName().contains(quest.getNameNPC())) {
				customPlayer.addQuest(quest);
				customPlayer.setCurrentQuest(quest);
			}
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onInteractAtEntity(PlayerInteractAtEntityEvent event) {
		if (event.isCancelled()) return;
		if (event.getRightClicked() instanceof Villager) {
			CustomPlayer customPlayer = Main.getCustomPlayerByPlayer(event.getPlayer());
			Villager villager = (Villager)event.getRightClicked();
			if (customPlayer.getCurrentQuest() == null) return;
			if (customPlayer.getCurrentQuest().getStep() instanceof QuestStepTalkVillager) {
				QuestStepTalkVillager step = (QuestStepTalkVillager)customPlayer.getCurrentQuest().getStep();
				if (step.getVillagerName().equals(villager.getCustomName())) {
					for (Speech speech : Main.getAllSpeeches()) {
						if (speech.getPlayer().getUniqueId().equals(event.getPlayer().getUniqueId())) {
							event.getPlayer().sendMessage("§cTu dois finir ton dialogue !");
							return;
						}
					}
					step.startSpeech(event.getPlayer());
				}
			}
		}
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent event) {
		CustomPlayer customPlayer = Main.getCustomPlayerByPlayer(event.getPlayer());
		if (customPlayer.getCurrentQuest() == null) return;
		if (customPlayer.getCurrentQuest().getStep() instanceof QuestStepGoToLocation) {
			QuestStepGoToLocation step = (QuestStepGoToLocation)customPlayer.getCurrentQuest().getStep();
			Area area = MainAreaPlugin.getAreaByLocation(event.getTo());
			if (area != null && area.getUUID().equals(step.getAreaUuidPlayerMustToGo())) {
				step.finish();
			}
		}
	}

}
