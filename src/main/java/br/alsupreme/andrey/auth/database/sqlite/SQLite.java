package main.java.br.alsupreme.andrey.auth.database.sqlite;

import java.io.File;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.sql.*;



import main.java.br.alsupreme.andrey.auth.SupremAuth;
import main.java.br.alsupreme.andrey.auth.database.*;
import main.java.br.alsupreme.andrey.auth.database.Error;

public class SQLite extends DataBase{
	private File file = new File(plugin.getServer().getPluginManager().getPlugin(plugin.getName()).getDataFolder(), plugin.getConfig().getString("database.sqlite.filename"));

	public SQLite(SupremAuth plugin) {
		super(plugin);
		genConnection();
	}

	@Override
	protected void genDatabase() {
		String script ="CREATE TABLE IF NOT EXISTS Player (\n"
				+ "	player VARCHAR(40),\n"
				+ " UUID VARCHAR(50) NOT NULL UNIQUE,\n"
				+ "	isLogged boolean,\n"
				+ "	password VARCHAR(45) NOT NULL,\n"
				+ " email VARCHAR(45),\n"
				+ " PRIMARY KEY(player)\n"
				+ "    );";
		
		try {
			Statement st = connection.createStatement();
			st.executeUpdate(script);
			Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE + "SQLite Connection: " + ChatColor.GREEN + "Success");
		} catch (SQLException e) {
			Error.execute(plugin, e);
			Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE + "SQLite Connection: " + ChatColor.RED + "Failure");
		}
		
	}
	
	private void genSQLiteFile() {
    	if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                plugin.getLogger().log(Level.SEVERE, "File write error: "+ plugin.getConfig().getString("database.sqlite.filename"));
                plugin.getLogger().log(Level.SEVERE, String.valueOf(e));
            }
        }
	}

	@Override
	protected void genConnection() {
		if(!file.exists()) {
			genSQLiteFile();
		}
		try {
			if(connection != null && !connection.isClosed())
				return;
			
			Class.forName("org.sqlite.JDBC");
			connection = (Connection) DriverManager.getConnection("jdbc:sqlite:" + file);
			genDatabase();
		}catch(SQLException ex) {
			Error.noConnection(plugin, ex);
		}catch(ClassNotFoundException ex) {
			plugin.getLogger().log(Level.SEVERE, "You need the SQLite JBDC library. Google it. Put it in /lib folder.");
		}
	}
}
