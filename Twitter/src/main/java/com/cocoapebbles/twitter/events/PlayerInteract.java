package com.cocoapebbles.twitter.events;

import com.cocoapebbles.twitter.clients.WorldEditClient;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteract implements Listener {

    /**
     * Sets the player for the world edit session
     * @param event
     */
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();
        System.out.println("responding to event:" + event.getAction()+event.getBlockFace());
    }
}
