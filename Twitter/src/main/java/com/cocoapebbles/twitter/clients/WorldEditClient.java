package com.cocoapebbles.twitter.clients;

import com.cocoapebbles.twitter.drawable.Dimensions;
import com.cocoapebbles.twitter.constants.Blocks;
import com.cocoapebbles.twitter.utility.Utility;
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
        //correction for the save file oddity
        location.add(0,0.5,0);
        String coordinate = locationToCoordinate(location);
        System.out.println(coordinate);
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

    private String locationToCoordinate(Location location){
        return location.getBlockX()+","+location.getBlockY()+","+location.getBlockZ();
    }

    private void performCommand(String command){
        Player player = this.getPlayer();
        player.performCommand(command);
    }

}
