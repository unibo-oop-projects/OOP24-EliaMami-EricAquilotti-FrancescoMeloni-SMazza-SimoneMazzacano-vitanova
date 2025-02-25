package it.unibo.model.chapter.map;

import it.unibo.model.tile.Tile;

/**
 * Models the background of a chapter.
 */
public interface Map {
    /**
     * 
     * @return the tiles of the map.
     */
    Tile[][] getTiles();
}
