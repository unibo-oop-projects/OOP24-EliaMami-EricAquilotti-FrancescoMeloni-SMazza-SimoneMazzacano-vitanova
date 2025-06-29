package it.unibo.model.human;

import java.util.List;

import it.unibo.common.Position;
import it.unibo.controller.InputHandler;
import it.unibo.model.chapter.map.Map;
import it.unibo.model.human.stats.HumanStats;

/**
 * Models a factory of NPCs that can be male or female.
 */
public interface HumanFactory {

    /**
     * Method that create a new male npc.
     * @param startingPosition the initial position.
     * @param map the chapter's map.
     * @return a new male human that moves randomly.
     */
    Human male(Position startingPosition, Map map);

    /**
     * Method that create a new female npc.
     * @param startingPosition the initial position.
     * @param map the chapter's map.
     * @return a new female human that moves randomly and can collide with other
     * humans that are not females.
     */
    Human female(Position startingPosition, Map map);


    /**
     * Method that create the player npc.
     * @param startingPosition the initial position.
     * @param map the chapter's map.
     * @param inputHandler the handler for the user input.
     * @return a new player human, the movement is based on the user input.
     */
    Human player(Position startingPosition, Map map, InputHandler inputHandler);

    /**
     * Method that create the player npc.
     * @param startingPosition the initial position.
     * @param map the chapter's map.
     * @param inputHandler the handler for the user input.
     * @param playerStats the stats for the player.
     * @return a new player human, the movement is based on the user input.
     */
    Human player(Position startingPosition, Map map, InputHandler inputHandler, HumanStats playerStats);

    /**
     * Method that create the player npc.
     * @param startingPosition the initial position.
     * @param map the chapter's map.
     * @param inputHandler the handler for the user input.
     * @param upgrade upgrade taken by the player.
     * @return a new player human, the movement is based on the user input.
     */
    Human player(Position startingPosition, Map map, InputHandler inputHandler, List<Integer> upgrade);
}
