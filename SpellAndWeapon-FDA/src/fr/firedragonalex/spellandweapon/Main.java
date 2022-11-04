package fr.firedragonalex.spellandweapon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import fr.firedragonalex.spellandweapon.chatwaiters.ChatWaiter;
import fr.firedragonalex.spellandweapon.chatwaiters.ListenerChatWaiter;
import fr.firedragonalex.spellandweapon.commands.Commands;
import fr.firedragonalex.spellandweapon.commands.CommandsAdmins;
import fr.firedragonalex.spellandweapon.commands.TabCompleterCommands;
import fr.firedragonalex.spellandweapon.custom.code.CustomEntity;
import fr.firedragonalex.spellandweapon.custom.code.CustomPlayer;
import fr.firedragonalex.spellandweapon.custom.code.ListenersCustomDamages;
import fr.firedragonalex.spellandweapon.custom.code.custommonsterspawner.CustomMonsterSpawner;
import fr.firedragonalex.spellandweapon.custom.code.ListenersCustom;
import fr.firedragonalex.spellandweapon.custom.easytoadd.Craft;
import fr.firedragonalex.spellandweapon.custom.easytoadd.CustomMonsterType;
import fr.firedragonalex.spellandweapon.element.ElementCommands;
import fr.firedragonalex.spellandweapon.element.ElementType;
import fr.firedragonalex.spellandweapon.element.EveryTicks;
import fr.firedragonalex.spellandweapon.element.ListenersElement;
import fr.firedragonalex.spellandweapon.gui.ListenersGui;
import fr.firedragonalex.spellandweapon.islandgenerator.ListenerGenerator;
import fr.firedragonalex.spellandweapon.quests.ListenersQuest;
import fr.firedragonalex.spellandweapon.quests.ListenersQuestGUI;
import fr.firedragonalex.spellandweapon.quests.steps.Speech;
import fr.firedragonalex.spellandweapon.saveandload.MySql;
import fr.firedragonalex.spellandweapon.saveandload.SaveAndLoad;
import fr.firedragonalex.spellandweapon.saveandload.SaveAndLoadListeners;
import fr.firedragonalex.spellandweapon.showdamage.ShowDamage;
import fr.firedragonalex.spellandweapon.spell.ListenersSpell;
import fr.firedragonalex.spellandweapon.spell.Spell;
import fr.firedragonalex.spellandweapon.woswaria.EverySecondsWoswaria;
import fr.firedragonalex.spellandweapon.woswaria.ListenerPortals;
import fr.firedragonalex.spellandweapon.woswaria.ListenersWoswaria;
import fr.firedragonalex.spellandweapon.woswaria.Woswaria;


public class Main extends JavaPlugin{
	
	private static Main main;

	private static List<CustomMonsterSpawner> allCustomMonsterSpawners;
	private static List<CustomPlayer> allCustomPlayers;
	private static List<CustomEntity> allCustomEntities;
	private static List<ChatWaiter> allChatWaiters;
	private static List<Speech> allSpeeches;
	private static List<LivingEntity> allUnkillableEntities;
	
	@Override
	public void onEnable() {
		Main.main = this;
		
		Woswaria.enabled();
		
		ShowDamage.defaultValues();
		Main.allCustomPlayers = new ArrayList<>();
		Main.allCustomEntities = new ArrayList<>();
		Main.allCustomMonsterSpawners = new ArrayList<>();
		Main.allChatWaiters = new ArrayList<>();
		Main.allSpeeches = new ArrayList<>();
		Main.allUnkillableEntities = new ArrayList<>();
		
		for (Player player : Bukkit.getOnlinePlayers()) {
			CustomPlayer customPlayer = new CustomPlayer(player);
			Main.getAllCustomPlayers().add(customPlayer);
			Main.getAllCustomEntities().add((CustomEntity)customPlayer);
		}
		
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(new Listeners(), this);
		pm.registerEvents(new ListenersQuest(), this);
		pm.registerEvents(new ListenersCustom(), this);
		pm.registerEvents(new ListenersElement(), this);
		pm.registerEvents(new ListenersWoswaria(), this);
		pm.registerEvents(new ListenersGui(), this);
		pm.registerEvents(new ListenersQuestGUI(), this);
		pm.registerEvents(new ListenersSpell(), this);
		pm.registerEvents(new ListenerChatWaiter(), this);
		pm.registerEvents(new ListenerGenerator(), this);
		pm.registerEvents(new ListenersCustomDamages(), this);
		pm.registerEvents(new ListenerPortals(), this);
		pm.registerEvents(new SaveAndLoadListeners(), this);
		
		this.getCommand("setelement").setExecutor(new ElementCommands());
		this.getCommand("givearmor").setExecutor(new CommandsAdmins());
		this.getCommand("givearmor").setTabCompleter(new TabCompleterCommands());
		this.getCommand("giveweapon").setExecutor(new CommandsAdmins());
		this.getCommand("givetransformacon").setExecutor(new CommandsAdmins());
		this.getCommand("givecustommonsterspawner").setTabCompleter(new TabCompleterCommands());
		this.getCommand("givecustommonsterspawner").setExecutor(new CommandsAdmins());
		this.getCommand("giveweapon").setTabCompleter(new TabCompleterCommands());
		this.getCommand("summonmonster").setExecutor(new CommandsAdmins());
		this.getCommand("summonmonster").setTabCompleter(new TabCompleterCommands());
		this.getCommand("quests").setExecutor(new Commands());
		this.getCommand("giveentityremover").setExecutor(new Commands());
		this.getCommand("resourcepack").setExecutor(new Commands());
		this.getCommand("spawnnpc").setExecutor(new CommandsAdmins());
		this.getCommand("armor").setExecutor(new Commands());
		this.getCommand("giveislandgenerator").setExecutor(new CommandsAdmins());
		this.getCommand("undoisland").setExecutor(new CommandsAdmins());
		
		EveryTicks everyTicks = new EveryTicks();
		everyTicks.runTaskTimer(this, 0, 1);
		
		//EverySecondsWoswaria everySecondsWoswaria = new EverySecondsWoswaria();
		//everySecondsWoswaria.runTaskTimer(this, 0, 20);
		
		MySql.setDatabase("woswaria_spellandweapon");
		
		SaveAndLoad.load();
		
		System.out.println("[SpellAndWeapon-FDA] Enabled !");
	}
	
	@Override
	public void onDisable() {
		//Bukkit.getPluginManager().isPluginEnabled(Bukkit.getPluginManager().getPlugin("RankAndLevels-FDA")); saves ranks
		ShowDamage.clear();
		SaveAndLoad.save();
		
		for (CustomEntity customEntity : Main.getAllCustomEntities()) {
			if (!(customEntity instanceof CustomPlayer)) {
				customEntity.getEntity().remove();
			}
		}
		
		System.out.println("[SpellAndWeapon-FDA] Disabled !");
	}
	
	public static Main getInstance() {
		return Main.main;
	}
	
	public static List<ChatWaiter> getAllChatWaiters() {
		return Main.allChatWaiters;
	}
	
	public static List<Speech> getAllSpeeches() {
		return Main.allSpeeches;
	}
	
	public static List<LivingEntity> getAllUnkillableEntities() {
		return Main.allUnkillableEntities;
	}
	
	public @Nullable Spell getSpell(String string) {
		for (Spell spell : Spell.values()) {
			if (spell.getFormula().get(0).equalsIgnoreCase(string)) {
				return spell;
			}
		}
		return null;
	}
	
	public static List<CustomPlayer> getAllCustomPlayers() {
		return Main.allCustomPlayers;
	}
	
	public static List<CustomEntity> getAllCustomEntities() {
		return Main.allCustomEntities;
	}

	public static CustomEntity getCustomEntityByEntity(LivingEntity entity) {
		for(CustomEntity customEntity : allCustomEntities) {
			if (customEntity.getEntity()==entity) {
				return customEntity;
			}
		}
		return null;
	}
	
	public static CustomPlayer getCustomPlayerByUUID(UUID uuid) {
		for (CustomPlayer customPlayer : allCustomPlayers) {
			if (customPlayer.getPlayer().getUniqueId().equals(uuid)) {
				return customPlayer;
			}
		}
		return null;
	}	
	
	public static CustomPlayer getCustomPlayerByPlayer(Player player) {
		for ( CustomPlayer customPlayer : allCustomPlayers) {
			if (customPlayer.getPlayer() == player) {
				return customPlayer;
			}
		}
		return null;
	}	
	
	public void spawnParticlesAround(Particle particle, Location location, int nbParticle) {
		location.setX(location.getX()-0.5+Math.random());
		location.setY(location.getY()+(Math.random()*2));
		location.setZ(location.getZ()-0.5+Math.random());
		location.getWorld().spawnParticle(particle, location, nbParticle);
		return;
	}
	
	public void spawnParticlesAround(Particle particle, Location location, int nbParticle, int repeat) {
		for (int i = 0; i < repeat; i++) {
			Location tempLocation = location.clone();
			tempLocation.setX(tempLocation.getX()-0.5+Math.random());
			tempLocation.setY(tempLocation.getY()+(Math.random()*2));
			tempLocation.setZ(tempLocation.getZ()-0.5+Math.random());
			tempLocation.getWorld().spawnParticle(particle, tempLocation, nbParticle);
		}
		return;
	}
	
	public void spawnParticlesAroundEntity(Particle particle, Entity entity, int nbParticle) {
		Location location = entity.getLocation();
		location.setX(location.getX()-0.5+Math.random());
		location.setY(location.getY()+(Math.random()*entity.getHeight()));
		location.setZ(location.getZ()-0.5+Math.random());
		location.getWorld().spawnParticle(particle, location, nbParticle);
		return;
	}
	
	public void spawnParticlesAroundEntity(Particle particle, Entity entity, int nbParticle, int repeat) {
		Location locationInput = entity.getLocation();
		for (int i = 0; i < repeat; i++) {
			Location location = locationInput.clone();
			location.setX(location.getX()-0.5+Math.random());
			location.setY(location.getY()+(Math.random()*entity.getHeight()));
			location.setZ(location.getZ()-0.5+Math.random());
			location.getWorld().spawnParticle(particle, location, nbParticle);
		}
		return;
	}

	public static List<CustomMonsterSpawner> getAllCustomMonsterSpawners() {
		return allCustomMonsterSpawners;
	}
}
