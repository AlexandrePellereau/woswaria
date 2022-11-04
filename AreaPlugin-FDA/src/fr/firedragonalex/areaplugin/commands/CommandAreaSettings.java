package fr.firedragonalex.areaplugin.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.firedragonalex.areaplugin.MainAreaPlugin;

public class CommandAreaSettings implements CommandExecutor {
	
	private MainAreaPlugin mainAreaPlugin;
	
	public CommandAreaSettings(MainAreaPlugin mainAreaPlugin) {
		this.mainAreaPlugin = mainAreaPlugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		// TODO Auto-generated method stub
		if (sender instanceof Player) {
			
			//List<String> ListTempVarToDelete = Arrays.asList("NewMate","RemoveMate","AreaSelected","RenameArea","InventoryContentBeforeSell");
			Player player;
			
			if (args.length == 0) {
				player = (Player)sender;
			} else if (args.length == 1) {
				sender.sendMessage("Soon...");
				return true;
			} else {
				sender.sendMessage("§c"+mainAreaPlugin.getCommand("areasettings").getUsage());
				return true;
			}
			
			mainAreaPlugin.removeAllTempVariablesToThisPlayer(player);
			
			mainAreaPlugin.getGui().startAreaGUI_ChoiceOpOrNot(player);
			return true;
		}
		return false;
	}
}
