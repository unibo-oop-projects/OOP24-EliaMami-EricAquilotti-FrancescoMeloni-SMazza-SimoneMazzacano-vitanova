package it.unibo.common;

/**
 * Implementation of a rectangle defined by the top-left corner, width and
 * height.
 */
public final class RectangleImpl implements Rectangle {
    private final double x;
    private final double y;
    private final double width;
    private final double height;

    /**
     * Initialize fields.
     * @param topLeftCorner the position of the top left corner.
     * @param width the width of the rectangle.
     * @param height the height of the rectangle.
     */
    public RectangleImpl(final Position topLeftCorner, final double width, final double height) {
        this.x = topLeftCorner.x();
        this.y = topLeftCorner.y();
        this.width = width;
        this.height = height;
    }

    @Override
    public boolean contains(final Position point) {
        return point.x() >= this.x
            && point.x() <= this.x + this.width
            && point.y() >= this.y
            && point.y() <= this.y + this.height;
    }

    @Override
    public Position topLeftCorner() {
        return new Position(x, y);
    }

    @Override
    public double width() {
        return width;
    }

    @Override
    public double height() {
        return height;
    }

}
