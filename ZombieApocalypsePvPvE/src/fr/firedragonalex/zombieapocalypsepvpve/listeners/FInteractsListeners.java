package fr.firedragonalex.zombieapocalypsepvpve.listeners;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.firedragonalex.zombieapocalypsepvpve.FMain;

public class FInteractsListeners implements Listener {
	private FMain main;

	public FInteractsListeners(FMain main) {
		this.main = main;
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		Action action = event.getAction();
		Block block = event.getClickedBlock();
		ItemStack it = event.getItem();
		if(action==Action.RIGHT_CLICK_BLOCK && block.getType()==Material.BREWING_STAND){
			event.setCancelled(true);
			if(it == null) return;
			if (it.getType()==Material.ROTTEN_FLESH) {
				int nbOfRottenFlesh = player.getInventory().getItemInMainHand().getAmount();
				player.getInventory().getItemInMainHand().setAmount(0);
				main.setAdvancementPeriod(main.getAdvancementPeriod()+nbOfRottenFlesh);
				Bukkit.broadcastMessage("§c"+player.getName()+" a ajouté "+nbOfRottenFlesh+" points !");
				player.sendMessage("Tu as ajouté : "+nbOfRottenFlesh+" points");
				main.updatePeriod();
			}
			
		}
	}
}
