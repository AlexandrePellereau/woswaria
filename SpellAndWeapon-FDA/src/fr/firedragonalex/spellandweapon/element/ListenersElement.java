package fr.firedragonalex.spellandweapon.element;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import fr.firedragonalex.spellandweapon.Main;
import fr.firedragonalex.spellandweapon.custom.code.CustomEntity;
import fr.firedragonalex.spellandweapon.custom.code.CustomMonster;
import fr.firedragonalex.spellandweapon.custom.code.CustomPlayer;
import fr.firedragonalex.spellandweapon.showdamage.ShowDamage;

public class ListenersElement implements Listener {
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onDamageWithElement(EntityDamageByEntityEvent event) {
		if (!event.isCancelled()) {
			if (event.getDamager() instanceof Player) {
				Player player = (Player)event.getDamager();
				CustomPlayer customPlayer = Main.getCustomPlayerByPlayer(player); 
				if (customPlayer.hasElement(ElementType.DARK)) {
					customPlayer.heal((int)Math.round(event.getDamage()));
				}
				if (customPlayer.hasElement(ElementType.VEGETAL)) {
					int damage = (int)Math.round(((customPlayer.getElement(ElementType.VEGETAL).getPower()*1.0)/300)*event.getDamage());
					customPlayer.damage(damage);
					ShowDamage.addDamage((double)damage, "§2", event.getEntity().getLocation());
				}
			}
		}
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onDamageByPotionEffect(EntityDamageEvent event) {
		if (!event.isCancelled()) {
			if (event.getEntity() instanceof LivingEntity) {
				CustomEntity customEntity = Main.getCustomEntityByEntity((LivingEntity)event.getEntity());
				if (customEntity != null) {
					DamageCause cause = event.getCause();
					switch (cause) {
					case FIRE_TICK:
						if (customEntity.hasElement(ElementType.FIRE)) {
							event.setDamage(5);
						}
						break;
					case WITHER:
						if (customEntity.hasElement(ElementType.DARK)) {
							event.setDamage(5);
						}
						break;
					case POISON:
						if (customEntity.hasElement(ElementType.VEGETAL)) {
							event.setCancelled(true);
						}
						break;
					default:
						break;
					}
				}
			}
		}
	}
}
