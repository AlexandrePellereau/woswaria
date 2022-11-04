package fr.firedragonalex.spellandweapon.custom.code;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import fr.firedragonalex.spellandweapon.Main;
import fr.firedragonalex.spellandweapon.custom.easytoadd.CustomArmor;
import fr.firedragonalex.spellandweapon.element.Element;
import fr.firedragonalex.spellandweapon.element.ElementType;
import fr.firedragonalex.spellandweapon.quests.Quest;
import fr.firedragonalex.spellandweapon.quests.QuestInProgress;
import fr.firedragonalex.spellandweapon.saveandload.SaveAndLoad;
import fr.firedragonalex.spellandweapon.spell.CastSpell;
import fr.firedragonalex.spellandweapon.spell.Spell;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.core.particles.ParticleParam;

public class CustomPlayer extends CustomEntity{
	
	private Player player;
	private CustomArmor[] customArmorEquipements = {null, null, null, null};
	
	private int xp;
	private int totalXp;
	
	private Spell spell;
	private int advancementFormula;
	private boolean isCastingSpell;
	private CastSpell castSpell;
	
	private QuestInProgress currentQuest;
	private List<QuestInProgress> quests;
	private List<Quest> questsCompleted;
	
	public CustomPlayer(Player player) {
		super(player, 0);
		this.player = player;
		this.updateStats();
		this.health = (int)player.getHealth()*(this.maxHealth/20);
		this.isCastingSpell = false;
		this.advancementFormula = 0;
		this.xp = 0;
		this.totalXp = 0;
		this.quests = new ArrayList<>();
		this.questsCompleted = new ArrayList<>();
	}
	
	///////////////////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	//////////////////////////////////Getters & Setters\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	///////////////////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	
	public Player getPlayer() {
		return this.player;
	}
	
	public CustomArmor[] getCustomArmorEquipements() {
		return this.customArmorEquipements;
	}
	
	public void setCustomArmor(int equipementNumber, CustomArmor customArmor) {
		this.customArmorEquipements[equipementNumber] = customArmor;
	}
	
	////////////////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	//////////////////////////////////Levels & Xp\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	////////////////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	
	public void setXp(int xp) {
		this.xp = xp;
		//this.totalXp = xp;
		this.isLevelUp();
	}
	
	public void addXp(int xp) {
		this.xp += xp;
		this.totalXp += xp;
		this.getPlayer().sendMessage("§a+ "+xp+" xp");
		this.isLevelUp();
	}
	
	public void isLevelUp() {
		while (this.xp >= this.xpNeedToLevel(this.level+1)) {
			this.xp -= this.xpNeedToLevel(level+1);
			this.level += 1;
			this.updateStats();
		}
		this.updateXp();
	}
	
	public void updateXp() {
		this.getPlayer().setLevel(this.level);
		this.getPlayer().setExp((float)(this.xp*1.0/this.xpNeedToLevel(this.level+1)));
		this.getPlayer().sendExperienceChange((float)(this.xp*1.0/this.xpNeedToLevel(this.level+1)));
	}
	
	public int getXp() {
		return this.xp;
	}
	
	public int getTotalXp() {
		return this.totalXp;
	}
	
	public int xpNeedToLevel(int level) {
		return level*50;
	}
	
	public int totalXpToLevels(int totalXp) {
		int level = 0;
		while (totalXp >= this.xpNeedToLevel(level+1)) {
			totalXp -= this.xpNeedToLevel(level);
			level += 1;
		}
		return level;
	}
	
	////////////////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	/////////////////////////////////////Quests\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	////////////////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	
	public QuestInProgress getCurrentQuest() {
		return this.currentQuest;
	}
	
	public List<QuestInProgress> getQuests() {
		return this.quests;
	}
	
	public List<Quest> getQuestsCompleted() {
		return this.questsCompleted;
	}
	
	public boolean canAddQuest(Quest quest) {
		if (this.hasUnlockQuest(quest)) return false;
		if (this.getLevel() < quest.getMinimumLevel()) return false;
		for (Quest questLoop : quest.getQuestsToDoBefore()) {
			if (!(this.getQuestsCompleted().contains(questLoop))) return false;
		}
		return true;
	}
	
	public QuestInProgress addQuest(Quest quest) {
		if (this.canAddQuest(quest)) {
			QuestInProgress questInProgress = new QuestInProgress(quest, this);
			this.quests.add(questInProgress);
			return questInProgress;
		} else {
			return null;
		}
	}
	
	public boolean hasUnlockQuest(Quest quest) {
		for (Quest quest2 : this.getAllQuests()) {
			if (quest == quest2) {
				return true;
			}
		}
		return false;
	}
	
	public boolean containsQuestInProgress(QuestInProgress quest) {
		for (QuestInProgress quest2 : this.getQuests()) {
			if (quest == quest2) {
				return true;
			}
		}
		return false;
	}
	
	private void setCurrentQuest(QuestInProgress questInProgress) {
		this.currentQuest = questInProgress;
	}
	
	public void setCurrentQuest(Quest quest) {
		if (this.hasUnlockQuest(quest)) {
			for (QuestInProgress questInProgress : this.getQuests()) {
				if (questInProgress.getQuest() == quest) {
					this.setCurrentQuest(questInProgress);
				}
			}
		} else {
			QuestInProgress questInProgress = this.addQuest(quest);
			this.setCurrentQuest(questInProgress);
		}
	}
	
	public List<Quest> getLockQuests() {
		List<Quest> allQuests = new ArrayList<>();
		for (Quest quest : Quest.values()) {
			if (!(this.hasUnlockQuest(quest))) {
				allQuests.add(quest);
			}
		}
		return allQuests;
	}
	
	private List<Quest> getAllQuests() {
		List<Quest> allQuests = new ArrayList<>();
		allQuests.addAll(this.questsCompleted);
		for (QuestInProgress questInProgress : this.quests) {
			allQuests.add(questInProgress.getQuest());
		}
		return allQuests;
	}
	
	public void finishQuest(Quest quest) {
		for (QuestInProgress questInProgress : this.getQuests()) {
			if (questInProgress.getQuest() == quest) {
				this.currentQuest = null;
				this.getQuests().remove(questInProgress);
				this.getQuestsCompleted().add(quest);
				this.getPlayer().sendTitle(ChatColor.GREEN+"Quête finie !", null, 10, 40, 20);
				SaveAndLoad.removeQuest(this.getPlayer().getUniqueId(), quest);
				return;
			}
		}
	}
	
	////////////////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	/////////////////////////////////////Spells\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	////////////////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	
	public boolean isCastingSpell() {
		return this.isCastingSpell;
	}
	
	public Spell getSpell() {
		return this.spell;
	}
	
	public int getAdvancementFormula() {
		return this.advancementFormula;
	}
	
	public CastSpell getCastSpell() {
		return this.castSpell;
	}
	
	public void setIsCastingSpell(boolean isCastingSpell) {
		this.isCastingSpell = isCastingSpell;
	}
	
	public void setSpell(Spell spell) {
		this.spell = spell;
	}
	
	public void setAdvancementFormula(int advancementFormula) {
		this.advancementFormula = advancementFormula;
	}
	
	public void startSpell() {
		this.castSpell = new CastSpell(this);
		this.castSpell.runTaskTimer(Main.getInstance(), 0, 1);
	}
	
	////////////////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	///////////////////////////////////Abstracts\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	////////////////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	
	
	@Override
	public void updateCustomHealthBar() {
		if (this.getPlayer() == null) return;
		if (!(this.getPlayer().getGameMode()==GameMode.CREATIVE)) {
			//String constantPrint = ".:-=+*#%@";
			String constantPrint = "0123456789+";
			String stringToBePrint = this.getHealth()+"/"+this.getMaxHealth()+"❤";
			if(this.getListElements().size() > 0) {
				stringToBePrint += " | ";
				for (Element element : this.listElements) {
					
					int index = Math.floorDiv(element.getPower(), 20);
					if (index > constantPrint.length()-1) {
						index = constantPrint.length()-1;
					}
					if (index <= 0) index = 0;
					stringToBePrint += element.getType().getColor() + constantPrint.charAt(index);
				}
			}
			TextComponent text_component = new TextComponent(stringToBePrint);
			text_component.setColor(net.md_5.bungee.api.ChatColor.RED);
			this.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, text_component);
		}
	}
	
	@Override
	public void death() {
		this.clearElements();
		this.setHealth(this.maxHealth);
		this.player.setFoodLevel(20);
		if (this.player.getBedSpawnLocation() == null) {
			this.player.teleport(this.player.getWorld().getSpawnLocation());
		} else {
			this.player.teleport(this.player.getBedSpawnLocation());
		}
		//this.player.kickPlayer("Tu es mort ! En temps normal, tu serais ban 5 min !");
		this.player.sendTitle("§cEnfer !", null, 0, 70, 20);
	}

	@Override
	public void updateHealth() {
		if (health > maxHealth) {
			health = maxHealth;
		}
		if (health > 0) {
			this.getEntity().setHealth((this.health*1.0/this.maxHealth*1.0)*20);
		}
	}

	@Override
	public void updateStats() {
		int baseAttack = 1;
		int baseRegeneration = 1;
		int baseDefense = 0;
		int baseMaxHealth = 100;
		this.setAttack((int)Math.round(level*(baseAttack/10.0)+baseAttack));
		this.setRegeneration((int)Math.round(level*(baseRegeneration/10.0)+baseRegeneration));
		this.setDefense((int)Math.round(level*(baseDefense/10.0)+baseDefense));
		this.setMaxHealth((int)Math.round(level*(baseMaxHealth/10.0)+baseMaxHealth));
		for (CustomArmor customArmor : this.customArmorEquipements) {
			if (customArmor != null) {
				this.setDefense(this.getDefense() + customArmor.getDefense());
				this.setMaxHealth(this.getMaxHealth() + customArmor.getHealth());
			}
		}
		this.updateHealth();
	}

}
