package com.cocoapebbles.twitter.events;

import com.cocoapebbles.twitter.clients.ListenerClient;
import com.cocoapebbles.twitter.clients.WorldEditClient;
import com.cocoapebbles.twitter.constants.Events;
import com.cocoapebbles.twitter.utility.Utility;
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
            lc.handleEvent(Events.BUTTON_DOWN,event);
        }
       System.out.println("responding to event:" + event.getAction()+" @location:"+ Utility.locationToBlockCoordinate(event.getClickedBlock().getLocation()));
    }
}
