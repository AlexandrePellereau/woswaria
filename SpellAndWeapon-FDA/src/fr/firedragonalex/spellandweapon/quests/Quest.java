package fr.firedragonalex.spellandweapon.quests;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;

import fr.firedragonalex.spellandweapon.custom.easytoadd.CustomMonsterType;
import fr.firedragonalex.spellandweapon.quests.steps.abstracts.AbstractQuestStep;
import fr.firedragonalex.spellandweapon.quests.steps.notabstracts.defaults.QuestStepBreakBlocks;
import fr.firedragonalex.spellandweapon.quests.steps.notabstracts.defaults.QuestStepGoToLocation;
import fr.firedragonalex.spellandweapon.quests.steps.notabstracts.defaults.QuestStepKillMonster;
import fr.firedragonalex.spellandweapon.quests.steps.notabstracts.defaults.QuestStepTalkVillager;
import fr.firedragonalex.spellandweapon.quests.steps.notabstracts.instants.QuestStepSpawnMonster;
import fr.firedragonalex.spellandweapon.quests.steps.notabstracts.instants.QuestStepTeleport;
import fr.firedragonalex.spellandweapon.quests.steps.notabstracts.instants.QuestStepTeleportPlayer;

public enum Quest {
	
	TUTORIAL("Tutoriel", Material.PAPER, 
			"Grendsaje", 0, Arrays.asList(),
			Arrays.asList(
			new QuestStepTalkVillager(ChatColor.GREEN+"TutoPNJ", Arrays.asList("Salut chevalier !","Alerte, on nous attaque !")),
			new QuestStepTeleportPlayer(new Location(Bukkit.getWorld("WoswariaTestWorld"), -66, 64, 1)),
			new QuestStepSpawnMonster(3, 5, CustomMonsterType.BLOB_EAU, new Location(Bukkit.getWorld("WoswariaTestWorld"), -66, 64, 1)),
			new QuestStepKillMonster(3, CustomMonsterType.BLOB_EAU),
			new QuestStepTalkVillager(ChatColor.GREEN+"TutoPNJ", Arrays.asList("Merci chevalier !"))
			)),
	DAILY1("Quête quotidienne 1", Material.PAPER,
			"Catherine", 0, Arrays.asList(),
			Arrays.asList(
			new QuestStepBreakBlocks(3,Material.COAL_ORE)
			)),
	QUETE_ECURIE_CHATEAU("test", Material.PAPER, 
			"Marie", 0, Arrays.asList(),
			Arrays.asList(
			new QuestStepTalkVillager(ChatColor.GREEN+"[palfrenière] Marie", Arrays.asList("Bonjour!","Puis-je vous demander une faveur?","Des Blobs gènent les chevaux de l'écurie. Pourriez-vous vous en débarasser?")),
			new QuestStepGoToLocation("14a92122-9c78-49e2-9bb0-3ed6718fd53b", new Location(Bukkit.getWorld("WoswariaTestWorld"), -66, 64, 1))
			));
			
	
	//Ici tu peux écrire les quêtes avant de les copier au dessus
	//private AbstractQuestStep test = new QuestStep
	
	private String name;
	private Material representedItem;
	private String nameNPC;
	private int minimumLevel;
	private List<Quest> questsToDoBefore;
	private List<AbstractQuestStep> allSteps;
	
	private Quest(String name, Material representedItem,
			@Nullable String nameNPC, int minimumLevel, List<Quest> questsToDoBefore,
			List<AbstractQuestStep> allSteps) {
		this.name = name;
		this.representedItem = representedItem;
		this.nameNPC = nameNPC;
		this.minimumLevel = minimumLevel;
		this.questsToDoBefore = questsToDoBefore;
		this.allSteps = allSteps;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Material getItem() {
		return this.representedItem;
	}
	
	public String getNameNPC() {
		return this.nameNPC;
	}
	
	public int getMinimumLevel() {
		return this.minimumLevel;
	}
	
	public List<Quest> getQuestsToDoBefore() {
		return this.questsToDoBefore;
	}
	
	public List<AbstractQuestStep> getAllSteps() {
		return this.allSteps;
	}
	
	public AbstractQuestStep getStep(int numberStep) {
		return this.allSteps.get(numberStep).clone();
	}
}
