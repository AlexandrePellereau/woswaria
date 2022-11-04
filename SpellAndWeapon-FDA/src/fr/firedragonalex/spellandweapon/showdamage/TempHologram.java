package fr.firedragonalex.spellandweapon.showdamage;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;



public class TempHologram extends BukkitRunnable {
	
	private int timer;
	private ArmorStand tempHologramArmorStand;
	//private EntityArmorStand tempHologramArmorStandNMS;

	public TempHologram(Double nbDamage, String color, Location coordinates) {
		boolean isOnlyClient = true;
		
		this.tempHologramArmorStand = null;
		//this.tempHologramArmorStandNMS = null;
		
		if (isOnlyClient) {
//			WorldServer worldServer = ((CraftWorld)coordinates.getWorld()).getHandle();
//			this.tempHologramArmorStandNMS = new EntityArmorStand(worldServer, coordinates.getX(), coordinates.getY(), coordinates.getZ());
//			this.tempHologramArmorStandNMS.setCustomNameVisible(true);
//			this.tempHologramArmorStandNMS.setCustomName(new ChatComponentText(color+Double.toString(Math.round(nbDamage*100.0)/100.0)));
//			this.tempHologramArmorStandNMS.setInvisible(true);
//			this.tempHologramArmorStandNMS.setNoGravity(true);
//			this.tempHologramArmorStandNMS.setInvulnerable(true);
//			PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving(this.tempHologramArmorStandNMS);
//			int radius = 10;
//			for (Entity entity : coordinates.getWorld().getNearbyEntities(coordinates, radius, radius, radius)) {
//				if (entity instanceof Player) {
//					Player player = (Player)entity;
//					PlayerConnection playerConnection = (PlayerConnection)((CraftPlayer)player).getHandle().a;
//					playerConnection.sendPacket(packet);
//				}
//			}
		} else {
			this.timer = 1;
			//Location newLocation = new Location(coordinates.getWorld(), coordinates.getX()+(2*Math.random())-1, coordinates.getY()+(2*Math.random())-21, coordinates.getZ()+(2*Math.random())-1);
			Location newLocation2 = new Location(coordinates.getWorld(), coordinates.getX()+(2*Math.random())-1, coordinates.getY()+(Math.random())-1, coordinates.getZ()+(2*Math.random())-1);
			this.tempHologramArmorStand = (ArmorStand)coordinates.getWorld().spawnEntity(newLocation2, EntityType.ARMOR_STAND);
			this.tempHologramArmorStand.setInvisible(true);
			this.tempHologramArmorStand.setGravity(false);
			this.tempHologramArmorStand.setInvulnerable(true);
			this.tempHologramArmorStand.setVisualFire(false);
			this.tempHologramArmorStand.setCustomName(color+Double.toString(Math.round(nbDamage*100.0)/100.0));
			this.tempHologramArmorStand.setCustomNameVisible(true);
		}
	}
	
	public void removeArmorStand() {
		if (tempHologramArmorStand != null) {
			tempHologramArmorStand.remove();
		}
	}
	
	@Override
	public void run() {
		if (timer == 0) {
			this.removeArmorStand();
			cancel();
		}
		timer--;
	}

}