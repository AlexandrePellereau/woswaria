package fr.firedragonalex.spellandweapon.custom.code;

import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;

import fr.firedragonalex.spellandweapon.Main;
import fr.firedragonalex.spellandweapon.custom.code.custommonsterspawner.CustomMonsterSpawner;
import fr.firedragonalex.spellandweapon.custom.easytoadd.CustomMonsterType;
import fr.firedragonalex.spellandweapon.element.Element;
import fr.firedragonalex.spellandweapon.quests.steps.notabstracts.defaults.QuestStepKillMonster;

public class CustomMonster extends CustomEntity {
	
	private CustomMonsterType customMonsterType;
	private CustomMonsterSpawner customMonsterSpawner;
	
	public CustomMonster(CustomMonsterType customMonsterType,int level, Location location) {
		super((LivingEntity)location.getWorld().spawnEntity(location, customMonsterType.getEntityType()),level);
		this.customMonsterType = customMonsterType;
		this.customMonsterSpawner = null;
		this.updateStats();
		this.health = this.maxHealth;
		this.listElementsImmune = customMonsterType.getListElementsImmune();
		if (this.getEntity().getType() == EntityType.SLIME) {
			Slime slime = (Slime)this.getEntity();
			int size;
			if (level == 0) {
				size = 1;
			} else if (level >= 25){
				size = 5;
			} else {
				size = Math.floorDiv(level, 5);
			}
			slime.setSize(size);
		}
		this.getEntity().getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(this.getMaxHealth());
		this.updateCustomHealthBar();
		this.getEntity().setCustomNameVisible(true);
		Main.getAllCustomEntities().add((CustomEntity)this);
	}
	
	public int getXpDrop() {
		return this.getLevel()*this.getCustomMonsterType().getXpGiven();
	}
	
	public CustomMonsterType getCustomMonsterType() {
		return this.customMonsterType;
	}
	
	public CustomMonsterSpawner getCustomMonsterSpawner() {
		return this.customMonsterSpawner;
	}
	
	public void remove() {
		Main.getAllCustomEntities().remove(this);
		this.entity.remove();
	}
	
	@Override
	public void updateCustomHealthBar() {
		String stringToBePrint = this.level+" | "+this.customMonsterType.getName()+" | "+this.health+"§c❤";
		//String constantPrint = " .:-=+*#%@";
		String constantPrint = "0123456789+";
		if(this.getListElements().size() > 0) {
			stringToBePrint += "§f | ";
			for (Element element : this.listElements) {
				
				int index = Math.floorDiv(element.getPower(), 20);
				if (index > constantPrint.length()-1) {
					index = constantPrint.length()-1;
				}
				if (index <= 0) index = 0;
				stringToBePrint += element.getType().getColor() + constantPrint.charAt(index);
			}
		}
		this.getEntity().setCustomName(stringToBePrint);
	}

	@Override
	public void death(){
		if (this.killer instanceof Player) {
			CustomPlayer customPlayer = Main.getCustomPlayerByPlayer((Player)this.killer);
			if (customPlayer != null) {
				if (customPlayer.getCurrentQuest().getStep() instanceof QuestStepKillMonster) {
					QuestStepKillMonster step = (QuestStepKillMonster)customPlayer.getCurrentQuest().getStep();
					if (step.getTypeOfMonsterToKill() == this.customMonsterType) {
						step.killMonster();
					}
				}
			}
		}
		for (CustomMonsterSpawner customMonsterSpawner : Main.getAllCustomMonsterSpawners()) {
			if (customMonsterSpawner.getAllCustomMonstersOwned().remove(this)) {
				customMonsterSpawner.trySpawn();
				return;
			}
		}
	}

	@Override
	public void updateHealth() {
		this.getEntity().setHealth(this.health);
	}

	@Override
	public void updateStats() {
		this.setAttack((int)Math.round(level*(this.customMonsterType.getAttack()/10.0)+this.customMonsterType.getAttack()));
		this.setRegeneration((int)Math.round(level*(this.customMonsterType.getRegeneration()/10.0)+this.customMonsterType.getRegeneration()));
		this.setDefense((int)Math.round(level*(this.customMonsterType.getDefense()/10.0)+this.customMonsterType.getDefense()));
		this.setMaxHealth((int)Math.round(level*(this.customMonsterType.getMaxHealth()/10.0)+this.customMonsterType.getMaxHealth()));
	}
}
