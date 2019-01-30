package com.cocoapebbles.twitter.commands;

import com.cocoapebbles.twitter.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import twitter4j.IDs;
import twitter4j.TwitterException;

import java.util.Arrays;

public class CommandHandler implements CommandExecutor {
    private Main m;

    public CommandHandler(Main m) {
        this.m = m;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
        String[] otherArgs = Arrays.copyOfRange(args, 1, args.length);
        switch(args[0]){
            case "friends": return friendHandler(sender,otherArgs);
            case "help": return helpHandler(sender,otherArgs);
            default: return false;
        }
    }

    public boolean friendHandler(CommandSender sender, String[]args){
        try {
            IDs ids = m.twitter.friendsFollowers().getFollowersIDs(-1);
            
        } catch(TwitterException e){
            e.printStackTrace();
        }
    }

    public boolean helpHandler(CommandSender sender, String[]args){
        String[] message = new String[]{
                ChatColor.AQUA+ "[Twitter] Cocoapebble's twitter Mod!",
                ChatColor.AQUA+"  help: makes all your dreams come true"
        };
        sender.sendMessage(message);
        return true;
    }


}


