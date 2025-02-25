package it.unibo.model.tile;

/**
 * Manages the Tiles and maps them to ids.
 */
public final class TileManager {
    private static final TileFactory TILE_FACTORY = new TileFactoryImpl();
    private static final Tile[] TILES = {
        TILE_FACTORY.tileGrass(),
        TILE_FACTORY.tileWater(),
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
