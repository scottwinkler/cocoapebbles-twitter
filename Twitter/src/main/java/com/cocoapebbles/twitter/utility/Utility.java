package com.cocoapebbles.twitter.utility;

import org.bukkit.Location;

public class Utility {
    public static Location relativePos(Location location, int x, int z){
        return new Location(location.getWorld(),location.getX()+(double)x,location.getY(),location.getZ()+(double)z);
    }

    public static Location relativePos(Location location, int x, int y, int z){
        return new Location(location.getWorld(),location.getX()+(double)x,location.getY()+(double)y,location.getZ()+(double)z);
    }
}
