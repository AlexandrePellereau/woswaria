package fr.firedragonalex.spellandweapon.spell;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import fr.firedragonalex.spellandweapon.custom.code.CustomPlayer;

public enum Spell {
	
	SHOOT_FIREBALL(SpellDifficulty.EASY, BarColor.RED, Arrays.asList("pan","pan")),
	SPAWN_TELEPORT(SpellDifficulty.EASY, BarColor.BLUE, Arrays.asList("woswariaworld","teleportme")),
	TESTWORLD_TELEPORT(SpellDifficulty.EASY, BarColor.BLUE, Arrays.asList("testworld","teleportme")),
	FARMWORLD_TELEPORT(SpellDifficulty.EASY, BarColor.BLUE, Arrays.asList("farmworld","teleportme")),
	HELP(SpellDifficulty.EASY, BarColor.BLUE, Arrays.asList("help","spellbook")),
	;
	
	private SpellDifficulty difficulty;
	private BarColor color;
	private List<String> formula;
	
	private Spell(SpellDifficulty difficulty, BarColor color, List<String> formula) {
		this.difficulty = difficulty;
		this.formula = formula;
		this.color = color;
	}
	
	public SpellDifficulty getDifficulty() {
		return this.difficulty;
	}
	
	public BarColor getColor() {
		return this.color;
	}
	
	public List<String> getFormula() {
		return this.formula;
	}
	
	public void cast(CustomPlayer customPlayer) {
		switch (this) {
		case HELP:
			customPlayer.getPlayer().sendMessage(ChatColor.YELLOW+"Voici la liste des sorts :");
			for (Spell spell : Spell.values()) {
				customPlayer.getPlayer().sendMessage(ChatColor.YELLOW+"- "+spell.getFormula().get(0));
			}
			break;
		case SHOOT_FIREBALL:
			Fireball fireball = (Fireball)customPlayer.getPlayer().getWorld().spawnEntity(customPlayer.getPlayer().getLocation(), EntityType.FIREBALL);
			fireball.setDirection(customPlayer.getPlayer().getEyeLocation().getDirection());
			fireball.setShooter(customPlayer.getPlayer());
			fireball.setYield(100);
			fireball.setIsIncendiary(true);
			break;
		case SPAWN_TELEPORT:
			customPlayer.getPlayer().teleport(new Location(Bukkit.getWorld("WoswariaWorld"), 0, 70, 0), TeleportCause.PLUGIN);
			break;
		case TESTWORLD_TELEPORT:
			//Bukkit.broadcastMessage("WoswariaTestWorld:"+Bukkit.getWorld("WoswariaTestWorld"));
			customPlayer.getPlayer().teleport(new Location(Bukkit.getWorld("WoswariaTestWorld"), 0, 70, 0), TeleportCause.PLUGIN);
			break;
		case FARMWORLD_TELEPORT:
			customPlayer.getPlayer().teleport(new Location(Bukkit.getWorld("WoswariaFarmWorld"), 0, 70, 0), TeleportCause.PLUGIN);
			break;
		default:
			break;
		}
		return;
	}
}
