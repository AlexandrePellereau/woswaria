package fr.firedragonalex.uhc.core.role.cooldown;

import org.bukkit.scheduler.BukkitRunnable;

import fr.firedragonalex.uhc.core.GameManager;

public class CooldownEnder extends BukkitRunnable {

    private Cooldown cooldown;

    public CooldownEnder(Cooldown cooldown) {
        this.cooldown = cooldown;
    }

    @Override
    public void run() {
        GameManager.getAllCooldowns().remove(cooldown);
        this.cancel();
    }

}
