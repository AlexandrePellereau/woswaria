package fr.firedragonalex.zombieapocalypsepvpve;

import org.bukkit.boss.BarColor;
import org.bukkit.boss.BossBar;

public enum FPeriod {
	PREHISTOIRE(1,3,30,100,BarColor.RED),ANTIQUITE(2,10,10,100,BarColor.YELLOW),MOYEN_AGE(3,1,10,100,BarColor.WHITE),RENAISSANCE(4,1,10,100,BarColor.YELLOW),TEMPS_MODERNES(5,1,10,100,BarColor.BLUE),APOCALYPSE(6,1,10,100,BarColor.PURPLE);
	//PREHISTOIRE(3,30,BarColor.RED) le vrai
	private int placeInTheTime;
	private int nbZombies;
	private int cooldownBetweenWaves;
	private int nbRottenFleshForNextPeriod;
	private BarColor colorPeriod;
	
	private FPeriod( int placeInTheTime ,int nbZombies, int cooldownBetweenWaves, int nbRottenFleshForNextPeriod , BarColor colorPeriod ) {
		this.placeInTheTime = placeInTheTime;
		this.nbZombies = nbZombies;
		this.cooldownBetweenWaves = cooldownBetweenWaves;
		this.nbRottenFleshForNextPeriod = nbRottenFleshForNextPeriod;
		this.colorPeriod = colorPeriod;
	}
	
	public int getPlaceInTheTime() {
		return placeInTheTime;
	}
	
	public int getNbZombies() {
		return nbZombies;
	}
	
	public int getCooldownBetweenWaves() {
		return cooldownBetweenWaves;
	}
	
	public int getNbRottenFleshForNextPeriod() {
		return nbRottenFleshForNextPeriod;
	}
	
	public BarColor getColorPeriod() {
		return colorPeriod;
	}
}
