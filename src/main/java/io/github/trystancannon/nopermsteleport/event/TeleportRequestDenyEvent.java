package io.github.trystancannon.nopermsteleport.event;

import io.github.trystancannon.nopermsteleport.core.TeleportRequest;

import org.bukkit.ChatColor;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * @author Trystan Cannon
 */
public final class TeleportRequestDenyEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private final TeleportRequest request;
    
    public TeleportRequestDenyEvent(TeleportRequest request) {
        this.request = request;
        
        request.getPlayerFrom().sendMessage(ChatColor.RED + request.getPlayerTo().getName() + " denied your teleport request.");
        request.getPlayerTo().sendMessage(ChatColor.RED + "Denied the request for " + request.getPlayerFrom().getName() + " to teleport to you.");
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
