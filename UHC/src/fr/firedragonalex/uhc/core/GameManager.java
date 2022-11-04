package fr.firedragonalex.uhc.core;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;

import fr.firedragonalex.uhc.core.role.InfectedPlayer;
import fr.firedragonalex.uhc.core.role.cooldown.Cooldown;
import fr.firedragonalex.uhc.core.runnable.GameStarter;
import fr.firedragonalex.uhc.specific.CustomEffect;
import fr.firedragonalex.uhc.specific.Role;
import fr.firedragonalex.uhc.specific.Side;

public class GameManager {
	
	private static GameManager instance;
	private static GameState gameState;
	private static List<Player> allPlayers;

	private static HashMap<Player, Role> playersToRole;
	private static List<Cooldown> allCooldowns = new ArrayList<>();
	private static List<InfectedPlayer> allInfectedPlayers = new ArrayList<>();
	
	public static void tpPlayersToSpawn() {
		int nbPlayers = GameManager.getAllPlayers().size();
		float angle = (float)(Math.PI*2.0/nbPlayers);
		for (int i = 0; i < nbPlayers; i++) {
			Player player = GameManager.getAllPlayers().get(i);
			player.teleport(new Location(Bukkit.getWorld("UHC_game_map"), Math.cos(i*angle)*Options.DISTANCE_FROM_SPAWN, 200, Math.sin(i*angle)*Options.DISTANCE_FROM_SPAWN));
		}
	}
	
	public static void checkGameStart() {
		if (GameManager.allPlayers.size() == Options.STARTING_PLAYER_NUMBER) {
			GameManager.setGameState(GameState.STARTING);
			new GameStarter().runTaskTimer(Main.getInstance(), 0, 20);
		}
	}
	
	@SuppressWarnings("deprecation")
	public static void sendTitleToAll(String message) {
		for (Player player : GameManager.getAllPlayers()) {
			player.sendTitle(message, null);
		}
	}
	@SuppressWarnings("deprecation")
	public static void sendSmallTitleToAll(String message) {
		for (Player player : GameManager.getAllPlayers()) {
			player.sendTitle(null, message);
		}
	}
	
	public static void sendMessageToAll(String message) {
		for (Player player : GameManager.getAllPlayers()) {
			player.sendMessage(message);
		}
	}
	
	public static void deleteDir(File file) {//not from me
	    File[] contents = file.listFiles();
	    if (contents != null) {
	        for (File f : contents) {
	            if (! Files.isSymbolicLink(f.toPath())) {
	                deleteDir(f);
	            }
	        }
	    }
	    file.delete();
	}
	
	public static boolean isWin() {
		if (GameManager.getAllPlayers().size() == 1) return true;
		if (! Options.IS_THERE_ROLE) return false;
		
		Side side = GameManager.getPlayersToRole().get(GameManager.getAllPlayers().get(0)).getSide();
		for (Player player : GameManager.getAllPlayers()) {
			if (side != GameManager.getPlayersToRole().get(player).getSide()) return false;
		}
		return true;
	}
	
	///////////////////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	//////////////////////////////////Getters & Setters\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	///////////////////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	
	public GameManager() {
		GameManager.instance = this;
		GameManager.gameState = GameState.WAITING_PLAYERS;
		GameManager.allPlayers = new ArrayList<>();
		GameManager.playersToRole = new HashMap<>();
		
		Main.getInstance().getServer().createWorld(new WorldCreator("UHC_game_map")).setDifficulty(Difficulty.PEACEFUL);
		
		int tryFindGoodSeed = 1;
		World world = Main.getInstance().getServer().createWorld(new WorldCreator("UHC_game_map"));
		//-2531225523334330054
		if (Options.CREATE_NEW_SEED) {
			while (world.getBlockAt(0, 60, 0).getBiome() != Biome.ROOFED_FOREST) {
				System.out.println("Center biome :"+world.getBlockAt(0, 60, 0).getBiome());
				Bukkit.unloadWorld("UHC_game_map", false);
				GameManager.deleteDir(new File("UHC_game_map"));
				world = Main.getInstance().getServer().createWorld(new WorldCreator("UHC_game_map"));
				tryFindGoodSeed++;
			}
			System.out.println("[UHC] The server has to generate "+tryFindGoodSeed+"seed to find a good one.");
		}
		world.setDifficulty(Difficulty.HARD);
	}
	
	public static GameManager getInstance() {
		return instance;
	}

	public static GameState getGameState() {
		return gameState;
	}

	public static void setGameState(GameState gameState) {
		GameManager.gameState = gameState;
	}

	public static List<Player> getAllPlayers() { 
		return GameManager.allPlayers;
	}
	
	public static HashMap<Player, Role> getPlayersToRole() { 
		return GameManager.playersToRole;
	}

	public static Role getRole(Player player) {
		return GameManager.playersToRole.get(player);
	}

	public static void addPlayer(Player player) {
		GameManager.allPlayers.add(player);
	}
	
	public static void removePlayer(Player player) {
		GameManager.allPlayers.remove(player);
		GameManager.playersToRole.remove(player);
	}

	public static List<Cooldown> getAllCooldowns() {
		return GameManager.allCooldowns;
	}

	public static void addCooldown(Cooldown cooldown) {
		GameManager.allCooldowns.add(cooldown);
	}

	public static List<InfectedPlayer> getAllInfectedPlayers() {
		return GameManager.allInfectedPlayers;
	}

	public static void applyCustomEffect(Player player, CustomEffect customEffect) {
		GameManager.allInfectedPlayers.add(new InfectedPlayer(player, customEffect));
	}
}
