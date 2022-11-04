package fr.firedragonalex.uhc.specific;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public enum Role {
	
	YUU_OMINAE(
		"Yuu Ominae",
		Side.GREEN,
		Arrays.asList(
			"Vous avez obtenu une armure blindée.",
			"Vous avez obtenu l'objet sirae 2.",
			"Leurs utilisations sont limitées, faites attention !"),
		null,
		Arrays.asList(
			ModdedItem.ARMURE_BLINDEE,
			ModdedItem.SIRAE_2)
	),
	LA_SCIENTIFIQUE(
		"La Scientifique",
		Side.GREEN,
		Arrays.asList(
			"Si vous parvenez à déchiffer les ruines de feu vous mettrerez vos ennemies en feu.",
			"Si vous restez pendant 10 min à coté du mec du serpent de feu, vous connaitrez l'identité de Yuu Ominae."),
		null,
		null
	),
	;
	
	private String name;
	private Side side;
	private List<String> roleDescription;
	private List<PotionEffect> startingEffects;
	private List<ModdedItem> startingModdedItems;
	
	private Role(String name, Side side, List<String> roleDescription, List<PotionEffect> startingEffects, List<ModdedItem> startingModdedItems) {
		this.name = name;
		this.side = side;
		this.roleDescription = roleDescription;
		this.startingEffects = startingEffects;
		this.startingModdedItems = startingModdedItems;
	}





	public void onAnnoucementSpecific(Player player) {
		switch (this) {
			case YUU_OMINAE:
				//if (SpecificOptions.getComposition().contains())
				break;
		
			default:
				break;
		}
	}



	

	public String getName() {
		return this.name;
	}

	public List<String> getDescription() {
		return this.roleDescription;
	}
	
	public Side getSide() {
		return this.side;
	}

	public void sendRoleDescription(Player player) {
		player.sendMessage(ChatColor.DARK_GREEN + "Vous êtes " + this.name + " !");
		player.sendMessage(ChatColor.DARK_GREEN + "Vous gagnez avec les " + this.side.getName() + " !");
		for (String description : roleDescription) {
			player.sendMessage(ChatColor.DARK_GREEN+description);
		}
	}
	
	public void onAnnoucement(Player player) {
		for (String line : roleDescription) {
			player.sendMessage(ChatColor.BLUE+line);
		}
		if (this.startingEffects != null) {
			for (PotionEffect potionEffect : this.startingEffects) {
				potionEffect.apply(player);
			}
		}
		if (this.startingModdedItems != null) {
			for (ModdedItem moddedItem : this.startingModdedItems) {
				HashMap<Integer, ItemStack> notGiven = player.getInventory().addItem(moddedItem.getItem());
				if (notGiven == null || !notGiven.isEmpty()) {
					player.getWorld().dropItem(player.getLocation(), notGiven.get(0));
				}
			}
		}
		this.onAnnoucementSpecific(player);
	}
	
	
	
	
}
