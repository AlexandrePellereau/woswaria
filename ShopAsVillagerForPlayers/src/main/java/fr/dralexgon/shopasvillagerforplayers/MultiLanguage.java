package fr.dralexgon.shopasvillagerforplayers;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class MultiLanguage {

    private static Main main;

    public static FileConfiguration enable() {
        main = Main.getInstance();

        if (!main.getDataFolder().exists()) {
            main.getDataFolder().mkdirs();
        }

        saveResourceIfNotExists("languages/fr_fr.yml");
        saveResourceIfNotExists("languages/en_us.yml");

        return loadLanguageFile("languages/" + main.getConfig().getString("language") + ".yml");
    }

    private static void saveResourceIfNotExists(String resourceName) {
        if (!new File(main.getDataFolder(), resourceName).exists()) {
            main.saveResource(resourceName, false);
        }
    }

    private static FileConfiguration loadLanguageFile(String fileName) {
        File file = new File(main.getDataFolder(), fileName);
        return YamlConfiguration.loadConfiguration(file);
    }

}
