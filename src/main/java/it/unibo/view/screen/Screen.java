package it.unibo.view.screen;

import java.awt.Color;
import java.util.List;

import it.unibo.common.Position;
import it.unibo.model.chapter.map.Map;
import it.unibo.model.human.Human;

/**
 * Models an Object that can render things on the screen.
 */
public interface Screen {
    /**
     * 
     * @param text the string to show.
     * @param position the position on the screen.
     * @param color the color of the text.
     * @param size the size of the text.
     */
    void loadText(String text, Position position, Color color, int size);

    /**
     * 
     * @param humans the humans to load.
     */
    void loadHumans(List<Human> humans);

    /**
     * 
     * @param map the map to load.
     */
    void loadMap(Map map);

    /**
     * Sets the offset for the rendering.
     * @param xOffset
     * @param yOffset
     */
    void setOffset(int xOffset, int yOffset);

    /**
     * Draws the loaded objects on the screen.
     */
    void show();
}
