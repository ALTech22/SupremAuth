package main.java.br.alsupreme.andrey.auth;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerHarvestBlockEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;


public class Events implements Listener{

	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		SupremAuth.getDatabase().updateData("Player", "isLogged", "false", "player", "=", "'"+e.getPlayer().getName()+"'");
		if(AuthSystem.hasRegistered(e.getPlayer().getName())) {
			e.getPlayer().sendMessage("Login with /login <password>");
		}else {
			e.getPlayer().sendMessage("register with /register <password> <email>");
		}
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		if(!AuthSystem.hasLogged(e.getPlayer().getName())) {
			e.setCancelled(true);
		}
	}
	@EventHandler
	public void onPlayerOpenGUI(InventoryOpenEvent e) {
		if(!AuthSystem.hasLogged(e.getPlayer().getName())) {
			e.setCancelled(true);
		}
	}
	@EventHandler
	public void onPlayerDrop(PlayerDropItemEvent e) {
		if(!AuthSystem.hasLogged(e.getPlayer().getName())) {
			e.setCancelled(true);
		}
	}
	@EventHandler
	public void onPlayerHarvestBlock(PlayerHarvestBlockEvent e) {
		if(!AuthSystem.hasLogged(e.getPlayer().getName())) {
			e.setCancelled(true);
		}
	}
	@EventHandler
	public void onPlayerSendChatMessage(AsyncPlayerChatEvent e) {
		if(!AuthSystem.hasLogged(e.getPlayer().getName())) {
			e.setCancelled(true);
		}
	}
	@EventHandler
	public void onPlayerUseCommand(PlayerCommandPreprocessEvent e) {
		String playername = e.getPlayer().getName();
		String command[] = e.getMessage().split(" ");
		
			if(!AuthSystem.hasRegistered(playername) || !AuthSystem.hasLogged(playername)) {
				
			if(command[0].equalsIgnoreCase("/register")){
				return;
				
			} else if(command[0].equalsIgnoreCase("/login")) {
				return;
			}
				e.setCancelled(true);
		}
	}
	@EventHandler
	public void onPlayerTakesDamage(EntityDamageEvent e) {
		if(e.getEntity() instanceof Player) {
			Player player = (Player) e.getEntity();
			if(!AuthSystem.hasLogged(player.getName())) {
				e.setCancelled(true);
			}
		}
	}
	@EventHandler
	public void onPlayerDamageEntity(EntityDamageByEntityEvent e) {
		if(e.getDamager() instanceof Player) {
			Player player = (Player) e.getDamager();
			if(!AuthSystem.hasLogged(player.getName())) {
				e.setCancelled(true);
			}
		}
	}
}
