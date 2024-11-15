package fr.dralexgon.shopasvillagerforplayers.database;

import fr.dralexgon.shopasvillagerforplayers.Main;
import org.bukkit.Bukkit;

import java.sql.SQLException;

public class SaveSqlite {

    VillagerShopDatabase villagerShopDatabase;

    public void enable() {
        try {
            if (!Main.getInstance().getDataFolder().exists()) {
                Main.getInstance().getDataFolder().mkdir();
            }
            villagerShopDatabase = new VillagerShopDatabase(Main.getInstance().getDataFolder().getAbsolutePath() + "/villager_shop.db");
            villagerShopDatabase.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("[ShopPlayerPNJ-FDA] Error when trying to connect to the database.");
            Bukkit.getPluginManager().disablePlugin(Main.getInstance());
        }
    }

    public void disable() {
        try {
            villagerShopDatabase.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("[ShopPlayerPNJ-FDA] Error when trying to close the database.");
        }
    }
}
