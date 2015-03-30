package io.github.trystancannon.nopermsteleport.event;

import io.github.trystancannon.nopermsteleport.core.TeleportRequest;
import org.bukkit.ChatColor;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * @author Trystan Cannon
 */
public final class TeleportRequestAcceptEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private final TeleportRequest request;
    
    public TeleportRequestAcceptEvent(TeleportRequest request) {
        this.request = request;
        
        request.getPlayerFrom().sendMessage(ChatColor.GREEN + request.getPlayerTo().getName() + " accepted your teleport request.");
        request.getPlayerTo().sendMessage(ChatColor.GREEN + "Accepted teleport request from " + request.getPlayerFrom().getName() + ".");
    }
    
    public TeleportRequest getRequest() {
        return request;
    }
    
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
    
    public static HandlerList getHandlerList() {
        return handlers;
    }
}
