package it.unibo.view.screen;

import it.unibo.model.chapter.Map;
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
     * @param human the human to render.
     */
    void renderHuman(Human human);

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
