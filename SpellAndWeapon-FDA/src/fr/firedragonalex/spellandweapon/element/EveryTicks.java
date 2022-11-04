package fr.firedragonalex.spellandweapon.element;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.RegionAccessor;
import org.bukkit.World;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.FallingBlock;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import fr.firedragonalex.spellandweapon.Main;
import fr.firedragonalex.spellandweapon.custom.code.CustomEntity;
import fr.firedragonalex.spellandweapon.custom.code.CustomPlayer;
import net.minecraft.commands.arguments.ArgumentParticle;

public class EveryTicks extends BukkitRunnable{
	
	private int timer;
	
	public EveryTicks() {
		this.timer = 0;
	}
	
	@Override
	public void run() {
		for(CustomEntity customEntity : Main.getAllCustomEntities()) {
			
			if (customEntity.getEntity().isInWater() && !customEntity.hasElement(ElementType.WATER)) {
				customEntity.addElement(new Element(ElementType.WATER, 10));
			}
			
			Location location = customEntity.getEntity().getLocation();
			List<ElementType> elementToRemove = new ArrayList<ElementType>();
			
			for (Element element : customEntity.getListElements()) {
				switch (element.getType()) {
				case WATER:
					if (customEntity.getEntity().isInWater()) {
						customEntity.addElement(new Element(ElementType.WATER, 2));
					} else {
						Main.getInstance().spawnParticlesAroundEntity(Particle.FALLING_WATER, customEntity.getEntity(), 10);
					}
					break;
				case ICE:
					customEntity.getEntity().setFreezeTicks(140);
					Main.getInstance().spawnParticlesAroundEntity(Particle.SNOWBALL, customEntity.getEntity(), 10);
					break;
				case DARK:
					Main.getInstance().spawnParticlesAroundEntity(Particle.ASH, customEntity.getEntity(), 10, 4);
					break;
				case VEGETAL:
					Main.getInstance().spawnParticlesAroundEntity(Particle.FALLING_SPORE_BLOSSOM, customEntity.getEntity(), 10, 2);
					break;
				case ELECTRICITY:
					Main.getInstance().spawnParticlesAroundEntity(Particle.ELECTRIC_SPARK, customEntity.getEntity(), 10);
					break;
				case STONE:
					location.setX(location.getX()-0.5+Math.random());
					location.setY(location.getY()+(Math.random()*customEntity.getEntity().getHeight()));
					location.setZ(location.getZ()-0.5+Math.random());
					location.getWorld().spawnParticle(Particle.BLOCK_CRACK, location, 5, Material.STONE.createBlockData());
					break;
				default:
					break;
				}
				
				element.setPower(element.getPower()-1);
				
				//l'element disparait :
				if (element.getPower() <= 0) {
					elementToRemove.add(element.getType());
					
				}
			}
			for (ElementType elementType : elementToRemove) {
				customEntity.removeElement(elementType);
			}
		}

		if (this.timer == 0) {
			this.timer = 20;
			for (CustomEntity customEntity : Main.getAllCustomEntities()) {
				if (customEntity instanceof CustomPlayer || customEntity.getListElements().size() > 0) {
					customEntity.updateCustomHealthBar();
				}
			}
		}
		timer--;
	}
}
