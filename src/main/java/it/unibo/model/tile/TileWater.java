package it.unibo.model.tile;

import it.unibo.view.sprite.Sprite;

public class TileWater implements Tile {

    @Override
    public boolean isWalkable() {
        return false;
    }

    @Override
    public Sprite getSprite() {
        return Sprite.TILE_WATER;
    }

}
