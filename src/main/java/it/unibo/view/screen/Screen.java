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
     * @param text the string to render.
     */
    void renderText(String text);

    /**
     * 
     * @param humans the humans to render.
     */
    void renderHumans(List<Human> humans);

    /**
     * 
     * @param map the map to render.
     */
    void renderMap(Map map);

    /**
     * Sets the offset for the rendering.
     * @param xOffset
     * @param yOffset
     */
    void setOffset(int xOffset, int yOffset);
}
