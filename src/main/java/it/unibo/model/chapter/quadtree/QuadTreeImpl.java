package it.unibo.model.chapter.quadtree;

import java.util.ArrayList;
import java.util.List;

import it.unibo.common.Circle;
import it.unibo.common.Position;
import it.unibo.common.Rectangle;
import it.unibo.common.RectangleImpl;
import it.unibo.model.human.Human;

/**
 * Implementation of a quad tree that allows insertion and queries.
 */
public final class QuadTreeImpl implements QuadTree {
    private static final int CAPACITY = 10;
    private final Rectangle boundary;
    private final List<Human> humans;
    private boolean isDivided;
    private QuadTree northWest;
    private QuadTree northEast;
    private QuadTree southWest;
    private QuadTree southEast;

    /**
     * 
     * @param boundary the boundary of the node.
     */
    public QuadTreeImpl(final Rectangle boundary) {
        this.boundary = boundary;
        this.humans = new ArrayList<>(CAPACITY);
    }

    @Override
    public boolean insert(final Human human) {
        if (!boundary.contains(human.getPosition())) {
            return false;
        }
        if (humans.size() < CAPACITY && !isDivided) {
            humans.add(human);
            return true;
        }
        if (!isDivided) {
            subdivide();
        }
        return northWest.insert(human)
            || northEast.insert(human)
            || southWest.insert(human)
            || southEast.insert(human);
    }

    private void subdivide() {
        final double x = this.boundary.topLeftCorner().x();
        final double y = this.boundary.topLeftCorner().y();
        final double h = this.boundary.height();
        final double w = this.boundary.width();

        final Rectangle ne = new RectangleImpl(new Position(x + w / 2, y), w / 2, h / 2);
        this.northEast = new QuadTreeImpl(ne);
        final Rectangle nw = new RectangleImpl(new Position(x, y), w / 2, h / 2);
        this.northWest = new QuadTreeImpl(nw);
        final Rectangle se = new RectangleImpl(new Position(x + w / 2, y + h / 2), w / 2, h / 2);
        this.southEast = new QuadTreeImpl(se);
        final Rectangle sw = new RectangleImpl(new Position(x, y + h / 2), w / 2, h / 2);
        this.southWest = new QuadTreeImpl(sw);
        this.isDivided = true;
    }

    @Override
    public void query(final Circle range, final List<Human> found) {
        if (!range.intersects(this.boundary)) {
            return;
        }
        for (final Human human : this.humans) {
            if (range.contains(human.getPosition())) {
                found.add(human);
            }
        }
        if (isDivided) {
            northWest.query(range, found);
            northEast.query(range, found);
            southWest.query(range, found);
            southEast.query(range, found);
        }
    }

}
