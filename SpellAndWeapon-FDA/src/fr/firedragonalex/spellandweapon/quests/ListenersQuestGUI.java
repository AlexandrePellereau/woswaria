package fr.firedragonalex.spellandweapon.quests;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import fr.firedragonalex.spellandweapon.Main;
import fr.firedragonalex.spellandweapon.custom.code.CustomPlayer;

public class ListenersQuestGUI implements Listener {
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		String inventoryTitle = event.getView().getTitle();
		Player player = (Player)event.getWhoClicked();
		ItemStack item = event.getCurrentItem();
		if (item==null) return;
		if (inventoryTitle.equals("�1QuestGUI_ChooseQuest")) {
			event.setCancelled(true);
			if (item.getType() != Material.BLACK_STAINED_GLASS_PANE && item.hasItemMeta()) {
				String questName = "";
				String[] questNameSplit = item.getItemMeta().getDisplayName().split(" ");
				for (int i = 0; i < questNameSplit.length - 1; i++) {
					questName += questNameSplit[i] + " ";
				}
				questName = questName.substring(2, questName.length()-1);
				CustomPlayer customPlayer = Main.getCustomPlayerByPlayer(player);
				for (Quest quest : Quest.values()) {
					if (quest.getName().equals(questName)) {
						customPlayer.setCurrentQuest(quest);
						QuestGui.startGui_Main(player);
					}
				}
			}
			
		}
	}

}
