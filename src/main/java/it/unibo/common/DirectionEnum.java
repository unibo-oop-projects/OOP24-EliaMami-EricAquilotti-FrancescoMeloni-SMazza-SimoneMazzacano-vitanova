package it.unibo.common;

/**
 * Enum to easily get the name of the direction that the humans are facing.
 */
public enum DirectionEnum {
    /**
     * The direction names.
     */
    UP, DOWN, LEFT, RIGHT, NONE;

    /**
     * 
     * @param direction the direction to cast to the enum.
     * @return the correct enum based on the direction.
     */
    public static DirectionEnum getDirectionEnum(final Direction direction) {
        final int dx = direction.getDx();
        final int dy = direction.getDy();

        if (dy < 0) {
            return UP;
        } else if (dx > 0) {
            return RIGHT;
        } else if (dy > 0) {
            return DOWN;
        } else if (dx < 0) {
            return LEFT;
        } else {
            return NONE;
        }
    }
}
