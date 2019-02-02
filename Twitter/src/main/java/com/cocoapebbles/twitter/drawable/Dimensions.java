package com.cocoapebbles.twitter.drawable;

public class Dimensions {
    private int lengthX;
    private int heightY;
    private int widthZ;
    public Dimensions(int lengthX, int heightY, int widthZ){
        this.lengthX = lengthX;
        this.heightY = heightY;
        this.widthZ = widthZ;
    }
    public int getLengthX() {
        return lengthX;
    }

    public void setLengthX(int lengthX) {
        this.lengthX = lengthX;
    }

    public int getHeightY() {
        return heightY;
    }

    public void setHeightY(int heightY) {
        this.heightY = heightY;
    }

    public int getWidthZ() {
        return widthZ;
    }

    public void setWidthZ(int widthZ) {
        this.widthZ = widthZ;
    }

    public Dimensions getFlatDimensions(){
        return new Dimensions(lengthX,1,widthZ);
    }
}
