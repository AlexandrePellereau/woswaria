package fr.firedragonalex.uhc.core.role.cooldown;

import org.bukkit.entity.Player;

public abstract class Cooldown {
    
    private Player player;
    private long endCooldownTimestamp;
    private CooldownEnder cooldownEnder;

    public Cooldown(Player player, int seconds) {
        this.player = player;
        this.cooldownEnder = new CooldownEnder(this);
        this.endCooldownTimestamp = System.currentTimeMillis() + seconds * 1000;
        this.cooldownEnder.runTaskLater(player.getServer().getPluginManager().getPlugin("UHC"), seconds * 20);
    }

    public Player getPlayer() {
        return player;
    }

    public long getEndCooldownTimestamp() {
        return endCooldownTimestamp;
    }
}
