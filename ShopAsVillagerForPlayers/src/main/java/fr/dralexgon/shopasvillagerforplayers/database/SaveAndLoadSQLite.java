package fr.dralexgon.shopasvillagerforplayers.database;

import fr.dralexgon.shopasvillagerforplayers.Main;
import fr.dralexgon.shopasvillagerforplayers.VillagerShop;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.sql.*;

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
                + "infinite_trade BOOLEAN"
                + ")");
        Statement statement2 = connection.createStatement();
        statement2.execute("CREATE TABLE IF NOT EXISTS villager_shop_inventories ("
                + "uuid TEXT,"
                + "item BLOB"
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
            //save();
            instance.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            Main.log("Error when trying to close the database.");
        }
    }

    public static void addVillagerShop(VillagerShop villagerShop) {
        String uuid = villagerShop.getVillager().getUniqueId().toString();
        String owner = villagerShop.getOwner().toString();
        String name = villagerShop.getName();
        String world = villagerShop.getVillager().getWorld().getUID().toString();
        int x = villagerShop.getVillager().getLocation().getChunk().getX();
        int z = villagerShop.getVillager().getLocation().getChunk().getZ();
        boolean infiniteTrade = villagerShop.hasInfiniteTrade();

        try {
            PreparedStatement preparedStatement = instance.connection.prepareStatement(
                    "INSERT INTO villager_shop (uuid, owner, name, world, x, z, infinite_trade) VALUES (?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, uuid);
            preparedStatement.setString(2, owner);
            preparedStatement.setString(3, name);
            preparedStatement.setString(4, world);
            preparedStatement.setInt(5, x);
            preparedStatement.setInt(6, z);
            preparedStatement.setBoolean(7, infiniteTrade);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            Main.log("Error when trying to add to the database.");
        }
    }

    public static boolean existsVillagerShop(String uuid) throws SQLException {
        PreparedStatement preparedStatement = instance.connection.prepareStatement("SELECT * FROM villager_shop WHERE uuid = ?");
        preparedStatement.setString(1, uuid);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }

    public static void updateVillagerShop(VillagerShop villagerShop) {
        String uuid = villagerShop.getVillager().getUniqueId().toString();
        String owner = villagerShop.getOwner().toString();
        String name = villagerShop.getName();
        String world = villagerShop.getVillager().getWorld().getUID().toString();
        int x = villagerShop.getVillager().getLocation().getChunk().getX();
        int z = villagerShop.getVillager().getLocation().getChunk().getZ();
        boolean infiniteTrade = villagerShop.hasInfiniteTrade();

        try {
            PreparedStatement preparedStatement = instance.connection.prepareStatement(
                    "UPDATE villager_shop SET owner = ?, name = ?, world = ?, x = ?, z = ?, infinite_trade = ? WHERE uuid = ?");
            preparedStatement.setString(1, owner);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, world);
            preparedStatement.setInt(4, x);
            preparedStatement.setInt(5, z);
            preparedStatement.setBoolean(6, infiniteTrade);
            preparedStatement.setString(7, uuid);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            Main.log("Error when trying to update the database.");
        }
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
                if (existsVillagerShop(villagerShop.getVillager().getUniqueId().toString())) {
                    updateVillagerShop(villagerShop);
                } else {
                    addVillagerShop(villagerShop);
                }
                //villagerShop.updateInventories(); no need
                saveInventory(villagerShop.getVillager().getUniqueId()+"1", villagerShop.getInventoryThingsObtained());
                saveInventory(villagerShop.getVillager().getUniqueId()+"2", villagerShop.getInventoryThingsToSell());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Main.log("Error when trying to save the database.");
        }
    }

    public static void saveInventory(String uuid, Inventory inventory) {
        try {
            PreparedStatement preparedStatement = instance.connection.prepareStatement("DELETE FROM villager_shop_inventories WHERE uuid = ?");
            preparedStatement.setString(1, uuid);
            preparedStatement.executeUpdate();
            for (ItemStack item : inventory.getContents()) {
                if (item == null)
                    continue;
                preparedStatement = instance.connection.prepareStatement("INSERT INTO villager_shop_inventories (uuid, item) VALUES (?, ?)");
                preparedStatement.setString(1, uuid);
                preparedStatement.setBytes(2, ItemStackSerializer.serializeBytes(item));
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Main.log("Error when trying to save the inventory.");
        }
    }

    public static Inventory loadInventory(String uuid) {
        try {
            PreparedStatement preparedStatement = instance.connection.prepareStatement("SELECT * FROM villager_shop_inventories WHERE uuid = ?");
            preparedStatement.setString(1, uuid);
            ResultSet resultSet = preparedStatement.executeQuery();
            Inventory inventory = Bukkit.createInventory(null, 27, "VillagerShop");
            while (resultSet.next()) {
                inventory.addItem(ItemStackSerializer.deserializeBytes(resultSet.getBytes("item")));
            }
            return inventory;
        } catch (SQLException e) {
            e.printStackTrace();
            Main.log("Error when trying to load the inventory.");
            return null;
        }
    }

    public void closeConnection() throws SQLException {
        this.connection.close();
    }

        /*
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
                resultSet.getBoolean("infinite_trade")
        );
        return villagerShop;
    }
     */
}
