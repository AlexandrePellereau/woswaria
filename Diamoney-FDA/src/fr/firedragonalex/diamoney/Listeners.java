package fr.firedragonalex.diamoney;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Listeners implements Listener {
	
	private Main main;
	
	public Listeners(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		main.addPlayerBank(0, player);
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent event) {
		String inventoryTitle = event.getView().getTitle();
		if (inventoryTitle.contains(":")) inventoryTitle = inventoryTitle.split(":")[0];
		Player player = (Player)event.getWhoClicked();
		if (!inventoryTitle.equals("§eDiamoney_Bank")) return;
		if (event.getAction() == InventoryAction.COLLECT_TO_CURSOR || event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
			event.setCancelled(true);
		}
		if (!event.getClickedInventory().equals(event.getInventory())) {
			return;
		}
		if (event.getSlot() < 9*3) {
			ItemStack item = event.getCurrentItem();
			if (item==null) return;
			if (item.getItemMeta() == null) return ;
			if (item.getItemMeta().getDisplayName() == "§0interface") {
				event.setCancelled(true);
				return;
			}
			if (item.getItemMeta().getLore() == null) return ;
			if (inventoryTitle.equals("§eDiamoney_Bank") && 
				item.getItemMeta().getLore().get(0).startsWith("Valeur en diamant : ") && 
				item.getItemMeta().getDisplayName().endsWith("@")) {

				event.setCancelled(true);
				String value = item.getItemMeta().getDisplayName();
				value = value.replace("@", "");
				int intValue = Integer.valueOf(value);
				PlayerBank playerBank = main.getPlayerBank(player);
				if (playerBank.hasMoney(intValue)) {
					playerBank.removeMoney(intValue);
					player.getInventory().addItem(item);
					player.sendMessage("§eVous avez retiré §b"+intValue+"@ §edans votre bank.");
					main.openGui(player);
				} else {
					player.sendMessage("§eVous avez moins de §b"+intValue+"@ §edans votre bank.");
				}
			}
		} else {
			ItemStack item = event.getCursor();
			if (item==null) return;
			if (item.getItemMeta() == null) return ;
			if (item.getItemMeta().getLore() == null) return ;
			if (inventoryTitle.equals("§eDiamoney_Bank") && 
				item.getItemMeta().getLore().get(0).startsWith("Valeur en diamant : ") && 
				item.getItemMeta().getDisplayName().endsWith("@")) {

				if (event.getAction() == InventoryAction.PLACE_ALL || 
					event.getAction() == InventoryAction.PLACE_ONE || 
					event.getAction() == InventoryAction.PLACE_SOME) {
					
					String value = item.getItemMeta().getDisplayName();
					value = value.replace("@", "");
					int intValue = Integer.valueOf(value);
					intValue = intValue * item.getAmount();
					PlayerBank playerBank = main.getPlayerBank(player);
					playerBank.addMoney(intValue);
					event.getWhoClicked().setItemOnCursor(null);
					player.sendMessage("§eVous avez déposé §b"+intValue+"@ §edans votre bank.");
					main.openGui(player);
				}
			}
		}
		//player.closeInventory();

	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onInteract(PlayerInteractEvent event) {
		ItemStack item = event.getItem();
		if (item != null && item.hasItemMeta() && item.getItemMeta().hasLore() && item.getItemMeta().getLore().get(0).startsWith("Valeur en diamant : ") && item.getItemMeta().getDisplayName().endsWith("@")) {
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onCraft(CraftItemEvent event) {
		List<ItemStack> itemToCheck = new ArrayList<ItemStack>();
		itemToCheck.add(event.getCurrentItem());
		itemToCheck.add(event.getCursor());
		for(int i = 0; i < 10;i++) {
			if (event.getInventory().getItem(i) != null) {
				itemToCheck.add(event.getInventory().getItem(i));
			}
		}
		for(ItemStack item : itemToCheck) {
			try {
				if ((item.getItemMeta().getLore().get(0).startsWith("Valeur en diamant : ") && item.getItemMeta().getDisplayName().endsWith("@")) || item.getType()==Material.BARRIER) {
					event.setCancelled(true);
					event.getInventory().setItem(0, main.customItem(Material.BARRIER, "§cMauvaise idée"));
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
	
	
//	@EventHandler
//	public void onCraft(PrepareItemCraftEvent event) {
//		Inventory inventory = event.getInventory();
//		if (inventory.getItem(0) != null) {
//			if (inventory.getItem(0).equals(main.getItemByValue(100))) {
//				for (int i = 1; i <= 9; i++) {
//					ItemStack itemStack = inventory.getItem(i);
//					if (itemStack != null) {
//						if (itemStack.equals(main.getItemByValue(100))) {
//							itemStack.setAmount(itemStack.getAmount()-1);
//							inventory.setItem(0,itemStack);
//						}
//					}
//				}
//			}
//		}
//		int nbDiamond = 0;
//		for (int i = 1; i <= 9; i++) {
//			ItemStack itemStack = inventory.getItem(i);
//			if (itemStack != null) {
//				if (itemStack.getType() == Material.DIAMOND) {
//					if (nbDiamond != 0) return;
//					nbDiamond = itemStack.getAmount();
//				}
//			}
//		}
//		inventory.setItem(0,main.getItemByValue(100));
//	}
	
	
//	@EventHandler
//	public void onCraft(PrepareItemCraftEvent event) {
//		Inventory inventory = event.getInventory();
//		Bukkit.broadcastMessage("test1");
//		int value = 0;
//		if (inventory.getItem(1) == null) return;
//		Bukkit.broadcastMessage("test1.1");
//		for (Integer integer : main.getValueToMaterialMoney().keySet()) {
//			Bukkit.broadcastMessage("test1.2");
//			if (inventory.getItem(1).equals(main.getItemByValue(integer))) {
//				value = integer;
//				break;
//			}
//		}
//		Bukkit.broadcastMessage("test2");
//		if (value == 0 || value == 500000) return;
//		Bukkit.broadcastMessage("test3");
//		for (int i = 1; i <= 9; i++) {
//			System.out.println(i);
//			ItemStack itemStack = main.getItemByValue(value);
//			if (i == 5) {
//				itemStack.setAmount(2);
//			} else {
//				itemStack.setAmount(1);
//			}
//			if (!inventory.getItem(i).equals(itemStack)) {
//				return;
//			}
//		}
//		Bukkit.broadcastMessage("bon craft");
//		List<Integer> list = new ArrayList<Integer>(main.getValueToMaterialMoney().keySet());
//		inventory.setItem(0, main.getItemByValue(list.get(list.indexOf(value)+1)));
//	}
}
