package com.cocoapebbles.twitter;

import org.bukkit.Location;

public class Region {
    private Location pos1;
    private Location pos2;

    public Region(Location pos1, Location pos2){
        this.pos1 = pos1;
        this.pos2 = pos2;
    }

    public Location getPos1() {
        return pos1;
    }

    public void setPos1(Location pos1) {
        this.pos1 = pos1;
    }

    public Location getPos2() {
        return pos2;
    }

    public void setPos2(Location pos2) {
        this.pos2 = pos2;
    }

    public int getLengthX(){
        return Math.abs(pos1.getBlockX()-pos2.getBlockX());
    }

    public int getWidthZ(){
        return Math.abs(pos1.getBlockZ()-pos2.getBlockZ());
    }
}
