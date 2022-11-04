package fr.firedragonalex.plugintuto;

import org.bukkit.plugin.java.JavaPlugin;

import fr.firedragonalex.plugintuto.commands.CommandChestRandom;
import fr.firedragonalex.plugintuto.commands.CommandTest;

public class Main extends JavaPlugin {
	
	@Override
	public void onEnable() {
		System.out.println("Le Plugin viens de s'allumer !");
		getCommand("test").setExecutor(new CommandTest());
		getCommand("alert").setExecutor(new CommandTest());
		getCommand("getstick").setExecutor(new CommandTest());
		getCommand("roles").setExecutor(new CommandTest());
		getCommand("chestspawn").setExecutor(new CommandChestRandom(this));
		getServer().getPluginManager().registerEvents(new PluginTutoListeners(), this);
	}
	
	@Override
	public void onDisable() {
		System.out.println("Le Plugin viens de s'éteindre !");
	}
}