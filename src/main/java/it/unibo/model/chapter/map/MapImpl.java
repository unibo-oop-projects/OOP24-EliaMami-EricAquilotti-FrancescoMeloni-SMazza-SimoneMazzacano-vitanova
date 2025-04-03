package it.unibo.model.chapter.map;

import it.unibo.model.tile.Tile;
import it.unibo.view.screen.ScreenImpl;

/**
 * Implementation of a game map.
 */
public final class MapImpl implements Map {

    private final int rows;
    private final int coloumns;
    private final Tile[][] tiles;

    /**
     * Initialize tileIds and loads the map from a file.
     * @param rows number of the rows of the map
     * @param coloumns number of the coloumns of the map
     */
    public MapImpl(final int rows, final int coloumns) {
        this.rows = rows;
        this.coloumns = coloumns;
        this.tiles = null;
    }

    @Override
    public int getRows() {
        return this.rows;
    }

    @Override
    public int getColoumns() {
        return this.coloumns;
    }

    @Override
    public Tile[][] getTiles() {
        return this.tiles;
    }

    @Override
    public Tile getTileFromPixel(final double x, final double y) {
        final int newX = (int) (x + ScreenImpl.TILE_SIZE / 2) / ScreenImpl.TILE_SIZE;
        final int newY = (int) (y + ScreenImpl.TILE_SIZE / 2)  / ScreenImpl.TILE_SIZE;
        return tiles[newX][newY];
    }

}
