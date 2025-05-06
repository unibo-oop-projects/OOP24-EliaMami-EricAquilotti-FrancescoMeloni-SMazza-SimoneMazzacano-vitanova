package it.unibo.model.malus;

import it.unibo.model.pickable.PickablePowerUp;

/**
 * Malus factory interface.
 */
public interface MalusFactory {

    /**
     * 
     * @param name the effect name
     * @param duration the duration in seconds
     * @param malus the value to multiply the parameter with
     * @return an effect that decrease the speed.
     */
    PickablePowerUp speedDecrease(String name, int duration, double malus);

    /**
     * 
     * @param name the effect name
     * @param duration the duration in seconds
     * @param malus the value to multiply the parameter with
     * @return an effect that decrease the radius range.
     */
    PickablePowerUp reproductionRangeDecrease(String name, int duration, double malus);

    /**
     * 
     * @param name the effect name
     * @param duration the duration in seconds
     * @param malus the value to multiply the parameter with
     * @return an effect that decrease the fertility.
     */
    PickablePowerUp fertilityDecrease(String name, int duration, double malus);
}
