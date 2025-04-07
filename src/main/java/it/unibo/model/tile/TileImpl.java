package it.unibo.model.tile;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import it.unibo.common.DirectionEnum;
import it.unibo.view.sprite.Sprite;

/**
 * Implementation of {@code Tile}.
 * @see Tile
 */
public final class TileImpl implements Tile {
    /**
     * Using the default constructor.
     */
    private List<TileType> possibleTiles = new LinkedList<>(Arrays.asList(TileType.values()));
    private int entropy = possibleTiles.size();
    private final Map<DirectionEnum, Tile> neighbours = new EnumMap<>(DirectionEnum.class);

    @Override
    public boolean isWalkable() {
        final var tile = getTileType();
        if (tile.isEmpty()) {
            throw new IllegalStateException("The tile has not been set.");
        }
        return TileType.isWalkable(tile.get());
    }

    @Override
    public Sprite getSprite() {
        final var tile = getTileType();
        if (tile.isEmpty()) {
            throw new IllegalStateException("The tile has not been set.");
        }
        //not needed try-catch because the path will be always in the enum Sprite.
        return Sprite.getSprite(getTileType().get());
    }

    private Optional<TileType> getTileType() {
        return hasType()
                ? Optional.of(this.possibleTiles.getFirst())
                : Optional.empty();
    }

    @Override
    public int getEntropy() {
        return this.entropy;
    }

    @Override
    public boolean hasType() {
        return this.entropy == 0;
    }

    @Override
    public void addNeighbour(final Tile tile, final DirectionEnum direction) {
        this.neighbours.put(direction, tile);
    }

    @Override
    public Map<DirectionEnum, Tile> getNeighbours() {
        return new EnumMap<>(this.neighbours);
    }

    @Override
    public List<TileType> getPossibleTiles() {
        return new LinkedList<>(this.possibleTiles);
    }

    @Override
    public void collapse(final Random rand) {
        this.possibleTiles = List.of(choseTileType(rand));
        this.entropy = 0;
    }

    private TileType choseTileType(final Random rand) {
        final List<Integer> weights = this.possibleTiles.stream()
                .map(TileType::getWeight)
                .toList();
        final int totalWeights = weights.stream()
                .mapToInt(Integer::intValue)
                .sum();
        int n = rand.nextInt(0, totalWeights);
        int index = -1;
        for (; n >= 0 && index < this.entropy - 1; index++) {
            n -= weights.get(index + 1);
        }
        return this.possibleTiles.get(index);
    }

    @Override
    public boolean costrain(final DirectionEnum direction) {
        boolean reduced = false;
        if (!hasType()) {
            final var oppositeDirection = DirectionEnum.getOpposite(direction);
            final var connectors = this.neighbours.get(oppositeDirection).getPossibleTiles()
                    .stream()
                    .map(p -> TileType.getEdges(p).get(direction))
                    .toList();
            final List<TileType> tilesToRemove = new LinkedList<>();
            for (final TileType possibleTileType : this.possibleTiles) {
                if (!connectors.contains(TileType.getEdges(possibleTileType).get(oppositeDirection))) {
                    tilesToRemove.add(possibleTileType);
                    reduced = true;
                }
            }
            this.possibleTiles.removeAll(tilesToRemove);
            this.entropy = this.possibleTiles.size();
        }
        return reduced;
    }

}
