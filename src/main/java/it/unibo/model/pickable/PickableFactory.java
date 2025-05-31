package it.unibo.model.pickable;

import java.time.Duration;

import it.unibo.common.Position;

/**
 * Pickable factory.
 */
public interface PickableFactory {

    /**
     * Method that returns a new pickable with a speed effect associated.
     * @param spawnPosition the spawn position of the pickable
     * @param duration the duration in seconds
     * @param boost the value to multiply the parameter with
     * @return a pickable that boost speed.
     */
    Pickable speedBoost(Position spawnPosition, Duration duration, double boost);

    /**
     * Method that returns a new pickable with a speed effect associated with a fixed duration and a fixed boost.
     * @param spawnPosition the spawn position of the pickable
     * @return a pickable that boost speed.
     */
    Pickable speedBoost(Position spawnPosition);

    /**
     * Method that returns a new pickable with a sickness resistence effect associated.
     * @param spawnPosition the spawn position of the pickable
     * @param duration the duration in seconds
     * @param boost the value to multiply the parameter with
     * @return a pickable that boost the sickness resistance.
     */
    Pickable sicknessResistenceBoost(Position spawnPosition, Duration duration, double boost);

    /**
     * Method that returns a new pickable with a sickness resistence effect associated with a fixed duration and a fixed boost.
     * @param spawnPosition the spawn position of the pickable
     * @return a pickable that boost the sickness resistance.
     */
    Pickable sicknessResistenceBoost(Position spawnPosition);

    /**
     * Method that returns a new pickable with a reproduction range effect associated.
     * @param spawnPosition the spawn position of the pickable 
     * @param duration the duration in seconds
     * @param boost the value to multiply the parameter with
     * @return a pickable that boost the reproduction range.
     */
    Pickable reproductionRangeBoost(Position spawnPosition, Duration duration, double boost);

    /**
     * Method that returns a new pickable with a reproduction range effect associated with a fixed duration and a fixed boost.
     * @param spawnPosition the spawn position of the pickable 
     * @return a pickable that boost the reproduction range.
     */
    Pickable reproductionRangeBoost(Position spawnPosition);

    /**
     * Method that returns a new pickable with a random effect associated with a fixed duration and a fixed boost.
     * @param spawnPosition
     * @return a random pickable boost.
     */
    Pickable randomBoost(Position spawnPosition);
}
