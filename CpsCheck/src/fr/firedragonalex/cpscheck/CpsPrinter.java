package fr.firedragonalex.cpscheck;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class CpsPrinter extends BukkitRunnable{
	
	private Player player;
	private Player target;
	private int timerTimeout;
	private int hitThisSecond;
	private Main main;
	
	public CpsPrinter(Main main,Player player, Player target) {
		this.player = player;
		this.target = target;
		this.timerTimeout = 30;
		this.hitThisSecond = 0;
		this.main = main;
	}
	
	public Player getTarget() {
		return this.target;
	}
	
	public void addHit() {
		this.hitThisSecond++;
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	@Override
	public void run() {
		this.player.sendMessage("§eCps de "+this.target.getName()+" : "+String.valueOf(this.hitThisSecond));
		this.hitThisSecond = 0;
		if (this.timerTimeout == 0) {
			main.getListCpsPrinters().remove(this);
			this.player.sendMessage("§eFin du check cps de "+this.target.getName()+" !");
			this.cancel();
		}
		this.timerTimeout--;
	}

}
