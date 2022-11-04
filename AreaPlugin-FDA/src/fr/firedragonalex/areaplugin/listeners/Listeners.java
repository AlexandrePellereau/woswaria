package fr.firedragonalex.areaplugin.listeners;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.LazyMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.Plugin;

import fr.firedragonalex.areaplugin.MainAreaPlugin;
import fr.firedragonalex.areaplugin.Selection;
import fr.firedragonalex.areaplugin.area.Area;
import fr.firedragonalex.areaplugin.farmarea.FarmArea;
//import net.minecraft.network.protocol.game.PacketPlayOutEntityMetadata;
//import net.minecraft.server.level.EntityPlayer;

public class Listeners implements Listener {
	private MainAreaPlugin mainAreaPlugin;

	public Listeners(MainAreaPlugin mainAreaPlugin) {
		this.mainAreaPlugin = mainAreaPlugin;
	}
	
	//@EventHandler
	//public void onMove(PlayerMoveEvent event) {
		//Player player = event.getPlayer();
		//for (Area area : main.getAllAreas()) {
		//	if (area.isInTheArea(player.getLocation())) {
		//		player.sendTitle(area.getName(), area.getOwner(), 0, 20, 10);
		//		return;
		//	}
		//}
		//player.sendTitle("§2Wilderness","", 0, 20, 10);
	//}
	
//	@EventHandler
//	public void onInteractTests(PlayerInteractEvent event) {
//		Action action = event.getAction();
//		Player player = event.getPlayer();
//		if (!player.isOp()) return;
//		if (action==Action.RIGHT_CLICK_AIR || action==Action.RIGHT_CLICK_BLOCK) {
//			ItemStack item = event.getItem();
//			if (item != null && item.getType() == Material.PAPER && item.hasItemMeta() && item.getItemMeta().getDisplayName() != null) {
//				switch (item.getItemMeta().getDisplayName()) {
//				case "printTempVariables":
//					for (List tempVar : mainAreaPlugin.getTempVariables()) {
//						Bukkit.broadcastMessage(tempVar.get(0).toString());
//						Bukkit.broadcastMessage(tempVar.get(1).toString());
//						Bukkit.broadcastMessage(tempVar.get(2).toString());
//					}
//					break;
//				default:
//					break;
//				}
//			}
//		}
//	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (event.getClickedBlock().getType() == Material.OAK_SIGN) {
				Sign sign = (Sign)event.getClickedBlock().getState();
				if (sign.getLine(0).equals(ChatColor.YELLOW+"A VENDRE PAR")) {
					mainAreaPlugin.removeAllTempVariablesToThisPlayer(event.getPlayer());
					
					Inventory price = mainAreaPlugin.getSignLocationToPrice().get(
						sign.getLocation().getBlockX()+";"+
						sign.getLocation().getBlockY()+";"+
						sign.getLocation().getBlockZ()+";"+
						sign.getLocation().getWorld().getUID());
					
					List tempList = new ArrayList<>();
					tempList.add(event.getPlayer());
					tempList.add("SellSignLocation");
					tempList.add(
						sign.getLocation().getBlockX()+";"+
						sign.getLocation().getBlockY()+";"+
						sign.getLocation().getBlockZ()+";"+
						sign.getLocation().getWorld().getUID());
					mainAreaPlugin.getTempVariables().add(tempList);

					mainAreaPlugin.getGui().startAreaGUI_SellSign(event.getPlayer());
				}
			}
		}
	}
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent event) {
		Player player = event.getPlayer();
		if (player.getOpenInventory().getTitle().equals(ChatColor.DARK_BLUE+"AreaGUI_SetPriceSellArea")) {
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPickup(EntityPickupItemEvent event) {
		if (event.getEntityType() != EntityType.PLAYER) return;
		Player player = (Player)event.getEntity();
		if (player.getOpenInventory().getTitle().equals(ChatColor.DARK_BLUE+"AreaGUI_SetPriceSellArea")) {
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onInteractBannedItems(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		Action action = event.getAction();
		if (action==Action.LEFT_CLICK_BLOCK || action==Action.RIGHT_CLICK_BLOCK) {
			Block block = event.getClickedBlock();
			ItemStack it = event.getItem();
			if(it == null) return;
			if(it.getType()==Material.TNT_MINECART && !mainAreaPlugin.getPlayerCanPlaceMinecartWithTnt() && action==Action.RIGHT_CLICK_BLOCK) {
				player.sendMessage("§c[AreeaPlugin-FDA]Désolé, les minecrarts avec tnt sont désactivés !");
				event.setCancelled(true);
				return;
			}
			if(it.getType()==Material.END_CRYSTAL && !mainAreaPlugin.getPlayerCanPlaceEndCrystal() && action==Action.RIGHT_CLICK_BLOCK) {
				player.sendMessage("§c[AreeaPlugin-FDA]Désolé, les crystaux de l'end sont désactivés !");
				event.setCancelled(true);
				return;
			}
			//if((it.getType()==Material.WATER_BUCKET || it.getType()==Material.LAVA_BUCKET || it.getType()==Material.BUCKET) && action==Action.RIGHT_CLICK_BLOCK) {
			event.setCancelled(true);
			for (Area area : mainAreaPlugin.getAllAreas()) {
				if (area.isInTheArea(block.getLocation())){
					if (player.getUniqueId().equals(area.getOwner())) {
						event.setCancelled(false);
						return;
					}
					if (area.getMatesCanBreakAndPlaceBlocks()) {
						for (UUID mate : area.getMates()) {
							if (player.getUniqueId().equals(mate)) {
								event.setCancelled(false);
								return;
							}
						}
					}
				}
			}
			if (!mainAreaPlugin.getIsWildernessUnbreakable()) {
				event.setCancelled(false);
				return;
			}
		}
	}
	
	@EventHandler
	public void onEntityExplode(EntityExplodeEvent event) {
		List<Block> blockToRemove = new ArrayList<Block>();
		for (Area area : mainAreaPlugin.getAllAreas()) {
			if (area.isInvulnerableToExplosion()) {
				for (Block block : event.blockList()) {
					if (area.isInTheArea(block.getLocation())) {
						blockToRemove.add(block);
					}
				}
				for (Block block : blockToRemove) {
					event.blockList().remove(block);
				}
			}
		}
	}
	
	@EventHandler
	public void onBlockExplode(BlockExplodeEvent event) {
		List<Block> blockToRemove = new ArrayList<Block>();
		for (Area area : mainAreaPlugin.getAllAreas()) {
			if (area.isInvulnerableToExplosion()) {
				for (Block block : event.blockList()) {
					if (area.isInTheArea(block.getLocation())) {
						blockToRemove.add(block);
					}
				}
				for (Block block : blockToRemove) {
					event.blockList().remove(block);
				}
			}
		}
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		PermissionAttachment attachment = player.addAttachment(mainAreaPlugin);
		if (mainAreaPlugin.getConfig().getBoolean("force_all_players_basics_permissions.have")) {
			attachment.setPermission("areasettings.use", true);
			attachment.setPermission("giveareaselector.use", true);
			attachment.setPermission("areasellrewards.use", true);
		} else if (mainAreaPlugin.getConfig().getBoolean("force_all_players_basics_permissions.have_not")) {
			attachment.setPermission("areasettings.use", false);
			attachment.setPermission("giveareaselector.use", false);
			attachment.setPermission("areasellrewards+.use", false);
		}
		if (mainAreaPlugin.getNotEarnItemStacks().containsKey(player.getUniqueId())) {
			player.sendMessage(ChatColor.RED+"[Server-AreaPlugin] Tu as reçu des objets grâce à la vente d'une zone. Utilise la commande /areasellrewards pour les récupérer !");
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBreak(BlockBreakEvent event) {
		if (!event.isCancelled()) {
			if (event.getBlock().getType() == Material.OAK_SIGN) {
				Sign sign = (Sign)event.getBlock().getState();
				if (sign.getLine(0).equals(ChatColor.YELLOW+"A VENDRE PAR")) {
					Location signLocation = sign.getLocation();
					mainAreaPlugin.getSignLocationToPrice().remove(signLocation.getBlockX()+";"+signLocation.getBlockY()+";"+signLocation.getBlockZ()+";"+signLocation.getWorld().getUID());
				}
			}
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		List<List> tempVariablesToRemove = new ArrayList<List>();
		for (List tempVar : mainAreaPlugin.getTempVariables()) {
			if (tempVar.get(0)==event.getPlayer()) {
				tempVariablesToRemove.add(tempVar);
			}
		}
		mainAreaPlugin.getTempVariables().removeAll(tempVariablesToRemove);
		for (Selection selection : mainAreaPlugin.getAllSelections()) {
			if (selection.getOwner()==event.getPlayer()) {
				mainAreaPlugin.getAllSelections().remove(selection);
				return;
			}
		}
	}
	
}
