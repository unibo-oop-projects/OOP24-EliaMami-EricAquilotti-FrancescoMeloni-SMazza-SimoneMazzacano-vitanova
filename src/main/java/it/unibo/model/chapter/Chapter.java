package it.unibo.model.chapter;

import java.util.List;

import it.unibo.model.chapter.map.Map;
import it.unibo.model.human.Human;
// import it.unibo.model.human.Player;

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
}
