package fr.firedragonalex.spellandweapon.chatwaiters;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.firedragonalex.spellandweapon.Main;
import fr.firedragonalex.spellandweapon.woswaria.Woswaria;

public abstract class ChatWaiter extends BukkitRunnable{
	
	protected Player player;
	private int timeout;
	
	public ChatWaiter(Player player) {
		this.player = player;
		this.timeout = 60;
		this.runTaskLater(Main.getInstance(), this.timeout*20);
		Main.getAllChatWaiters().add(this);
	}
	
	public ChatWaiter(Player player, int timeout) {
		this.player = player;
		this.runTaskLater(Main.getInstance(), this.timeout*20);
		Main.getAllChatWaiters().add(this);
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	public void totalExecute(String message) {
		this.execute(message);
		Main.getAllChatWaiters().remove(this);
		this.cancel();
	}
	
	public abstract void execute(String message);

	@Override
	public void run() {
		this.player.sendMessage("§cTemps écoulé !");
		Main.getAllChatWaiters().remove(this);
		this.cancel();
	}

}
