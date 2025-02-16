package it.unibo.model.chapter.quadtree;

import java.util.List;

import it.unibo.common.Circle;
import it.unibo.model.human.Human;

/**
 * Models a data structure that allows fast collision checking by not looking at
 * all humans but only the close ones.
 */
public interface QuadTree {
    /**
     * 
     * @param human the human to insert in the tree.
     * @return true if the human was inserted.
     */
    boolean insert(Human human);

    /**
     * Fills the found list with the humans that are inside the given range.
     * @param range the range to check.
     * @param found the list to fill.
     */
    void query(Circle range, List<Human> found);
}
