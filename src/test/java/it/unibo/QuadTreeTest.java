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
import it.unibo.model.chapter.quadtree.QuadTree;
import it.unibo.model.chapter.quadtree.QuadTreeImpl;
import it.unibo.model.human.Human;
import it.unibo.model.human.MaleImpl;

class QuadTreeTest {
    @Test
    void insertionTest() {
        final QuadTree tree = new QuadTreeImpl(
            new RectangleImpl(new Position(0, 0), 4, 4)
        );
        // basic insertion
        assertTrue(tree.insert(new MaleImpl(new Position(2, 0))));
        assertTrue(tree.insert(new MaleImpl(new Position(0, 1))));
        // edge
        assertTrue(tree.insert(new MaleImpl(new Position(0, 0))));
        assertTrue(tree.insert(new MaleImpl(new Position(0, 4))));
        // no insertion
        assertFalse(tree.insert(new MaleImpl(new Position(-1, 0))));
    }

    @Test
    void queryTest() {
        final QuadTree tree = new QuadTreeImpl(
            new RectangleImpl(new Position(0, 0), 4, 4)
        );
        final Human male1 = new MaleImpl(new Position(2, 2));
        final Human male2 = new MaleImpl(new Position(0, 4));
        final Human male3 = new MaleImpl(new Position(0, 2));
        assertTrue(tree.insert(male1));
        assertTrue(tree.insert(male2));
        assertTrue(tree.insert(male3));

        final List<Human> closeHumans = new ArrayList<>();
        tree.query(new CircleImpl(4, 4, 4), closeHumans);
        assertEquals(Set.of(male1, male2), Set.copyOf(closeHumans));
    }
}
