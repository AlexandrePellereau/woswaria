package fr.firedragonalex.rankandlevels.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import fr.firedragonalex.rankandlevels.Main;
import fr.firedragonalex.rankandlevels.rank.Rank;
import fr.firedragonalex.spellandweapon.alexlibrairy.UsefulFunctions;

public class ListenerGUI implements Listener{
	
	@EventHandler
	public void onTest(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (player.isOp() && event.getMaterial() == Material.REDSTONE_TORCH) {
			player.sendMessage("[RankAndLevels] test1 "+Main.getLevelRewardsAdvancement().get(player.getUniqueId()));
		}
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent event) {
		String inventoryTitle = event.getView().getTitle();
		Player player = (Player)event.getWhoClicked();
		ItemStack item = event.getCurrentItem();
		if (item == null || !item.hasItemMeta()) return;
		if (inventoryTitle.contains(";") && inventoryTitle.split(";")[0].equals("§1LevelRewards")) {
			event.setCancelled(true);
			Integer page = Integer.valueOf(inventoryTitle.split(";")[1].split(":")[1]);
			Integer rankNumber = Integer.valueOf(inventoryTitle.split(";")[2].split(":")[1]);
			String itemName = item.getItemMeta().getDisplayName();
			if (!itemName.startsWith(ChatColor.YELLOW+"")) { return; }
			if (itemName.contains("Normal") || itemName.contains("Rare") || itemName.contains("Très rare") || itemName.contains("Epique") || itemName.contains("Légendaire")) {
				if (item.containsEnchantment(Enchantment.DURABILITY)) {
					Rank rank = Rank.valueOf(itemName.substring(itemName.indexOf(",")+1, itemName.indexOf(")")).toUpperCase());
					if (Main.getRank(player).equalsOrBetter(rank)) {
						ListenerGUI.clickOnRewardUnlock(player, itemName, rank, rankNumber, page);
					} else {
						player.sendMessage(ChatColor.RED+"Tu n'as pas le grade requis !");
					}
				} else {
					player.sendMessage(ChatColor.RED+"Tu n'as pas le niveau requis !");
				}
			}
			else if (itemName.equals(ChatColor.YELLOW+"Précédent")) {
				Gui.GuiLevelRewards(player, (page-1)*7, Rank.values()[rankNumber*2]);
			}
			else if (itemName.equals(ChatColor.YELLOW+"Suivant")) {
				Gui.GuiLevelRewards(player, (page+1)*7, Rank.values()[rankNumber*2]);
			}
			else if (itemName.equals(ChatColor.YELLOW+"Grade plus haut")) {
				Gui.GuiLevelRewards(player, page*7, Rank.values()[(rankNumber+1)*2]);
			}
			else if (itemName.equals(ChatColor.YELLOW+"Grade moins haut")) {
				Gui.GuiLevelRewards(player, page*7, Rank.values()[(rankNumber-1)*2]);
			}
		}
	}
	
	public static void clickOnRewardUnlock(Player player, String itemName, Rank rank, int rankNumber, int page) {
		UUID id = player.getUniqueId();
		int level = Integer.valueOf(itemName.split(" ")[1]);
		if (level == 1) {
			HashMap<Rank, Integer> startLevelRewardsAdvancement = new HashMap<>();
			if (Main.getLevelRewardsAdvancement().get(id) != null) {
				startLevelRewardsAdvancement = Main.getLevelRewardsAdvancement().get(id);
			}
			startLevelRewardsAdvancement.put(rank, 1);
			if (ListenerGUI.giveLevelRewards(level, rank, player)) {
				Main.getLevelRewardsAdvancement().put(id, startLevelRewardsAdvancement);
			}
			Gui.GuiLevelRewards(player, page, Rank.values()[rankNumber*2]);
		} else {
			if (Main.getLevelRewardsAdvancement().containsKey(id)) {
				if (Main.getLevelRewardsAdvancement().get(id).containsKey(rank) && Main.getLevelRewardsAdvancement().get(id).get(rank) + 1 == level) {
					if (ListenerGUI.giveLevelRewards(level, rank, player)) {
						Main.getLevelRewardsAdvancement().get(id).replace(rank, level);
					} 
					Gui.GuiLevelRewards(player, page, Rank.values()[rankNumber*2]);
				} else {
					player.sendMessage(ChatColor.RED+"Tu dois d'abord récupérer les récompenses précédentes !");
					return;
				}
			} else {
				player.sendMessage("§c[Error] Level 1 missing");
			}
		}
	}
	
	public static boolean giveLevelRewards(int level, Rank rank, Player player) {
		int money = level;
		fr.firedragonalex.diamoney.Main.getPlayerBank(player).addMoney(money);
		player.sendMessage(ChatColor.YELLOW+"Tu as reçu "+ChatColor.AQUA+money+"@"+ChatColor.YELLOW+" !");
		List<ItemStack> rewards = new ArrayList<>();
		rewards.add(Main.customItem(Material.SUNFLOWER, "Jeton cosmétique de niveau 1"));
		if (UsefulFunctions.canAdd(rewards, player.getInventory())) {
			for (ItemStack itemstack : rewards) {
				player.getInventory().addItem(itemstack);
			}
			player.sendMessage(ChatColor.YELLOW+"Tu as bien reçus la récompense du niveau "+level+" !");
			return true;
		} else {
			player.sendMessage(ChatColor.RED+"Tu dois faire de la place dans ton inventaire !");
			return false;
		}
	}
}
