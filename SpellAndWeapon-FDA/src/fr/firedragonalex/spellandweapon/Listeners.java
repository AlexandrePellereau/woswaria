package fr.firedragonalex.spellandweapon;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExhaustionEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerAnimationType;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import fr.firedragonalex.spellandweapon.alexlibrairy.UsefulFunctions;
import fr.firedragonalex.spellandweapon.custom.code.CustomEntity;
import fr.firedragonalex.spellandweapon.custom.code.CustomMonster;
import fr.firedragonalex.spellandweapon.custom.code.CustomPlayer;
import fr.firedragonalex.spellandweapon.custom.code.custommonsterspawner.CustomMonsterSpawner;
import fr.firedragonalex.spellandweapon.custom.easytoadd.CustomMonsterType;
import fr.firedragonalex.spellandweapon.custom.easytoadd.CustomWeapon;
import fr.firedragonalex.spellandweapon.element.Element;
import fr.firedragonalex.spellandweapon.element.ElementType;
import fr.firedragonalex.spellandweapon.spell.Spell;

public class Listeners implements Listener {
	
	private Main main;

	public Listeners() {
		this.main = Main.getInstance();
	}
	
	//////////////////////////////////////////////////////////////////////
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		//tests
//		if (CustomWeapon.SHURIKEN_EN_DIAMANT.getItem().getItemMeta().getDisplayName().equals(event.getItem().getItemMeta().getDisplayName())) {
//			event.getItem().setAmount(16);
//		}
	}
	
	//////////////////////////////////////////////////////////////
	
	
	
	@EventHandler
	public void onInteractWithEntityRemover(PlayerInteractAtEntityEvent event) {
		ItemStack item = event.getPlayer().getInventory().getItemInMainHand();
		if (item != null && item.hasItemMeta() && item.getItemMeta().getDisplayName().equals(ChatColor.RED+"EntityRemover")) {
			event.getRightClicked().remove();
		}
	}
	
	@EventHandler
	public void onInteractWithTransformacon(PlayerInteractAtEntityEvent event) {
		ItemStack item = event.getPlayer().getInventory().getItemInMainHand();
		if (item != null && item.hasItemMeta() && item.getItemMeta().getDisplayName().equals(ChatColor.LIGHT_PURPLE+"Transformacon")) {
			if (event.getRightClicked() instanceof LivingEntity) {
				LivingEntity livingEntity = (LivingEntity)event.getRightClicked();
				if (livingEntity.hasAI()) {
					livingEntity.setAI(false);
					event.getPlayer().sendMessage(ChatColor.YELLOW+"Cette créature est devenue con !");
				} else {
					event.getPlayer().sendMessage(ChatColor.RED+"Cette créature est déjà con !");
				}

			} else {
				event.getPlayer().sendMessage(ChatColor.RED+"Ce truc est déjà con !");
			}
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onInteractCustomEntitySpawner(PlayerInteractEvent event) {
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK && (event.getItem() != null && event.getItem().hasItemMeta() && event.getItem().getItemMeta().getDisplayName().startsWith(ChatColor.LIGHT_PURPLE+"CustomMonsterSpawner"))) {
			event.setCancelled(true);
			Block block = event.getClickedBlock();
			
			HashMap<String, String> lore = UsefulFunctions.loreToHashMap(event.getItem());
			CustomMonsterType customMonsterType = CustomMonsterType.valueOf(lore.get("CustomMonsterType"));
			int level = Integer.valueOf(lore.get("Level"));
			int nbMonsterMax = Integer.valueOf(lore.get("NbMonsterMax"));
			int ticksBetweenMonsterSpawn = Integer.valueOf(lore.get("TicksBetweenMonsterSpawn"));
			int spawnRadius = Integer.valueOf(lore.get("SpawnRadius"));
			int keepMonsterRadius = Integer.valueOf(lore.get("KeepMonsterRadius"));
			new CustomMonsterSpawner(block.getLocation(), customMonsterType, level, nbMonsterMax, ticksBetweenMonsterSpawn, spawnRadius, keepMonsterRadius);
		}
	}
	
}
