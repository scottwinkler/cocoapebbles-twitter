package com.cocoapebbles.twitter.drawable;

import com.cocoapebbles.twitter.Region;
import com.cocoapebbles.twitter.clients.WorldEditClient;
import com.cocoapebbles.twitter.constants.Blocks;

public class Road implements Drawable {
    private WorldEditClient wec;
    private Region region;
    private int block;

    public Road(Region region){
        wec = WorldEditClient.getInstance();
        this.region = region;
        this.block = Blocks.STONE;
    }
    @Override
    public void draw() {
        wec.setRegion(region,block);
    }

    @Override
    public void clear() {
        wec.clearRegion(region,0);
    }
}
