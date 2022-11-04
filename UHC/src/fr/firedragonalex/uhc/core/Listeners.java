package fr.firedragonalex.uhc.core;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import fr.firedragonalex.uhc.core.langague.Translate;
import fr.firedragonalex.uhc.core.runnable.LauchFireworks;
import fr.firedragonalex.uhc.core.runnable.ShutdownLater;
import fr.firedragonalex.uhc.specific.ModdedItem;

public class Listeners implements Listener {
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if (GameManager.getGameState() == GameState.WAITING_PLAYERS) {
			GameManager.addPlayer(player);
			
			player.teleport(new Location(
					Bukkit.getWorld(Options.SPAWN_POINT_LOBBY_WORLD), 
					Options.SPAWN_POINT_LOBBY_X, Options.SPAWN_POINT_LOBBY_Y, Options.SPAWN_POINT_LOBBY_Z));
			player.setGameMode(GameMode.SURVIVAL);
			player.setHealth(20);
			player.setFoodLevel(20);
			player.setSaturation(0);
			player.getInventory().clear();
			
			for (PotionEffect effect : player.getActivePotionEffects()) {
				player.removePotionEffect(effect.getType());
			}
			
			event.setJoinMessage(ChatColor.YELLOW+player.getDisplayName()+ChatColor.YELLOW+" "+Translate.PLAYER_JOIN_GAME.getString()+" ("+GameManager.getAllPlayers().size()+"/"+Options.STARTING_PLAYER_NUMBER+")");
			
			GameManager.checkGameStart();
		} else {
			event.setJoinMessage(null);
			player.kickPlayer(ChatColor.YELLOW+Translate.GAME_ALREADY_START.getString());
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		if (GameManager.getGameState() == GameState.WAITING_PLAYERS) {
			GameManager.getAllPlayers().remove(player);
			Bukkit.broadcastMessage(ChatColor.YELLOW+player.getDisplayName()+ChatColor.YELLOW+" "+Translate.PLAYER_QUIT_GAME.getString()+" ("+GameManager.getAllPlayers().size()+"/"+Options.STARTING_PLAYER_NUMBER+")");
			return;
		}
		if (GameManager.getGameState() == GameState.ENDING) {
			GameManager.getAllPlayers().remove(player);
			Bukkit.shutdown();
			return;
		}
	}
	
	@EventHandler
	public void onEnityDamage(EntityDamageEvent event) {
		if (!(event.getEntity() instanceof Player)) return;
		if (GameManager.getGameState() == GameState.PLAYING_PVP_OFF || GameManager.getGameState() == GameState.PLAYING) return;
		event.setCancelled(true);
	}
	
	@EventHandler
	public void onEnityDamageByEntity(EntityDamageByEntityEvent event) {
		if (GameManager.getGameState() != GameState.PLAYING_PVP_OFF) return;
		
		Entity entity = event.getEntity();
		Entity damager = event.getDamager();
		
		if (!(entity instanceof Player)) return;
		
		if (damager instanceof Player || (damager instanceof Arrow && ((Arrow)damager).getShooter() instanceof Player)) {
			event.setCancelled(true);
		}
		
	}
	
	@EventHandler
	public void onRegeneration(EntityRegainHealthEvent event) {
		if (event.getEntity() instanceof Player && GameManager.getGameState() == GameState.ENDING) event.setCancelled(true);
	}
	
	@EventHandler
	public void onPlaceBlock(BlockPlaceEvent event) {
		if (Arrays.asList(GameState.PLAYING_INVICIBLE, GameState.PLAYING_PVP_OFF, GameState.PLAYING).contains(GameManager.getGameState())) return;
		event.setCancelled(true);
	}
	
	@EventHandler
	public void onBreakBlock(BlockBreakEvent event) {
		if (Arrays.asList(GameState.PLAYING_INVICIBLE, GameState.PLAYING_PVP_OFF, GameState.PLAYING).contains(GameManager.getGameState())) return;
		event.setCancelled(true);
	}
	
	@EventHandler
	public void onBreakBlockNotCancel(BlockBreakEvent event) {
		if (event.isCancelled()) return;
		Block block = event.getBlock();
		
		if (Options.IS_THERE_INSTANT_SMELT_ORE) {
			if (block.getType() == Material.IRON_ORE) block.getLocation().getWorld().dropItem(block.getLocation(), new ItemStack(Material.IRON_INGOT));
			if (block.getType() == Material.GOLD_ORE) block.getLocation().getWorld().dropItem(block.getLocation(), new ItemStack(Material.GOLD_INGOT));
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.LOW)
	public void onDeath(EntityDamageEvent event) {
		if (event.isCancelled()) return;
		if (! (event.getEntity() instanceof Player)) return;
		
		Player player = (Player)event.getEntity();
		if (! (player.getHealth() - event.getDamage() <= 0)) return;
		event.setCancelled(true);
		
		if (! Arrays.asList(GameState.PLAYING_PVP_OFF, GameState.PLAYING).contains(GameManager.getGameState())) return;
		
		
		player.playSound(player.getLocation(), Sound.WITHER_SPAWN, 1, 0);
		player.sendTitle(ChatColor.RED+Translate.YOU_DIED.getString(), null);
		player.setGameMode(GameMode.SPECTATOR);
		Bukkit.broadcastMessage(ChatColor.RED+player.getDisplayName()+Translate.DEATH_MESSAGE.getString());
		if (Options.IS_THERE_ROLE) Bukkit.broadcastMessage(ChatColor.RED+"Son role �tait "+GameManager.getPlayersToRole().get(player).getName());
		
		
		GameManager.removePlayer(player);
		
		if (GameManager.isWin()) {
			GameManager.setGameState(GameState.ENDING);
			for (Player winner : GameManager.getAllPlayers()) {
				new LauchFireworks(25, winner).runTaskTimer(Main.getInstance(), 0, 4);
				winner.sendTitle(ChatColor.YELLOW+Translate.VICTORY.getString(), null);
			}
			new ShutdownLater().runTaskLater(Main.getInstance(), Options.VICTORY_DURATION*20);
		}
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		if (!(event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_AIR) && event.isCancelled()) return;
		ItemStack item = event.getItem();
		Player player = event.getPlayer();
		if (item == null) return;
		if (! item.hasItemMeta()) return;

		if (ModdedItem.isItModdedItem(item)) {
			event.setCancelled(true);
			ModdedItem moddedItem = ModdedItem.getModdedItem(item);
			if (moddedItem.canUse(player)) {
				if (moddedItem.hasNoCoolwdown(player)) {
					moddedItem.use(player, item);
				} else {
					player.sendMessage(ChatColor.YELLOW+Translate.COOL_DOWN.getString().replace("{number}", ""+moddedItem.getCoolDown(player)));
				}
			} else {
				player.sendMessage(ChatColor.RED+Translate.CANT_USE_ITEM.getString());
			}
		}

	}
}
