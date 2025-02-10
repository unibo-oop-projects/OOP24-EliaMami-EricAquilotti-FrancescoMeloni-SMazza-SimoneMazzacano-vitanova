package it.unibo.view.screen;

import it.unibo.common.Position;
import it.unibo.model.human.Human;
import it.unibo.model.tile.Tile;

/**
 * Models an Object that can render things on the screen.
 */
public interface Screen {
    /**
     * 
     * @param text the string to render.
     */
    void renderText(String text);

    /**
     * 
     * @param human the human to render.
     */
    void renderHuman(Human human);

    /**
     * 
     * @param position position where to render.
     * @param tile the tile to render.
     */
    void renderTile(Position position, Tile tile);

    /**
     * Sets the offset for the rendering.
     * @param xOffset
     * @param yOffset
     */
    void setOffset(int xOffset, int yOffset);
}
