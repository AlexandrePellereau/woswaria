package fr.firedragonalex.diamoney;

import org.bukkit.OfflinePlayer;

public class PlayerBank {
	
	private int money;
	private OfflinePlayer player;
	private Main main;
	
	public PlayerBank(int money, OfflinePlayer player, Main main) {
		this.money = money;
		this.player = player;
		this.main = main;
	}
	
	public OfflinePlayer getPlayer() {
		return this.player;
	}
	
	public int getMoney() {
		return this.money;
	}
	
	public void addMoney(int money) {
		this.money += money;
	}
	
	public void removeMoney(int money) {
		this.money -= money;
	}
	
	public boolean hasMoney(int money) {
		return this.money - money >= 0;
	}
}
