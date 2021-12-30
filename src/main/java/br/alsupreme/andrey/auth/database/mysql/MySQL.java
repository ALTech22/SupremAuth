package main.java.br.alsupreme.andrey.auth.database.mysql;

import java.sql.*;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import main.java.br.alsupreme.andrey.auth.SupremAuth;
import main.java.br.alsupreme.andrey.auth.database.*;
import main.java.br.alsupreme.andrey.auth.database.Error;

public class MySQL extends DataBase{
	public MySQL(SupremAuth plugin) {
		super(plugin);
		genConnection();
	}
	
	@Override
	protected void genDatabase() {
		String script = "-- ====================================================================================================================================\n"
				+ "-- ========= INICIO DO SCRIPT DE GERAÇÃO DE BANCO DE DADOS ALREALMS ===================================================================\n"
				+ "-- ====================================================================================================================================\n"
				+ "-- ========= CRIAÇÃO E ABERTURA DO BANCO DE DADOS ALREALM =============================================================================\n"
				+ "-- ====================================================================================================================================\n"
				+ "-- ======================================== ANDREY HENRIQUE LOPES ZEFERINO ============================================================\n"
				+ "-- ============================================== 09/24/2021 ==========================================================================\n"
				+ "-- ====================================================================================================================================\n"
				+ "\n"
				+ "CREATE DATABASE IF NOT EXISTS "+plugin.getConfig().getString("database.mysql.db")+";\n";
		try {
			Statement st = connection.createStatement();
			int debug = st.executeUpdate(script);
			String message = (debug == 1) ? ChatColor.GREEN + "Success" : ChatColor.RED + "Failure";
			Bukkit.getConsoleSender().sendMessage("Creation/Update of database: " + message);
		} catch (SQLException e) {
			Error.execute(plugin, e);
		}
	}
	private void genTable() {
		String script = "CREATE TABLE IF NOT EXISTS Player (\n"
				+ "	   player VARCHAR(40),\n"
				+ "    UUID VARCHAR(50) NOT NULL UNIQUE,\n"
				+ "	    isLogged boolean,\n"
				+ "    `password` VARCHAR(45) NOT NULL,\n"
				+ "    email VARCHAR(45),\n"
				+ "    PRIMARY KEY(player)\n"
				+ "    )Engine InnoDB;";
		try {
			Statement st = connection.createStatement();
			for(int i=0; i<20; i++) {
				st.executeUpdate(script);
			}
			st.close();
		} catch (SQLException e) {
			Error.execute(plugin, e);
		}
	}
	@Override
	protected void genConnection() {
		try {
			String databaseCreator = plugin.getConfig().getString("database.mysql.ip") + "/" + plugin.getConfig().getString("database.mysql.db");
			
			connection = DriverManager.getConnection(
					"jdbc:mysql://"+databaseCreator,
					
					plugin.getConfig().getString("database.mysql.user"),
					plugin.getConfig().getString("database.mysql.password")
					);
			Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE + "MYSQL Connection: " + ChatColor.GREEN + "Success");
		}catch(Exception uselessException) {
			try {
				
				Class.forName("com.mysql.cj.jdbc.Driver");
				String databaseCreator = plugin.getConfig().getString("database.mysql.ip") + ""; //+ "/" + plugin.getConfig().getString("database.mysql.db");
				connection = DriverManager.getConnection(
						"jdbc:mysql://"+databaseCreator,
						
						plugin.getConfig().getString("database.mysql.user"),
						plugin.getConfig().getString("database.mysql.password")
						);
				genDatabase();
				closeConnection();
				databaseCreator = plugin.getConfig().getString("database.mysql.ip") + "/" + plugin.getConfig().getString("database.mysql.db");
				
				connection = DriverManager.getConnection(
						"jdbc:mysql://"+databaseCreator,
						
						plugin.getConfig().getString("database.mysql.user"),
						plugin.getConfig().getString("database.mysql.password")
						);
				genTable();
				Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE + "MYSQL Connection: " + ChatColor.GREEN + "Success");
			}catch (Exception e) {
				Error.execute(plugin, e);
				Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE + "MYSQL Connection: " + ChatColor.RED + "Failure");
			}
		}
	}
	

}
