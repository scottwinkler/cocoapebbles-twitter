package com.cocoapebbles.twitter.utility;
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
