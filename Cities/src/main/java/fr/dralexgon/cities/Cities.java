package fr.dralexgon.cities;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.Locale;
import java.util.ResourceBundle;

public final class Cities extends JavaPlugin {

    static Locale locale;
    static ResourceBundle bundle;

    @Override
    public void onEnable() {
        Cities.locale = new Locale("fr", "FR");
        Cities.bundle = ResourceBundle.getBundle("Bundle", Cities.locale);
        System.out.println("Hello SpigotMC");
        System.out.println(Cities.bundle.getString("console.plugin_enable"));
    }

    @Override
    public void onDisable() {
        System.out.println("Cities plugin is disabled");
    }
}
