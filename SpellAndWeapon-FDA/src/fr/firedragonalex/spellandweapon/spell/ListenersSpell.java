package fr.firedragonalex.spellandweapon.spell;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import fr.firedragonalex.spellandweapon.Main;
import fr.firedragonalex.spellandweapon.custom.code.CustomPlayer;

public class ListenersSpell implements Listener {
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		String message = event.getMessage();
		CustomPlayer customPlayer = Main.getCustomPlayerByPlayer(event.getPlayer());
		if (customPlayer.isCastingSpell()) {
			event.setCancelled(true);
			customPlayer.getCastSpell().tryFormula(message);
		} else {
			if (message.startsWith("!")) {
				event.setCancelled(true);
				message = message.replace("!","");
				Spell spell = Main.getInstance().getSpell(message);
				if (spell != null) {
					customPlayer.setIsCastingSpell(true);
					customPlayer.setSpell(spell);
					customPlayer.setAdvancementFormula(1);
					customPlayer.startSpell();
				} else {
					event.getPlayer().sendMessage(ChatColor.RED+"Cette formule magique n'existe pas.");
				}
			}
		}
	}
}
