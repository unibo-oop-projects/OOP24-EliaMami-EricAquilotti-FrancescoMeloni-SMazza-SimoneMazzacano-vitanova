package it.unibo.common;

import java.util.Objects;

/**
 * Handles a direction and tells the movement on the x and y axis.
 */
public final class Direction {
    private final boolean up, right, down, left;

    /**
     * Creates a new Direction object with defined direction booleans.
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
     * Retrieves the movement on the x axis.
     * 
     * @return the movement on the x axis.
     */
    public int getDx() {
        return (left ? -1 : 0) + (right ? 1 : 0);
    }

    /**
     * Retrieves the movement on the y axis.
     * 
     * @return the movement on the y axis.
     */
    public int getDy() {
        return (up ? -1 : 0) + (down ? 1 : 0);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Direction other = (Direction) obj;
        return this.up == other.up
            && this.right == other.right
            && this.down == other.down
            && this.left == other.left;
    }

    @Override
    public int hashCode() {
        return Objects.hash(up, right, down, left);
    }

}
