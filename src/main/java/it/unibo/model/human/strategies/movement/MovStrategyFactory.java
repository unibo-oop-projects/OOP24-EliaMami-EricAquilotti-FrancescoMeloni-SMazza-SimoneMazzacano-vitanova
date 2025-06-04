package it.unibo.model.human.strategies.movement;

import it.unibo.controller.InputHandler;

/**
 * Factory for creating different movement strategies.
 */
public interface MovStrategyFactory {

    /**
     * Retrieves a Movement strategy that moves based on the user input.
     * 
     * @param inputHandler the handler to get input from for player movement.
     * @return the movement strategy for a player.
     */
    MovStrategy userInputMovement(InputHandler inputHandler);

    /**
     * Retieves a Movement strategy that moves in random directions.
     * 
     * @return a movement strategy for random-controlled humans.
     */
    MovStrategy randomMovement();
}

