package fr.firedragonalex.zombieapocalypsepvpve;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import fr.firedragonalex.zombieapocalypsepvpve.commands.MyCommands;
import fr.firedragonalex.zombieapocalypsepvpve.listeners.FDamageListeners;
import fr.firedragonalex.zombieapocalypsepvpve.listeners.FInteractsListeners;
import fr.firedragonalex.zombieapocalypsepvpve.listeners.FPlayerListeners;
import fr.firedragonalex.zombieapocalypsepvpve.tasks.FSpawnZombies;

public class FMain extends JavaPlugin{
	
	private World myWorld;
	private int nbPlayerForStart;
	private int timeOfTheLastWave;
	private int advancementPeriod;
	private int advancementApocalypse;
	private int nbPlayersAtStart;
	private List<Player> players;
	private List<Integer> sizeOfTheMap;
	private List<FPeriod> allThePeriod;
	private FState state;
	private FPeriod period;
	private BossBar BossBarPeriod;
	private BossBar BossBarNbZombies;
	
	@Override
	public void onEnable() {
		nbPlayerForStart = 2;
		timeOfTheLastWave = 0;
		advancementApocalypse = 0;
		players = new ArrayList<Player>();
		sizeOfTheMap = new ArrayList<Integer>();
		myWorld = Bukkit.getWorld("world");
		sizeOfTheMap.add(25);//x for the border
		sizeOfTheMap.add(25);//y for the border
		allThePeriod = new ArrayList<FPeriod>();
		allThePeriod.add(FPeriod.PREHISTOIRE);
		allThePeriod.add(FPeriod.ANTIQUITE);
		allThePeriod.add(FPeriod.MOYEN_AGE);
		allThePeriod.add(FPeriod.RENAISSANCE);
		allThePeriod.add(FPeriod.TEMPS_MODERNES);
		allThePeriod.add(FPeriod.APOCALYPSE);
		advancementPeriod = 0;
		advancementApocalypse = 0;
		setState(FState.WAITING);
		setPeriod(FPeriod.PREHISTOIRE);
		BossBarPeriod = Bukkit.createBossBar("Periode : "+getPeriod().name(), getPeriod().getColorPeriod(), BarStyle.SOLID, BarFlag.PLAY_BOSS_MUSIC);
		BossBarPeriod.setProgress(0);
		BossBarPeriod.setVisible(true);
		BossBarNbZombies = Bukkit.createBossBar("Nombre de zombies vivants", BarColor.GREEN, BarStyle.SOLID, BarFlag.PLAY_BOSS_MUSIC);
		BossBarNbZombies.setVisible(true);
		System.out.println("[ZombieApocalypsePvPvE] Starting enable !");
		getCommand("join").setExecutor(new MyCommands());
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new FPlayerListeners(this), this);
		pm.registerEvents(new FDamageListeners(this), this);
		pm.registerEvents(new FInteractsListeners(this), this);
		FSpawnZombies spawnZombies = new FSpawnZombies(this);
		spawnZombies.runTaskTimer(this, 0, 20);
		System.out.println("[ZombieApocalypsePvPvE] Plugin enabled !");
	}
	
	public void updatePeriod(){
		if (getAdvancementPeriod()>=getPeriod().getNbRottenFleshForNextPeriod()) {
			setAdvancementPeriod(getAdvancementPeriod()-getPeriod().getNbRottenFleshForNextPeriod());
			int myIndex = getPeriod().getPlaceInTheTime();
			//getAllThePeriod().get(myIndex);
			if (getPeriod()==FPeriod.APOCALYPSE) {
				advancementApocalypse+=1;
			}
			setPeriod(getAllThePeriod().get(myIndex));
			getBossBarPeriod().setTitle("Periode : "+getPeriod().name());
			getBossBarPeriod().setColor(getPeriod().getColorPeriod());
			for (Player myPlayer : players) {
				myPlayer.sendTitle("§cNouvelle période !", getPeriod().name(), 1, 20*10, 1);
			}
		}
		Bukkit.broadcastMessage("nb avancement : "+getAdvancementPeriod());
		getBossBarPeriod().setProgress(getAdvancementPeriod()*1.0/getPeriod().getNbRottenFleshForNextPeriod()*1.0);
	}
	
	public void setNbPlayersAtStart(int nb){
		this.nbPlayersAtStart = nb;
	}
	
	public int getNbPlayersAtStart(){
		return this.nbPlayersAtStart;
	}
	
	public BossBar getBossBarPeriod(){
		return this.BossBarPeriod;
	}
	
	public List<FPeriod> getAllThePeriod(){
		return this.allThePeriod;
	}
	public int getAdvancementPeriod(){
		return this.advancementPeriod;
	}
	
	public void setAdvancementPeriod(int nb){
		this.advancementPeriod = nb;
	}
	
	public BossBar getBossBarNbZombies(){
		return this.BossBarNbZombies;
	}
	
	public void setNbPlayerForStart(Integer nb){
		this.nbPlayerForStart = nb;
	}
	
	public void setState(FState state) {
		this.state = state;
	}
	
	public boolean isState(FState state) {
		return this.state == state;
	}
	
	public FPeriod getPeriod() {
		return this.period;
	}
	
	public void setPeriod(FPeriod period) {
		this.period = period;
	}
	
	public boolean isPeriod(FPeriod period) {
		return this.period == period;
	}
	
	public List<Player> getPlayers() {
		return this.players;
	}
	
	public int getNbPlyerForStart() {
		return this.nbPlayerForStart;
	}
	
	public void setTimeOfTheLastWave(int nb) {
		this.timeOfTheLastWave = nb;
	}
	
	public int getTimeOfTheLastWave() {
		return this.timeOfTheLastWave;
	}
	
	public World getWorld() {
		return this.myWorld;
	}
	
	public List<Integer> getSizeOfTheMap(){
		return this.sizeOfTheMap;
	}
	
	public void checkWin() {
		if (isState(FState.PLAYING)) {
			if (players.size() == 1) {
				Player player = players.get(0);
				Bukkit.broadcastMessage(player.getName()+" a gagné la partie !");
				//Bukkit.spigot().restart();
			}
		}
	}
	
	@Override
	public void onDisable() {
		System.out.println("[ZombieApocalypsePvPvE] Plugin disabled !");
		//spawnZombies.cancel();
	}
}
