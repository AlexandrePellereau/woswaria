package fr.firedragonalex.livetiktokonsign;

import java.io.File;
import java.io.IOException;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Command implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) return false;
		Player player = (Player)sender;
		if (args.length == 2) {
			//Process process = Runtime.getRuntime().exec("cmd /c start C:\\test.bat");
			try {
				Main.process = Runtime.getRuntime().exec("py plugins/TiktokAPI.py "+args[0]);//, null, new File("c:\\program files\\test\\"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			Main.scriptPython = new ScriptPython("TiktokAPI");
//			Main.scriptPython.runScript(args[0]);
//			Main.scriptPython.finish();
			this.spawnSign(player, args[1]);
		}
		if (args.length == 1) {
			if (Main.process == null) {
				player.sendMessage("§cYou must say your tiktok username at the first time.");
			} else {
				this.spawnSign(player, args[0]);
			}
		}
		return false;
	}
	
	private void spawnSign(Player player, String argument) {
		if (SignType.valueOf(argument.toUpperCase()) == null) {
			player.sendMessage("§c Error !");
		} else {
			Block block = player.getLocation().getBlock();
			block.setType(Material.OAK_SIGN);
			Main.allSigns.put((Sign)block.getState(), SignType.valueOf(argument.toUpperCase()));
		}
	}
}
