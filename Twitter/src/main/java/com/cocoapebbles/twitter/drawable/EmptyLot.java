package com.cocoapebbles.twitter.drawable;

import com.cocoapebbles.twitter.Region;
import com.cocoapebbles.twitter.clients.WorldEditClient;
import com.cocoapebbles.twitter.constants.Blocks;

public class EmptyLot implements Drawable{
    private WorldEditClient wec;
    private Region region;
    private int block;

    public EmptyLot(Region region){
        wec = WorldEditClient.getInstance();
        this.region = region;
        this.block = Blocks.GRASS;
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
