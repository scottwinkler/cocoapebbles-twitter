package com.cocoapebbles.twitter.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.Arrays;

public class CommandHandler implements CommandExecutor {
    private JavaPlugin p;

    public CommandHandler(JavaPlugin p) {
        this.p = p;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
        String[] otherArgs = Arrays.copyOfRange(args, 1, args.length);
        switch(args[0]){
            case "help": return helpHandler(sender,otherArgs);
            default: return false;
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


