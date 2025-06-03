package it.unibo.view.screen;

import java.awt.Color;
import java.time.Duration;
import java.util.List;
import java.util.Optional;

import it.unibo.common.Position;
import it.unibo.model.chapter.PopulationCounter;
import it.unibo.model.chapter.map.Map;
import it.unibo.model.human.Human;
import it.unibo.view.menu.MenuContent;
import it.unibo.model.pickable.Pickable;

/**
 * Models an Object that can render things on the screen.
 */
public interface Screen {
    /**
     * Loads a text that will appear on the screen.
     * 
     * @param text the string to show.
     * @param position the position on the screen.
     * @param color the color of the text.
     * @param size the size of the text.
     */
    void loadText(String text, Position position, Color color, int size);

    /**
     * Loads a menu that will be shown into the screen.
     * 
     * @param content content that represent the menu
     */
    void loadMenu(MenuContent content);

    /**
     * Loads a list of humans that will be shown into the screen.
     * 
     * @param humans the humans to load.
     */
    void loadHumans(List<Human> humans);

    /**
     * Loads a list of powerUps that will be shown into the screen.
     * 
     * @param pickablePowerUps the pickable power ups to load.
     */
    void loadPickablePowerUp(List<Pickable> pickablePowerUps);

    /**
     * Loads the map that will be shown into the screen.
     * 
     * @param map the map to load.
     */
    void loadMap(Map map);

    /**
     * Loads the duration of the timer that will be shown into the screen.
     * 
     * @param timerValue the duration of the timer to load.
     */
    void loadTimer(Optional<Duration> timerValue);

    /**
     * Loads the population counter to be displayed on the screen.
     * The counter is rendered in a predefined position.
     * 
     * @param populationCounter the population counter to load.
     * @see PopulationCounter
     */
    void loadPopulationCounter(Optional<PopulationCounter> populationCounter);

    /**
     * Sets the offset for the rendering.
     * @param xOffset the offset on the x axis.
     * @param yOffset the offset on the y axis.
     */
    void setOffset(int xOffset, int yOffset);
}
