package fr.firedragonalex.spellandweapon.quests.steps.notabstracts.defaults;

import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Location;

import fr.firedragonalex.spellandweapon.quests.steps.abstracts.AbstractQuestStep;
import fr.firedragonalex.spellandweapon.quests.steps.abstracts.AbstractQuestStepDefault;

public class QuestStepGoToLocation extends AbstractQuestStepDefault {
	
	private UUID areaUuidPlayerMustToGo;
	private Location coordPrint;
	private String messageToPrint;
	
	public QuestStepGoToLocation(String areaUuidPlayerMustToGo, Location coordPrint) {
		this.areaUuidPlayerMustToGo = UUID.fromString(areaUuidPlayerMustToGo);
		this.coordPrint = coordPrint;
		this.messageToPrint = null;
	}
	
	public QuestStepGoToLocation(String areaUuidPlayerMustToGo, Location coordPrint, String messageToPrint) {
		this.areaUuidPlayerMustToGo = UUID.fromString(areaUuidPlayerMustToGo);
		this.coordPrint = coordPrint;
		this.messageToPrint = messageToPrint;
	}
	
	public UUID getAreaUuidPlayerMustToGo() {
		return this.areaUuidPlayerMustToGo;
	}
	
	public Location getCoordPrint() {
		return this.coordPrint;
	}

	@Override
	public AbstractQuestStep clone() {
		return new QuestStepGoToLocation(this.areaUuidPlayerMustToGo.toString(), this.coordPrint);
	}

	@Override
	public String toPrint() {
		if (this.messageToPrint == null) {
			return "Dirigez vous vers les coordonnées "+ChatColor.GREEN+coordPrint.getBlockX()+" "+coordPrint.getBlockY()+" "+coordPrint.getBlockZ()+ChatColor.YELLOW+" du monde "+ChatColor.GREEN+coordPrint.getWorld().getName()+ChatColor.YELLOW+".";
		} else {
			return this.messageToPrint + "("+ChatColor.GREEN+coordPrint.getBlockX()+" "+coordPrint.getBlockY()+" "+coordPrint.getBlockZ()+ChatColor.YELLOW+" du monde "+ChatColor.GREEN+coordPrint.getWorld().getName()+ChatColor.YELLOW+")";
		}
		
	}

	@Override
	public void checkFinish() { }

	@Override
	public int getAdvencementStep() {
		return 0;
	}
	
	@Override
	public void setAdvencementStep(int advencementStep) {}
}
