package com.cocoapebbles.twitter.drawable;

import com.cocoapebbles.twitter.Region;
import com.cocoapebbles.twitter.clients.TwitterClient;
import com.cocoapebbles.twitter.clients.WorldEditClient;
import org.bukkit.Location;
import twitter4j.TwitterException;
import twitter4j.User;


public class House implements Drawable{
    private User user;
    private Region region;
    private boolean flip;
    private TwitterClient tc;
    private WorldEditClient wec;

    public House(long userId, Region region,boolean flip){
        tc = TwitterClient.getInstance();
        wec = WorldEditClient.getInstance();
        this.region = region;
        this.flip = flip;
        try {
            user = tc.twitter.users().showUser(userId);
        } catch(TwitterException e){
            e.printStackTrace();
        }
       ;
    }

    public void draw(){
        wec.drawSchematic(Const.HOUSE_SCHEMATIC,region.getPos1(),flip);
    }

    public void clear(){
        wec.clearRegion(region,20);
    }
}
