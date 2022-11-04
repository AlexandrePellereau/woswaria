package fr.firedragonalex.uhc.core;

import fr.firedragonalex.uhc.core.langague.Langague;

public class Options {
	
	public static Langague LANGAGUE;
	
	public static String SPAWN_POINT_LOBBY_WORLD;
	public static double SPAWN_POINT_LOBBY_X;
	public static double SPAWN_POINT_LOBBY_Y;
	public static double SPAWN_POINT_LOBBY_Z;
	
	public static boolean CREATE_NEW_SEED;
	
	public static int STARTING_PLAYER_NUMBER;
	
	public static int BEFORE_STARTING_DURATION;
	public static int DISTANCE_FROM_SPAWN;
	public static int INVICIBILITY_DURATION;
	public static int PVP_OFF_DURATION;
	public static int VICTORY_DURATION;
	
	public static boolean IS_THERE_WORLDBORDER;
	public static double STARTING_WORLDBORDER_SIZE;
	public static double WORLDBORDER_CHANGING_SIZE_POURCENTAGE;
	public static long WORLDBORDER_MOVING_DURATION;
	public static long DURATION_BETWEEN_WORLDBORDER_MOVING;
	
	public static boolean IS_THERE_TOTAL_BREAK_TREE;
	public static boolean IS_THERE_INSTANT_SMELT_ORE;
	public static boolean IS_THERE_STARTING_STUFF;
	
	public static String CHEST_STARTING_STUFF_WORLD;
	public static double CHEST_STARTING_STUFF_X;
	public static double CHEST_STARTING_STUFF_Y;
	public static double CHEST_STARTING_STUFF_Z;
	
	
	
	public static boolean IS_THERE_ROLE;
	public static int ROLE_ANNOUCEMENT_DURATION;
	
	public static void update() {
		Options.LANGAGUE = Langague.FRENCH;
		
		Options.SPAWN_POINT_LOBBY_WORLD = "world";
		Options.SPAWN_POINT_LOBBY_X = 0.5;
		Options.SPAWN_POINT_LOBBY_Y = 102;
		Options.SPAWN_POINT_LOBBY_Z = 0.5;
		
		Options.CREATE_NEW_SEED = false;
		
		Options.STARTING_PLAYER_NUMBER = 1;
		
		Options.BEFORE_STARTING_DURATION = 5;//10
		Options.DISTANCE_FROM_SPAWN = 300;
		Options.INVICIBILITY_DURATION = 10;//10
		Options.PVP_OFF_DURATION = 20*60;//20*60
		Options.VICTORY_DURATION = 20;
		
		Options.IS_THERE_WORLDBORDER = true;
		Options.STARTING_WORLDBORDER_SIZE = 5000;//5000
		Options.WORLDBORDER_CHANGING_SIZE_POURCENTAGE = 50;//50
		Options.WORLDBORDER_MOVING_DURATION = 10;//60
		Options.DURATION_BETWEEN_WORLDBORDER_MOVING = 2*60;//2*60
		
		Options.IS_THERE_TOTAL_BREAK_TREE = false;
		Options.IS_THERE_INSTANT_SMELT_ORE = false;
		Options.IS_THERE_INSTANT_SMELT_ORE = false;
		
		Options.CHEST_STARTING_STUFF_WORLD = "world";
		Options.CHEST_STARTING_STUFF_X = 0.5;
		Options.CHEST_STARTING_STUFF_Y = 102;
		Options.CHEST_STARTING_STUFF_Z = 0.5;
		



		Options.IS_THERE_ROLE = true;
		Options.ROLE_ANNOUCEMENT_DURATION = 15;//20*60
	}
	
}
