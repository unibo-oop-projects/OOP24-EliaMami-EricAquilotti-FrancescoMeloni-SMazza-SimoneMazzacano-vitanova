package it.unibo.model.chapter.map.generator;

import it.unibo.model.tile.Tile;

/**
 * Class for generating the map for the Chapter randomly with the Wave function collapse algorithm.
 */
public interface MapGenerator {

    /**
     * Returns a Matrix of {@code Tile} generated with the Wave Function Collapse Algorithm.
     * @return a random generated Map.
     */
    Tile[][] generateMap();
}
