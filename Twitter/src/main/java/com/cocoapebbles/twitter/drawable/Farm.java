package com.cocoapebbles.twitter.drawable;

import com.cocoapebbles.twitter.clients.ListenerClient;
import com.cocoapebbles.twitter.clients.TwitterClient;
import com.cocoapebbles.twitter.clients.WorldEditClient;
import com.cocoapebbles.twitter.constants.Blocks;
import com.cocoapebbles.twitter.constants.Dim;
import com.cocoapebbles.twitter.constants.Schematics;
import com.cocoapebbles.twitter.utility.Utility;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Sheep;
import twitter4j.PagableResponseList;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;

import java.util.ArrayList;
import java.util.Collections;

public class Farm implements Drawable {

    private User user;
    private Dimensions dimensions = Dim.HOUSE_DIMENSIONS;
    private Location location;
    private boolean flip;
    private ArrayList<User> followers;
    private Twitter twitter;
    private WorldEditClient wec;
    private ListenerClient lc;

    public Farm(){
        twitter = TwitterClient.getInstance().twitter;
        wec = WorldEditClient.getInstance();
        lc = ListenerClient.getInstance();
    }

    @Override
    public void draw() {
        wec.drawSchematic(Schematics.FARM,Utility.relativePos(location,0,1,0),flip);
        followers = getFollowers();
        World world = location.getWorld();
        Location entityLocation = Utility.relativePos(location, 8,4,6);
        for(User user : followers){
            Sheep sheep = (Sheep) world.spawnEntity(entityLocation,EntityType.SHEEP);
            sheep.setCustomName(user.getScreenName());
            entityLocation = entityLocation.add(0,0,1);
        }
    }

    @Override
    public void clear() {
        wec.setRegion(location,dimensions,flip, Blocks.AIR);
        Location grassLocation = Utility.relativePos(location,0,-1,0);
        wec.setRegion(grassLocation,dimensions.getFlatDimensions(),flip,Blocks.GRASS);
    }

    @Override
    public <T> void initialize(T entity, Location location, boolean flip) {
        user = (User) entity;
        this.location = location;
        this.flip = flip;
        this.dimensions = Dim.FARM_DIMENSIONS;
    }

    //Could put this in a Twitter DAO
    public ArrayList<User> getFollowers(){
        ArrayList<User> users = new ArrayList<>();
        try {
            String screenName = user.getScreenName();
            long cursor = -1;
            PagableResponseList<User> paginatedUsers;
            do {
                paginatedUsers = twitter.friendsFollowers().getFollowersList(screenName, cursor, 200);
                for (User user : paginatedUsers) {
                    users.add(user);
                }
            } while ((cursor = paginatedUsers.getNextCursor()) != 0);
        } catch(TwitterException e){
            e.printStackTrace();
        }
        Collections.sort(users);
        return users;
    }

    @Override
    public Dimensions getDimensions() {
        return Dim.FARM_DIMENSIONS;
    }
}
