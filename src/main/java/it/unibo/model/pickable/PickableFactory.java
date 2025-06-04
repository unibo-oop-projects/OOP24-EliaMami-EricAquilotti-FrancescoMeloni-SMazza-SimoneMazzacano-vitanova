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
     * @param value the value to multiply the stat with
     * @return a pickable that multiply speed.
     */
    Pickable speedPickable(Position spawnPosition, Duration duration, double value);

    /**
     * Method that returns a new pickable with a speed effect
     * associated with a fixed duration and a fixed multiplier.
     * @param spawnPosition the spawn position of the pickable
     * @return a pickable that multiply speed.
     */
    Pickable speedPickable(Position spawnPosition);

    /**
     * Method that returns a new pickable with a sickness resistence effect associated.
     * @param spawnPosition the spawn position of the pickable
     * @param duration the duration in seconds
     * @param value the value to multiply the stat with
     * @return a pickable that multiply the sickness resistance.
     */
    Pickable sicknessResistencePickable(Position spawnPosition, Duration duration, double value);

    /**
     * Method that returns a new pickable with a sickness resistence effect
     * associated with a fixed duration and a fixed multiplier.
     * @param spawnPosition the spawn position of the pickable
     * @return a pickable that multiply the sickness resistance.
     */
    Pickable sicknessResistencePickable(Position spawnPosition);

    /**
     * Method that returns a new pickable with a reproduction range effect associated.
     * @param spawnPosition the spawn position of the pickable 
     * @param duration the duration in seconds
     * @param value the value to multiply the stat with
     * @return a pickable that multiply the reproduction range.
     */
    Pickable reproductionRangePickable(Position spawnPosition, Duration duration, double value);

    /**
     * Method that returns a new pickable with a reproduction range effect
     * associated with a fixed duration and a fixed multiplier.
     * @param spawnPosition the spawn position of the pickable 
     * @return a pickable that multiply the reproduction range.
     */
    Pickable reproductionRangePickable(Position spawnPosition);

    /**
     * Method that returns a new pickable with a random effect
     * associated with a fixed duration and a fixed multiplier.
     * @param spawnPosition the spawn position of the pickable
     * @return a random pickable multiply.
     */
    Pickable randomPickable(Position spawnPosition);
}
