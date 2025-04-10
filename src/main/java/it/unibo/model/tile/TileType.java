package it.unibo.model.tile;

import java.util.Map;

import it.unibo.common.DirectionEnum;

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
    TILE_WATER,
    /**
     * Rock tile.
     */
    TILE_ROCK,
    /**
     * Upper coast tile.
     */
    TILE_COAST_UP,
    /**
     * Right coast tile.
     */
    TILE_COAST_RIGHT,
    /**
     * Down coast tile.
     */
    TILE_COAST_DOWN,
    /**
     * Left coast tile.
     */
    TILE_COAST_LEFT,
    /**
     * Upper-Right coast tile.
     */
    TILE_COAST_UP_RIGHT,
    /**
     * Right-Down coast tile.
     */
    TILE_COAST_RIGHT_DOWN,
    /**
     * Down-Left coast tile.
     */
    TILE_COAST_DOWN_LEFT,
    /**
     * Upper-Left coast tile.
     */
    TILE_COAST_UP_LEFT,
    /**
     * Upper-Right2 coast tile.
     */
    TILE_COAST_UP_RIGHT2,
    /**
     * Right-Down2 coast tile.
     */
    TILE_COAST_RIGHT_DOWN2,
    /**
     * Down-Left2 coast tile.
     */
    TILE_COAST_DOWN_LEFT2,
    /**
     * Upper-Left2 coast tile.
     */
    TILE_COAST_UP_LEFT2;

    /**
     * Returns a Map describing the edges of a type of tile.
     * @param tileType the tile's type we want to know the edges.
     * @return a Map of all the edges, on the directions [UP, RIGHT, DOWN, LEFT], of {@code tileType}.
     */
    public static Map<DirectionEnum, TileEdge> getEdges(final TileType tileType) {
        switch (tileType) {
            case TILE_WATER:
                return Map.of(DirectionEnum.UP, TileEdge.EDGE_WATER,
                        DirectionEnum.RIGHT, TileEdge.EDGE_WATER,
                        DirectionEnum.DOWN, TileEdge.EDGE_WATER,
                        DirectionEnum.LEFT, TileEdge.EDGE_WATER);
            case TILE_COAST_UP:
                return Map.of(DirectionEnum.UP, TileEdge.EDGE_GRASS,
                        DirectionEnum.RIGHT, TileEdge.EDGE_COAST_UP,
                        DirectionEnum.DOWN, TileEdge.EDGE_WATER,
                        DirectionEnum.LEFT, TileEdge.EDGE_COAST_UP);
            case TILE_COAST_RIGHT:
                return Map.of(DirectionEnum.UP, TileEdge.EDGE_COAST_RIGHT,
                        DirectionEnum.RIGHT, TileEdge.EDGE_GRASS,
                        DirectionEnum.DOWN, TileEdge.EDGE_COAST_RIGHT,
                        DirectionEnum.LEFT, TileEdge.EDGE_WATER);
            case TILE_COAST_DOWN:
                return Map.of(DirectionEnum.UP, TileEdge.EDGE_WATER,
                        DirectionEnum.RIGHT, TileEdge.EDGE_COAST_DOWN,
                        DirectionEnum.DOWN, TileEdge.EDGE_GRASS,
                        DirectionEnum.LEFT, TileEdge.EDGE_COAST_DOWN);
            case TILE_COAST_LEFT:
                return Map.of(DirectionEnum.UP, TileEdge.EDGE_COAST_LEFT,
                        DirectionEnum.RIGHT, TileEdge.EDGE_WATER,
                        DirectionEnum.DOWN, TileEdge.EDGE_COAST_LEFT,
                        DirectionEnum.LEFT, TileEdge.EDGE_GRASS);
            case TILE_COAST_UP_RIGHT:
                return Map.of(DirectionEnum.UP, TileEdge.EDGE_GRASS,
                        DirectionEnum.RIGHT, TileEdge.EDGE_GRASS,
                        DirectionEnum.DOWN, TileEdge.EDGE_COAST_RIGHT,
                        DirectionEnum.LEFT, TileEdge.EDGE_COAST_UP);
            case TILE_COAST_RIGHT_DOWN:
                return Map.of(DirectionEnum.UP, TileEdge.EDGE_COAST_RIGHT,
                        DirectionEnum.RIGHT, TileEdge.EDGE_GRASS,
                        DirectionEnum.DOWN, TileEdge.EDGE_GRASS,
                        DirectionEnum.LEFT, TileEdge.EDGE_COAST_DOWN);
            case TILE_COAST_DOWN_LEFT:
                return Map.of(DirectionEnum.UP, TileEdge.EDGE_COAST_LEFT,
                        DirectionEnum.RIGHT, TileEdge.EDGE_COAST_DOWN,
                        DirectionEnum.DOWN, TileEdge.EDGE_GRASS,
                        DirectionEnum.LEFT, TileEdge.EDGE_GRASS);
            case TILE_COAST_UP_LEFT:
                return Map.of(DirectionEnum.UP, TileEdge.EDGE_GRASS,
                        DirectionEnum.RIGHT, TileEdge.EDGE_COAST_UP,
                        DirectionEnum.DOWN, TileEdge.EDGE_COAST_LEFT,
                        DirectionEnum.LEFT, TileEdge.EDGE_GRASS);
            case TILE_COAST_UP_RIGHT2:
                return Map.of(DirectionEnum.UP, TileEdge.EDGE_COAST_RIGHT,
                        DirectionEnum.RIGHT, TileEdge.EDGE_COAST_UP,
                        DirectionEnum.DOWN, TileEdge.EDGE_WATER,
                        DirectionEnum.LEFT, TileEdge.EDGE_WATER);
            case TILE_COAST_RIGHT_DOWN2:
                return Map.of(DirectionEnum.UP, TileEdge.EDGE_WATER,
                        DirectionEnum.RIGHT, TileEdge.EDGE_COAST_DOWN,
                        DirectionEnum.DOWN, TileEdge.EDGE_COAST_RIGHT,
                        DirectionEnum.LEFT, TileEdge.EDGE_WATER);
            case TILE_COAST_DOWN_LEFT2:
                return Map.of(DirectionEnum.UP, TileEdge.EDGE_WATER,
                        DirectionEnum.RIGHT, TileEdge.EDGE_WATER,
                        DirectionEnum.DOWN, TileEdge.EDGE_COAST_LEFT,
                        DirectionEnum.LEFT, TileEdge.EDGE_COAST_DOWN);
            case TILE_COAST_UP_LEFT2:
                return Map.of(DirectionEnum.UP, TileEdge.EDGE_COAST_LEFT,
                        DirectionEnum.RIGHT, TileEdge.EDGE_WATER,
                        DirectionEnum.DOWN, TileEdge.EDGE_WATER,
                        DirectionEnum.LEFT, TileEdge.EDGE_COAST_UP);
            default:
                return Map.of(DirectionEnum.UP, TileEdge.EDGE_GRASS,
                        DirectionEnum.RIGHT, TileEdge.EDGE_GRASS,
                        DirectionEnum.DOWN, TileEdge.EDGE_GRASS,
                        DirectionEnum.LEFT, TileEdge.EDGE_GRASS);
        }
    }

    /**
     * Returns the weight of a type of tile,  the weight is used by the random to generate the map.
     * @param tileType the tile's type we want to know the weight. 
     * @return the weight of the {@code tileType}.
     */
    public static int getWeight(final TileType tileType) {
        final int weightTileGrass = 750;
        final int weightTileWater = 4;
        final int weightTileRock = 5;
        final int weightTileSingleCoast = 6;
        final int weightTileDoubleCoast = 8;
        final int weightTileDoubleCoast2 = 1;
        switch (tileType) {
            case TILE_GRASS:
                return weightTileGrass;
            case TILE_WATER:
                return weightTileWater;
            case TILE_ROCK:
                return weightTileRock;
            case TILE_COAST_UP:
            case TILE_COAST_RIGHT:
            case TILE_COAST_DOWN:
            case TILE_COAST_LEFT:
                return weightTileSingleCoast;
            case TILE_COAST_UP_RIGHT:
            case TILE_COAST_RIGHT_DOWN:
            case TILE_COAST_DOWN_LEFT:
            case TILE_COAST_UP_LEFT:
                return weightTileDoubleCoast;
            default:
                return weightTileDoubleCoast2;
        }
    }

    /**
     * Returns a boolean representing if the type of tile can be walked on.
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
     * Returns a {@code String} representing the path of a type of tile.
     * @param tileType the tile's type we want to have the image path.
     * @return the image path that corresponds to {@code tileType}.
     */
    public static String getPath(final TileType tileType) {
        switch (tileType) {
            case TILE_GRASS:
                return "tile/grass.png";
            case TILE_WATER:
                return "tile/water.png";
            case TILE_COAST_UP:
                return "tile/coastUp.png";
            case TILE_COAST_RIGHT:
                return "tile/coastRight.png";
            case TILE_COAST_DOWN:
                return "tile/coastDown.png";
            case TILE_COAST_LEFT:
                return "tile/water.png";
            case TILE_COAST_UP_RIGHT:
                return "tile/coastUpRight.png";
            case TILE_COAST_RIGHT_DOWN:
                return "tile/coastRightDown.png";
            case TILE_COAST_DOWN_LEFT:
                return "tile/coastDownLeft.png";
            case TILE_COAST_UP_LEFT:
                return "tile/coastUpLeft.png";
            case TILE_COAST_UP_RIGHT2:
                return "tile/coastUpRight2.png";
            case TILE_COAST_RIGHT_DOWN2:
                return "tile/coastRightDown2.png";
            case TILE_COAST_DOWN_LEFT2:
                return "tile/coastDownLeft2.png";
            default:
                return "tile/coastUpLeft2.png";
        }
    }
}
