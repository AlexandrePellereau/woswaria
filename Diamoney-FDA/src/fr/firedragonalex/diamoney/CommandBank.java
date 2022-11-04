package fr.firedragonalex.diamoney;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandBank implements CommandExecutor {
	
	private Main main;

	public CommandBank(Main main) {
		this.main = main;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] arg3) {
		if (!(sender instanceof Player)) return false;
		Player player = (Player)sender;
		main.openGui(player);
		return true;
	}
}
