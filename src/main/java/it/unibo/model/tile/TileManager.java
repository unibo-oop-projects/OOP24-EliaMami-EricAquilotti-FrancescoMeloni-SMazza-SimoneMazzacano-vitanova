package it.unibo.model.tile;

/**
 * Manages the Tiles and maps them to ids.
 */
public interface TileManager {
    /**
     * 
     * @param id the id of the tile.
     * @return the corrisponding tile.
     */
    Tile getTile(int id);
}
