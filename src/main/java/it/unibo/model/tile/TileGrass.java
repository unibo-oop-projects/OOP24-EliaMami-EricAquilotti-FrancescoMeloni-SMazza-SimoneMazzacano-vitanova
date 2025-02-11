package it.unibo.model.tile;

import it.unibo.view.sprite.Sprite;

public class TileGrass implements Tile {

    @Override
    public boolean isWalkable() {
        return true;
    }

    @Override
    public Sprite getSprite() {
        return Sprite.TILE_GRASS;
    }
    
}
