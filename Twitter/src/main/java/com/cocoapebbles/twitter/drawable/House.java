package com.cocoapebbles.twitter.drawable;

import com.cocoapebbles.twitter.clients.TwitterClient;
import com.cocoapebbles.twitter.clients.WorldEditClient;
import com.cocoapebbles.twitter.constants.Blocks;
import com.cocoapebbles.twitter.constants.Dim;
import com.cocoapebbles.twitter.constants.Schematics;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import twitter4j.User;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.*;


public class House implements Drawable{
    private User user;
    private Dimensions dimensions = Dim.HOUSE_DIMENSIONS;
    private Location location;
    private boolean flip;

    private TwitterClient tc;
    private WorldEditClient wec;

    public House(){
        tc = TwitterClient.getInstance();
        wec = WorldEditClient.getInstance();
    }

    public <T> void initialize(T entity,Location location, boolean flip){
        this.user = (User) entity;
        this.location = location;
        this.flip = flip;
    }

    public Dimensions getDimensions(){
        return dimensions;
    }

    public void draw(){
        //Determine which house to draw based on how many followers the user has
        String schematic;
        int followersCount = user.getFollowersCount();
        if(followersCount<500){
            schematic = Schematics.POOR_HOUSE;
        } else if (followersCount<10000){
            schematic = Schematics.NORMAL_HOUSE;
        } else{
            schematic = Schematics.RICH_HOUSE;
        }

        //could put this in a network utility
       // System.out.println("400x400 profile pic"+user.get400x400ProfileImageURLHttps());
        String avatarUrl = user.get400x400ProfileImageURLHttps();
        String dataFolder = Bukkit.getServer().getPluginManager().getPlugin("Twitter").getDataFolder().getAbsolutePath();
        dataFolder = dataFolder.replace("/Twitter","")+ "/Images/"+user.getScreenName()+".png";
       // dataFolder = dataFolder + "/Images/"+user.getScreenName()+".png";
      //  System.out.println(dataFolder);
        try(InputStream in = new URL(avatarUrl).openStream()){
            Path path = Paths.get(dataFolder);
            if (Files.notExists(path)){
              //  System.out.println("file doesnt exist, creating");
                //Files.createFile(path);
               try {
                   Files.copy(in, path);
               }catch(FileAlreadyExistsException e){
                   e.printStackTrace();
               }
            }

        }catch(IOException e){
            e.printStackTrace();
        }

        wec.drawSchematic(schematic,location,flip);
    }

    public void clear(){
        wec.setRegion(location,dimensions,flip, Blocks.AIR);
        wec.setRegion(location,dimensions.getFlatDimensions(),flip,Blocks.GRASS);
    }
}
