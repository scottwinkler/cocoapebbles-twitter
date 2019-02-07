package com.cocoapebbles.twitter.events;

import com.cocoapebbles.twitter.clients.ListenerClient;
import com.cocoapebbles.twitter.constants.Events;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class EntityDeath implements Listener {
    ListenerClient lc;
    public EntityDeath(){
        lc = ListenerClient.getInstance();
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event){
        if(event.getEntity() instanceof Villager){
            lc.handleEvent(Events.VILLAGER_MURDERED,event);
        }
    }
}
