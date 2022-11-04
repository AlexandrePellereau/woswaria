package fr.firedragonalex.zombieapocalypsepvpve.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MyCommands implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] arg3) {
		if (sender instanceof Player) {
			Player player = (Player)sender;
			player.teleport(new Location(Bukkit.getWorld("world"), 0, 32, 0));
			return true;
		}
		return false;
	}

}
