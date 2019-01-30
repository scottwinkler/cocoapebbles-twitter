package com.cocoapebbles.twitter.events;

import com.cocoapebbles.twitter.clients.WorldEditClient;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {

    /**
     * Sets the player for the world edit session
     * @param event
     */
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        WorldEditClient wec = WorldEditClient.getInstance();
        wec.addPlayer(player);
    }
}
