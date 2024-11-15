package fr.dralexgon.shopasvillagerforplayers.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Villager.Profession;

public class TabCompletionSkinVillagerShop implements TabCompleter{
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String arg2, String[] arg3) {
		List<String> listStringProfession = new ArrayList<>();
		for (Profession profession : Profession.values()) {
			listStringProfession.add(profession+"");
		}
		return listStringProfession;
	}
}
