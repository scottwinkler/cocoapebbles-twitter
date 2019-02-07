package com.cocoapebbles.twitter.events;

import com.cocoapebbles.twitter.clients.ListenerClient;
import com.cocoapebbles.twitter.constants.Events;
import org.bukkit.entity.*;
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
        Entity entity = event.getEntity();
        if(entity instanceof  LivingEntity){
            LivingEntity le = (LivingEntity) entity;
            if (le.getKiller()!=null){
                if (le instanceof  Villager){
                    lc.handleEvent(Events.VILLAGER_MURDERED,event);
                }
                else{
                    lc.handleEvent(Events.MOB_MURDERED,event);
                }
            }

        }
    }
}
