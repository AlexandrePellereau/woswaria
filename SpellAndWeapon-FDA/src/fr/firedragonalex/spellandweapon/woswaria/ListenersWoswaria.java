package fr.firedragonalex.spellandweapon.woswaria;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.world.PortalCreateEvent;
import org.bukkit.inventory.ItemStack;

import fr.firedragonalex.spellandweapon.Main;
import fr.firedragonalex.spellandweapon.alexlibrairy.UsefulFunctions;
import fr.firedragonalex.spellandweapon.custom.code.custommonsterspawner.CustomMonsterSpawner;
import fr.firedragonalex.spellandweapon.custom.easytoadd.Craft;
import fr.firedragonalex.spellandweapon.custom.easytoadd.CustomArmor;
import fr.firedragonalex.spellandweapon.custom.easytoadd.CustomMonsterType;
import fr.firedragonalex.spellandweapon.quests.Quest;

public class ListenersWoswaria implements Listener {
	
	@EventHandler
	public void onCreaturSpawn(CreatureSpawnEvent event) {
		if (event.getSpawnReason() == SpawnReason.SPAWNER || 
			event.getSpawnReason() == SpawnReason.NATURAL ||
			event.getSpawnReason() == SpawnReason.DEFAULT) {
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onAmorClick(InventoryClickEvent event) {
		ItemStack item = event.getCursor();
		Player player = (Player)event.getWhoClicked();
		if (CustomArmor.isAnArmor(item) && event.getSlotType() == SlotType.ARMOR) {
			event.setCancelled(true);
			player.sendMessage(ChatColor.RED+"Tu dois equiper les armures avec /armor !");
			player.sendMessage(ChatColor.RED+"Ici tu peux mettre des armures ou vêtements cosmétiques !");
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBreakOre(BlockBreakEvent event) {
		if(event.getPlayer().getGameMode() == GameMode.CREATIVE) return;
		if (Woswaria.newDropOre.containsKey(event.getBlock().getType())) {
			event.setDropItems(false);
			event.setExpToDrop(0);
			event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), Woswaria.newDropOre.get(event.getBlock().getType()));
		}
	}
	
	@EventHandler
	public void onInteractMilkBucket(PlayerInteractEvent event) {
		ItemStack item = event.getItem();
		if (item != null && item.getType() == Material.MILK_BUCKET) {
			event.setCancelled(true);
			item.setType(Material.BUCKET);
			event.getPlayer().sendMessage("§cLes seaux de lait sont bloqués sur Woswaria.");
			event.getPlayer().sendMessage("§c(Pour le bon fonctionnement des éléments.)");
		}
	}
	
	@EventHandler
	public void onCreatePortal(PortalCreateEvent event) {
		Bukkit.broadcastMessage("Portal Register");
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		WoswariaGui.startGui_ResourcePack(player);
		if(player.hasPlayedBefore()) return;
		Main.getCustomPlayerByPlayer(player).addQuest(Quest.TUTORIAL);
		player.sendTitle(ChatColor.YELLOW+"Utilise /QUEST pour commencer le tutoriel", null, 10, 40, 20);
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent event) {
		String inventoryTitle = event.getView().getTitle();
		Player player = (Player)event.getWhoClicked();
		ItemStack item = event.getCurrentItem();
		if (item == null || !item.hasItemMeta()) return;
		if (inventoryTitle.equals("§1WoswariaGUI_DownloadRessourcePack")) {
			switch (item.getItemMeta().getDisplayName()) {
			case "§2Télécharger/Activer le ressource pack !":
				player.setResourcePack("https://cdn.discordapp.com/attachments/935293453389168650/981553113905377350/Official_Woswaria_Pack.zip");
				break;
			case "§aJ'ai déjà le ressource pack !":
				break;
			case "Télécharger optifine !":
				player.sendMessage(ChatColor.YELLOW+"------------------------------");
				player.sendMessage(ChatColor.YELLOW+"https://optifine.net/downloads");
				player.sendMessage(ChatColor.YELLOW+"------------------------------");
				break;
			case "§cNe pas télécharger le ressource pack !":
				break;
			default:
				return;
			}
			player.closeInventory();
		}
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (event.getView().getTitle().equals(ChatColor.DARK_BLUE+"WoswariaGUI_Portal")) {
			ItemStack item = event.getCurrentItem();
			event.setCancelled(true);
			event.getWhoClicked().closeInventory();

			((Player)event.getWhoClicked()).teleport(Woswaria.nameToLocationSpawnPoint.get(item.getItemMeta().getDisplayName()));
		}
	}
	
	@EventHandler
	public void onCraft(CraftItemEvent event) {
		if ((event.getCursor() != null && event.getCursor().getType() == Material.BARRIER) || (event.getCurrentItem() != null && event.getCurrentItem().getType() == Material.BARRIER)) {
			event.setCancelled(true);
			return;
		}
		if (event.getInventory().getItem(0) != null && !event.getInventory().getItem(0).hasItemMeta()) {
			for(int i = 1; i < 10;i++) {
				if (event.getInventory().getItem(i) != null && event.getInventory().getItem(i).hasItemMeta()) {
					event.setCancelled(true);
					event.getInventory().setItem(0, Craft.customItem(Material.BARRIER, "§cMauvaise idée"));
					return;
				}
			}
		}
	}
	
	@EventHandler
	private void onKillEntity(EntityDeathEvent event) {
		Entity entity = event.getEntity();
		if (entity instanceof Villager) {
			Villager villager = (Villager)event.getEntity();
			if (villager.getCustomName().startsWith(ChatColor.GREEN+"")) {
				villager.setHealth(villager.getAttribute(Attribute.GENERIC_MAX_HEALTH).getDefaultValue());
			}
		}
		for (LivingEntity entityLoop : Main.getAllUnkillableEntities()) {
			if (entity == entityLoop) {
				entityLoop.setHealth(entityLoop.getAttribute(Attribute.GENERIC_MAX_HEALTH).getDefaultValue());
			}
		}
	}
}
