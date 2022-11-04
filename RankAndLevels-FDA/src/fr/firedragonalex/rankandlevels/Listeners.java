package fr.firedragonalex.rankandlevels;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.firedragonalex.cities.City;
import fr.firedragonalex.rankandlevels.rank.Rank;
import fr.firedragonalex.rankandlevels.saveandload.LoadRanks;
import fr.firedragonalex.rankandlevels.saveandload.SaveAndLoad;
import fr.firedragonalex.spellandweapon.custom.code.CustomPlayer;

public class Listeners implements Listener {
	
	private static HashMap<Material, ChatColor> bannerToChatColor;
	
	public Listeners() {
		bannerToChatColor = new HashMap<>();
		bannerToChatColor.put(Material.WHITE_BANNER, ChatColor.WHITE);
		bannerToChatColor.put(Material.BLACK_BANNER, ChatColor.BLACK);
		bannerToChatColor.put(Material.BLUE_BANNER, ChatColor.DARK_BLUE);
		bannerToChatColor.put(Material.BROWN_BANNER, ChatColor.DARK_GRAY);
		bannerToChatColor.put(Material.CYAN_BANNER, ChatColor.BLUE);
		bannerToChatColor.put(Material.GRAY_BANNER, ChatColor.GRAY);
		bannerToChatColor.put(Material.GREEN_BANNER, ChatColor.DARK_GREEN);
		bannerToChatColor.put(Material.LIGHT_BLUE_BANNER, ChatColor.BLUE);
		bannerToChatColor.put(Material.LIGHT_GRAY_BANNER, ChatColor.GRAY);
		bannerToChatColor.put(Material.LIME_BANNER, ChatColor.GREEN);
		bannerToChatColor.put(Material.MAGENTA_BANNER, ChatColor.LIGHT_PURPLE);
		bannerToChatColor.put(Material.ORANGE_BANNER, ChatColor.RED);
		bannerToChatColor.put(Material.PINK_BANNER, ChatColor.LIGHT_PURPLE);
		bannerToChatColor.put(Material.PURPLE_BANNER, ChatColor.DARK_PURPLE);
		bannerToChatColor.put(Material.RED_BANNER, ChatColor.DARK_RED);
		bannerToChatColor.put(Material.YELLOW_BANNER, ChatColor.YELLOW);
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		LoadRanks.loadPlayer(event.getPlayer());
		SaveAndLoad.loadLevel(event.getPlayer().getUniqueId());
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		SaveAndLoad.saveLevel(event.getPlayer().getUniqueId());
	}
	
	@EventHandler
	public void onPlayerExpChangeEvent(PlayerExpChangeEvent event) {
		event.setAmount(0);
	}
	
	@EventHandler
	public void onPlayerChangeLevel(PlayerLevelChangeEvent event) {
		CustomPlayer customPlayer = fr.firedragonalex.spellandweapon.Main.getCustomPlayerByPlayer(event.getPlayer());
		customPlayer.getPlayer().sendTitle("§aFélicitations !", "§aVous êtes maintenant niveau "+customPlayer.getLevel()+" !", 20, 20*3, 20);
		customPlayer.getPlayer().sendMessage("§aFais /levelrewards pour récupérer ta récompense de niveau !");
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		if (event.isCancelled()) return;
		
		Player player = event.getPlayer();
		City city = fr.firedragonalex.cities.Main.getNationality(player);
		String cityName;
		if (city == null) {
			cityName = ChatColor.BLUE+"Woswaria";
		} else {
			cityName = bannerToChatColor.get(city.getBanner().getType()) + city.getName();
		}
		Rank rank = Main.getRank(event.getPlayer());
		event.setFormat(rank.getColor()+"["+rank.getName()+" de "+cityName+rank.getColor()+"] "+event.getPlayer().getName()+" : %2$s");
//		if (rank != null) {
//			event.setFormat(rank.getColor()+"["+rank.getName()+" de "+ChatColor.BLUE+"Example"+rank.getColor()+"] "+event.getPlayer().getName()+" : %2$s");
//		} else {
//			event.getPlayer().sendMessage("Tu n'as pas de grade (même celui de joueur normal) donc tu ne peux parler dans le chat. S'il vous plait, contacter un administarteur.");
//		}
	}
	
}
