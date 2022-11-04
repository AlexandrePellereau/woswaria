package fr.firedragonalex.zombieapocalypsepvpve.listeners;


import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.firedragonalex.zombieapocalypsepvpve.FMain;
import fr.firedragonalex.zombieapocalypsepvpve.FState;
import fr.firedragonalex.zombieapocalypsepvpve.tasks.FAutostart;

public class FPlayerListeners implements Listener {
	
	private FMain main;

	public FPlayerListeners(FMain fMain) {
		this.main = fMain;
	}
	
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
//		//Bukkit.getWorld("world").
//		//Villager villager = new Entity(new Location(Bukkit.getWorld("world"), 0, 5, 0), EntityType.VILLAGER);
//		Entity villager = Bukkit.getWorld("world").spawnEntity(new Location(Bukkit.getWorld("world"), 0, 5, 0), EntityType.VILLAGER);
//		Villager myVillager = (Villager)villager;
//		myVillager.setCustomName("test");
//		myVillager.setCustomNameVisible(true);
//		myVillager.setProfession(Profession.LIBRARIAN);
//		MerchantRecipe myFirstTrade = new MerchantRecipe(new ItemStack(Material.ACACIA_BOAT,1),10);
//		myFirstTrade.addIngredient(new ItemStack(Material.ACACIA_BUTTON,1));
//		myVillager.getRecipes().add(myFirstTrade);
//		//myVillager.setRecipe(1, new MerchantRecipe(new ItemStack(Material.ACACIA_BOAT,1),9999999));
//		myVillager.setCustomName("test2");
//		myVillager.setCustomNameVisible(true);
		Player player = event.getPlayer();
		player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION,20*3600,0));
		main.getBossBarPeriod().addPlayer(player);
		main.getBossBarNbZombies().addPlayer(player);

		
		if (main.isState(FState.WAITING) && !main.getPlayers().contains(player)) {
			Location spawn = new Location(main.getWorld(), 0, 35, 0);
			player.setGameMode(GameMode.SURVIVAL);
			player.teleport(spawn);
			player.getInventory().clear();
			player.setFoodLevel(20);
			player.setHealth(20);
			main.getPlayers().add(player);
			event.setJoinMessage("§e"+player.getName()+" a rejoint la partie ! §c("+main.getPlayers().size()+"/"+main.getNbPlyerForStart()+")");
		}
		
		if (main.isState(FState.PLAYING)) {
			if (main.getPlayers().contains(player)) {
				player.sendMessage("[Server] Je te conseille de rester connecté !");
			}else {
				//player.kickPlayer("Une partie est déjà en cours !");
				//return;
			}
		}
		
		if (main.isState(FState.WAITING) && main.getPlayers().size() == main.getNbPlyerForStart()) {
			FAutostart start = new FAutostart(main);
			main.setNbPlayersAtStart(main.getPlayers().size());
			start.runTaskTimer(main, 0, 20);
			main.setState(FState.STARTING);
		}
//		System.out.println("test1");
//		main.getBossBar().setVisible(true);
//		System.out.println("test2");
//		main.getBossBar().addPlayer(player);
//		System.out.println("test3");
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		if (main.getPlayers().contains(player)) {
			event.setQuitMessage("§e"+player.getName()+" a quitté la partie ! :(");
		}else {
			event.setQuitMessage(null);
		}
		main.checkWin();
	}
	
	@EventHandler
	public void onPlace(BlockPlaceEvent event) {
		if (!event.getPlayer().isOp()) {
			if (!main.isState(FState.PLAYING)) {
				event.getPlayer().sendMessage("§cTu ne peux pas poser des blocks ici !");
				event.setCancelled(true);
				return;
			}
		}
	}
	
	@EventHandler
	public void onBreak(BlockBreakEvent event) {
		if (!event.getPlayer().isOp()) {
			if (!main.isState(FState.PLAYING)) {
				event.getPlayer().sendMessage("§cTu ne peux pas casser des blocks ici !");
				event.setCancelled(true);
				return;
			}
		}
	}

}
