package io.github.trystancannon.nopermsteleport.core;

import java.util.Objects;
import org.bukkit.entity.Player;

/**
 * @author Trystan Cannon
 */
public class TeleportRequest {
    private final Player from;
    private final Player to;
    
    public TeleportRequest(Player from, Player to) {
        this.from = from;
        this.to = to;
    }
    
    public Player getPlayerFrom() {
        return from;
    }
    
    public Player getPlayerTo() {
        return to;
    }
    
    @Override
    public boolean equals(Object object) {
        if (object instanceof TeleportRequest) {
            TeleportRequest request = (TeleportRequest) object;
            
            return request.from.getUniqueId().equals(this.from.getUniqueId()) &&
                   request.to.getUniqueId().equals(this.to.getUniqueId());
        }
        
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.from);
        hash = 67 * hash + Objects.hashCode(this.to);
        return hash;
    }
}
