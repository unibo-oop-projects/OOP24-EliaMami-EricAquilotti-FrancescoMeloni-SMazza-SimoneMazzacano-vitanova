package it.unibo.model.pickable;

import it.unibo.common.Position;

public interface PickablePowerUpFactory {
    
    /**
     * 
     * @param spawnPosition the spawn position of the pickable power up 
     * @param name the power up name
     * @param duration the duration in seconds
     * @param boost the value to multiply the parameter with
     * @return a power up that boost speed.
     */
    PickablePowerUp speedBoost(Position spawnPosition, int duration, double boost);

    /**
     * 
     * @param spawnPosition the spawn position of the pickable power up 
     * @param name the power up name
     * @param duration the duration in seconds
     * @param boost the value to multiply the parameter with
     * @return a power up that boost the sickness resistance.
     */
    PickablePowerUp sicknessResistenceBoost(Position spawnPosition, int duration, double boost);

    /**
     * 
     * @param spawnPosition the spawn position of the pickable power up
     * @param name the power up name
     * @param duration the duration in seconds
     * @param boost the value to multiply the parameter with
     * @return a power up that boost the reproduction range.
     */
    PickablePowerUp reproductionRangeBoost(Position spawnPosition, int duration, double boost);
}
