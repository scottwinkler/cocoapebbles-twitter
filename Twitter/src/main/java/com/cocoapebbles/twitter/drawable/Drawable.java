package com.cocoapebbles.twitter.drawable;

import org.bukkit.Location;

/**
 * Represents a drawable plot in XYZ space
 */
public interface Drawable {

    /**
     * Draw at default location
     */
    void draw();

    /**
     * Removes the resource
     */
    void clear();

    /**
     * Initialize the drawable resource
     */
    <T> void initialize(T entity,Location location, boolean flip);

    /**
     * Gets the Dimensions
     */

    Dimensions getDimensions();
}
