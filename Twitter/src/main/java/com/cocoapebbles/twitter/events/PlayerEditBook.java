package com.cocoapebbles.twitter.events;

import com.cocoapebbles.twitter.clients.TwitterClient;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEditBookEvent;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import java.util.List;

public class PlayerEditBook implements Listener {
    Twitter twitter;
    public PlayerEditBook(){
        twitter = TwitterClient.getInstance().twitter;
    }

    @EventHandler
    public void onPlayerEditBook(PlayerEditBookEvent event){
        if(event.isSigning()){
            List<String> pages = event.getNewBookMeta().getPages();
            StringBuilder sb = new StringBuilder();
            for (String s : pages){
                sb.append(s);
            }
            StatusUpdate statusUpdate = new StatusUpdate(sb.toString());
            try {
                twitter.tweets().updateStatus(statusUpdate);
            }catch(TwitterException e){
                e.printStackTrace();
            }
        }
    }
}
