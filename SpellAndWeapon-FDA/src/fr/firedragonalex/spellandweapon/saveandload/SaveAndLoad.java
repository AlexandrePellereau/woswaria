package fr.firedragonalex.spellandweapon.saveandload;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fr.firedragonalex.spellandweapon.Main;
import fr.firedragonalex.spellandweapon.custom.code.CustomPlayer;
import fr.firedragonalex.spellandweapon.custom.easytoadd.CustomArmor;
import fr.firedragonalex.spellandweapon.quests.Quest;
import fr.firedragonalex.spellandweapon.quests.QuestInProgress;
import fr.firedragonalex.spellandweapon.quests.steps.abstracts.AbstractQuestStep;
import fr.firedragonalex.spellandweapon.quests.steps.abstracts.AbstractQuestStepDefault;

public class SaveAndLoad {
	
	//////////////////SAVE\\\\\\\\\\\\\\\\\\
	
	public static void save() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			SaveAndLoad.saveCustomPlayer(player.getUniqueId());
		}
		MySql.stopConnection();
	}
	
	public static void saveCustomPlayer(UUID uuid) {
		SaveAndLoad.saveArmor(uuid);
		SaveAndLoad.saveQuestInProgress(uuid);
		SaveAndLoad.saveQuestsCompleted(uuid);
	}
	
	private static void saveArmor(UUID uuid) {
		MySql.checkMysqlConnection();
		
		CustomPlayer customPlayer = Main.getCustomPlayerByUUID(uuid);
		
		for (int i = 0; i < 4; i++) {
			CustomArmor customArmor = customPlayer.getCustomArmorEquipements()[i];
			if (customArmor != null) {
				HashMap<String, String> valuesMustBeEquals = new HashMap<>();
				valuesMustBeEquals.put("PlayerUUID", "'"+uuid+"'");
				valuesMustBeEquals.put("EquipementLocation", i+"");
				HashMap<String, String> newValues = new HashMap<>();
				newValues.put("ArmorName", "'"+customArmor.name()+"'");
				MySql.insertOrUpdate("Armor",valuesMustBeEquals,newValues);
			} else {
				if (MySql.existOneLineOrMore("Armor", "PlayerUUID = '"+uuid+"' AND EquipementLocation = "+i)) {
					HashMap<String, String> valuesMustBeEquals = new HashMap<>();
					valuesMustBeEquals.put("PlayerUUID", "'"+uuid+"'");
					valuesMustBeEquals.put("EquipementLocation", i+"");
					HashMap<String, String> newValues = new HashMap<>();
					newValues.put("ArmorName", "'null'");
					MySql.insertOrUpdate("Armor",valuesMustBeEquals,newValues);
				}
			}
		}
	}
	
	private static void saveQuestInProgress (UUID uuid) {
		MySql.checkMysqlConnection();
		
		CustomPlayer customPlayer = Main.getCustomPlayerByUUID(uuid);
		
		for (QuestInProgress questInProgress : customPlayer.getQuests()) {
			HashMap<String, String> valuesMustBeEquals = new HashMap<>();
			valuesMustBeEquals.put("PlayerUUID", "'"+uuid+"'");
			valuesMustBeEquals.put("QuestName", "'"+questInProgress.getQuest().name()+"'");
			HashMap<String, String> newValues = new HashMap<>();
			newValues.put("Advencement", questInProgress.getAdvancement()+"");
			AbstractQuestStep step = questInProgress.getStep();
			if (step instanceof AbstractQuestStepDefault) {
				newValues.put("AdvencementStep", ((AbstractQuestStepDefault)step).getAdvencementStep()+"");
			} else {
				newValues.put("AdvencementStep", "-1");
			}
			MySql.insertOrUpdate("QuestInProgress",valuesMustBeEquals,newValues);
		}
	}
	
	private static void saveQuestsCompleted(UUID uuid) {
		MySql.checkMysqlConnection();
		
		CustomPlayer customPlayer = Main.getCustomPlayerByUUID(uuid);
		
		for (Quest quest : customPlayer.getQuestsCompleted()) {
			HashMap<String, String> columnToValue = new HashMap<>();
			columnToValue.put("QuestName", "'"+quest.toString()+"'");
			MySql.insertOrUpdatePlayerUUID("QuestCompleted",uuid, columnToValue);
		}
	}
	
	//////////////////LOAD\\\\\\\\\\\\\\\\\\

	public static void load() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			SaveAndLoad.loadCustomPlayer(player.getUniqueId());
		}
	}
	
	public static void loadCustomPlayer(UUID uuid) {
		SaveAndLoad.loadArmor(uuid);
		SaveAndLoad.loadQuestInProgress(uuid);
		SaveAndLoad.loadQuestsCompleted(uuid);
	}
	
	private static void loadArmor(UUID uuid) {
		MySql.checkMysqlConnection();
		
		CustomPlayer customPlayer = Main.getCustomPlayerByUUID(uuid);
		ResultSet result = MySql.getAllLinesWherePlayerUUID("Armor", uuid);
		
		try {
			while (result.next()) {
				if (result.getString(2).equals("null")) {
					customPlayer.setCustomArmor(result.getInt(3), null);
					return;
				}
				CustomArmor customArmor = CustomArmor.valueOf(result.getString(2));
				if (customArmor != null) {
					customPlayer.setCustomArmor(result.getInt(3), customArmor);
				}
			}
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	private static void loadQuestInProgress (UUID uuid) {
		MySql.checkMysqlConnection();
		
		CustomPlayer customPlayer = Main.getCustomPlayerByUUID(uuid);
		ResultSet result = MySql.getAllLinesWherePlayerUUID("QuestInProgress", uuid);
		try {
			while (result.next()) {
				QuestInProgress questInProgress = new QuestInProgress(Quest.valueOf(result.getString(2)), customPlayer);
				questInProgress.setStep(result.getInt(3));
				AbstractQuestStep step = questInProgress.getStep();
				if (step instanceof AbstractQuestStepDefault) {
					((AbstractQuestStepDefault)step).setAdvencementStep(result.getInt(4));
				}
				customPlayer.getQuests().add(questInProgress);
			}
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	private static void loadQuestsCompleted(UUID uuid) {
		MySql.checkMysqlConnection();
		
		CustomPlayer customPlayer = Main.getCustomPlayerByUUID(uuid);
		ResultSet result = MySql.getAllLinesWherePlayerUUID("QuestCompleted", uuid);
		try {
			while (result.next()) {
				customPlayer.getQuestsCompleted().add(Quest.valueOf(result.getString(2)));
			}
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	public static void removeQuest(UUID uuid, Quest quest) {
		MySql.checkMysqlConnection();
		
		MySql.tryRemove("QuestInProgress", "PlayerUUID = '"+uuid+"' AND QuestName = '"+quest.name()+"'");
	}

}
