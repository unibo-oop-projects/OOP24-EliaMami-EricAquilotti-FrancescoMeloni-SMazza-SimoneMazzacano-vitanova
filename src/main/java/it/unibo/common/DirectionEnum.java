package it.unibo.common;

/**
 * Enum to easily get the name of the direction that the humans are facing.
 */
public enum DirectionEnum {
    /**
     * The direction names.
     */
    NORTH, EAST, SOUTH, WEST, NONE;

    /**
     * 
     * @param direction the direction to cast to the enum.
     * @return the correct enum based on the direction.
     */
    public static DirectionEnum getDirectionEnum(final Direction direction) {
        final int dx = direction.getDx();
        final int dy = direction.getDy();

        if (dy < 0) {
            return NORTH;
        } else if (dx > 0) {
            return EAST;
        } else if (dy > 0) {
            return SOUTH;
        } else if (dx < 0) {
            return WEST;
        } else {
            return NONE;
        }
    }
}
