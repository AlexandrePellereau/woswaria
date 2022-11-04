package fr.firedragonalex.plugintuto.commands;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.firedragonalex.plugintuto.Main;

public class CommandChestRandom implements CommandExecutor {
	
	private Main main;

	public CommandChestRandom(Main main) {
		this.main = main;
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player)sender;
		
		if(cmd.getName().equalsIgnoreCase("chestspawn")){
			
			Random r = new Random();
			double x = r.nextInt(500);
			double y = r.nextInt(100);
			double z = r.nextInt(100);
			
			//Location spawnChest = new Location(Bukkit.getWorld("world"), x, y, z);
			Location spawnChest = player.getLocation();
		    spawnChest.getBlock().setType(Material.CHEST);
		    
		    Chest chest = (Chest) spawnChest.getBlock().getState();
		    Inventory chestMenu = chest.getInventory();
		    
		    for (int i = 0; i < 3; i++) {
		    
		    if(Math.random() * 100 < 30)
		    {
		    	chestMenu.setItem(r.nextInt(29), new ItemStack(Material.FEATHER, r.nextInt(64)));
		    }
		    if(Math.random() * 100 < 30)
		    {
		    	chestMenu.setItem(r.nextInt(29), new ItemStack(Material.SUGAR, r.nextInt(64)));
		    }
		    if(Math.random() * 100 < 30)
		    {
		    	chestMenu.setItem(r.nextInt(29), new ItemStack(Material.BEETROOT, r.nextInt(64)));
		    }
		    if(Math.random() * 100 < 30)
		    {
		    	chestMenu.setItem(r.nextInt(29), new ItemStack(Material.BLAZE_POWDER, r.nextInt(64)));
		    }
		    if(Math.random() * 100 < 30)
		    {
		    	chestMenu.setItem(r.nextInt(29), new ItemStack(Material.GLISTERING_MELON_SLICE, r.nextInt(64)));
		    }
		    if(Math.random() * 100 < 30)
		    {
		    	chestMenu.setItem(r.nextInt(29), new ItemStack(Material.FIRE_CHARGE, r.nextInt(64)));
		    }
		    if(Math.random() * 100 < 50)
		    {
		    	chestMenu.setItem(r.nextInt(29), new ItemStack(Material.STICK, r.nextInt(64)));
		    }
		    if(Math.random() * 100 < 30)
		    {
		    	chestMenu.setItem(r.nextInt(29), new ItemStack(Material.SWEET_BERRIES, r.nextInt(64)));
		    }
		    if(Math.random() * 100 < 30)
		    {
		    	chestMenu.setItem(r.nextInt(29), new ItemStack(Material.PRISMARINE_SHARD, r.nextInt(64)));
		    }
		    if(Math.random() * 100 < 30)
		    {
		    	chestMenu.setItem(r.nextInt(29), new ItemStack(Material.PRISMARINE_CRYSTALS, r.nextInt(64)));
		    }
		    
		    
		    
		    
		    
		    
		    
		    
		    
		    
		    }
		    Bukkit.broadcastMessage("un coffre a spawn en x:"+x+" y:"+y+" z:"+z);
			
			return true;
		}
		
		return false;
	}
		return false;
	}

}
