package fr.firedragonalex.cpscheck;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {
	
	private Main main;
	
	public Commands(Main main) {
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player)sender;
			try {
				Player target = Bukkit.getPlayer(args[0]);
				main.newCpsPrinter(player,target);
			} catch (Exception e) {
				player.sendMessage("§cTu dois cibler un joueur.");
			}
		}
		return false;
	}

}
