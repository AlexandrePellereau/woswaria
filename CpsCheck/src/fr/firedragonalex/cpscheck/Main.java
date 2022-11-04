package fr.firedragonalex.cpscheck;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{
	
	private List<CpsPrinter> listCpsPrinters;
	
	@Override
	public void onEnable() {
		this.listCpsPrinters = new ArrayList<CpsPrinter>();
		getServer().getPluginManager().registerEvents(new Listeners(this), this);
		getCommand("cpscheck").setExecutor(new Commands(this));
		System.out.println("[CpsCheck] Enabled !");
	}
	
	public void newCpsPrinter(Player player, Player target) {
		for (CpsPrinter cpsPrinter : this.listCpsPrinters) {
			if (cpsPrinter.getPlayer() == player && cpsPrinter.getTarget() == target) {
				player.sendMessage("§cTu regarde déjà le cps de ce joueur.");
				return;
			}
		}
		CpsPrinter cpsPrinter = new CpsPrinter(this,player,target);
		cpsPrinter.runTaskTimer(this, 0, 20);
		this.listCpsPrinters.add(cpsPrinter);
	}
	
	public List<CpsPrinter> getListCpsPrinters() {
		return this.listCpsPrinters;
	}
	
	
	@Override
	public void onDisable() {
		System.out.println("[CpsCheck] Disabled !");
	}

}
