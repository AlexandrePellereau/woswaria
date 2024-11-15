package fr.dralexgon.shopasvillagerforplayers.database;

import fr.dralexgon.shopasvillagerforplayers.Main;
import org.bukkit.Bukkit;

import java.sql.SQLException;

public class SaveSqlite {

    private static VillagerShopDatabase villagerShopDatabase;

    public static void enable() {
        try {
            if (!Main.getInstance().getDataFolder().exists()) {
                Main.getInstance().getDataFolder().mkdir();
            }
            villagerShopDatabase = new VillagerShopDatabase(Main.getInstance().getDataFolder().getAbsolutePath() + "/villager_shop.db");
            villagerShopDatabase.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            Main.log("Error when trying to connect to the database.");
            Bukkit.getPluginManager().disablePlugin(Main.getInstance());
        }
    }

    public static void disable() {
        try {
            villagerShopDatabase.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            Main.log("Error when trying to close the database.");
        }
    }
}
