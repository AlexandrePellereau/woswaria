package fr.firedragonalex.rankandlevels.saveandload;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

import fr.firedragonalex.rankandlevels.Main;

public class MySql {
	
	private static boolean showSqlCommand = false;
	
	private static Connection connection = null;
	private static String database = "";
	
	public static void setDatabase(String database) {
		MySql.database = database;
	}
	
	public static void tryPrintSqlCommand(String sql) {
		if (MySql.showSqlCommand) {
			System.out.println("["+Main.getInstance().getName()+"] Executing the sql-command : \u001B[32m"+sql+"\u001B[0m");
		}
	}
	
	public static void stopConnection() {
		if (MySql.connection == null) return;
		
		try {
			MySql.connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		MySql.connection = null;
	}
	
	public static void checkMysqlConnection() {
		if (MySql.connection != null) return;
		
		if (MySql.database == "") {
			System.out.println("["+Main.getInstance().getName()+"]\u001B[31m[ERROR] : Database not defind !\u001B[0m");
			return;
		}
		
		String host = "localhost";
		String port = "3306";
		String username = "root";
		String password = "";
		try {
			MySql.connection = DriverManager.getConnection("jdbc:mysql://" +
				     host + ":" + port + "/" + MySql.database + "?useSSL=false",
				     username, password);
		} catch (Exception e) {
			//System.out.println(e);
			e.printStackTrace();
		}
	}
	
	public static void executeSql(String sql) {
		try {
			MySql.tryPrintSqlCommand(sql);
			PreparedStatement preparedStatement = MySql.connection.prepareStatement(sql);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static ResultSet executeSqlQuery(String sql) {
		try {
			MySql.tryPrintSqlCommand(sql);
			PreparedStatement preparedStatement = MySql.connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			return resultSet;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	///////////////////////////OPTIONAL/////////////////////////////////
	
	public static boolean existOneLineOrMore(String database, String condition) {
		try {
			return MySql.executeSqlQuery("SELECT * FROM "+database+" WHERE "+condition).next();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static ResultSet getLineWherePlayerUUID(String database, UUID PlayerUUID) {
		try {
			ResultSet result = MySql.executeSqlQuery("SELECT * FROM "+database+" WHERE PlayerUUID = '"+PlayerUUID+"'");
			result.next();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static ResultSet getAllLinesWherePlayerUUID(String database, UUID PlayerUUID) {
		ResultSet result = MySql.executeSqlQuery("SELECT * FROM "+database+" WHERE PlayerUUID = '"+PlayerUUID+"'");
		return result;
	}
	
	public static void insertOrUpdatePlayerUUID(String database, UUID PlayerUUID, HashMap<String, String> columnToValue) {
		
		if (MySql.existOneLineOrMore(database, "PlayerUUID = '"+PlayerUUID+"'")) {
			
			String sqlCommand = "UPDATE "+database+" SET ";
			for (String columnName : columnToValue.keySet()) {
				sqlCommand += columnName+" = "+columnToValue.get(columnName)+",";
			}
			sqlCommand = sqlCommand.substring(0, sqlCommand.length()-1);
			sqlCommand += " WHERE PlayerUUID = '"+PlayerUUID+"'";
			MySql.executeSql(sqlCommand);
			
		} else {
			String sqlCommand = "INSERT INTO "+database+" (";
			sqlCommand += "PlayerUUID"+",";
			for (String columnName : columnToValue.keySet()) {
				sqlCommand += columnName + ",";
			}
			sqlCommand = sqlCommand.substring(0, sqlCommand.length()-1);
			sqlCommand += ") VALUES (";
			sqlCommand += "'"+PlayerUUID+"'"+",";
			for (String columnName : columnToValue.keySet()) {
				sqlCommand += columnToValue.get(columnName) + ",";
			}
			sqlCommand = sqlCommand.substring(0, sqlCommand.length()-1);
			sqlCommand += ")";
			MySql.executeSql(sqlCommand);
		}
	}
	
	public static void insertOrUpdate(String database, HashMap<String, String> valuesMustBeEqual, HashMap<String, String> newValues) {
		String condition = "";
		for (String columnName : valuesMustBeEqual.keySet()) {
			condition += columnName+" = "+valuesMustBeEqual.get(columnName)+" AND ";
		}
		condition = condition.substring(0, condition.length()-5);
	
		if (MySql.existOneLineOrMore(database, condition)) {
			
			String sqlCommand = "UPDATE "+database+" SET ";
			for (String columnName : newValues.keySet()) {
				sqlCommand += columnName+" = "+newValues.get(columnName)+",";
			}
			sqlCommand = sqlCommand.substring(0, sqlCommand.length()-1);
			sqlCommand += " WHERE "+condition;
			MySql.executeSql(sqlCommand);
			
		} else {
			String sqlCommand = "INSERT INTO "+database+" (";
			for (String columnName : valuesMustBeEqual.keySet()) {
				sqlCommand += columnName + ",";
			}
			if (valuesMustBeEqual.size() == 0 || newValues.size() == 0) {
				sqlCommand = sqlCommand.substring(0, sqlCommand.length()-1);
			}
			for (String columnName : newValues.keySet()) {
				sqlCommand += columnName + ",";
			}
			sqlCommand = sqlCommand.substring(0, sqlCommand.length()-1);
			sqlCommand += ") VALUES (";
			for (String columnName : valuesMustBeEqual.keySet()) {
				sqlCommand += valuesMustBeEqual.get(columnName) + ",";
			}
			if (valuesMustBeEqual.size() == 0 || newValues.size() == 0) {
				sqlCommand = sqlCommand.substring(0, sqlCommand.length()-1);
			}
			for (String columnName : newValues.keySet()) {
				sqlCommand += newValues.get(columnName) + ",";
			}
			sqlCommand = sqlCommand.substring(0, sqlCommand.length()-1);
			sqlCommand += ")";
			MySql.executeSql(sqlCommand);
		}
	}
}
