package fr.firedragonalex.areaplugin;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import fr.firedragonalex.areaplugin.area.Area;

public abstract class AreaEvent {
	
	private static boolean isCancelled;
	private static String cancelMessage;
	private static List<AreaEvent> eventsRegistered;
	
	public static void setDefaultValues() {
		AreaEvent.isCancelled = false;
		//AreaEvent.cancelMessage = "§cUn autre plugin a empêché la création de cette zone !";
		AreaEvent.eventsRegistered = new ArrayList<>();
	}
	
	public static boolean isCancelled() {
		return AreaEvent.isCancelled;
	}

	public static void setCancelled(boolean cancel) {
		AreaEvent.isCancelled = cancel;
	}
	
	public static void setCancelled(boolean cancel, String cancelMessage) {
		AreaEvent.isCancelled = cancel;
		AreaEvent.cancelMessage = cancelMessage;
	}
	
	public static void sendMessageCancel(Player player) {
		player.sendMessage(AreaEvent.cancelMessage);
	}
	
	public static void registerEvent(AreaEvent areaEvent) {
		AreaEvent.eventsRegistered.add(areaEvent);
	}
	
	public static boolean callCreateAreaEvent(Area area) {
		AreaEvent.isCancelled = false;
		AreaEvent.cancelMessage = "§cUn autre plugin a empêché la création de cette zone !";
		for (AreaEvent areaEvent : AreaEvent.eventsRegistered) {
			areaEvent.onCreateAreaEvent(area);
		}
		return AreaEvent.isCancelled;
	}
	
	public static boolean callBuyAreaEvent(Area area, Player buyer, Inventory price) {
		AreaEvent.isCancelled = false;
		AreaEvent.cancelMessage = "§cUn autre plugin a empêché l'achât de cette zone !";
		for (AreaEvent areaEvent : AreaEvent.eventsRegistered) {
			areaEvent.onBuyAreaEvent(area, buyer, price);
		}
		return AreaEvent.isCancelled;
	}
	
	public abstract void onCreateAreaEvent(Area area);
	
	public abstract void onBuyAreaEvent(Area area, Player buyer, Inventory price);
}
