package fr.firedragonalex.spellandweapon.quests.steps;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.firedragonalex.spellandweapon.Main;
import fr.firedragonalex.spellandweapon.quests.steps.notabstracts.defaults.QuestStepTalkVillager;

public class Speech extends BukkitRunnable{
	
	private int line;
	private Player player;
	private QuestStepTalkVillager step;
	
	public Speech(Player player,QuestStepTalkVillager step) {
		this.line = 0;
		this.player = player;
		this.step = step;
	}
	
	public Player getPlayer() {
		return this.player;
	}

	@Override
	public void run() {
		if (this.line < this.step.getListMessages().size()) {
			this.player.sendMessage("{"+this.step.getVillagerName()+ChatColor.WHITE+"} "+this.step.getListMessages().get(this.line));
			this.line++;
		} else {
			this.cancel();
			Main.getAllSpeeches().remove(this);
			this.step.finish();
		}
	}

}
