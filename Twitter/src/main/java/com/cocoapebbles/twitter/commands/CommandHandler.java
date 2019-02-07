package com.cocoapebbles.twitter.commands;

import com.cocoapebbles.twitter.Main;
import com.cocoapebbles.twitter.Town;
import com.cocoapebbles.twitter.clients.WorldEditClient;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class CommandHandler implements CommandExecutor {
    private Main m;
    private Town town;
    public CommandHandler(Main m) {
        this.m = m;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
        String[] otherArgs = Arrays.copyOfRange(args, 1, args.length);
        switch(args[0]){
            case "town": return townHandler(sender,otherArgs);
            case "help": return helpHandler(sender,otherArgs);
            default: return false;
        }
    }

    public boolean townHandler(CommandSender sender, String[]args){
        switch(args[0]) {
            case "clear": {
                town.clearAll();
                break;
            }
            case "create": {
                Player player = (Player) sender;
                WorldEditClient wec = WorldEditClient.getInstance();
                wec.addPlayer(player);
                World world = player.getWorld();
                Location location = player.getLocation().subtract(0,1,0);
                location = new Location(world,(int)location.getBlockX(),(int)location.getBlockY(),(int)location.getBlockZ());
                town = new Town(world,location);
                town.drawAll();
                break;
            }
        }
        return true;
    }

    public boolean helpHandler(CommandSender sender, String[]args){
        String[] message = new String[]{
                ChatColor.AQUA+ "[Twitter] Cocoapebble's twitter Mod!",
                ChatColor.AQUA+"  help: makes all your dreams come true",
                ChatColor.AQUA+"  town create: makes all your dreams come true",
                ChatColor.AQUA+"  town clear: makes all your dreams come true"
        };
        sender.sendMessage(message);
        return true;
    }


}


