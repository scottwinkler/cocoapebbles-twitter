package com.cocoapebbles.twitter;

import com.cocoapebbles.twitter.commands.CommandHandler;

import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import twitter4j.Relationship;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

import java.util.logging.Logger;

public class Main extends JavaPlugin implements Listener{
    private Logger logger;

    public Main(){
        logger = this.getLogger();
    }
    @Override
    public void onEnable(){
        registerCommands();
        registerEvents();
        AccessToken accessToken = new AccessToken("1080969936032346112-bw6j16YIknQXtx8t04PxFZTuF8JtZr","FB08uZremdyLa63wma780vwMZjVImQ3dyFQsrbEWV1aEu");
        Twitter twitter = new TwitterFactory().getInstance(accessToken);
        try {
            Relationship relationship = twitter.showFriendship("@ScottWinkler16", "@ansonium");
            System.out.println("isSourceBlockingTarget: " + relationship.isSourceBlockingTarget());
            System.out.println("isSourceFollowedByTarget: " + relationship.isSourceFollowedByTarget());
            System.out.println("isSourceFollowingByTarget: " + relationship.isSourceFollowingTarget());
            System.out.println("isSourceNotificationsEnabled: " + relationship.isSourceNotificationsEnabled());
            System.out.println("canSourceDm: " + relationship.canSourceDm());
        }
        catch (TwitterException e) {
            e.printStackTrace();
            System.out.println("Failed to show friendship: " + e.getMessage());
        }
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
    }


}
