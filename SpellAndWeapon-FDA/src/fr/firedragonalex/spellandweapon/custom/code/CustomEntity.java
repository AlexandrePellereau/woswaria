package fr.firedragonalex.spellandweapon.custom.code;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.firedragonalex.spellandweapon.Main;
import fr.firedragonalex.spellandweapon.element.Element;
import fr.firedragonalex.spellandweapon.element.ElementType;
import net.minecraft.core.BlockPosition;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.level.pathfinder.PathEntity;

public abstract class CustomEntity {
	
	protected LivingEntity entity;
	protected LivingEntity killer;
	protected int level;
	protected int attack;
	protected int regeneration;
	protected int defense;
	protected int maxHealth;
	protected int health;
	protected List<Element> listElements;
	protected List<ElementType> listElementsImmune;
	private List<Mob> mobsWhoHaveThisInTarget;
	
	public CustomEntity(LivingEntity entity, int level) {
		this.entity = entity;
		this.level = level;
		this.killer = null;
		this.listElements = new ArrayList<Element>();
		this.listElementsImmune = new ArrayList<ElementType>();
		if (!(this.getEntity() instanceof Player)) {
			
		}
		this.mobsWhoHaveThisInTarget = new ArrayList<Mob>();
	}
	
	public abstract void death();
	
	public abstract void updateStats();
	
	public abstract void updateCustomHealthBar();
	
	public abstract void updateHealth();
	
    public void moveTo(LivingEntity e, Location moveTo, int speed) {
//        // Create a path to the location
//        PathEntity path = ((EntityInsentient)((CraftLivingEntity) e).getHandle()).getNavigation().a(new BlockPosition(moveTo.getX(), moveTo.getY(), moveTo.getZ()), speed);
//        //.a(moveTo.getX(), moveTo.getY(), moveTo.getZ());
//        		
//        // Move to that path at 'speed' speed.
//        ((EntityInsentient)((CraftLivingEntity) e).getHandle()).getNavigation().a(path, speed);
    }
    
	///////////////////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	//////////////////////////////////Getters & Setters\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	///////////////////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	
	public LivingEntity getEntity() {
		return this.entity;
	}
	
	public LivingEntity getKiller() {
		return this.killer;
	}
	
	public void setKiller(LivingEntity killer) {
		this.killer = killer;
	}
	
	public int getLevel() {
		return this.level;
	}
	
	public int getAttack() {
		return this.attack;
	}
	
	public int getRegeneration() {
		return this.regeneration;
	}
	
	public int getDefense() {
		return this.defense;
	}
	
	public int getMaxHealth() {
		return this.maxHealth;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
	public void setAttack(int attack) {
		this.attack = attack;
	}
	
	public void setRegeneration(int regeneration) {
		this.regeneration = regeneration;
	}
	
	public void setDefense(int defense) {
		this.defense = defense;
	}
	
	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}
	
	///////////////////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	/////////////////////////////////////////Damage\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	///////////////////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	
	public int getHealth() {
		return this.health;
	}
	
	public void setHealth(int health) {
		if (health >= this.maxHealth) {
			health = this.maxHealth;
		}
		this.health = health;
		if (health <= 0) {
			this.health = 0;
			this.kill();
		}
		this.updateHealth();
		this.updateCustomHealthBar();
	}
	
	public void kill() {
		for (Mob mob : this.mobsWhoHaveThisInTarget) {
			mob.setTarget(null);
		}
		this.death();
	}
	
	public void damage(int damage) {
		int finalDamege = (int)Math.round((100/(100.0f+(float)this.defense))*damage);
		this.setHealth(this.health-finalDamege);
	}
	
	public void damage(int damage,LivingEntity damager) {
		this.killer = damager;
		this.damage(damage);
	}
	
	public void regeneration() {
		this.setHealth(this.regeneration+this.health);
	}
	
	public void heal(int health) {
		this.setHealth(this.getHealth()+health);
	}
	
	/////////////////////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	/////////////////////////////////////////Elements\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	/////////////////////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	
	public List<Element> getListElements() {
		return this.listElements;
	}
	
	public boolean isImmune(ElementType elementType) {
		return this.listElementsImmune.contains(elementType);
	}
	
	public @Nullable Element getElement(ElementType elementType) {
		for (Element element : this.getListElements()) {
			if (element.getType() == elementType) {
				return element;
			}
		}
		return null;
	}
	
	public boolean hasElement(ElementType elementType) {
		for (Element element : this.getListElements()) {
			if (element.getType() == elementType) {
				return true;
			}
		}
		return false;
	}
	
	private boolean reactionsElement(Element elementToAdd) {
		//reactions elementaires glace et eau
		if (elementToAdd.getType()==ElementType.ICE && this.hasElement(ElementType.FIRE)) {
			Element tempWaterElement = new Element(ElementType.WATER, Math.floorDiv(elementToAdd.getPower()+this.getElement(ElementType.FIRE).getPower(), 2));
			this.removeElement(ElementType.FIRE);
			this.addElement(tempWaterElement);
			return true;
		}
		if (elementToAdd.getType()==ElementType.FIRE && this.hasElement(ElementType.ICE)) {
			Element tempWaterElement = new Element(ElementType.WATER, Math.floorDiv(elementToAdd.getPower()+this.getElement(ElementType.ICE).getPower(), 2));
			this.removeElement(ElementType.ICE);
			this.addElement(tempWaterElement);
			return true;
		}
		//reactions elementaires eau et feu
		if (elementToAdd.getType()==ElementType.FIRE && this.hasElement(ElementType.WATER)) {
			return true;
		}
		if (elementToAdd.getType()==ElementType.WATER && this.hasElement(ElementType.FIRE)) {
			for (Element element : this.getListElements()) {
				if (element.getType()==ElementType.FIRE) {
					this.getListElements().remove(element);
					this.getEntity().setFireTicks(0);
					return true;
				}
			}
		}
		///////////////////////////////////////////////
		//reactions elementaires vents et tous les autres
		if (elementToAdd.getType()==ElementType.WIND) {
			Location location = this.getEntity().getLocation();
			this.getEntity().getWorld().spawnParticle(Particle.CLOUD,location,Math.floorDiv(elementToAdd.getPower(), 3));
			for (Element element : this.getListElements()) {
				element.setPower(element.getPower()-elementToAdd.getPower());
			}
			return true;
		}
		return false;
	}
	
	public void addElement(Element elementToAdd) {
		if (elementToAdd == null) return;
		if (this.isImmune(elementToAdd.getType())) return;
		if (!this.reactionsElement(elementToAdd)) {
			//ajouts de l'éléments + addition des puissances
			for (Element element : this.getListElements()) {
				if (element.getType() == elementToAdd.getType()) {
					element.setPower(element.getPower()+elementToAdd.getPower());
					this.updateElementEffects();
					return;
				}
			}
			this.getListElements().add(elementToAdd);
			this.updateElementEffects();
		}
	}
	
	public void updateElementEffects() {
		for (Element element : listElements) {
			switch (element.getType()) {
			case FIRE:
				this.getEntity().setFireTicks(element.getPower());
				break;
			case ICE:
				this.getEntity().setFreezeTicks(140);
				this.getEntity().removePotionEffect(PotionEffectType.SLOW);
				this.getEntity().removePotionEffect(PotionEffectType.JUMP);
				this.getEntity().addPotionEffect(new PotionEffect(PotionEffectType.SLOW,element.getPower(),10,false,false));
				this.getEntity().addPotionEffect(new PotionEffect(PotionEffectType.JUMP,element.getPower(),-10,false,false));
				break;
			case WATER:
				this.getEntity().removePotionEffect(PotionEffectType.SLOW_DIGGING);
				this.getEntity().addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING,element.getPower(),2,false,false));
				break;
			case DARK:
				this.getEntity().removePotionEffect(PotionEffectType.BLINDNESS);
				this.getEntity().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,element.getPower(),0,false,false));
				this.getEntity().removePotionEffect(PotionEffectType.WITHER);
				this.getEntity().addPotionEffect(new PotionEffect(PotionEffectType.WITHER,element.getPower(),0,false,false));
				break;
			case VEGETAL:
				this.getEntity().removePotionEffect(PotionEffectType.POISON);
				this.getEntity().addPotionEffect(new PotionEffect(PotionEffectType.POISON,element.getPower(),0,false,false));
				break;
			case ELECTRICITY:
				element.setLightningStriker(this.getEntity(),element);
				break;
			case STONE:
				this.getEntity().removePotionEffect(PotionEffectType.JUMP);
				this.getEntity().addPotionEffect(new PotionEffect(PotionEffectType.JUMP,element.getPower(),-10,false,false));
				break;
			case LIGHT:
				this.getEntity().removePotionEffect(PotionEffectType.GLOWING);
				this.getEntity().addPotionEffect(new PotionEffect(PotionEffectType.GLOWING,element.getPower(),0,false,false));
				Iterator<Entity> loop = this.getEntity().getNearbyEntities(30, 5, 30).iterator();
				while (loop.hasNext()) {
					Entity entity = loop.next();
					if (entity instanceof Mob) {
						Mob mob = (Mob)entity;
						mob.setTarget(this.getEntity());
						this.mobsWhoHaveThisInTarget.add(mob);
					}
				}
			default:
				break;
			}
		}
	}
	
	public boolean removeElement(ElementType elementType) {
		switch(elementType) {
		case FIRE:
			this.getEntity().setFireTicks(0);
			break;
		case WATER:
			this.getEntity().removePotionEffect(PotionEffectType.SLOW_DIGGING);
			break;
		case ICE:
			this.getEntity().removePotionEffect(PotionEffectType.JUMP);
			this.getEntity().removePotionEffect(PotionEffectType.SLOW);
			break;
		case DARK:
			this.getEntity().removePotionEffect(PotionEffectType.BLINDNESS);
			this.getEntity().removePotionEffect(PotionEffectType.WITHER);
			break;
		case VEGETAL:
			this.getEntity().removePotionEffect(PotionEffectType.POISON);
			break;
		case STONE:
			this.getEntity().removePotionEffect(PotionEffectType.JUMP);
			break;
		default:
			break;
		}
		for (Element element : this.getListElements()) {
			if (element.getType() == elementType) {
				this.getListElements().remove(element);
				if (elementType == ElementType.ELECTRICITY) {
					if (!element.getLightningStriker().isCancelled()) {
						element.getLightningStriker().strikeLighting();
					}
				}
				return true;
			}
		}
		return false;
	}
	
	public void clearElements() {
		for (ElementType elementType : ElementType.values()) {
			this.removeElement(elementType);
		}
	}

}
