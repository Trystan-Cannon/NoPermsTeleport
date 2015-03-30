package io.github.trystancannon.nopermsteleport.event;

import io.github.trystancannon.nopermsteleport.core.TeleportRequest;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * @author Trystan Cannon
 */
public class TeleportRequestTimerEndEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private final TeleportRequestEvent requestEvent;
    
    public TeleportRequestTimerEndEvent(TeleportRequestEvent requestEvent) {
        this.requestEvent = requestEvent;
    }
    
    public TeleportRequest getRequest() {
        return requestEvent.getRequest();
    }
    
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
    
    public static HandlerList getHandlerList() {
        return handlers;
    }
}
