package fr.firedragonalex.spellandweapon.commands;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

import fr.firedragonalex.spellandweapon.Main;
import fr.firedragonalex.spellandweapon.custom.code.CustomEntity;
import fr.firedragonalex.spellandweapon.custom.code.CustomMonster;
import fr.firedragonalex.spellandweapon.custom.easytoadd.Craft;
import fr.firedragonalex.spellandweapon.custom.easytoadd.CustomArmor;
import fr.firedragonalex.spellandweapon.custom.easytoadd.CustomMonsterType;
import fr.firedragonalex.spellandweapon.custom.easytoadd.CustomWeapon;
import fr.firedragonalex.spellandweapon.islandgenerator.IslandGenereator;

public class CommandsAdmins implements CommandExecutor{
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] arguments) {
		if (!(sender instanceof Player)) return false;
		Player player = (Player)sender;
		switch (cmd.getName()) {
		case "summonmonster":
			Location location = null;
			int level;
			if (arguments.length == 1) {
				level = 1;
			} else if (arguments.length == 2) {
				location = player.getLocation();
				level = Integer.valueOf(arguments[1]);
			} else if (arguments.length == 5) {
				location = new Location(null, Integer.valueOf(arguments[2]), Integer.valueOf(arguments[3]), Integer.valueOf(arguments[4]));
				level = Integer.valueOf(arguments[1]);
			} else {
				break;
			}	
			CustomMonsterType customMonsterType = CustomMonsterType.valueOf(arguments[0]);
			new CustomMonster(customMonsterType, level, location);
			return true;
		case "giveweapon":
			CustomWeapon customWeapon = CustomWeapon.valueOf(arguments[0]);
			player.getInventory().addItem(customWeapon.getItem());
			player.sendMessage("§eVous avez bien reçu "+customWeapon.getName());
			return true;
		case "givearmor":
			CustomArmor customArmor = CustomArmor.valueOf(arguments[0]);
			player.getInventory().addItem(customArmor.getItem(Integer.valueOf(arguments[1])));
			player.sendMessage("§eVous avez bien reçu "+customArmor.getName());
			return true;
		case "givetransformacon":
			player.getInventory().addItem(Craft.customItem(Material.BELL, ChatColor.LIGHT_PURPLE+"Transformacon"));
			player.sendMessage("§eVous avez bien reçu un "+ChatColor.LIGHT_PURPLE+"Transformacon");
			return true;
		case "givecustommonsterspawner":
			if (arguments.length != 6) break;
			player.getInventory().addItem(Craft.customItem(
					Material.GOLDEN_HOE, 
					ChatColor.LIGHT_PURPLE+"CustomMonsterSpawner of "+CustomMonsterType.valueOf(arguments[0]),
					Arrays.asList(
							"CustomMonsterType: "+arguments[0],
							"Level: "+arguments[1],
							"NbMonsterMax: "+arguments[2],
							"TicksBetweenMonsterSpawn: "+arguments[3],
							"SpawnRadius: "+arguments[4],
							"KeepMonsterRadius: "+arguments[5])));
			return true;
		case "giveislandgenerator":
			if (arguments.length != 3) break;
			player.getInventory().addItem(Craft.customItem(
					Material.SNOWBALL, 
					ChatColor.LIGHT_PURPLE+"IslandGenerator",
					Arrays.asList(
							"Size: "+arguments[0],
							"Height: "+arguments[1],
							"Frequency: "+arguments[2])));
			return true;
		case "undoisland":
			if (arguments.length != 0 || IslandGenereator.getLastIsland() == null) break;
			for (Block block : IslandGenereator.getLastIsland().getAllBlocks()) {
				block.setType(Material.AIR);
			}
			return true;
		case "spawnnpc":
			String npcName = "";
			for (String string : arguments) {
				npcName += string + " ";
			}
			npcName = npcName.substring(0, npcName.length()-1);
			Villager pnj = (Villager)player.getWorld().spawnEntity(player.getLocation(), EntityType.VILLAGER);
			pnj.setAI(false);
			pnj.setGravity(true);
			pnj.setInvulnerable(true);
			pnj.setSilent(true);
			pnj.setCustomNameVisible(true);
			pnj.setCustomName(ChatColor.GREEN+npcName);
			return true;
		default:
			break;
		}
		player.sendMessage(ChatColor.RED+cmd.getUsage());
		return true;
	}

}
