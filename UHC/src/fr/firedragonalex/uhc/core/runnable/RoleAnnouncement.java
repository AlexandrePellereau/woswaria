package fr.firedragonalex.uhc.core.runnable;

import java.util.Collections;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.firedragonalex.uhc.core.GameManager;
import fr.firedragonalex.uhc.specific.Role;
import fr.firedragonalex.uhc.specific.SpecificOptions;

public class RoleAnnouncement extends BukkitRunnable {
	
	public RoleAnnouncement() {
		//this.timer = Options.ROLE_ANNOUCEMENT_DURATION;
	}

	@Override
	public void run() {
		List<Role> roles = SpecificOptions.getComposition();
		Collections.shuffle(roles);
		for (int index = 0; index < roles.size(); index++) {
			Role role = roles.get(index);
			Player player = GameManager.getAllPlayers().get(index);
			GameManager.getPlayersToRole().put(player, role);
		}
		for (int index = 0; index < roles.size(); index++) {
			Role role = roles.get(index);
			Player player = GameManager.getAllPlayers().get(index);
			role.sendRoleDescription(player);
		}
		this.cancel();
	}
	
}
