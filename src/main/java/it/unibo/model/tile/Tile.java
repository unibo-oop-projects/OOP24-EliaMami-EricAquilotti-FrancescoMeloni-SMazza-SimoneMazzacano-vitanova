package it.unibo.model.tile;

/**
 * Class representing a Tile that can be walkable or not walkable.
 */
public interface Tile {
    /**
     * 
     * @return true if a human can walk on it.
     */
    boolean isWalkable();
}
