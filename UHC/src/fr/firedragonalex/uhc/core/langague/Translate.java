package fr.firedragonalex.uhc.core.langague;

import java.util.Arrays;
import java.util.List;

import fr.firedragonalex.uhc.core.Options;

public enum Translate {
	
	PLAYER_JOIN_GAME(Arrays.asList("join the game !","a rejoint la partie !")),
	PLAYER_QUIT_GAME(Arrays.asList("quit the game !","a quitté la partie !")),
	GAME_ALREADY_START(Arrays.asList("There is already a game !","La partie a d�j� commenc�e !")),
	START_GAME(Arrays.asList("Game started !","Partie lancée !")),
	START_PLAYING(Arrays.asList("Let's go !","C'est partie !")),
	ANNOUCEMENT_INVICIBILITY(Arrays.asList("You are invicible during {number}s !","Vous êtes invicble pendant {number}s !")),
	ANNOUCEMENT_REMOVE_INVICIBILITY(Arrays.asList("You will no longer be invincible in {number}s !","Vous ne serez plus invincible dans {number}s !")),
	REMOVE_INVICIBILITY(Arrays.asList("You are now no longer invincible !","Vous n'êtes plus invincible !")),
	ANNOUCEMENT_PVP_ON(Arrays.asList("PvP will be on in {number}s !","Le PvP sera activé dans {number}s !")),
	PVP_ON(Arrays.asList("PvP on !","PvP activé !")),
	WORLDBOREDER_MOVING(Arrays.asList("The border move to {number} blocks from center !","La bordure se déplace en {number} blocks du centre !")),
	DEATH_MESSAGE(Arrays.asList(" died !"," est mort !")),
	YOU_DIED(Arrays.asList("YOU DIED !","VOUS ETES MORT !")),
	VICTORY(Arrays.asList("VICTORY !","VICTOIRE !")),
	CANT_USE_ITEM(Arrays.asList("You can't use this item !","Vous ne pouvez pas utiliser cet item !")),
	COOL_DOWN(Arrays.asList("You can use this item in {number}s !","Vous pouvez utiliser cet item dans {number}s !")),
	REMAINING_USE(Arrays.asList("Use remaining : ", "Utilisation restante : ")),
	ITEM_BREAK_NO_USE_REMAINING(Arrays.asList("This item break because you don't have any use remaining !","Cet item a cassé car vous n'avez plus d'utilisation restantes !")),
	END_GAME(Arrays.asList("Game closed !","Partie finie !")),
	;
	
	private List<String> strings;
	
	private Translate(List<String> strings) {
		this.strings = strings;
	}
	
	public String getString() {
		return this.strings.get(Options.LANGAGUE.getId());
	}
}
