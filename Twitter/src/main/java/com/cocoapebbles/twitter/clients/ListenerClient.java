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

    public void handleEvent(Event event){
        String eventName = event.getEventName();
        System.out.println("entry set:");
        listenerMap.entrySet().forEach(item->System.out.println(item.getKey()+"count: "+item.getValue().size()));
        System.out.println(eventName+event.getClass().getName());
        eventName = event.getClass().getName();
        if(listenerMap.containsKey(eventName)){
            ArrayList<Consumer<Event>> consumers = listenerMap.get(eventName);
            System.out.println("consumers #:"+consumers.size());
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
