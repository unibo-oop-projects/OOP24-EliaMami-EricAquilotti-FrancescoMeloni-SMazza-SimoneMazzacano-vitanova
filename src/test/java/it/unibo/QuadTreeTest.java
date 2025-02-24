package it.unibo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import it.unibo.common.CircleImpl;
import it.unibo.common.Position;
import it.unibo.common.RectangleImpl;
import it.unibo.model.chapter.quadtree.Point;
import it.unibo.model.chapter.quadtree.QuadTree;
import it.unibo.model.chapter.quadtree.QuadTreeImpl;

class QuadTreeTest {
    @Test
    void insertionTest() {
        final QuadTree tree = new QuadTreeImpl(
            new RectangleImpl(new Position(0, 0), 4, 4)
        );
        // basic insertion
        assertTrue(tree.insert(new Point(new Position(2, 0), null)));
        assertTrue(tree.insert(new Point(new Position(0, 1), null)));
        // edge
        assertTrue(tree.insert(new Point(new Position(0, 0), null)));
        assertTrue(tree.insert(new Point(new Position(0, 4), null)));
        // no insertion
        assertFalse(tree.insert(new Point(new Position(-1, 0), null)));
    }

    @Test
    void queryTest() {
        final QuadTree tree = new QuadTreeImpl(
            new RectangleImpl(new Position(0, 0), 4, 4)
        );
        final Point point1 = new Point(new Position(2, 2), null);
        final Point point2 = new Point(new Position(0, 4), null);
        final Point point3 = new Point(new Position(0, 2), null);
        assertTrue(tree.insert(point1));
        assertTrue(tree.insert(point2));
        assertTrue(tree.insert(point3));

        final List<Point> closeHumans = new ArrayList<>();
        tree.query(new CircleImpl(4, 4, 4), closeHumans);
        assertEquals(Set.of(point1, point2), Set.copyOf(closeHumans));
    }
}
