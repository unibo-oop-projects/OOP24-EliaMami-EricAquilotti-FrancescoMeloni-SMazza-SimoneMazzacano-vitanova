package it.unibo.model.effect;

public interface EffectFactory {
    
    /**
     * 
     * @param name the effect name
     * @param duration the duration in seconds
     * @param boost the value to multiply the parameter with
     * @return an effect that boost speed.
     */
    Effect speedBoost(String name, int duration, double boost);

    /**
     * 
     * @param name the effect name
     * @param duration the duration in seconds
     * @param boost the value to multiply the parameter with
     * @return an effect that boost the sickness resistance.
     */
    Effect sicknessResistenceBoost(String name, int duration, double boost);

    /**
     * 
     * @param name the effect name
     * @param duration the duration in seconds
     * @param boost the value to multiply the parameter with
     * @return an effect that boost the reproduction range.
     */
    Effect reproductionRangeBoost(String name, int duration, double boost);

    /**
     * 
     * @param name the effect name
     * @param duration the duration in seconds
     * @param malus the value to multiply the parameter with
     * @return an effect that decrease the speed.
     */
    Effect speedDecrease(String name, int duration, double malus);

    /**
     * 
     * @param name the effect name
     * @param duration the duration in seconds
     * @param malus the value to multiply the parameter with
     * @return an effect that decrease the radius range.
     */
    Effect radiusRangeDecrease(String name, int duration, double malus);

    /**
     * 
     * @param name the effect name
     * @param duration the duration in seconds
     * @param malus the value to multiply the parameter with
     * @return an effect that decrease the fertility.
     */
    Effect fertilityDecrease(String name, int duration, double malus);
}
