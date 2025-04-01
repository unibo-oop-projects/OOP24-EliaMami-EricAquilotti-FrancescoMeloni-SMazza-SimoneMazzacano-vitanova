package it.unibo.model.tile;

import java.util.Map;
import java.util.Set;

public enum TileType {
    /**
     * The tile type.
     */
    TILE_GRASS,
    TILE_WATER;

    /**
     * 
     * @return a Map of all tile edges, on the directions [North, East, South, West], of a tile type.
     */
    public static Map<TileType, Set<TileEdge>> getRules() {
        return Map.of(
            TILE_GRASS, Set.of(TileEdge.EDGE_GRASS, TileEdge.EDGE_GRASS, TileEdge.EDGE_GRASS, TileEdge.EDGE_GRASS),
            TILE_WATER, Set.of(TileEdge.EDGE_GRASS, TileEdge.EDGE_GRASS, TileEdge.EDGE_GRASS, TileEdge.EDGE_GRASS)
        );
    }

    /**
     * 
     * @return a Map of all the weights of the tiles (probability of selecting the tile).
     */
    public static Map<TileType, Integer> getWeights() {
        return Map.of(
            TILE_GRASS, 16,
            TILE_WATER, 4
        );
    }

    /**
     * 
     * @return the image path that corresponds to {@code tileType}.
     */
    public static String getPath(TileType tileType) {
        switch (tileType) {
            case TILE_GRASS:
                return "tile/grass.png";
            case TILE_WATER:
                return "tile/water.png";
            default:
                throw new IllegalArgumentException("The tile type does not exist.");
        }
    }
}
