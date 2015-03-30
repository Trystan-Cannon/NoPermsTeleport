package io.github.trystancannon.nopermsteleport.event;

import io.github.trystancannon.nopermsteleport.core.NoPermsTeleport;
import io.github.trystancannon.nopermsteleport.core.TeleportRequest;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * @author Trystan Cannon
 */
public final class TeleportRequestEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private final TeleportRequest request;
    private boolean isCanceled;
    
    public TeleportRequestEvent(TeleportRequest request, NoPermsTeleport plugin) {
        this.request = request;
        
        // Inform the players of the request.
        request.getPlayerFrom().sendMessage(ChatColor.ITALIC + "Request sent to " + request.getPlayerTo().getName() + "! Request expires in 10 seconds.");
        request.getPlayerTo().sendMessage(ChatColor.ITALIC + request.getPlayerFrom().getName() + " would like to teleport to you. Type /nptpaccept or /nptpdeny to accept or deny this request.");
        
        // Times out after 10 seconds.
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new TeleportRequestTimer(this), 200L);
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

    @Override
    public boolean isCancelled() {
        return isCanceled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        isCanceled = cancel;
    }
}
