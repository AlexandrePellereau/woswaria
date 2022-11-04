package fr.firedragonalex.spellandweapon.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import fr.firedragonalex.spellandweapon.custom.easytoadd.CustomArmor;
import fr.firedragonalex.spellandweapon.custom.easytoadd.CustomMonsterType;
import fr.firedragonalex.spellandweapon.custom.easytoadd.CustomWeapon;
import fr.firedragonalex.spellandweapon.quests.Quest;

public class TabCompleterCommands implements TabCompleter{
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		List<String> listStringOutput = new ArrayList<String>();
		switch (cmd.getName()) {
		case "giveweapon":
			for (CustomWeapon customWeapon : CustomWeapon.values()) {
				if (customWeapon.toString().contains(args[args.length-1].toUpperCase())) {
					listStringOutput.add(customWeapon+"");
				}
			}
			break;
		case "givearmor":
			for (CustomArmor customArmor : CustomArmor.values()) {
				if (customArmor.toString().contains(args[args.length-1].toUpperCase())) {
					listStringOutput.add(customArmor+"");
				}
			}
			break;
		case "summonmonster":
			if (args.length == 1) {
				for (CustomMonsterType customMonsterType : CustomMonsterType.values()) {
					if (customMonsterType.toString().contains(args[args.length-1].toUpperCase())) {
						listStringOutput.add(customMonsterType.toString());
					}
				}
			}
		case "givecustomentityspawner":
			for (CustomMonsterType customMonsterType : CustomMonsterType.values()) {
				if (customMonsterType.toString().contains(args[args.length-1].toUpperCase())) {
					listStringOutput.add(customMonsterType.toString());
				}
			}
			break;
		case "givecustommonsterspawner":
			if (args.length != 1) return Arrays.asList("number");
			for (CustomMonsterType customMonsterType : CustomMonsterType.values()) {
				if (customMonsterType.toString().contains(args[args.length-1].toUpperCase())) {
					listStringOutput.add(customMonsterType.toString());
				}
			}
			break;
//		case "spawnnpc":
//			if (args.length == 1) {
//				for (Quest quest : Quest.values()) {
//					if (quest.toString().contains(args[args.length-1].toUpperCase())) {
//						listStringOutput.add(quest.toString());
//					}
//				}
//			}
//			break;
		default:
			break;
		}
		return listStringOutput;
	}
}
