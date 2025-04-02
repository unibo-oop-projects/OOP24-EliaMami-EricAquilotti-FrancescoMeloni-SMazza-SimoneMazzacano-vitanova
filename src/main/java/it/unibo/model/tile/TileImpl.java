package it.unibo.model.tile;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import it.unibo.common.DirectionEnum;
import it.unibo.view.sprite.Sprite;

public class TileImpl implements Tile {

    private final List<TileType> possibleTiles = new LinkedList<>(TileType.getRules().keySet());
    private int entropy = possibleTiles.size();
    private final Map<DirectionEnum, Tile> neighbours = new HashMap<>();

    public TileImpl() {
    }

    @Override
    public boolean isWalkable() {
        var tile = getTileType();
        if (tile.isEmpty()) {
            throw new IllegalStateException("The tile has not been set.");
        }
        return TileType.isWalkable(tile.get());
    }

    @Override
    public Sprite getSprite() {
        var tile = getTileType();
        if (tile.isEmpty()) {
            throw new IllegalStateException("The tile has not been set.");
        }
        //not needed try-catch because the path will be always in the enum Sprite.
        return Sprite.getSprite(TileType.getPath(tile.get()));
    }

    private Optional<TileType> getTileType() {
        return (this.entropy != 0) 
                ? Optional.of(this.possibleTiles.getFirst())
                : Optional.empty();
    }

    @Override
    public int getEntropy() {
        return this.entropy;
    }

    @Override
    public void addNeighbour(Tile tile, DirectionEnum direction) {
        this.neighbours.put(direction, tile);
    }

    @Override
    public Optional<Tile> getNeighbour(DirectionEnum direction) {
        Tile neighbour = this.neighbours.get(direction);
        return (neighbour != null) ? Optional.of(neighbour)
                : Optional.empty();
    }

    @Override
    public Map<DirectionEnum, Tile> getNeighbours() {
        return new HashMap<>(this.neighbours);
    }

    @Override
    public List<TileType> getPossibleTiles() {
        return new LinkedList<>(this.possibleTiles);
    }

    @Override
    public void collapse() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'collapse'");
    }

    @Override
    public boolean costrain(List<TileType> neighbourPossibleTiles, DirectionEnum direction) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'costrain'");
    }

}
