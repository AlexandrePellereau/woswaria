package fr.firedragonalex.spellandweapon.quests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.firedragonalex.spellandweapon.Main;
import fr.firedragonalex.spellandweapon.custom.code.CustomPlayer;
import fr.firedragonalex.spellandweapon.custom.easytoadd.Craft;
import fr.firedragonalex.spellandweapon.quests.steps.abstracts.AbstractQuestStep;
import fr.firedragonalex.spellandweapon.quests.steps.abstracts.AbstractQuestStepDefault;
import fr.firedragonalex.spellandweapon.quests.steps.abstracts.AbstractQuestStepInstant;

public class QuestGui {
	
	public static void startGui_Main(Player player) {
		CustomPlayer customPlayer = Main.getCustomPlayerByPlayer(player);
		Inventory inventory = Bukkit.createInventory(null, 3*9, "§1QuestGUI_ChooseQuest");
		
		for (int i = 0; i < 3*9; i++) {
			if (i < customPlayer.getQuests().size()) {
				
				QuestInProgress questInProgress = customPlayer.getQuests().get(i);
				Quest quest = questInProgress.getQuest();
				AbstractQuestStepDefault questStep = (AbstractQuestStepDefault)customPlayer.getQuests().get(i).getStep();
				
				int advancementNoInstant = 0;
				for (int k = 0; k < questInProgress.getAdvancement(); k++) {
					AbstractQuestStep abstractQuestStep = quest.getStep(k);
					if (!(abstractQuestStep instanceof AbstractQuestStepInstant)) {
						advancementNoInstant++;
					}
				}
				int allStepsNoInstant = 0;
				for (int k = 0; k < quest.getAllSteps().size(); k++) {
					AbstractQuestStep abstractQuestStep = quest.getStep(k);
					if (!(abstractQuestStep instanceof AbstractQuestStepInstant)) {
						allStepsNoInstant++;
					}
				}
				
				ItemStack item = Craft.customItem(
						quest.getItem(),ChatColor.YELLOW+quest.getName()+" ("+advancementNoInstant+"/"+allStepsNoInstant+")",
						Arrays.asList(ChatColor.YELLOW+questStep.toPrint())
						);
				
				if (customPlayer.getCurrentQuest() != null && customPlayer.getCurrentQuest().getQuest() == quest) {
					ItemMeta itemMeta = item.getItemMeta();
					itemMeta.addEnchant(Enchantment.DURABILITY, 0, true);
					itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
					item.setItemMeta(itemMeta);
				}
				inventory.setItem(i,item);
			} else {
				inventory.setItem(i,Craft.customItem(Material.BLACK_STAINED_GLASS_PANE, "§"));
			}
		}
		
		player.openInventory(inventory);
	}
}
