package it.unibo.model.chapter.map;

import java.util.Arrays;

import it.unibo.model.chapter.map.generator.MapGenerator;
import it.unibo.model.chapter.map.generator.WaveFunctionCollapse;
import it.unibo.model.tile.Tile;
import it.unibo.view.screen.ScreenImpl;

/**
 * Implementation of a game map.
 */
public final class MapImpl implements Map {

    /**
     * The vertical margin, used to not display the borders of the map.
     */
    public static final int MARGIN_ROWS = ScreenImpl.BASE_WINDOW_HEIGHT / ScreenImpl.TILE_SIZE;
    /**
     * The horizontal margin, used to not display the borders of the map.
     */
    public static final int MARGIN_COLOUMNS = ScreenImpl.BASE_WINDOW_WIDTH / ScreenImpl.TILE_SIZE;

    private final int rows;
    private final int coloumns;
    private final Tile[][] tiles;

    /**
     * Sets {@code rows}, {@code coloums} and generate {@code tiles}.
     * @see MapGenerator
     * @see WaveFunctionCollapse
     * @param rows number of the rows of the map
     * @param coloumns number of the coloumns of the map
     */
    public MapImpl(final int rows, final int coloumns) {
        this.rows = rows + MARGIN_ROWS;
        this.coloumns = coloumns + MARGIN_COLOUMNS;
        this.tiles = new WaveFunctionCollapse(this.rows, this.coloumns).generateMap();
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
        return Arrays.copyOf(this.tiles, this.coloumns);
    }

    @Override
    public Tile getTileFromPixel(final double x, final double y) {
        final int newX = (int) (x + ScreenImpl.TILE_SIZE / 2) / ScreenImpl.TILE_SIZE;
        final int newY = (int) (y + ScreenImpl.TILE_SIZE  - ScreenImpl.TILE_SIZE / 8)  / ScreenImpl.TILE_SIZE;
        if (newX <= this.coloumns && newY <= this.rows) {
            return tiles[newX][newY];
        }
        throw new IllegalArgumentException("The coordinates are not in the map.");
    }

}
