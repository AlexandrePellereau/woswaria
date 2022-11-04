package fr.firedragonalex.zombieapocalypsepvpve.listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;

import fr.firedragonalex.zombieapocalypsepvpve.FMain;
import fr.firedragonalex.zombieapocalypsepvpve.FPeriod;
import fr.firedragonalex.zombieapocalypsepvpve.FState;

public class FDamageListeners implements Listener {
	
	private FMain main;

	public FDamageListeners(FMain main) {
		this.main = main;
	}
	
	@EventHandler
	public void onKill(PlayerDeathEvent event) {
		Player player = event.getEntity();
		if (main.getPlayers().contains(player) && main.isState(FState.PLAYING)) {
			main.getPlayers().remove(player);
			//player.kickPlayer("You died !");
			player.setGameMode(GameMode.SPECTATOR);
			player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION,20*3600,0));
			main.checkWin();
		}
	}
	
//	@EventHandler
//	public void onDamage(EntityDamageEvent event) {
//		if (!main.isState(FState.WAITING)) {
//			Entity victim = event.getEntity();
//			if (victim instanceof Player) {
//				Player player = (Player)victim;
//				if (player.getHealth() <= event.getDamage()) {
//					event.setDamage(0);
//					if (main.getPlayers().contains(player)){
//						main.getPlayers().remove(player);
//						player.setGameMode(GameMode.SPECTATOR);
//						player.sendTitle("§cTu es mort !","", 1, 20*3, 1);
//					}
//				}
//			}else {
//				
//			}
//		main.checkWin();
//		}
//	}
	
	
	@EventHandler
	private void onKillEntity(EntityDeathEvent event) {
		Entity entity = event.getEntity();
		if (entity instanceof Zombie) {
			//System.out.println("test1 passed");
			for (ItemStack myItem : event.getDrops()) {
				event.getEntity().getKiller().getInventory().addItem(myItem);
				if (myItem.getType()==Material.ROTTEN_FLESH) {
					event.getEntity().getKiller().sendMessage("+"+myItem.getAmount()+" rotten flesh !");
				}else {
					event.getEntity().getKiller().sendMessage("§bRARE LOOT !");
					event.getEntity().getKiller().stopSound(Sound.ENTITY_EXPERIENCE_ORB_PICKUP);
				}
				
				//System.out.println("test2 passed");
			}
			//System.out.println("test3 passed");
			event.getDrops().clear();
			List<Zombie> listOfZombies = new ArrayList<Zombie>();
			List<Entity> listOfEntity = main.getWorld().getEntities();
			//System.out.println("test4 passed");
			for (Entity myEntity : listOfEntity) {
				if (myEntity instanceof Zombie) {
					listOfZombies.add((Zombie)myEntity);
				}
			}
			//System.out.println("test5 passed");
			if (main.getNbPlayersAtStart()>0) {
				main.getBossBarNbZombies().setProgress(listOfZombies.size()*1.0/main.getPeriod().getNbZombies()*main.getPlayers().size()*1.0);
			}
			//System.out.println("test6 passed");
		}

	}
	
	
	@EventHandler
	public void onPvp(EntityDamageByEntityEvent event) {
		Entity victim = event.getEntity();
		Entity damager = event.getDamager();
		if (victim instanceof Player) {
			Player playerVictim = (Player)victim;
			if (damager instanceof Player) {
				Player playerDamager = (Player)damager;
				playerDamager.sendMessage("§cTu ne peux pas attaquer un \"allié\" !");
				playerDamager.sendMessage("§cEssaye de le pieger !");
				System.out.println("[ZombieApocalypsePvPvE] "+playerDamager.getName()+" attack "+playerVictim.getName()+" but it was cancelled !");
				event.setCancelled(true);
			}
			if (damager instanceof Arrow) {
				Arrow arrowDamager = (Arrow)damager;
				ProjectileSource arrowShooter = arrowDamager.getShooter();
				if (arrowShooter instanceof Player) {
					Player playerShooter = (Player)arrowShooter;
					playerShooter.sendMessage("§cTu ne peux pas tirer sur un \"allié\" !");
					playerShooter.sendMessage("§cEssaye de le pieger !");
					System.out.println("[ZombieApocalypsePvPvE] "+playerShooter.getName()+" attack "+playerVictim.getName()+" but it was cancelled !");
					event.setCancelled(true);
				}
			}
			//event.setDamage(0);
			//event.setCancelled(true);
		}
	}
}
