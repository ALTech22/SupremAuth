package main.java.br.alsupreme.andrey.auth.database;

import java.util.logging.Level;

import org.bukkit.plugin.Plugin;

public class Error {
	
    public static void execute(Plugin plugin, Exception ex){
        plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(), ex);
    }
    public static void close(Plugin plugin, Exception ex){
        plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionClose(), ex);
    }
    public static void noConnection(Plugin plugin, Exception ex) {
    	plugin.getLogger().log(Level.SEVERE, Errors.noSQLConnection(), ex);
    }
  
}
