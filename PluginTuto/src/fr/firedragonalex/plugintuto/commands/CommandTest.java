package fr.firedragonalex.plugintuto.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CommandTest implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		//sender.sendMessage("Bravo, tu as réussit test.");
		if (sender instanceof Player) {
			Player player = (Player)sender;
			if (cmd.getName().equalsIgnoreCase("test")) {
				player.sendMessage("§eBravo, tu as réussit le test.");
				return true;
			}
			if (cmd.getName().equalsIgnoreCase("alert")) {
				if (args.length == 0) {
					player.sendMessage("Tu ne peux pas envoyer un broadcast vide !");
					player.sendMessage("Utilise : /alert <message>");
				}
				if (args.length >= 1) {
					StringBuilder broadcast = new StringBuilder();
					for(String part : args) {
						broadcast.append(part + " ");
					}
					Bukkit.broadcastMessage("§e["+player.getName()+"] " + broadcast.toString());
				}
			}
			if (cmd.getName().equalsIgnoreCase("getstick")) {
				player.sendMessage("Préparation du baton !");
				ItemStack myCustomItem = new ItemStack(Material.STICK,1);
				ItemMeta customM = myCustomItem.getItemMeta();
				StringBuilder nameStick = new StringBuilder();
				for(String part : args) {
					nameStick.append(part + "");
				}
				customM.setDisplayName(nameStick.toString());
				player.sendMessage(nameStick.toString());
				//customM.setLore(Arrays.asList("Right click if want to use this item","Speed II 30s","Haste I 30s","Weakness I 30s"));
				customM.addEnchant(Enchantment.DURABILITY, 0, true);
				customM.addItemFlags(ItemFlag.HIDE_ENCHANTS);
				myCustomItem.setItemMeta(customM);
				player.getInventory().addItem(myCustomItem);
				
				player.updateInventory();
			}
			if (cmd.getName().equalsIgnoreCase("roles")) {
				//player.sendTitle("Hello!", "This is a test.", 1, 20*3, 1);
				List<Player> MylistOfPlayers = new ArrayList<>(Bukkit.getOnlinePlayers());
			    Random rand = new Random();
			    Player assassin = MylistOfPlayers.get(rand.nextInt(MylistOfPlayers.size()));
			    for (Player MyPlayer : MylistOfPlayers) {
			    	//MyPlayer.removePotionEffect();
			    	MyPlayer.setGameMode(GameMode.ADVENTURE);
			    	MyPlayer.getInventory().clear();
			    	//MyPlayer.getInventory().addItem(new ItemStack(Material.EMERALD,32));
			    	MyPlayer.getInventory().addItem(new ItemStack(Material.STONE_SWORD,1));
			    	MyPlayer.getInventory().setHelmet(new ItemStack(Material.CHAINMAIL_HELMET,1));
			    	MyPlayer.getInventory().setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE,1));
			    	MyPlayer.getInventory().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS,1));
			    	MyPlayer.getInventory().setBoots(new ItemStack(Material.CHAINMAIL_BOOTS,1));
					MyPlayer.updateInventory();
			    	MyPlayer.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION,20*300,0));
			    	MyPlayer.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION,20*1,255));
			    	MyPlayer.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING,20*1,255));
			    	MyPlayer.addPotionEffect(new PotionEffect(PotionEffectType.HEAL,20*1,100));
			    	MyPlayer.teleport(new Location(Bukkit.getWorld("world"), 13, 5, 8));
			        if(MyPlayer!=assassin) {
			        	MyPlayer.sendMessage("§9Vous êtes un policier !");
			        	MyPlayer.sendTitle("§9Vous êtes un policier !", "§9Vous devez tuer l'assassin pour gagner !", 1, 20*3, 1);
						ItemStack myCustomItem2 = new ItemStack(Material.STICK,1);
						ItemMeta customM2 = myCustomItem2.getItemMeta();
						customM2.setDisplayName("Baton du policier");
						myCustomItem2.setItemMeta(customM2);
						MyPlayer.getInventory().addItem(myCustomItem2);
						MyPlayer.updateInventory();
			        }
			    }
			    assassin.sendMessage("§cVous êtes l'assassin !");
			    assassin.sendTitle("§cVous êtes l'assassin !", "§cVous devez tuer tous les policiers pour gagner !", 1, 20*3, 1);
			    //stick de l'assasin///////////////////////////////////////////
				ItemStack myCustomItem3 = new ItemStack(Material.STICK,1);
				assassin.getInventory().addItem(myCustomItem3);
				ItemStack myCustomItem = new ItemStack(Material.STICK,1);
				ItemMeta customM = myCustomItem.getItemMeta();
				customM.setDisplayName("Baton de l'assassin");
				customM.addEnchant(Enchantment.KNOCKBACK, 2, true);
				myCustomItem.setItemMeta(customM);
				assassin.getInventory().addItem(myCustomItem);
				assassin.updateInventory();
				///////////////////////////////////////////////////////////////
			}
			return true;
		}
		return false;
	}
}