package fr.firedragonalex.spellandweapon.showdamage;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import fr.firedragonalex.spellandweapon.Main;

public class ShowDamage {
	
	private static List<TempHologram> allDamages;
	
	public static void addDamage(Double nbDamage, String color, Location coordinates) {
		TempHologram theHologram = new TempHologram(nbDamage, color, coordinates);
		theHologram.runTaskTimer(Main.getInstance(), 0, 20);
		ShowDamage.allDamages.add(theHologram);
		return;
	}
	
	public static void clear() {
		for (TempHologram tempHologram : ShowDamage.allDamages) {
			tempHologram.removeArmorStand();
		}
	}

	public static void defaultValues() {
		ShowDamage.allDamages = new ArrayList<>();
	}
}
