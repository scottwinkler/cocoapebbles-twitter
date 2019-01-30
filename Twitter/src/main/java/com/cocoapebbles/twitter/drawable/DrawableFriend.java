package com.cocoapebbles.twitter.drawable;

import com.cocoapebbles.twitter.clients.TwitterClient;
import com.cocoapebbles.twitter.clients.WorldEditClient;
import org.bukkit.Location;
import twitter4j.TwitterException;
import twitter4j.User;


public class DrawableFriend implements Drawable{
    private User user;
    private Location pos1;
    private Location pos2;
    private TwitterClient tc;
    private WorldEditClient wec;

    public DrawableFriend(long userId, Location location){
        tc = TwitterClient.getInstance();
        wec = WorldEditClient.getInstance();
        try {
            user = tc.twitter.users().showUser(userId);
        } catch(TwitterException e){
            e.printStackTrace();
        }
        pos1 = location;
        pos2 = new Location(location.getWorld(),location.getX()+ Const.PLOT_LENGTH,location.getY(),location.getZ()+ Const.PLOT_WIDTH);
    }

    public void draw(){
        wec.drawSchematic(Const.HOUSE_SCHEMATIC,pos1);
    }

    public void redraw(){
        clear();
        draw();
    }

    public void clear(){
        wec.clearRegion(pos1,pos2);
    }
}
