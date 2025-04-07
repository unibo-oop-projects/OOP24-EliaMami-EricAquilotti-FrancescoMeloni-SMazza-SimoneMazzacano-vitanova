package it.unibo.common;

/**
 * Enum to easily get the name of the direction that the humans are facing.
 */
public enum DirectionEnum {
    /**
     * The direction names.
     */
    UP, RIGHT, DOWN, LEFT, NONE;

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

    /**
     * Returns the opposite direction of {@code direction}.
     * @param direction the direction we want the opposite.
     * @return the opposite direction.
     */
    public static DirectionEnum getOpposite(final DirectionEnum direction) {
        switch (direction) {
            case UP:
                return DOWN;
            case RIGHT:
                return LEFT;
            case DOWN:
                return UP;
            case LEFT:
                return RIGHT;
            default:
                return NONE;
        }
    }
}
