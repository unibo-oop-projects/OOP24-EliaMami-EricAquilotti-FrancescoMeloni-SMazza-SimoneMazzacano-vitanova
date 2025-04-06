package it.unibo.model.chapter.map;

import it.unibo.model.tile.Tile;

/**
 * Models the background of a chapter.
 */
public interface Map {
    /**
     * 
     * @return the number of rows of the map.
     */
    int getRows();

    /**
     * 
     * @return the number of coloumns of the map.
     */
    int getColoumns();

    /**
     * 
     * @return the tiles of the map.
     */
    Tile[][] getTiles();

    /**
     * @param x coloumn's coordinates in pixel
     * @param y row's coordinates in pixel
     * @return the tile of the map.
     * @throws IllegalArgumentException if the coordinates are not in the map.
     */
    Tile getTileFromPixel(double x, double y);
}
