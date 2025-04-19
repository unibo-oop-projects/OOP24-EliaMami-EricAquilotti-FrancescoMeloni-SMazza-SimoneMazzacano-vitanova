package it.unibo.model.human.strategies.reproduction;

import it.unibo.common.Position;

/**
 * Models a factory for reproduction strategies.
 */
public interface ReproStrategyFactory {
    /**
     * 
     * @param startingPosition the position needed to set the reproduction area.
     * @return the reproduction strategy for males (they do not collide)
     */
    ReproStrategy maleReproStrategy(Position startingPosition);

    /**
     * 
     * @param startingPosition the position needed to set the reproduction area.
     * @return the reproduction strategy for female that can collide with males
     * and have a cooldown for reproduction.
     */
    ReproStrategy femaleReproStrategy(Position startingPosition);
}
