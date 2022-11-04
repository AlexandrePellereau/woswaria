package fr.firedragonalex.livetiktokonsign;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class TabCompleter implements org.bukkit.command.TabCompleter{

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		List<String> listStringOutput = new ArrayList<>();
		for (SignType signType : SignType.values()) {
			if (signType.toString().contains(args[args.length-1].toUpperCase())) {
				listStringOutput.add(signType+"");
			}
		}
		return listStringOutput;
	}

}
