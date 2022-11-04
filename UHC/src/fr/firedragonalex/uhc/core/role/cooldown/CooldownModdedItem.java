package fr.firedragonalex.uhc.core.role.cooldown;

import org.bukkit.entity.Player;

import fr.firedragonalex.uhc.specific.ModdedItem;

public class CooldownModdedItem extends Cooldown {

    private ModdedItem moddedItem;

    public CooldownModdedItem(Player player, ModdedItem moddedItem, int seconds) {
        super(player, seconds);
        this.moddedItem = moddedItem;
    }

    public ModdedItem getModdedItem() {
        return moddedItem;
    }

    public long getCooldown() {
        return Math.round((this.getEndCooldownTimestamp() - System.currentTimeMillis()) / 1000.0);
    }
}
