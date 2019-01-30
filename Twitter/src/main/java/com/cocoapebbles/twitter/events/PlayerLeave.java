package com.cocoapebbles.twitter.events;

import com.cocoapebbles.twitter.clients.WorldEditClient;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeave implements Listener {

    /**
     * Sets the player for the world edit session
     * @param event
     */
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){

        Player player = event.getPlayer();
        WorldEditClient wec = WorldEditClient.getInstance();
        wec.removePlayer(player);
    }
}
