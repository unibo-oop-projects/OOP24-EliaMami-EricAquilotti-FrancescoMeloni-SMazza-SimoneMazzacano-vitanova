package it.unibo.model.chapter;

import java.time.Duration;
import java.util.List;

import it.unibo.common.ChapterState;
import it.unibo.model.chapter.map.Map;
import it.unibo.model.human.Human;

/**
 * Models a chapter that will handle the map, all humans movements and
 * collisions.
 */
public interface Chapter {
    /**
     * Goes to the next state i.e. moves all the humans and checks for
     * collisions.
     * @param gameDeltaTime the game-time passed since the last update 
     * (if the game is paused gameDeltaTime will be zero).
     */
    void update(Duration gameDeltaTime);

    /**
     * 
     * @return the gameMap.
     */
    Map getMap();

    /**
     * 
     * @return the list of humans except player that are currently on the map.
     */
    List<Human> getHumans();

    /**
     * 
     * @return the player.
     */
    Human getPlayer();

    /**
     * 
     * @return the number of humans on the map to reach.
     */
    int getPopulationGoal();

    /**
     * 
     * @return state of the chapter.
     */
    ChapterState getState();

    /**
     * brings back the chapter to its initial state.
     */
    void restart();

    /**
     * 
     * @return the timer value.
     */
    Duration getTimerValue();
}
