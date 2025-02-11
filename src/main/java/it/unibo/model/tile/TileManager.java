package it.unibo.model.tile;

/**
 * Manages the Tiles and maps them to ids.
 */
public final class TileManager {

    /**
     * Array that contains all the tile types.
     */
    private static final Tile[] TILES = {
        new TileGrass(),
        new TileWater(),
    };

    private TileManager() { }

    /**
     * 
     * @param id the id of the tile.
     * @return the corrisponding tile.
     */
    public static Tile getTile(final int id) {
        return TILES[id];
    }
}
