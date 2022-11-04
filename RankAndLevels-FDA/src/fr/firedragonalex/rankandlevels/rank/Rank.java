package fr.firedragonalex.rankandlevels.rank;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;

import fr.firedragonalex.rankandlevels.Main;

public enum Rank {
	
	CITOYEN    ("Citoyen",ChatColor.GRAY,Arrays.asList()),
	BOURGEOIS  ("Bourgeois",ChatColor.WHITE,Arrays.asList()),
	BARON      ("Baron",ChatColor.YELLOW,Arrays.asList()),
	COMTE      ("Comte",ChatColor.GOLD,Arrays.asList()),
	DUC        ("Duc",ChatColor.AQUA,Arrays.asList()),
	ARISTOCRATE("Aristocrate",ChatColor.BLUE,Arrays.asList()),
	SEIGNEUR   ("Seigneur",ChatColor.LIGHT_PURPLE,Arrays.asList()),
	PRINCE     ("Prince",ChatColor.RED,Arrays.asList()),
	
	ROI        ("Roi",ChatColor.RED,Arrays.asList()),// Réservé à FireDragonAlex (c'est pour le Role Play)
	ANGE       ("Ange",ChatColor.DARK_RED,Arrays.asList()),
	ADMIN      ("Dieux",ChatColor.DARK_RED,Arrays.asList());
	
	private String name;
	private ChatColor color;
	private List<RankPermissions> allRankPermissions;
	
	private Rank(String name,ChatColor color, List<RankPermissions> allRankPermissions) {
		this.name = name;
		this.color = color;
		this.allRankPermissions = allRankPermissions;
	}
	
	public String getName() {
		return this.name;
	}
	
	public ChatColor getColor() {
		return this.color;
	}
	
	public boolean hasPermission(RankPermissions rankPermission) {
		return allRankPermissions.contains(rankPermission);
	}
	
	public int getLevel() {
		List<Rank> allRanks = Arrays.asList(Rank.values());
		return allRanks.indexOf(this);
	}
	
	public boolean equalsOrBetter(Rank rank) {
		return rank.getLevel() <= this.getLevel();
	}
	
}
