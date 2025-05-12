package it.unibo.model.human.solidcollisions;

import it.unibo.common.Position;

/**
 * Interface representing the solid collisions.
 */
public interface SolidCollisions {
    /**
     * Returns a boolean representing if the tile on the position {@code pos} is walkable.
     * @throws IllegalArgumentException if the coordinates are not in the map.
     * @param pos the position you want to know if it's walkable.
     * @return true if the position given can be walked on.
     */
    boolean isWalkable(Position pos);
}
