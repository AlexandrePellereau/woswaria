package fr.firedragonalex.spellandweapon.woswaria;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.Inventory;

import fr.firedragonalex.spellandweapon.custom.easytoadd.Craft;

public class ListenerPortals implements Listener {
	
	@EventHandler
	public void onPortalPlayer(PlayerPortalEvent event) {//PortalCreateEvent
		event.setCancelled(true);
		ListenerPortals.openGuiTpWorld(event.getPlayer());
		event.getPlayer().teleport(new Location(Bukkit.getWorld("WoswariaBetweenWorld"), 0.5, 2, 0.5), TeleportCause.PLUGIN);
	}
	
	public static void openGuiTpWorld(Player player) {

		int lines = 1;
		Inventory gui = Bukkit.createInventory(null, lines*9, ChatColor.DARK_BLUE+"WoswariaGUI_Portal");
		
		for (int i = 0; i < lines*9; i++) {
			gui.setItem(i,Craft.customItem(Material.BLACK_STAINED_GLASS_PANE, "§"));
		}
		
		gui.setItem(0,Craft.customItem(Material.BRICKS,
				ChatColor.YELLOW+"Aller au centre du monde Woswaria",Arrays.asList(
				ChatColor.LIGHT_PURPLE+"Overlord normal, les zones entre les villes sont incassables",
				ChatColor.LIGHT_PURPLE+"Pvp désactivé",
				ChatColor.LIGHT_PURPLE+"Ici tu peux acheter une maison ou créer une ville.")));
		gui.setItem(1,Craft.customItem(Material.GRASS_BLOCK,
				ChatColor.YELLOW+"Aller au centre de l'overworld (monde normal)",Arrays.asList(
				ChatColor.LIGHT_PURPLE+"Nether normal,vanilla.(Reset toute les semaines)",
				ChatColor.LIGHT_PURPLE+"Pvp désactivé",
				ChatColor.LIGHT_PURPLE+"Ici tu peux récolter les ressources du nether.")));
		gui.setItem(2,Craft.customItem(Material.NETHERRACK,
				ChatColor.YELLOW+"Aller au centre du nether",Arrays.asList(
				ChatColor.LIGHT_PURPLE+"Nether normal,vanilla.(Reset toute les semaines)",
				ChatColor.LIGHT_PURPLE+"Pvp désactivé",
				ChatColor.LIGHT_PURPLE+"Ici tu peux récolter les ressources du nether.")));
		gui.setItem(3,Craft.customItem(Material.COBBLESTONE,
				ChatColor.YELLOW+"Aller à la carrière de pierre",Arrays.asList(
				ChatColor.LIGHT_PURPLE+"Map custom incassable",
				ChatColor.LIGHT_PURPLE+"Pvp désactivé",
				ChatColor.LIGHT_PURPLE+"Ici tu peux combattre les monstres :",
				ChatColor.LIGHT_PURPLE+"- Golem de pierre (niveau 1-10)",
				ChatColor.LIGHT_PURPLE+"- Golem de roche (niveau 1-10)",
				ChatColor.LIGHT_PURPLE+"Ici tu peux récupérer les ressources :",
				ChatColor.LIGHT_PURPLE+"- Pierre",
				ChatColor.LIGHT_PURPLE+"- Cobblestone",
				ChatColor.LIGHT_PURPLE+"- Cobblestone moussue",
				ChatColor.LIGHT_PURPLE+"- Cobblestone condensée")));
		gui.setItem(4,Craft.customItem(Material.IRON_SWORD,
				ChatColor.YELLOW+"Aller à la zone de combat",Arrays.asList(
				ChatColor.LIGHT_PURPLE+"Map custom incassable",
				ChatColor.LIGHT_PURPLE+"Pvp activé",
				ChatColor.LIGHT_PURPLE+"Ici tu peux te battre contre des joueurs.")));
		gui.setItem(4,Craft.customItem(Material.BEDROCK,
				ChatColor.YELLOW+"Aller à la zone de test",Arrays.asList(
				ChatColor.LIGHT_PURPLE+"Map custom incassable",
				ChatColor.LIGHT_PURPLE+"Pvp désactivé",
				ChatColor.LIGHT_PURPLE+"Ici tu peux voir les dieuveloppeurs créer",
				ChatColor.LIGHT_PURPLE+"et tester les nouvelles fonctionnalités")));
		player.openInventory(gui);
	}
}
