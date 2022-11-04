package fr.firedragonalex.uhc.core;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import fr.firedragonalex.uhc.specific.SpecificListeners;
import fr.firedragonalex.uhc.specific.SpecificOptions;

public class Main extends JavaPlugin {
	
	private static Main instance;
	@SuppressWarnings("unused")
	private static GameManager gameManager;
	
	@Override
	public void onEnable() {
		Main.instance = this;
		Options.update();
		SpecificOptions.update();
		
		if (Options.IS_THERE_ROLE && Options.STARTING_PLAYER_NUMBER != SpecificOptions.getComposition().size()) {
			System.out.println("\u001B[31m"+"[UHC] FATAL ERROR : STARTING_PLAYER_NUMBER must be equal to the quantity of roles in the composition !"+"\u001B[0m");
			Bukkit.shutdown();
			return;
		}
		
		Main.gameManager = new GameManager();
		
		getServer().getPluginManager().registerEvents(new Listeners(), this);
		getServer().getPluginManager().registerEvents(new SpecificListeners(), this);
		
		getCommand("role").setExecutor(new Commands());
		
		System.out.println("[UHC] Enbaled !");
	}
	
	public static Main getInstance() {
		return Main.instance;
	}
	
	@Override
	public void onDisable() {
		System.out.println("[UHC] Disabled !");
	}
}
