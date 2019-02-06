package com.cocoapebbles.twitter.clients;

import com.cocoapebbles.twitter.drawable.Dimensions;
import com.cocoapebbles.twitter.utility.Utility;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.ArrayList;

public class WorldEditClient {
    private static WorldEditClient _instance = null;
    private String _cachedSchematic;
    private boolean _flip;
    private ArrayList<Player> _players;

    private WorldEditClient(){
        _players = new ArrayList<>();
        _cachedSchematic = "";
        _flip = false;
    }

    public static WorldEditClient getInstance(){
        if(_instance == null){
            _instance = new WorldEditClient();
        }
        return _instance;
    }

    public Player getPlayer() {
        if (!_players.isEmpty()){
            return _players.get(0);
        }
        return null;
    }

    public void addPlayer(Player player)
    {
        //add player only if it isn't already in the players array
        for (Player p: _players){
            if(p.getUniqueId()==player.getUniqueId()){
                return;
            }
        }
        _players.add(player);
        player.performCommand("/toggleplace");
    }

    public void removePlayer(Player player){
        for(Player p : new ArrayList<>(_players)){
            if(p.getUniqueId() == player.getUniqueId()){
                _players.remove(p);
                break;
            }
        }
    }

    public void drawImage(String fileName, Location location,BlockFace blockFace){
        performCommand("image create "+fileName);
        Player player = getPlayer();
        World world = player.getWorld();
        ItemStack itemStack = new ItemStack(Material.AIR);
        Block topLeftBlock = world.getBlockAt(location);
        PlayerInteractEvent playerInteractEvent = new PlayerInteractEvent(player, Action.RIGHT_CLICK_BLOCK,itemStack,topLeftBlock, blockFace);
        BukkitClient.callEvent(playerInteractEvent);
    }

    //The Holographic Display plugin does not have a function for changing the location, so have to manually edit the save file
    public void writeText(String id, String text, Location location, boolean flip){
        performCommand("hd create "+id);
        String filePath = BukkitClient.getDataDir().replace("/Twitter","/HolographicDisplays/database.yml");
        ArrayList<String> lines = new ArrayList<String>(Arrays.asList(Utility.splitToNChar(text,50)));
       File file = new File(filePath);
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);
        yamlConfiguration.getConfigurationSection(id).set("location","world, "+location.getX()+","+location.getY()+","+location.getZ());
        yamlConfiguration.getConfigurationSection(id).set("lines",lines);
        try {
            yamlConfiguration.save(file);
        }catch(IOException e){
            e.printStackTrace();
        }
         performCommand("hd reload");
    }

    public void drawSchematic(String schematic, Location location,boolean flip){
        //correction for the save file oddity
        location.add(0,1,0);
        String coordinate = locationToCoordinate(location);
        //load schematic only if it isn't already in the clipboard
        if(!_cachedSchematic.equals(schematic)){
            performCommand("/schematic load "+schematic);
            _cachedSchematic = schematic;
            _flip = flip;
            if(_flip){
                performCommand("/rotate 180");
            }
        } else{
            if(!(_flip==flip)){
                _flip=flip;
                performCommand("/rotate 180");
            }
        }
        performCommand("/pos1 "+coordinate);
        performCommand("/paste");
    }

    public void setRegion(Location location, Dimensions dimensions, boolean flip, int block){
        Location pos1 = location;
        Location pos2 = null;
        if (!flip){
            pos2 = Utility.relativePos(location,dimensions.getLengthX()-1,dimensions.getHeightY()-1,dimensions.getWidthZ()-1);
        } else {
            pos2 = Utility.relativePos(location,-dimensions.getLengthX()+1,dimensions.getHeightY()-1,-dimensions.getWidthZ()+1);
        }
        String coordinate1 = locationToCoordinate(pos1);
        String coordinate2 = locationToCoordinate(pos2);
        performCommand("/pos1 "+coordinate1);
        performCommand("/pos2 " + coordinate2);
        performCommand("/set "+block);
    }

    public String locationToCoordinate(Location location){
        return location.getBlockX()+","+location.getBlockY()+","+location.getBlockZ();
    }

    private void performCommand(String command){
        Player player = this.getPlayer();
        player.performCommand(command);
    }

}
