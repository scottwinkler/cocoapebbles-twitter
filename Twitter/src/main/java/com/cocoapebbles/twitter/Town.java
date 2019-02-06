package com.cocoapebbles.twitter;

import com.cocoapebbles.twitter.clients.TwitterClient;
import com.cocoapebbles.twitter.constants.Dim;
import com.cocoapebbles.twitter.drawable.*;
import com.cocoapebbles.twitter.utility.Utility;
import org.bukkit.Location;
import org.bukkit.World;
import twitter4j.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Supplier;

public class Town {
    private World world;
    private Twitter twitter;
    private ArrayList<Drawable> drawables;

    //Could put this in a Twitter DAO
    public ArrayList<User> getFriends(){
        ArrayList<User> users = new ArrayList<>();
        try {
            String screenName = twitter.users().getAccountSettings().getScreenName();
            long cursor = -1;
            PagableResponseList<User> paginatedUsers;
            do {
                paginatedUsers = twitter.friendsFollowers().getFriendsList(screenName, cursor, 200);
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

    public Town(World world){
        drawables = new ArrayList<>();
        this.world = world;
        twitter = TwitterClient.getInstance().twitter;
        ArrayList<User> friends = getFriends();
        int rowCount = 5;
        int len = friends.size();
        Location location = new Location(world,0,70,0);
        for (int i =0;i<len;i+=10){
            int end = i + rowCount*2;
            if(end>len){
                end = len;
            }
            ArrayList<User> friendsSlice = new ArrayList<User>(friends.subList(i,end));
            createCityBlock(friendsSlice,House::new,rowCount,location);
            int zOffset = Dim.HOUSE_DIMENSIONS.getWidthZ()*2+Dim.ROAD_WIDTH;
            location.add(0,0,zOffset);
        }
    }




    //A helper function for creating city blocks, surrounded by roads and filled with drawables
    private void createCityBlock(ArrayList<?> entities, Supplier<Drawable> constructor, int rowCount, Location location){
        Drawable reference = constructor.get();
        Dimensions dimensions = reference.getDimensions();
        int widthZ = dimensions.getWidthZ();
        int lengthX = dimensions.getLengthX();
        int roadWidth = Dim.ROAD_WIDTH;

        //create 4 roads surrounding the block
        System.out.println("Making roads");
        //top
        drawables.add(new Road(new Dimensions(lengthX*rowCount,1,roadWidth), Utility.relativePos(location,roadWidth,2*widthZ+roadWidth)));
        //right
        drawables.add(new Road(new Dimensions(roadWidth,1,2*(widthZ+roadWidth)),Utility.relativePos(location,0,0)));
        //bottom
        drawables.add(new Road(new Dimensions(lengthX*rowCount,1,roadWidth),Utility.relativePos(location,roadWidth,0)));
        //left
        drawables.add(new Road(new Dimensions(roadWidth,1,2*(widthZ+roadWidth)),Utility.relativePos(location,roadWidth+lengthX*rowCount,0)));

        //Create drawables
        int len = entities.size();
        int end = 2*rowCount;
        int curX = roadWidth;
        int curZ = roadWidth;
        for(int i =0;i<end;i++){
            Drawable drawable = null;
            int remaining = (end-i);
            if (remaining ==rowCount){
                //reset to top right corner
                curZ = 2*widthZ+roadWidth-1;
                curX = roadWidth+lengthX-1;
            }
            boolean flip = (remaining<=5);
            Location curPos = Utility.relativePos(location,curX,curZ);
            //create entity if able
            if (i<len){
                drawable = constructor.get();
                drawable.initialize(entities.get(i),curPos,flip);
            } else {
                drawable = new EmptyLot(dimensions.getFlatDimensions());
                drawable.initialize(null,curPos,flip);
            }
            drawables.add(drawable);

            //calculate next coordinate position, going across
            curX+=lengthX;
        }
    }

    public void drawAll(){
        for (Drawable drawable : drawables){
            drawable.draw();
        }
    }

    public void clearAll(){
        for(Drawable drawable : drawables){
            drawable.clear();
        }
    }
}
