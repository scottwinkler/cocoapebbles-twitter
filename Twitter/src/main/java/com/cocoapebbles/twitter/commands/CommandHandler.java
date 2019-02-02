package com.cocoapebbles.twitter.commands;

import com.cocoapebbles.twitter.Main;
import com.cocoapebbles.twitter.Town;
import com.cocoapebbles.twitter.clients.WorldEditClient;
import org.bukkit.ChatColor;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

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
            case "friends": return friendHandler(sender,otherArgs);
            case "help": return helpHandler(sender,otherArgs);
            default: return false;
        }
    }

    public boolean friendHandler(CommandSender sender, String[]args){
        switch(args[0]) {
            case "clear": town.clearAll();
            case "create": {
                Player player = (Player) sender;
                PlayerInteractEvent pie = new PlayerInteractEvent(player, Action.LEFT_CLICK_BLOCK,player.getInventory().getItemInMainHand(),player.getWorld().getBlockAt(player.getLocation()),BlockFace.NORTH_NORTH_WEST);
                m.pm.callEvent(pie);
                WorldEditClient wec = WorldEditClient.getInstance();
                wec.addPlayer(player);
                town = new Town(player.getWorld());
                town.drawAll();
            }
        }
        return true;
    }

    public boolean helpHandler(CommandSender sender, String[]args){
        String[] message = new String[]{
                ChatColor.AQUA+ "[Twitter] Cocoapebble's twitter Mod!",
                ChatColor.AQUA+"  help: makes all your dreams come true",
                ChatColor.AQUA+"  friends: makes all your dreams come true"
        };
        sender.sendMessage(message);
        return true;
    }


}


