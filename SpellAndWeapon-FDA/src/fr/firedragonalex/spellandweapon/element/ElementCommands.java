package fr.firedragonalex.spellandweapon.element;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.firedragonalex.spellandweapon.Main;

public class ElementCommands implements CommandExecutor{
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player)sender;
			if (cmd.getName().equalsIgnoreCase("setelement")) {
				//try {
					ElementType elementType = ElementType.valueOf(args[0].toUpperCase());
					int power = Integer.valueOf(args[1]);
					Main.getCustomPlayerByPlayer(player).addElement(new Element(elementType, power));
				//} catch (Exception e) {
				//}
				
				
			}
			
		}
		return false;
	}

}
