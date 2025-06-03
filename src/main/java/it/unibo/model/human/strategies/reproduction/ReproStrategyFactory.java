package it.unibo.model.human.strategies.reproduction;

import it.unibo.common.Position;

/**
 * Factory for creating different reproduction strategies.
 */
public interface ReproStrategyFactory {
    /**
     * Retrieves the reproduction strategy for males that never collide.
     * 
     * @param startingPosition the position needed to set the reproduction area.
     * @return the reproduction strategy for males (they do not collide)
     */
    ReproStrategy maleReproStrategy(Position startingPosition);

    /**
     * Retrieves the reproduction stategy for females that can collide with
     * males and have a cooldown for reproduction.
     * 
     * @param startingPosition the position needed to set the reproduction area.
     * @return the reproduction strategy for female that can collide with males
     * and have a cooldown for reproduction.
     */
    ReproStrategy femaleReproStrategy(Position startingPosition);
}
