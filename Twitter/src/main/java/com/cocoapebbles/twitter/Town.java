package com.cocoapebbles.twitter;

import com.cocoapebbles.twitter.clients.TwitterClient;
import com.cocoapebbles.twitter.drawable.*;
import org.bukkit.Location;
import org.bukkit.World;
import twitter4j.IDs;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import java.util.ArrayList;

public class Town {
    private World world;
    private Twitter twitter;
    private ArrayList<Drawable> drawables;


    public Town(World world){
        drawables = new ArrayList<>();
        this.world = world;
        twitter = TwitterClient.getInstance().twitter;
        try {
            IDs ids = twitter.friendsFollowers().getFriendsIDs(-1);
            long[] idsArr = ids.getIDs();
           // int index = 0;
            //int end = idsArr.length + (10-(idsArr.length%10))-1;
            Location location = new Location(world,0,70,0);
            for (int i =0;i<idsArr.length;i=i+10){
                System.out.println("Creating new housing block: "+i);
                createHousingBlock(idsArr,i,location);
                //only going 24 instead of 28 to create some overlap with the roads
                location.add(0.0,0.0,24);
            }
        }catch (TwitterException e){
            e.printStackTrace();
        }
    }

    private Location relativePos(Location location,int x, int z){
        return new Location(location.getWorld(),location.getX()+(double)x,location.getY(),location.getZ()+(double)z);
    }

    private Region relativeRegion(Location location,int x, int z, int lengthX, int widthZ){
        return new Region(relativePos(location,x,z),relativePos(location,x+lengthX-1,z+widthZ-1));
    }

    private void createCityBlock(Object[] entities, int lengthX, int widthZ, Location location){

    }
    private void createHousingBlock(long[] ids, int index, Location location){
        //create 4 roads surrounding the block
        System.out.println("Making roads");
        drawables.add(new Road(relativeRegion(location,0,0,4,28)));
        drawables.add(new Road(relativeRegion(location,4,0,50,4)));
        drawables.add(new Road(relativeRegion(location,54,0,4,28)));
        drawables.add(new Road(relativeRegion(location,4,24,50,4)));

        //create houses
        int len = ids.length;
        int end = index+10;
        int curX = 4;
        int curZ = 4;
        System.out.println("index: " +index+",end: "+end+", ids len: "+ids.length);
        for(int i =index;i<end;i++){
            Drawable drawable = null;
            int remaining = (end-i);
            //reset to top right corner
            if (remaining==5){
                curZ=23;
                curX=4;
            }
            Region region = null;
            System.out.println("curX: "+curX+", curZ: "+curZ+", remaining: "+ remaining);
            boolean flip = (remaining>5);
            if (flip){
                region = new Region(relativePos(location,curX+9,curZ),relativePos(location,curX,curZ+9));
            }else{
                region = new Region(relativePos(location,curX,curZ),relativePos(location,curX+9,curZ-9));
            }
            //create a house if able, otherwise fill with an empty lot
            if (i<len) {
                long id = ids[i];
                drawable = new House(id, region, flip);
            } else{
                drawable = new EmptyLot(region);
            }
            drawables.add(drawable);

            //calculate next coordinate position, going in a zigzag fashion
            curX+=10;
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
