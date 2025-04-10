package it.unibo.common;

/**
 * Handles a direction and tells the movement on the x and y axis.
 */
public class Direction {
    private final boolean up, right, down, left;

    /**
     * 
     * @param up true if going up.
     * @param right true if going right.
     * @param down true if going down.
     * @param left true if going left.
     */
    public Direction(final boolean up, final boolean right, final boolean down, final boolean left) {
        this.up = up;
        this.right = right;
        this.down = down;
        this.left = left;
    }

    /**
     * 
     * @return the movement on the x axis.
     */
    public int getDx() {
        return (left ? -1 : 0) + (right ? 1 : 0);
    }

    /**
     * 
     * @return the movement on the y axis.
     */
    public int getDy() {
        return (up ? -1 : 0) + (down ? 1 : 0);
    }
}
