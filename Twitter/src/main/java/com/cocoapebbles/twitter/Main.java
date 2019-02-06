package com.cocoapebbles.twitter;

import com.cocoapebbles.twitter.clients.BukkitClient;
import com.cocoapebbles.twitter.commands.CommandHandler;

import com.cocoapebbles.twitter.events.PlayerInteract;
import com.cocoapebbles.twitter.events.PlayerJoin;
import com.cocoapebbles.twitter.events.PlayerLeave;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import java.util.logging.Logger;

public class Main extends JavaPlugin implements Listener{

    public Main(){}

    @Override
    public void onEnable(){
        registerCommands();
        registerEvents();
        initializeBukkitClient();

    }

    public void initializeBukkitClient(){
        BukkitClient.initialize(this);
    }

    @Override
    public void onDisable(){

    }

    public void registerCommands(){
        getCommand("twitter").setExecutor(new CommandHandler(this));
    }

    public void registerEvents()
    {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new PlayerJoin(),this);
        pm.registerEvents(new PlayerLeave(),this);
        pm.registerEvents(new PlayerInteract(),this);
    }


}
