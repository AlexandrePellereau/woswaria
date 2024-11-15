package fr.dralexgon.shopasvillagerforplayers.database;

import fr.dralexgon.shopasvillagerforplayers.Main;
import fr.dralexgon.shopasvillagerforplayers.VillagerShop;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.Inventory;

import java.io.File;
import java.sql.*;
import java.util.List;
import java.util.UUID;

public class SaveAndLoadSQLite {

    private static SaveAndLoadSQLite instance;
    private final Connection connection;

    public SaveAndLoadSQLite(String path) throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:" + path);
        Statement statement = connection.createStatement();
        statement.execute(
                "CREATE TABLE IF NOT EXISTS villager_shop ("
                + "uuid TEXT PRIMARY KEY,"
                + "owner TEXT,"
                + "name TEXT,"
                + "world TEXT,"
                + "x INT,"
                + "z INT,"
                + "inventory_obtained TEXT,"
                + "inventory_to_sell TEXT,"
                + "infinite_trade BOOLEAN"
                + ")");
    }

    public static void enable() {
        try {
            if (!Main.getInstance().getDataFolder().exists()) {
                Main.getInstance().getDataFolder().mkdir();
            }
            instance = new SaveAndLoadSQLite(Main.getInstance().getDataFolder().getAbsolutePath() + "/villager_shop.db");
            load();
        } catch (SQLException e) {
            e.printStackTrace();
            Main.log("Error when trying to connect to the database.");
            Bukkit.getPluginManager().disablePlugin(Main.getInstance());
        }
    }

    public static void disable() {
        try {
            instance.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            Main.log("Error when trying to close the database.");
        }
    }

    public static void addVillagerShop(String uuid,
                                String owner, String name,
                                String world, int x, int z,
                                String inventoryObtained, String inventoryToSell,
                                boolean infiniteTrade
                                ) throws SQLException {
        PreparedStatement preparedStatement = instance.connection.prepareStatement(
                    "INSERT INTO villager_shop (uuid, owner, name, world, x, z, inventory_obtained, inventory_to_sell, infinite_trade) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
        preparedStatement.setString(1, uuid);
        preparedStatement.setString(2, owner);
        preparedStatement.setString(3, name);
        preparedStatement.setString(4, world);
        preparedStatement.setInt(5, x);
        preparedStatement.setInt(6, z);
        preparedStatement.setString(7, inventoryObtained);
        preparedStatement.setString(8, inventoryToSell);
        preparedStatement.setBoolean(9, infiniteTrade);
        preparedStatement.executeUpdate();
    }

    public static boolean existsVillagerShop(String uuid) throws SQLException {
        PreparedStatement preparedStatement = instance.connection.prepareStatement("SELECT * FROM villager_shop WHERE uuid = ?");
        preparedStatement.setString(1, uuid);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }

    public static void updateVillagerShop(String uuid,
                                   String owner, String name,
                                   String world, int x, int z,
                                   String inventoryObtained, String inventoryToSell,
                                   boolean infiniteTrade
                                   ) throws SQLException {
        PreparedStatement preparedStatement = instance.connection.prepareStatement(
                    "UPDATE villager_shop SET owner = ?, name = ?, world = ?, x = ?, z = ?, inventory_obtained = ?, inventory_to_sell = ?, infinite_trade = ? WHERE uuid = ?");
        preparedStatement.setString(1, owner);
        preparedStatement.setString(2, name);
        preparedStatement.setString(3, world);
        preparedStatement.setInt(4, x);
        preparedStatement.setInt(5, z);
        preparedStatement.setString(6, inventoryObtained);
        preparedStatement.setString(7, inventoryToSell);
        preparedStatement.setBoolean(8, infiniteTrade);
        preparedStatement.setString(9, uuid);
        preparedStatement.executeUpdate();
    }

    public static void removeVillagerShop(String uuid) throws SQLException {
        PreparedStatement preparedStatement = instance.connection.prepareStatement("DELETE FROM villager_shop WHERE uuid = ?");
        preparedStatement.setString(1, uuid);
        preparedStatement.executeUpdate();
    }

    public static VillagerShop getVillagerShop(String uuid) throws SQLException {
        PreparedStatement preparedStatement = instance.connection.prepareStatement("SELECT * FROM villager_shop WHERE uuid = ?");
        preparedStatement.setString(1, uuid);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (!resultSet.next()) {
            return null;
        }

        VillagerShop villagerShop = new VillagerShop(
                resultSet.getString("uuid"),
                resultSet.getString("owner"),
                resultSet.getString("name"),
                resultSet.getString("world"),
                resultSet.getInt("x"),
                resultSet.getInt("z"),
                resultSet.getString("inventory_obtained"),
                resultSet.getString("inventory_to_sell"),
                resultSet.getBoolean("infinite_trade")
        );
        return villagerShop;
    }

    public static void load() throws SQLException {
        PreparedStatement preparedStatement = instance.connection.prepareStatement("SELECT * FROM villager_shop");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            VillagerShop villagerShop = new VillagerShop(
                    resultSet.getString("uuid"),
                    resultSet.getString("owner"),
                    resultSet.getString("name"),
                    resultSet.getString("world"),
                    resultSet.getInt("x"),
                    resultSet.getInt("z"),
                    resultSet.getString("inventory_obtained"),
                    resultSet.getString("inventory_to_sell"),
                    resultSet.getBoolean("infinite_trade")
            );
            Main.getInstance().getListVillagersShop().add(villagerShop);
        }
    }

    public static void save() {
        try {
            PreparedStatement preparedStatement = instance.connection.prepareStatement("DELETE FROM villager_shop");
            preparedStatement.executeUpdate();
            for (VillagerShop villagerShop : Main.getInstance().getListVillagersShop()) {
                addVillagerShop(
                        villagerShop.getVillager().getUniqueId().toString(),
                        villagerShop.getOwner().toString(),
                        villagerShop.getName(),
                        villagerShop.getVillager().getWorld().getUID().toString(),
                        villagerShop.getVillager().getLocation().getBlockX(),
                        villagerShop.getVillager().getLocation().getBlockZ(),
                        villagerShop.getVillager().getUniqueId()+"1",
                        villagerShop.getVillager().getUniqueId()+"2",
                        villagerShop.hasInfiniteTrade()
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Main.log("Error when trying to save the database.");
        }
    }

    public void closeConnection() throws SQLException {
        save();
        this.connection.close();
    }
}
