package io.github.trystancannon.nopermsteleport.event;

import org.bukkit.Bukkit;

/**
 * @author Trystan Cannon
 */
public final class TeleportRequestTimer implements Runnable {
    private final TeleportRequestEvent teleportRequestEvent;
    
    public TeleportRequestTimer(TeleportRequestEvent teleportRequestEvent) {
        this.teleportRequestEvent = teleportRequestEvent;
    }
    
    @Override
    public void run() {
        if (!teleportRequestEvent.isCancelled()) {
            Bukkit.getPluginManager().callEvent(new TeleportRequestTimerEndEvent(teleportRequestEvent));
        }
    }
}
