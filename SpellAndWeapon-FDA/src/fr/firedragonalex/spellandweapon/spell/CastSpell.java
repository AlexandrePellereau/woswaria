package fr.firedragonalex.spellandweapon.spell;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.firedragonalex.spellandweapon.Main;
import fr.firedragonalex.spellandweapon.custom.code.CustomPlayer;

public class CastSpell extends BukkitRunnable{
	
	private int timer;
	private int totalTime;
	private CustomPlayer customPlayer;
	private BossBar bossBarTimer;
	private boolean isComplete;
	

	public CastSpell(CustomPlayer customPlayer) {
		this.isComplete = false;
		this.totalTime = customPlayer.getSpell().getDifficulty().getTicksPerCharacter()*customPlayer.getSpell().getFormula().get((customPlayer.getAdvancementFormula())).length();
		this.timer = this.totalTime;
		this.customPlayer = customPlayer;
		this.bossBarTimer = Bukkit.createBossBar(customPlayer.getSpell().getFormula().get(customPlayer.getAdvancementFormula()), customPlayer.getSpell().getColor(), BarStyle.SOLID, BarFlag.PLAY_BOSS_MUSIC);
		this.bossBarTimer.setProgress(0);
		this.bossBarTimer.setVisible(true);
		this.bossBarTimer.addPlayer(customPlayer.getPlayer());
	}
	
	@Override
	public void run() {
		if (this.isComplete) {
			this.customPlayer.setIsCastingSpell(false);
			this.customPlayer.getSpell().cast(customPlayer);
			this.bossBarTimer.removeAll();
			this.cancel();
		}
		this.bossBarTimer.setProgress(timer*1.0/totalTime*1.0);
		if (timer == 0) {
			this.spellFail();
		}
		timer--;
	}
	
	public void tryFormula(String message) {
		if (message.equalsIgnoreCase(this.customPlayer.getSpell().getFormula().get(this.customPlayer.getAdvancementFormula()))) {
			this.customPlayer.getPlayer().sendTitle("§c"+message, "", 10, 40, 20);
			if (this.customPlayer.getSpell().getFormula().size()==(this.customPlayer.getAdvancementFormula()+1)) {
				this.setIsComplete(true);
			} else {
				this.bossBarTimer.removeAll();
				this.cancel();
				this.customPlayer.setAdvancementFormula(this.customPlayer.getAdvancementFormula()+1);
				this.customPlayer.startSpell();
			}
		} else {
			this.spellFail();
		}
	}
	
	public void setIsComplete(boolean input) {
		this.isComplete = input;
	}
	
	public void spellFail() {
		this.customPlayer.getPlayer().sendTitle("§cSort raté !", "", 10, 40, 20);
		this.bossBarTimer.removeAll();
		this.customPlayer.setIsCastingSpell(false);
		this.cancel();
	}
}
