package com.cocoapebbles.twitter.drawable;

import org.bukkit.Location;

public interface Drawable {

    /**
     * Draw at default location
     */
    void draw();

    /**
     * Cleans up resource
     */
    void clear();
}
