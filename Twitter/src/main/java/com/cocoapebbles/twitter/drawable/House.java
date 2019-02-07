package com.cocoapebbles.twitter.drawable;

import com.cocoapebbles.twitter.clients.BukkitClient;
import com.cocoapebbles.twitter.clients.ListenerClient;
import com.cocoapebbles.twitter.clients.TwitterClient;
import com.cocoapebbles.twitter.clients.WorldEditClient;
import com.cocoapebbles.twitter.constants.Blocks;
import com.cocoapebbles.twitter.constants.Dim;
import com.cocoapebbles.twitter.constants.Events;
import com.cocoapebbles.twitter.constants.Schematics;
import com.cocoapebbles.twitter.utility.Utility;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import twitter4j.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.*;
import java.util.Collection;


public class House implements Drawable{
    private User user;
    private Dimensions dimensions = Dim.HOUSE_DIMENSIONS;
    private Location location;
    private boolean flip;

    private Twitter twitter;
    private WorldEditClient wec;
    private ListenerClient lc;

    public House(){
        twitter = TwitterClient.getInstance().twitter;
        wec = WorldEditClient.getInstance();
        lc = ListenerClient.getInstance();
    }

    public <T> void initialize(T entity,Location location, boolean flip){
        this.user = (User) entity;
        this.location = location;
        this.flip = flip;
    }

    public Dimensions getDimensions(){
        return dimensions;
    }


    //could put this in a network utility
    public String downloadImage(String avatarUrl){
        String dataFolder = BukkitClient.getDataDir();
        String fileName = user.getScreenName()+".png";
        String filePath= dataFolder.replace("/Twitter","")+ "/Images/"+fileName;
        try(InputStream in = new URL(avatarUrl).openStream()){
            Path path = Paths.get(filePath);
            if (Files.notExists(path)){
                try {
                    Files.copy(in, path);
                }catch(FileAlreadyExistsException e){
                    e.printStackTrace();
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return fileName;
    }

    private void handleVillagerMurdered(Event event){
        EntityDeathEvent e = (EntityDeathEvent) event;
        Villager villager = (Villager) e.getEntity();
        String screenName = user.getScreenName();
        if(villager.getCustomName().equals(screenName)){
            this.clear();
            try {
                twitter.friendsFollowers().destroyFriendship(screenName);
            } catch(TwitterException tw){
                tw.printStackTrace();
            }
        }
    }

    private void handleButtonDown(Event event){
        PlayerInteractEvent e = (PlayerInteractEvent) event;
        Location point = e.getClickedBlock().getLocation();
        if(Utility.containsPoint(point,location,dimensions,flip)){
            System.out.println("in region");
            World world = location.getWorld();
            Location localCoordinate = Utility.globalToLocalCoordinates(point,location,flip);
            System.out.println(Utility.locationToCoordinate(localCoordinate));
            boolean isLike = localCoordinate.getBlockX() == 4;
            boolean isRetweet = localCoordinate.getBlockX() == 2;
            if (isLike){
                System.out.println("is like");
                //update like sign
                Location signLocation = Utility.localToGlobalCoordinates(location,4,1,1,flip);
                Block signBlock = world.getBlockAt(signLocation);
                Sign sign = (Sign) signBlock.getState();
                long id = user.getStatus().getId();
                String text = "like (+1)";
                try {
                    if (sign.getLine(1).equals(text)) {
                        text = "undo like (-1)";
                        twitter.favorites().createFavorite(id);
                    } else {
                        twitter.favorites().destroyFavorite(id);
                    }
                }catch(TwitterException twe){
                    twe.printStackTrace();
                }
                sign.setLine(1,text);
                sign.update();
            } else if (isRetweet){
                System.out.println("is retweet");
                //update retweet sign
                Location signLocation = Utility.localToGlobalCoordinates(location,2,1,1,flip);
                Block signBlock = world.getBlockAt(signLocation);
                Sign sign = (Sign) signBlock.getState();
                long id = user.getStatus().getId();
                String text = "retweet (+1)";
                try {
                    if (sign.getLine(1).equals(text)) {
                        text = "undo retweet (-1)";
                        twitter.tweets().retweetStatus(id);
                    } else {
                        twitter.tweets().unRetweetStatus(id);
                    }
                }catch(TwitterException twe){
                    twe.printStackTrace();
                }
                sign.setLine(1,text);
                sign.update();
            }
        }
    }

    public void draw(){
        //Determine which house to draw based on how many followers the user has
        String schematic;
        int followersCount = user.getFollowersCount();
        if(followersCount<10000){
            schematic = Schematics.POOR_HOUSE;
        } else if (followersCount<1000000){
            schematic = Schematics.NORMAL_HOUSE;
        } else{
            schematic = Schematics.RICH_HOUSE;
        }


        wec.drawSchematic(schematic,Utility.relativePos(location,0,1,0),flip);

        String avatarUrl = user.get400x400ProfileImageURLHttps();
        String fileName = downloadImage(avatarUrl);

        //draw front avatar panel
        Location imageLocation = Utility.localToGlobalCoordinates(location,3,10,4,flip);
        BlockFace blockFace = flip ? BlockFace.SOUTH : BlockFace.NORTH;
        wec.drawImage(fileName,imageLocation,blockFace);

        //draw back avatar panel;
        imageLocation = Utility.localToGlobalCoordinates(location,1,10,4,flip);
        blockFace = flip ? BlockFace.NORTH : BlockFace.SOUTH;
        wec.drawImage(fileName,imageLocation,blockFace);

        //Create villager and remove existing if there is one
        Location entityLocation = Utility.localToGlobalCoordinates(location,5,6,4,flip);
        World world = location.getWorld();
        Collection<Entity> entities = world.getNearbyEntities(location,20,20,20);
        String screenName = user.getScreenName();
        for(Entity entity : entities ){
            String customName = entity.getCustomName();
            if (customName!=null && customName.equals(screenName)){
                entity.remove();
            }
        }
        Villager villager = (Villager) world.spawnEntity(entityLocation, EntityType.VILLAGER);
        villager.setCustomName(screenName);
        villager.setProfession(Villager.Profession.NITWIT);
        villager.setCareer(Villager.Career.NITWIT);
        lc.subscribe(Events.VILLAGER_MURDERED,e->handleVillagerMurdered(e));

        //Set latest tweet
        Location tweetLocation = Utility.localToGlobalCoordinates(location,4,2,0,flip);
        String tweetText = user.getStatus().getText();
        wec.writeText(Long.toString(user.getId()),tweetText,tweetLocation,flip);

        //Create like and retweet buttons
        Location votingLocation = Utility.localToGlobalCoordinates(location,2,1,0,flip);

        wec.drawSchematic(Schematics.VOTING,votingLocation,flip);
        Location signLocation = Utility.localToGlobalCoordinates(location,4,1,1,flip);
        System.out.println(Utility.locationToCoordinate(signLocation));
        Block signBlock = world.getBlockAt(signLocation);
        Sign sign = (Sign) signBlock.getState();
        sign.setLine(1,"like (+1)");
        sign.update(true);
        signLocation = Utility.localToGlobalCoordinates(location,2,1,1,flip);
        signBlock = world.getBlockAt(signLocation);
        sign = (Sign) signBlock.getState();
        sign.setLine(1,"retweet (+1)");
        sign.update(true);

        lc.subscribe(Events.BUTTON_DOWN,e -> handleButtonDown(e));

        //Fill Chest with Tweets
        Location chestLocation = Utility.localToGlobalCoordinates(location,6,1,5,flip);
        Chest chest = (Chest) world.getBlockAt(chestLocation).getState();
        Inventory chestInventory = chest.getInventory().getHolder().getInventory();
        try{
            ResponseList<Status> statusResponseList = twitter.getUserTimeline(user.getId(),new Paging().count(54));
            statusResponseList.stream().limit(54).forEach(s -> chestInventory.addItem(createBook(s)));
        } catch(TwitterException e){
            e.printStackTrace();
        }

    }

    public ItemStack createBook(Status status){
        String text = status.getText()+"\nretweets: "+status.getRetweetCount()+", likes: "+status.getFavoriteCount();
        String[] pages = Utility.splitToNChar(text,255);
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta bookMeta = (BookMeta) book.getItemMeta();
        bookMeta.setPages(pages);
        bookMeta.setAuthor(user.getScreenName());
        bookMeta.setGeneration(BookMeta.Generation.ORIGINAL);
        bookMeta.setTitle(status.getCreatedAt().toString());
        book.setItemMeta(bookMeta);
        return book;
    }

    public void clear(){
        wec.setRegion(location,dimensions,flip, Blocks.AIR);
        Location grassLocation = Utility.relativePos(location,0,0,0);
        wec.setRegion(grassLocation,dimensions.getFlatDimensions(),flip,Blocks.GRASS);
        wec.removeText(Long.toString(user.getId()));
        String dataFolder = BukkitClient.getDataDir();
        String fileName = user.getScreenName()+".png";
        String filePath= dataFolder.replace("/Twitter","")+ "/Images/"+fileName;
        Utility.deleteFile(filePath);
    }
}
