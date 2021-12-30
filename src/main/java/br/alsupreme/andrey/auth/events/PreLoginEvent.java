package main.java.br.alsupreme.andrey.auth.events;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PreLoginEvent extends Event implements Cancellable{
	
	private static final HandlerList handlers = new HandlerList();
	private boolean isCancelled;

	@Override
	public boolean isCancelled() {
		// TODO Auto-generated method stub
		return isCancelled;
	}

	@Override
	public void setCancelled(boolean isCancelled) {
		// TODO Auto-generated method stub
		this.isCancelled = isCancelled;
	}

	@Override
	public HandlerList getHandlers() {
		// TODO Auto-generated method stub
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}

}
