package it.unibo.model.human.strategies.movement;

import it.unibo.controller.InputHandler;

/**
 * Factory for creating different movement strategies.
 */
public interface MovStrategyFactory {

    /**
     * @param inputHandler the handler to get input from for player movement.
     * @return the movement strategy for a player.
     */
    MovementStrategy userInputMovement(InputHandler inputHandler);

    /**
     * @return a movement strategy for random-controlled humans.
     */
    MovementStrategy randomMovement();
}

