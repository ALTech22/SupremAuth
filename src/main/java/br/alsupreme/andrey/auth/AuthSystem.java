package main.java.br.alsupreme.andrey.auth;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import main.java.br.alsupreme.andrey.auth.database.DataBase;
import main.java.br.alsupreme.andrey.auth.events.LoginEvent;
import main.java.br.alsupreme.andrey.auth.events.PreLoginEvent;
import main.java.br.alsupreme.andrey.auth.events.PreRegisterEvent;
import main.java.br.alsupreme.andrey.auth.events.RegisterEvent;

public class AuthSystem {
	private boolean encrypt;
	private SupremAuth plugin;
	private Player player;
	
	
	public AuthSystem(SupremAuth plugin, Player player) {
		this.plugin = plugin;
		this.player = player;
		this.encrypt = plugin.getConfig().getBoolean("encrypt");
		if(plugin.getConfig().getString("database.sgbd").equalsIgnoreCase("sqlite"))
			this.encrypt = false;
	}
	
	public void register(String args[]) {
		if(!hasRegistered(player.getName())) {
			PreRegisterEvent pre = new PreRegisterEvent();
			Bukkit.getPluginManager().callEvent(pre);
			if(pre.isCancelled()) {
				return;
			}
			if(args.length > 0 && args.length < 2) {
				RegisterEvent re = new RegisterEvent();
				if(plugin.getConfig().getBoolean("login_required_email")) {
					String password = args[0];
					String email = args[1];
					if(args.length != 2) {
						player.sendMessage("email is need for register in this server");
						return;
					}
					if(args.length > 2) {
						player.sendMessage("Use underline instead of space in password");
						return;
					}
					
					if(encrypt)
						if(SupremAuth.getDatabase().insertData("Player", null, "'"+player.getName()+"','"+player.getUniqueId()+"', false, MD5('"+password+"'), '"+email+"'")) {
							Bukkit.getConsoleSender().sendMessage("Player: " + player.getName() + " Registered");
							player.sendMessage("you're successful registered, please make login with /login <password>");
							Bukkit.getPluginManager().callEvent(re);
						}
					else
						if(SupremAuth.getDatabase().insertData("Player", null, "'"+player.getName()+"','"+player.getUniqueId()+"', false, '"+password+"',  '"+email+"'")) {
							Bukkit.getConsoleSender().sendMessage("Player: " + player.getName() + " Registered");
							player.sendMessage("you're successful registered, please make login with /login <password>");	
							Bukkit.getPluginManager().callEvent(re);
						}
					
					
				}else {
					String password = args[0];
					if(args.length > 1) {
						player.sendMessage("Use underline instead of space");
						return;
					}
					if(encrypt) {
						if(SupremAuth.getDatabase().insertData("Player", null, "'"+player.getName()+"','"+player.getUniqueId()+"', false, MD5('"+password+"'), null")) {
							Bukkit.getConsoleSender().sendMessage("Player: " + player.getName() + " Registered");
							player.sendMessage("you're successful registered, please make login with /login <password>");
							Bukkit.getPluginManager().callEvent(re);
						}
					}
					else {
						if(SupremAuth.getDatabase().insertData("Player", null, "'"+player.getName()+"','"+player.getUniqueId()+"', false, '"+password+"', null")) {
							Bukkit.getConsoleSender().sendMessage("Player: " + player.getName() + " Registered");
							player.sendMessage("you're successful registered, please make login with /login <password>");
							Bukkit.getPluginManager().callEvent(re);
						}
					}
				}
			}else {
				player.sendMessage("The correct use of this command is /register <password> <email>");
			}
		}else {
			player.sendMessage("You're already registered");
		}
	}
	
	public void login(String args[]) {
		PreLoginEvent ple = new PreLoginEvent();
		Bukkit.getPluginManager().callEvent(ple);
		if(ple.isCancelled()){
			return;
		}
		if(args.length == 1) {
			String password = args[0];
			String databasePassword = (encrypt) ? SupremAuth.getDatabase().getString("Player", "password", "password = MD5('"+password+"')  AND player = '"+player.getName()+"'") : SupremAuth.getDatabase().getString("Player", "password", "password ='"+password+"'  AND player = '"+player.getName()+"'");
			if(databasePassword == null) {
				player.sendMessage("Wrong password!");
			}else {
				if(SupremAuth.getDatabase().updateData("Player", "isLogged", "true", "player", "=", "'"+player.getPlayer().getName()+"'")) {
					LoginEvent le = new LoginEvent();
					Bukkit.getPluginManager().callEvent(le);
					Bukkit.getConsoleSender().sendMessage("Player: "+player.getName()+" has logged");
					player.sendMessage("Login success");
				}else {
					Bukkit.getConsoleSender().sendMessage("Player: "+player.getName()+" has a fail in login");
				}
			}
		}else {
			player.sendMessage("Correct use of this command is /login <password>");
		}
	}
	
	public void changePassword(String args[]) {
		if(args.length > 3) {
			player.sendMessage("Use underline instead of space");
			return;
		}
		if(encrypt) {
			if(correctPassword(args[1])) {
				String newPass = args[2];
				if(SupremAuth.getDatabase().updateData("Player", "password", "MD5('"+newPass+"')", "player", "=", "'"+player.getName()+"'")) {
					player.sendMessage("Password successful altered");
					Bukkit.getConsoleSender().sendMessage("Player: " + player.getName() + " has altered the password");
				}
			}else {
				player.sendMessage("Wrong old password");
				return;
			}
		}else {
			if(correctPassword(args[1])) {
				String newPass = args[2];
				if(SupremAuth.getDatabase().updateData("Player", "password", "'"+newPass+"'", "player", "=", "'"+player.getName()+"'")) {
					player.sendMessage("Password successful altered");
					Bukkit.getConsoleSender().sendMessage("Player: " + player.getName() + " has altered the password");
				}
			}else {
				player.sendMessage("Wrong old password");
			}
			
		}
	}
	
	private boolean correctPassword(String pass) {
		boolean isCorrect = false;
		if(encrypt) {
			isCorrect = (SupremAuth.getDatabase().getString("Player", "password", "password = MD5('"+pass+"') AND player = '"+player.getName()+"'") != null);
		}else {
			isCorrect = (SupremAuth.getDatabase().getString("Player", "password", "password = '"+pass+"' AND player = '"+player.getName()+"'") != null);
		}
		return isCorrect;
	}
	
	public static boolean hasRegistered(String playername) {
		String playerName = SupremAuth.getDatabase().getString("Player", "player", "player = '"+playername+"'");
		return (playerName == null) ? false : true;
	}
	public static boolean hasLogged(String playername) {
		return SupremAuth.getDatabase().getBool("Player", "isLogged", "player = '" +playername+"'");
	}
	
}
