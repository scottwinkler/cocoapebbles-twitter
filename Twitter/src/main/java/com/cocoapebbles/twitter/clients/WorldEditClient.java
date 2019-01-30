package com.cocoapebbles.twitter.clients;

import com.cocoapebbles.twitter.drawable.Drawable;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class WorldEditClient {
    private static WorldEditClient _instance = null;
    private ArrayList<Player> _players;

    private WorldEditClient(){
        _players = new ArrayList<>();
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

    public void addPlayer(Player player) {
        _players.add(player);
    }

    public void removePlayer(Player player){
        for(Player p : new ArrayList<>(_players)){
            if(p.getUniqueId() == player.getUniqueId()){
                _players.remove(p);
                break;
            }
        }
    }

    public void drawSchematic(String schematic, Location location){
        String coordinate = locationToCoordinate(location);
        performCommand("/schematic load "+schematic);
        performCommand("/pos1 "+coordinate);
        performCommand("/paste");
    }

    public void clearRegion(Location pos1, Location pos2){
        String coordinate1 = locationToCoordinate(pos1);
        String coordinate2 = locationToCoordinate(pos2);
        performCommand("/pos1 "+coordinate1);
        performCommand("/pos2 " + coordinate2);
        performCommand("/replace 0");
        //replace floor with grass
        //take the lowest point as y for both coordinate so we have a region with a height of 0
        int minY = pos1.getBlockY();
        if(pos2.getBlockY()<minY){
            minY = pos2.getBlockY();
        }
        pos1.setY(minY);
        pos2.setY(minY);
        coordinate1 = locationToCoordinate(pos1);
        coordinate2 = locationToCoordinate(pos2);
        performCommand("/set 1");
    }

    private String locationToCoordinate(Location location){
        return location.getBlockX()+","+location.getBlockY()+","+location.getBlockZ();
    }

    private void performCommand(String command){
        Player player = this.getPlayer();
        player.performCommand(command);
    }

}
