package it.unibo.common;

/**
 * @param up tells if going up.
 * @param right tells if going right.
 * @param down tells if going down.
 * @param left tells if going left.
 */
public record Direction(boolean up, boolean right, boolean down, boolean left) {
}
