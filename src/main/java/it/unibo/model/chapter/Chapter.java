package it.unibo.model.chapter;

import java.time.Duration;
import java.util.List;

import it.unibo.common.ChapterState;
import it.unibo.model.chapter.map.Map;
import it.unibo.model.human.Human;
import it.unibo.model.pickable.Pickable;

/**
 * Models a chapter that will handle the map, all humans movements and
 * collisions.
 */
public interface Chapter {
    /**
     * Goes to the next state i.e. moves all the humans and checks for
     * collisions.
     */
    void update();

    /**
     * Method that returns the chapter map.
     * @return the map.
     */
    Map getMap();

    /**
     * Method that returns the current chapter number.
     * @return the chapter number.
     */
    int getChapterNumber();

    /**
     * Method that return the list of all npc in the chapter.
     * @return the list of humans except player that are currently on the map.
     */
    List<Human> getHumans();

    /**
     * Method that returns the list of all pickable power up spawned in the map.
     * @return the list of pickable power up that are currently on the map.
     */
    List<Pickable> getPickablePowerUp();

    /**
     * Method that returns the player from the humans list.
     * @return the player.
     */
    Human getPlayer();

    /**
     * Method that returns the population goal.
     * @return the number of humans to reach to win.
     */
    int getPopulationGoal();

    /**
     * Method that returns the chapter's state.
     * @return state of the chapter, it can be won/lost/in progress.
     */
    ChapterState getState();

    /**
     * Brings back the chapter to its initial state.
     */
    void restart();

    /**
     * Method that returns the remaining timer value.
     * @return the remaining timer value.
     */
    Duration getTimerValue();
}
