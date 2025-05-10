package it.unibo.model.pickable;

import java.time.Duration;

import it.unibo.common.Position;

/**
 * Pickable power up factory.
 */
public interface PickableFactory {

    /**
     * 
     * @param spawnPosition the spawn position of the pickable power up 
     * @param duration the duration in seconds
     * @param boost the value to multiply the parameter with
     * @return a power up that boost speed.
     */
    Pickable speedBoost(Position spawnPosition, Duration duration, double boost);

    /**
     * 
     * @param spawnPosition the spawn position of the pickable power up 
     * @param duration the duration in seconds
     * @param boost the value to multiply the parameter with
     * @return a power up that boost the sickness resistance.
     */
    Pickable sicknessResistenceBoost(Position spawnPosition, Duration duration, double boost);

    /**
     * 
     * @param spawnPosition the spawn position of the pickable power up
     * @param duration the duration in seconds
     * @param boost the value to multiply the parameter with
     * @return a power up that boost the reproduction range.
     */
    Pickable reproductionRangeBoost(Position spawnPosition, Duration duration, double boost);
}
