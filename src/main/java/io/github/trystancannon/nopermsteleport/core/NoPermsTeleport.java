package io.github.trystancannon.nopermsteleport.core;

import io.github.trystancannon.nopermsteleport.event.TeleportRequestTimerEndEvent;
import io.github.trystancannon.nopermsteleport.event.TeleportRequestAcceptEvent;
import io.github.trystancannon.nopermsteleport.event.TeleportRequestDenyEvent;
import io.github.trystancannon.nopermsteleport.event.TeleportRequestEvent;
import io.github.trystancannon.nopermsteleport.event.TeleportRequestTimerEndEvent;

import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Trystan Cannon
 */
public class NoPermsTeleport extends JavaPlugin implements Listener {

    private static final HashMap<TeleportRequest, TeleportRequestEvent> requests = new HashMap<>();

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
    }

    /**
     * Retrieves the <code>Player</code> object from all currently online
     * players with the given name.
     * 
     * Might cause lag due to a nested <code>for</code> loop.
     * 
     * @param name
     * @return <code>Player</code> object with given name who is currently online.
     */
    private Player getPlayerFromName(String name) {
        for (World world : getServer().getWorlds()) {
            for (Player player : world.getPlayers()) {
                if (player.getName().equalsIgnoreCase(name)) {
                    return player;
                }
            }
        }

        return null;
    }
    
    /**
     * Retries the first request found in which the given player is the
     * player to whom another is requesting to teleport.
     * 
     * @param to
     * @return Request in which the player given is the player to whom another
     * is requesting to teleport.
     */
    private TeleportRequest getRequestWithPlayerAsTo(Player to) {
        // Could cause lag depending on the number of requests.
        for (TeleportRequest request : requests.keySet()) {
            if (request.getPlayerTo() == to) {
                return request;
            }
        }
        
        return null;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player playerIssuingCommand = (Player) sender;
            String commandName = command.getName();

            if (commandName.equalsIgnoreCase("nptpa")) {
                Player player = null;

                if (args.length >= 1) {
                    player = getPlayerFromName(args[0]);
                }

                if (player != null) {
                    addRequest(playerIssuingCommand, player);
                } else {
                    playerIssuingCommand.sendMessage(ChatColor.RED + "Player is offline or nonexistent.");
                }

                return true;
            } else if (commandName.equalsIgnoreCase("nptpaccept")) {
                TeleportRequest requestToAccept = getRequestWithPlayerAsTo(playerIssuingCommand);
                
                if (requestToAccept != null) {
                    acceptRequest(requestToAccept);
                }
                
                return true;
            } else if (commandName.equalsIgnoreCase("nptpdeny")) {
                TeleportRequest requestToDeny = getRequestWithPlayerAsTo(playerIssuingCommand);
                
                if (requestToDeny != null) {
                    denyRequest(requestToDeny);
                }
                
                return true;
            }
        }

        return false;
    }

    /**
     * Removes the request from the request hash table because it has timed out.
     * @param requestTimerEndEvent 
     */
    @EventHandler
    public void onTeleportRequestTimerEnd(TeleportRequestTimerEndEvent requestTimerEndEvent) {
        TeleportRequest request = requestTimerEndEvent.getRequest();
        
        request.getPlayerFrom().sendMessage(ChatColor.RED + "Your request to teleport to " + request.getPlayerTo().getName() + " has timed out.");
        requests.remove(request);
    }
    
    /**
     * Teleports the requesting player to the other when their request is accepted.
     * @param requestAcceptEvent 
     */
    @EventHandler
    public void onTeleportRequestAccept(TeleportRequestAcceptEvent requestAcceptEvent) {
        TeleportRequest request = requestAcceptEvent.getRequest();
        request.getPlayerFrom().teleport(request.getPlayerTo().getLocation());
    }

    /**
     * Accepts the given teleport request, spawning a <code>TeleportRequestAcceptEvent</code>.
     * @param request 
     */
    public void acceptRequest(TeleportRequest request) {
        requests.get(request).setCancelled(true);
        requests.remove(request);
        
        Bukkit.getPluginManager().callEvent(new TeleportRequestAcceptEvent(request));
    }
    
    /**
     * Denies the given teleport request, spawning a <code>TeleportRequestDenyEvent</code>.
     * @param request 
     */
    public void denyRequest(TeleportRequest request) {
        requests.get(request).setCancelled(true);
        requests.remove(request);
        
        Bukkit.getPluginManager().callEvent(new TeleportRequestDenyEvent(request));
    }
    
    /**
     * Adds a new teleport request. If a request already exists from this player
     * to another, then the current request will be canceled and replaced with a
     * new one, resetting the timeout.
     *
     * @param from
     * @param to
     */
    private void addRequest(Player from, Player to) {
        TeleportRequest request = new TeleportRequest(from, to);
        TeleportRequestEvent requestEvent = new TeleportRequestEvent(request, this);

        // If the user already has a request pending, cancel it.
        if (requests.get(request) != null) {
            requests.get(request).setCancelled(true);
            requests.remove(request);
        }

        requests.put(request, requestEvent);
        Bukkit.getPluginManager().callEvent(requestEvent);
    }
}
