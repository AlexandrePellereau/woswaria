package fr.dralexgon.shopasvillagerforplayers.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class VillagerShopDatabase {

    private final Connection connection;

    public VillagerShopDatabase(String path) throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:sqlite:" + path);
        Statement statement = this.connection.createStatement();
        statement.execute(
                "CREATE TABLE IF NOT EXISTS villager_shop ("
                + "uuid TEXT PRIMARY KEY,"
                + "owner TEXT,"
                + "name TEXT,"
                + "world TEXT,"
                + "x INT,"
                + "y INT,"
                + "z INT,"
                + "x_direction INT,"
                + "z_direction INT,"
                + "inventory_obtained TEXT,"
                + "inventory_to_sell TEXT,"
                + "skin TEXT,"
                + "infinite_trade BOOLEAN"
                + ")");
    }

    public void closeConnection() throws SQLException {
        this.connection.close();
    }
}
