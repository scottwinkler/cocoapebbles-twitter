package com.cocoapebbles.twitter.events;

import com.cocoapebbles.twitter.clients.ListenerClient;
import com.cocoapebbles.twitter.clients.WorldEditClient;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteract implements Listener {
    ListenerClient lc;
    WorldEditClient wec;
    public PlayerInteract(){
        lc = ListenerClient.getInstance();
        wec = WorldEditClient.getInstance();
    }
    /**
     * Sets the player for the world edit session
     * @param event
     */
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();

        if (event.getAction()== Action.PHYSICAL&& event.getClickedBlock().getType()== Material.STONE_PRESSURE_PLATE){
            lc.handleEvent(event);
        }
        System.out.println("responding to event:" + event.getAction()+event.getBlockFace()+" @location:"+wec.locationToCoordinate(event.getClickedBlock().getLocation()));
    }
}
