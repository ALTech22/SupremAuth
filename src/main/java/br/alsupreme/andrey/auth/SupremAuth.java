package main.java.br.alsupreme.andrey.auth;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import main.java.br.alsupreme.andrey.auth.database.DataBase;
import main.java.br.alsupreme.andrey.auth.database.mysql.MySQL;
import main.java.br.alsupreme.andrey.auth.database.sqlite.SQLite;

public class SupremAuth extends JavaPlugin{
	
	private static DataBase db;
	
	@Override
	public void onEnable() {
		
		this.saveResource("config.yml", false);
		
		if(this.getConfig().getString("database.sgbd").equalsIgnoreCase("sqlite")) {
			db = new SQLite(this);
		}else if(this.getConfig().getString("database.sgbd").equalsIgnoreCase("mysql")) {
			db = new MySQL(this);
		}
		
		registerCommands();
		Bukkit.getPluginManager().registerEvents(new Events(), this);
		
		
	}
	
	private void registerCommands() {
		Commands commands = new Commands(this);
		AuthTabCompleter tabCompleter = new AuthTabCompleter();
		this.getCommand("login").setExecutor(commands);
		this.getCommand("login").setTabCompleter(tabCompleter);
		this.getCommand("register").setExecutor(commands);
		this.getCommand("register").setTabCompleter(tabCompleter);
		this.getCommand("change").setExecutor(commands);
		this.getCommand("change").setTabCompleter(tabCompleter);
	}
	
	public static DataBase getDatabase() {
		return db;
	}
	public static void setDatabase(DataBase database) {
		db = database;
	}
}
