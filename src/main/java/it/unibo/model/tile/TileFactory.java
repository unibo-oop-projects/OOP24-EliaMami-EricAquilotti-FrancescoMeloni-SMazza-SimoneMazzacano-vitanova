package it.unibo.model.tile;

/**
 * Models a factory for the tiles.
 */
public interface TileFactory {
    /**
     * 
     * @return a grass tile that is walkable.
     */
    Tile tileGrass();

    /**
     * 
     * @return a water tile that is not walkable.
     */
    Tile tileWater();
}
