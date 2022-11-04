package fr.firedragonalex.spellandweapon.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.util.Vector;

import fr.firedragonalex.spellandweapon.custom.easytoadd.Craft;
import fr.firedragonalex.spellandweapon.gui.Gui;
import fr.firedragonalex.spellandweapon.quests.QuestGui;
import fr.firedragonalex.spellandweapon.woswaria.WoswariaGui;

public class Commands implements CommandExecutor{
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] arguments) {
		if (!(sender instanceof Player)) return false;
		Player player = (Player)sender;
		switch (cmd.getName()) {
		case "quests":
			QuestGui.startGui_Main(player);
			break;
		case "giveentityremover":
			player.getInventory().addItem(Craft.customItem(Material.BONE, ChatColor.RED+"EntityRemover"));
			break;
		case "resourcepack":
			WoswariaGui.startGui_ResourcePack(player);
			break;
		case "armor":
			Gui.startGui_Armor(player);
			break;
		default:
			player.sendMessage(ChatColor.RED+cmd.getUsage());
			break;
		}
		return true;
	}

}
