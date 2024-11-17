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

        main.saveResource("languages/fr_FR.yml", true);
        main.saveResource("languages/en_US.yml", true);
        //TODO: main.saveResource("languages/de_DE.yml", true);

        return loadLanguageFile("languages/" + main.getConfig().getString("language") + ".yml");
    }

    private static FileConfiguration loadLanguageFile(String fileName) {
        File file = new File(main.getDataFolder(), fileName);
        return YamlConfiguration.loadConfiguration(file);
    }

}
