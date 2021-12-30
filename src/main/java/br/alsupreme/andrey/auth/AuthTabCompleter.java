package main.java.br.alsupreme.andrey.auth;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class AuthTabCompleter implements TabCompleter{

	@Override
	public List<String> onTabComplete(CommandSender sender, Command com, String lab, String[] args) {
		if(sender instanceof Player) {
			List<String> tabCompleter = new ArrayList<String>();
			if(com.getName().equals("register") || com.getName().equals("login")) {
				tabCompleter.add("Password");
			}else if(com.getName().equals("change")) {
				if(args.length == 1)
					tabCompleter.add("password");
				else if(args[0].equals("password")) {
					if(args.length == 2)
						tabCompleter.add("oldPassword");
					else if(args.length == 3)
						tabCompleter.add("newPassword");
				}
			}
			return tabCompleter;
		}
		return null;
	}

}
