package com.cocoapebbles.twitter.clients;

import com.cocoapebbles.twitter.Region;
import com.cocoapebbles.twitter.constants.Blocks;
import com.cocoapebbles.twitter.drawable.Drawable;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class WorldEditClient {
    private static WorldEditClient _instance = null;
    private String _currentSchematic;
    private ArrayList<Player> _players;

    private WorldEditClient(){
        _players = new ArrayList<>();
        _currentSchematic = "";
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

    public void drawSchematic(String schematic, Location location,boolean flip){
        String coordinate = locationToCoordinate(location);
        //load schematic only if it isn't already in the clipboard
        if(!_currentSchematic.equals(schematic)){
            performCommand("/schematic load "+schematic);
            _currentSchematic = schematic;
        }
        if(flip){
            performCommand("/rotate 180");
        }
        performCommand("/pos1 "+coordinate);
        performCommand("/paste");
        //to clean up clipboard
        if(flip){
            performCommand("/rotate 180");
        }
    }

    /**
     * expects the two points of region to be at the same y height
     * @param region
     * @param height
     */
    public void clearRegion(Region region,int height){
        Location pos1 = region.getPos1();
        Location pos2 = new Location(region.getPos2().getWorld(),region.getPos2().getX(),region.getPos2().getY()+height,region.getPos2().getZ());
        pos2.setY(pos2.getY()+height);
        String coordinate1 = locationToCoordinate(pos1);
        String coordinate2 = locationToCoordinate(pos2);
        performCommand("/pos1 "+coordinate1);
        performCommand("/pos2 " + coordinate2);
        int airBlock = Blocks.AIR;
        performCommand("/set " +airBlock);
        coordinate2 = locationToCoordinate(region.getPos2());
        //performCommand("/pos1 "+coordinate1);
        performCommand("/pos2 " + coordinate2);
        int grassBlock = Blocks.GRASS;
        performCommand("/set "+grassBlock);
    }

    public void setRegion(Region region, int block){
        Location pos1 = region.getPos1();
        Location pos2 = region.getPos2();
        String coordinate1 = locationToCoordinate(pos1);
        String coordinate2 = locationToCoordinate(pos2);
        performCommand("/pos1 "+coordinate1);
        performCommand("/pos2 " + coordinate2);
        performCommand("/set "+block);
    }

    private String locationToCoordinate(Location location){
        return location.getBlockX()+","+location.getBlockY()+","+location.getBlockZ();
    }

    private void performCommand(String command){
        Player player = this.getPlayer();
        player.performCommand(command);
    }

}
