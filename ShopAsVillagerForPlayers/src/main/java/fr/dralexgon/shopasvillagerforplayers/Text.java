package fr.dralexgon.shopasvillagerforplayers;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;

public class Text {
    private static String language;
    private static JsonObject json;

    public static void loadLanguageConfig() {
        Text.language = Main.getInstance().getConfig().getString("language");
    }

    public static void loadJsonFile() {
        try {
            String text = new String(Files.readAllBytes(new File("src/main/resources/languages/" + language + ".json").toPath()));
            json = new JsonParser().parse(text).getAsJsonObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setLanguage(String language) {
        Text.language = language;
    }

    public static String getLanguage() {
        return language;
    }

    public static String get(String key) {
        return json.get(key).getAsString();
    }
}
