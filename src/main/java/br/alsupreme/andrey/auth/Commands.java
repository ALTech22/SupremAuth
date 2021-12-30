package main.java.br.alsupreme.andrey.auth;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor{
	private SupremAuth plugin;
	
	public Commands(SupremAuth plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command com, String lab, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			AuthSystem as = new AuthSystem(plugin, player);
			if(com.getName().equals("register")) {
				as.register(args);
			}else if(com.getName().equals("login")) {
				as.login(args);
			}else if(com.getName().equals("change")) {
				if(args[0].equalsIgnoreCase("password")) {
					as.changePassword(args);
				}else if(args[0].equalsIgnoreCase("email")){
					
				}
			}
		}else {
			sender.sendMessage("comando só para jogadores");
			return true;
		}
		return true;
	}

}
