package fr.firedragonalex.uhc.specific;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.firedragonalex.uhc.core.GameManager;
import fr.firedragonalex.uhc.core.langague.Translate;
import fr.firedragonalex.uhc.core.role.InfectedPlayer;
import fr.firedragonalex.uhc.core.role.cooldown.Cooldown;
import fr.firedragonalex.uhc.core.role.cooldown.CooldownModdedItem;

public enum ModdedItem {
	
	ARMURE_BLINDEE(
		ChatColor.LIGHT_PURPLE+"Armure blindée",
		2, 10 * 60, 
		Arrays.asList(
			new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 3*60*20, 0, false, false),
			new PotionEffect(PotionEffectType.HEALTH_BOOST, 3*60*20, 0, false, false)), 
		null,
		Material.FEATHER, true),
	SIRAE_2(
		ChatColor.LIGHT_PURPLE+"Siraé 2",
		2, 15 * 60,
		Arrays.asList(
			new PotionEffect(PotionEffectType.SPEED, 3*60*20, 0, false, false),
			new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 3*60*20, 0, false, false)),
		null,
		Material.FEATHER, true),
	;
	
	private String name;
	private int numberOfUses;
	private int cooldown;
	private List<PotionEffect> allPotionEffects;// = Arrays.asList(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 3*60*20, 0, false, false));
	private List<CustomEffect> allCustomEffects;
	private Material typeItem;
	private boolean isLookedEnchanted;
	
	private ModdedItem(
		String name,
		@Nullable int numberOfUses, int cooldown, @Nullable List<PotionEffect> allPotionEffects, @Nullable List<CustomEffect> allCustomEffects, 
		Material typeItem, boolean isLookedEnchanted) {

		this.name = name;
		this.numberOfUses = numberOfUses;
		this.cooldown = cooldown;
		this.allPotionEffects = allPotionEffects;
		this.allCustomEffects = allCustomEffects;
		this.typeItem = typeItem;
		this.isLookedEnchanted = isLookedEnchanted;
	}



	public void onUseSpecific(PlayerInteractEvent event) {
		
	}




	public ItemStack getItem() {
		ItemStack item = new ItemStack(typeItem);
		//item = this.setLore(numberOfUses, item);
		this.setLore(numberOfUses, item);
		ItemMeta mItem = item.getItemMeta();
		if (this.isLookedEnchanted) {
			mItem.addEnchant(Enchantment.DURABILITY, 0, true);
			mItem.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		}
		mItem.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		mItem.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		mItem.spigot().setUnbreakable(true);
		mItem.setDisplayName(name);
		item.setItemMeta(mItem);
		return item;
	}

	public ItemStack setLore(int uses, ItemStack item) {
		ItemMeta mItem = item.getItemMeta();
		List<String> finalLore = new ArrayList<String>();
		if (numberOfUses != -1) {
			finalLore.add("");
			finalLore.add(Translate.REMAINING_USE.getString() + uses);
		}
		mItem.setLore(finalLore);
		item.setItemMeta(mItem);
		return item;
	}

	public static boolean isItModdedItem(ItemStack item) {
		for (ModdedItem moddedItem : ModdedItem.values()) {
			if (moddedItem.getItem().getItemMeta().getDisplayName().equals(item.getItemMeta().getDisplayName())) return true;
		}
		return false;
	}

	public static ModdedItem getModdedItem(ItemStack item) {
		for (ModdedItem moddedItem : ModdedItem.values()) {
			if (moddedItem.getItem().getItemMeta().getDisplayName().equals(item.getItemMeta().getDisplayName())) return moddedItem;
		}
		return null;
	}

	public boolean canUse(Player player) {
		if (numberOfUses == -1) return true;
		if (numberOfUses > 0) return true;
		return false;
	}

	public boolean hasNoCoolwdown(Player player) {
		for (Cooldown cooldown : GameManager.getAllCooldowns()) {
			if (cooldown instanceof CooldownModdedItem) {
				CooldownModdedItem cooldownModdedItem = (CooldownModdedItem) cooldown;
				if (cooldownModdedItem.getPlayer() == player && cooldownModdedItem.getModdedItem() == this) return false;
			}
		}
		return true;
	}
	
	public void use(Player player, ItemStack item) {
		if (allPotionEffects != null) {
			for (PotionEffect potionEffect : allPotionEffects) {
				player.addPotionEffect(potionEffect);
			}
		}
		if (allCustomEffects != null) {
			for (CustomEffect customEffect : allCustomEffects) {
				GameManager.getAllInfectedPlayers().add(new InfectedPlayer(player, customEffect));
			}
		}
		GameManager.addCooldown(new CooldownModdedItem(player, this, cooldown));
		this.updateNumberOfUses(player, item);
	}

	public int getNumberOfUses(ItemStack item) {
		if (numberOfUses == -1) return -1;
		if (item.getItemMeta().getLore() == null) return numberOfUses;
		for (String lore : item.getItemMeta().getLore()) {
			if (lore.contains(Translate.REMAINING_USE.getString())) {
				String[] split = lore.split(Translate.REMAINING_USE.getString());
				return Integer.parseInt(split[1]);
			}
		}
		return numberOfUses;
	}

	public void updateNumberOfUses(Player player, ItemStack item) {
		if (numberOfUses == -1) return;
		if (item.getItemMeta().getLore() == null) return;
		for (String lore : item.getItemMeta().getLore()) {
			if (lore.contains(Translate.REMAINING_USE.getString())) {
				int numberOfUses = this.getNumberOfUses(item);
				numberOfUses--;
				if (numberOfUses == 0) {
					player.sendMessage(ChatColor.RED+Translate.ITEM_BREAK_NO_USE_REMAINING.getString());
					player.getInventory().removeItem(item);
					return;
				}
				this.setLore(numberOfUses, item);
				return;
			}
		}
	}

	public long getCoolDown(Player player) {
		for (Cooldown cooldown : GameManager.getAllCooldowns()) {
			if (cooldown instanceof CooldownModdedItem) {
				CooldownModdedItem cooldownModdedItem = (CooldownModdedItem) cooldown;
				if (cooldownModdedItem.getPlayer() == player && cooldownModdedItem.getModdedItem() == this) return cooldownModdedItem.getCooldown();
			}
		}
		return -1;
	}
}
