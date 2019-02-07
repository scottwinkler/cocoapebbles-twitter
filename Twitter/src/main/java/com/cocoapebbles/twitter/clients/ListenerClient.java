package com.cocoapebbles.twitter.clients;

import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;

public class ListenerClient {
    private static ListenerClient _instance = null;
    private HashMap<String, ArrayList<Consumer<Event>>> listenerMap;

    private ListenerClient(){
        listenerMap = new HashMap<>();
    }

    public void subscribe(String eventName, Consumer<Event> consumer){
        if(!listenerMap.containsKey(eventName)){
            ArrayList<Consumer<Event>> consumers = new ArrayList<>();
            consumers.add(consumer);
            listenerMap.put(eventName,consumers);
        } else {
            ArrayList<Consumer<Event>> consumers = listenerMap.get(eventName);
            consumers.add(consumer);
            listenerMap.replace(eventName,consumers);
        }
    }

    public void handleEvent(String eventName, Event event){
        if(listenerMap.containsKey(eventName)){
            ArrayList<Consumer<Event>> consumers = listenerMap.get(eventName);
            consumers.forEach(c->c.accept(event));
        }
    }

    public void unsubscribe(String eventName,Consumer<Event> consumer){
        if(listenerMap.containsKey(eventName)){
            ArrayList<Consumer<Event>> consumers = listenerMap.get(eventName);
            consumers.remove(consumer);
            listenerMap.replace(eventName,consumers);
        }
    }

    public static ListenerClient getInstance(){
        if(_instance == null){
            _instance = new ListenerClient();
        }
        return _instance;
    }
}
