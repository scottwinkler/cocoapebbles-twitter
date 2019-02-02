package com.cocoapebbles.twitter.drawable;

import com.cocoapebbles.twitter.clients.WorldEditClient;
import com.cocoapebbles.twitter.constants.Blocks;
import org.bukkit.Location;

public class Road implements Drawable {
    private WorldEditClient wec;
    private Dimensions dimensions;
    private Location location;
    private boolean flip;
    private int block;

    public Road(Dimensions dimensions){
        this.dimensions = dimensions;
        wec = WorldEditClient.getInstance();
        block = Blocks.STONE;
    }

    public Road(Dimensions dimensions,Location location){
        this.dimensions = dimensions;
        this.location = location;
        this.flip = false;
        wec = WorldEditClient.getInstance();
        block = Blocks.STONE;
    }

    @Override
    public void draw() {
        wec.setRegion(location,dimensions,flip,block);
    }

    @Override
    public void clear() {
        wec.setRegion(location,dimensions,flip,Blocks.GRASS);
    }

    @Override
    /**
     * Dont use this, instead use the constructor
     */
    public <T> void initialize(T entity, Location location, boolean flip) {
        this.location = location;
        this.flip = flip;
    }

    @Override
    public Dimensions getDimensions() {
        return dimensions;
    }
}
