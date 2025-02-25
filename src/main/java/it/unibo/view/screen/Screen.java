package it.unibo.view.screen;

import java.util.List;

import it.unibo.model.chapter.map.Map;
import it.unibo.model.human.Human;

/**
 * Models an Object that can render things on the screen.
 */
public interface Screen {
    /**
     * 
     * @param text the string to load.
     */
    void loadText(String text);

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
