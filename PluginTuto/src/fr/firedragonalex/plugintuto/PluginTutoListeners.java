package fr.firedragonalex.plugintuto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PluginTutoListeners implements Listener {
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		//player.getInventory().addItem(new ItemStack(Material.IRON_SWORD, 3));
		Bukkit.broadcastMessage("§e"+player.getName()+"§e est de retour dans ce serveur génial !");
		player.sendTitle("§k§eBienvenu "+player.getName()+"§e !§k", "§eAmuser vous bien !", 1, 20*3, 1);
		
		//ItemStack myCustomItem = new ItemStack(Material.SLIME_BALL,1);
		//ItemMeta customM = myCustomItem.getItemMeta();
		//customM.setDisplayName("Poison");
		//customM.setLore(Arrays.asList("Poison I","30s","Right click if want to use this item "));
		//myCustomItem.setItemMeta(customM);
		//player.getInventory().addItem(myCustomItem);
		
		//player.updateInventory();
		
		//player.getWorld().spawnEntity(player.getLocation(),EntityType.DOLPHIN);
	}
	
	@EventHandler
	public void onKill(PlayerDeathEvent event) {
		Player player = event.getEntity();
		if (player.getKiller() instanceof Player) {
			player.sendTitle("§cYou died !", "", 1, 20*10, 1);
			player.setGameMode(GameMode.SPECTATOR);
			player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION,20*1,10));
		}
		//player.kickPlayer("You died !");
		List<Player> MylistOfPlayers = new ArrayList<>(Bukkit.getOnlinePlayers());
		if (MylistOfPlayers.size()==1) {
			Bukkit.broadcastMessage("GG !");
		}
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		Action action = event.getAction();
		ItemStack it = event.getItem();
		if(it == null) return;
		if(it.getType()==Material.STICK && it.hasItemMeta() && (action==Action.RIGHT_CLICK_AIR || action==Action.RIGHT_CLICK_BLOCK)){
			if (it.getItemMeta().getDisplayName().equalsIgnoreCase("AgilityStick")) {
				player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,20*30,1));//20 tick = 1s donc 30s = 20*30
				player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING,20*30,0));
				player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS,20*30,1));
				//player.sendMessage("Click");
			}
			if(it.getItemMeta().getDisplayName().equalsIgnoreCase("ForceStick")) {
				player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE,20*10,2));//20 tick = 1s donc 30s = 20*30
				player.addPotionEffect(new PotionEffect(PotionEffectType.HARM,1,0));
				//player.sendMessage("Click");
			}
			if(it.getItemMeta().getDisplayName().equalsIgnoreCase("HealStick")) {
				player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION,20*1,10));//20 tick = 1s donc 30s = 20*30
				player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,20*30,2));
				//player.sendMessage("Click");
			}
			if(it.getItemMeta().getDisplayName().equalsIgnoreCase("FeedStick")) {
				player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION,20*1,10));//20 tick = 1s donc 30s = 20*30
				player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING,20*60,1));
				//player.sendMessage("Click");
			}
			if(it.getItemMeta().getDisplayName().equalsIgnoreCase("AquaStick")) {
				player.addPotionEffect(new PotionEffect(PotionEffectType.DOLPHINS_GRACE,20*30,0));//20 tick = 1s donc 30s = 20*30
				player.addPotionEffect(new PotionEffect(PotionEffectType.CONDUIT_POWER,20*30,3));
				player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,20*30,4));
				//player.sendMessage("Click");
			}
			if(it.getItemMeta().getDisplayName().equalsIgnoreCase("ApprenticeTurtleStick")) {
				player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,20*30,0));
				player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,20*30,0));
				//player.sendMessage("Click");
			}
			if(it.getItemMeta().getDisplayName().equalsIgnoreCase("Baton du policier")) {
				player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST,20*50,-2));
				player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION,20*20,2));
				//player.sendMessage("Click");
			}
		}
		if(it.getType()==Material.BLAZE_ROD && it.hasItemMeta() && (action==Action.RIGHT_CLICK_AIR || action==Action.RIGHT_CLICK_BLOCK)){
			if (it.getItemMeta().getDisplayName().equalsIgnoreCase("AgilityStick")) {
				player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,20*30,1));//20 tick = 1s donc 30s = 20*30
				player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING,20*30,0));
				player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS,20*30,1));
				//player.sendMessage("Click");
			}
			if(it.getItemMeta().getDisplayName().equalsIgnoreCase("ForceStick")) {
				player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE,20*10,2));//20 tick = 1s donc 30s = 20*30
				player.addPotionEffect(new PotionEffect(PotionEffectType.HARM,1,0));
				//player.sendMessage("Click");
			}
			if(it.getItemMeta().getDisplayName().equalsIgnoreCase("HealStick")) {
				player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION,20*1,10));//20 tick = 1s donc 30s = 20*30
				player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,20*30,2));
				//player.sendMessage("Click");
			}
			if(it.getItemMeta().getDisplayName().equalsIgnoreCase("FeedStick")) {
				player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION,20*1,10));//20 tick = 1s donc 30s = 20*30
				player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING,20*60,1));
				//player.sendMessage("Click");
			}
			if(it.getItemMeta().getDisplayName().equalsIgnoreCase("AquaStick")) {
				player.addPotionEffect(new PotionEffect(PotionEffectType.DOLPHINS_GRACE,20*30,0));//20 tick = 1s donc 30s = 20*30
				player.addPotionEffect(new PotionEffect(PotionEffectType.CONDUIT_POWER,20*30,3));
				player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,20*30,4));
				//player.sendMessage("Click");
			}
		}
		if(it.getType()==Material.STICK && it.hasItemMeta() && action==Action.RIGHT_CLICK_BLOCK){
			if (it.getItemMeta().getDisplayName().equalsIgnoreCase("getSeed")) {
				if (event.getClickedBlock().getType() == Material.CHEST) {
					
				}
			}
		}
	}
}
