package fr.firedragonalex.uhc.core.role;

import org.bukkit.entity.Player;

import fr.firedragonalex.uhc.specific.CustomEffect;

public class InfectedPlayer {
    
    private Player player;
    private CustomEffect customEffect;

    public InfectedPlayer(Player player, CustomEffect customEffect) {
        this.player = player;
        this.customEffect = customEffect;
    }

    public Player getPlayer() {
        return player;
    }

    public CustomEffect getCustomEffect() {
        return customEffect;
    }
}
