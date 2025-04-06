package it.unibo.model.chapter.map;

import it.unibo.model.tile.Tile;

/**
 * Class for generating the map for the Chapter randomly with the Wave function collapse algorithm.
 */
public interface MapGeneration {

    /**
     * 
     * @return a random generated Map.
     */
    Tile[][] generateMap();
}
