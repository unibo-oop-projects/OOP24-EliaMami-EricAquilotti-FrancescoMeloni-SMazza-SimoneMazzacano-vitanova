package it.unibo.common;

/**
 * Handles a direction and tells the movement on the x and y axis.
 */
public class Direction {
    private final boolean north, east, south, west;

    /**
     * 
     * @param up true if going up.
     * @param right true if going right.
     * @param down true if going down.
     * @param left true if going left.
     */
    public Direction(final boolean north, final boolean east, final boolean south, final boolean west) {
        this.north = north;
        this.east = east;
        this.south = south;
        this.west = west;
    }

    /**
     * 
     * @return the movement on the x axis.
     */
    public int getDx() {
        return (west ? -1 : 0) + (east ? 1 : 0);
    }

    /**
     * 
     * @return the movement on the y axis.
     */
    public int getDy() {
        return (north ? -1 : 0) + (south ? 1 : 0);
    }
}
