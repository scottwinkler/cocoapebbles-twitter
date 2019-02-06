package com.cocoapebbles.twitter.clients;

import com.cocoapebbles.twitter.Main;
import org.bukkit.event.Event;

import java.util.logging.Logger;

public class BukkitClient {
    private static Main _main = null;
    public static Main getInstance(){
        return _main;
    }

    public static void callEvent(Event event){
        _main.getServer().getPluginManager().callEvent(event);
    }

    public static String getDataDir(){
        return _main.getDataFolder().getAbsolutePath();
    }

    public static Logger getLogger(){
        return _main.getLogger();
    }

    public static void initialize(Main main){
        _main = main;
    }
}
