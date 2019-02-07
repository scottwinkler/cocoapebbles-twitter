package com.cocoapebbles.twitter.utility;
import com.cocoapebbles.twitter.clients.WorldEditClient;
import com.cocoapebbles.twitter.drawable.Dimensions;
import org.apache.commons.io.IOUtils;
import org.bukkit.Location;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

public class Utility {
    public static Location relativePos(Location location, int x, int z){
        return new Location(location.getWorld(),location.getX()+(double)x,location.getY(),location.getZ()+(double)z);
    }

    public static Location relativePos(Location location, int x, int y, int z){
        return new Location(location.getWorld(),location.getX()+(double)x,location.getY()+(double)y,location.getZ()+(double)z);
    }

    //could make this support 90 and 270 rotation, besides just 180 rotation
    public static Location localToGlobalCoordinates(Location location, int x, int y, int z, boolean flip){
        int xOffset=x;
        int yOffset = y;
        int zOffset=z;
        if(flip){
            xOffset = -x;
            zOffset = -z;
        }
        return relativePos(location,xOffset,yOffset,zOffset);
    }

    public static Location globalToLocalCoordinates(Location point, Location location, boolean flip){
        System.out.println("Calculating global to local for: " + Utility.locationToCoordinate(point)+" with reference to location: "+Utility.locationToCoordinate(location));
        double xOffset = location.getX();
        double yOffset = -location.getY();
        double zOffset = location.getZ();
        if(!flip){
            xOffset = -xOffset;
            zOffset = -zOffset;
        }
        Location newLocation= new Location(location.getWorld(),point.getX()+xOffset,point.getY()+yOffset,point.getZ()+zOffset);
        System.out.println("Output: "+Utility.locationToCoordinate(newLocation));
        return newLocation;
    }

    public static boolean containsPoint(Location point, Location location, Dimensions dimensions, boolean flip){
        //WorldEditClient wec = WorldEditClient.getInstance();
       // System.out.println(wec.locationToCoordinate(point)+"|"+wec.locationToCoordinate(location));
        boolean xInside=true;
        boolean yInside=true;
        boolean zInside=true;
        double leftX;
        double rightX;
        if(!flip){
            leftX = location.getX()+dimensions.getLengthX()-1;
            rightX = location.getX();
        } else{
            leftX = location.getX();
            rightX = location.getX()-dimensions.getLengthX()+1;
        }
        xInside = (point.getX()<=leftX)&&(point.getX()>=rightX);

        double bottomY= location.getY()-1;
        double topY=location.getY()+dimensions.getHeightY()-1;
       // System.out.println ("bottomZ: "+bottomY+", topY: "+topY +", pointY: "+point.getY());
        yInside = (point.getY()<=topY)&&(point.getY()>=bottomY);

        double backZ;
        double forwardZ;
        if(!flip){
            backZ = location.getZ()+dimensions.getWidthZ()-1;
            forwardZ = location.getBlockZ();
        } else {
            backZ = location.getZ();
            forwardZ = location.getBlockZ()-dimensions.getWidthZ()+1;
        }
        //System.out.println ("backZ: "+backZ+", forwardZ: "+forwardZ +", pointZ: "+point.getZ());
        zInside = (point.getZ()<=backZ)&&(point.getZ()>=forwardZ);
        //System.out.println(xInside+","+yInside+","+zInside);
        return xInside&&yInside&&zInside;

    }

    public static String locationToCoordinate(Location location){
        return location.getX()+","+location.getY()+","+location.getZ();
    }

    public static String locationToBlockCoordinate(Location location){
        return location.getBlockX()+","+location.getBlockY()+","+location.getBlockZ();
    }

    public static String loadFile(String filePath){
        String out = null;
        try {
            InputStream is = Files.newInputStream(Paths.get(filePath));
            out = IOUtils.toString(is,"UTF-8");

        }catch(IOException e){
            e.printStackTrace();
        }
        return out;
    }

    public static void deleteFile(String filePath){
        Path path = Paths.get(filePath);
        try {
            Files.deleteIfExists(path);
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public static void saveFile(String filePath, String content){
        Path path = Paths.get(filePath);
        try {
            OutputStream os = Files.newOutputStream(path);
            Files.write(path,content.getBytes(), StandardOpenOption.CREATE,StandardOpenOption.TRUNCATE_EXISTING);
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Split text into n number of characters.
     *
     * @param text the text to be split.
     * @param size the split size.
     * @return an array of the split text.
     */
    public static String[] splitToNChar(String text, int size) {
        ArrayList<String> parts = new ArrayList<>();

        int length = text.length();
        for (int i = 0; i < length; i += size) {
            parts.add(text.substring(i, Math.min(length, i + size)));
        }
        return parts.toArray(new String[0]);
    }
}
