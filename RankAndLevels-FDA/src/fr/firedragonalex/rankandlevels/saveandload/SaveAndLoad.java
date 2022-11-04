package fr.firedragonalex.rankandlevels.saveandload;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fr.firedragonalex.rankandlevels.Main;
import fr.firedragonalex.rankandlevels.rank.Rank;
import fr.firedragonalex.spellandweapon.custom.code.CustomPlayer;

public class SaveAndLoad {
	
	public static void save() {
		SaveAndLoad.saveLevelRewards();
		//SaveAndLoad.saveLevels();
		for (Player player : Bukkit.getOnlinePlayers()) {
			SaveAndLoad.saveLevel(player.getUniqueId());
		}
		MySql.stopConnection();
	}
	
	public static void load() {
		SaveAndLoad.loadLevelRewards();
		//SaveAndLoad.loadLevels();
		for (Player player : Bukkit.getOnlinePlayers()) {
			SaveAndLoad.loadLevel(player.getUniqueId());
		}
	}
	
	public static void saveLevel(UUID uuid) {
		MySql.checkMysqlConnection();
		
		CustomPlayer customPlayer = fr.firedragonalex.spellandweapon.Main.getCustomPlayerByUUID(uuid);
		
		HashMap<String, String> columnToValue = new HashMap<>();
		columnToValue.put("Level", customPlayer.getLevel()+"");
		columnToValue.put("Xp", customPlayer.getXp()+"");
		columnToValue.put("TotalXp", customPlayer.getTotalXp()+"");
		MySql.insertOrUpdatePlayerUUID("Levels",uuid, columnToValue);
		
//		if (MySql.existOneLineOrMore("Levels", "PlayerUUID = '"+uuid+"'")) {
//			
//			String sqlCommand = "UPDATE Levels SET ";
//			sqlCommand += "Level = "+customPlayer.getLevel()+",";
//			sqlCommand += "Xp = "+customPlayer.getXp()+",";
//			sqlCommand += "TotalXp = "+customPlayer.getTotalXp()+" ";
//			sqlCommand += "WHERE PlayerUUID = '"+uuid+"'";
//			MySql.executeSql(sqlCommand);
//			
//		} else {
//			String sqlCommand = "INSERT INTO Levels (PlayerUUID,Level,Xp,TotalXp) VALUES (";
//			sqlCommand += "'"+uuid+"'"+",";
//			sqlCommand += customPlayer.getLevel()+",";
//			sqlCommand += customPlayer.getXp()+",";
//			sqlCommand += customPlayer.getTotalXp()+") ";
//			MySql.executeSql(sqlCommand);
//		}
	}
	
	public static void loadLevel(UUID uuid) {
		MySql.checkMysqlConnection();
		
		CustomPlayer customPlayer = fr.firedragonalex.spellandweapon.Main.getCustomPlayerByUUID(uuid);
		ResultSet result = MySql.getLineWherePlayerUUID("Levels", uuid);
		//ResultSet result = MySql.executeSqlQuery("SELECT * FROM Levels WHERE PlayerUUID = '"+uuid+"'");
		try {
			customPlayer.setLevel(result.getInt(2));
			customPlayer.setXp(result.getInt(3));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		customPlayer.updateXp();
		customPlayer.updateStats();
	}
	
	//////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////
	
	public static void saveLevelRewards() {
		List<List<String>> listOflines = new ArrayList<List<String>>();
		listOflines.add(Arrays.asList("PlayerUUID","Rank","Level"));
		for (UUID uuid : Main.getLevelRewardsAdvancement().keySet()) {
			for (Rank rank : Main.getLevelRewardsAdvancement().get(uuid).keySet()) {
				int level = Main.getLevelRewardsAdvancement().get(uuid).get(rank);
				List<String> line = new ArrayList<>();
				line.add(uuid.toString());
				line.add(rank.toString());
				line.add(level+"");
				listOflines.add(line);
			}
		}
		UseCsvFiles.save("LevelRewards", listOflines, "plugins/RankAndLevels-FDA/saves/");
	}
	
	
	
	public static void loadLevelRewards() {
		List<List<String>> lines = UseCsvFiles.load("LevelRewards", "plugins/RankAndLevels-FDA/saves/");
		for (List<String> line : lines) {
			UUID uuid = UUID.fromString(line.get(0));
			Rank rank = Rank.valueOf(line.get(1));
			int level = Integer.valueOf(line.get(2));
			HashMap<Rank, Integer> rankToLevel = new HashMap<>();
			if (Main.getLevelRewardsAdvancement().get(uuid) != null) {
				rankToLevel = Main.getLevelRewardsAdvancement().get(uuid);
			}
			rankToLevel.put(rank, level);
			Main.getLevelRewardsAdvancement().put(uuid,rankToLevel);
		}
	}
	
	//////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////
	
//	public static void loadLevels() {
//		List<List<String>> lines = UseCsvFiles.load("Levels", "plugins/RankAndLevels-FDA/saves/");
//		for (List<String> line : lines) {
//			UUID uuid = UUID.fromString(line.get(0));
//			int level = Integer.valueOf(line.get(1));
//			int xp = Integer.valueOf(line.get(2));
//			CustomPlayer customPlayer = fr.firedragonalex.spellandweapon.Main.getCustomPlayerByUUID(uuid);
//			customPlayer.setLevel(level);
//			customPlayer.setXp(xp);
//		}
//	}
//	
//	public static void saveLevels() {
//		List<List<String>> listOflines = new ArrayList<List<String>>();
//		listOflines.add(Arrays.asList("PlayerUUID","Rank","Level"));
//		for (CustomPlayer customPlayer : fr.firedragonalex.spellandweapon.Main.getAllCustomPlayers()) {
//			List<String> line = new ArrayList<>();
//			line.add(customPlayer.getPlayer().getUniqueId().toString());
//			line.add(customPlayer.getLevel()+"");
//			line.add(customPlayer.getXp()+"");
//			listOflines.add(line);
//		}
//		UseCsvFiles.save("Levels", listOflines, "plugins/RankAndLevels-FDA/saves/");
//	}
}
