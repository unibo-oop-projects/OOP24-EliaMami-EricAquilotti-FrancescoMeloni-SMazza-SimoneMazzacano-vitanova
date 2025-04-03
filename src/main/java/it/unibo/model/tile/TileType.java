package it.unibo.model.tile;

import java.util.Set;

/**
 * Enum representing all types of tiles.
 */
public enum TileType {
    /**
     * Grass tile.
     */
    TILE_GRASS,
    /**
     * Water tile.
     */
    TILE_WATER;

    /**
     * @param tileType the tile's type we want to know the edges.
     * @return a Set of all the edges, on the directions [North, East, South, West], of a {@code tileType}.
     */
    public static Set<TileEdge> getEdges(final TileType tileType) {
        switch (tileType) {
            case TILE_GRASS:
                return Set.of(TileEdge.EDGE_GRASS, TileEdge.EDGE_GRASS, TileEdge.EDGE_GRASS, TileEdge.EDGE_GRASS);
            default:
                return Set.of(TileEdge.EDGE_GRASS, TileEdge.EDGE_GRASS, TileEdge.EDGE_GRASS, TileEdge.EDGE_GRASS);
        }
    }

    /**
     * @param tileType the tile's type we want to know the weight. 
     * @return the weight of the {@code tileType}.
     */
    public static int getWeight(final TileType tileType) {
        switch (tileType) {
            case TILE_GRASS:
                return 16;
            default:
                return 4;
        }
    }

    /**
     * @param tileType the tile's type we want to know if it is walkable.
     * @return true if a human can walk on the {@code tileType}.
     */
    public static boolean isWalkable(final TileType tileType) {
        switch (tileType) {
            case TILE_GRASS:
                return true;
            default:
                return false;
        }
    }

    /**
     * @param tileType the tile's type we want to have the image path.
     * @return the image path that corresponds to {@code tileType}.
     */
    public static String getPath(final TileType tileType) {
        switch (tileType) {
            case TILE_GRASS:
                return "tile/grass.png";
            default:
                return "tile/water.png";
        }
    }
}
