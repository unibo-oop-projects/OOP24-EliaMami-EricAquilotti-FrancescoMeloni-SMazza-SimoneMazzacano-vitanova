package it.unibo.model.effect;

import java.time.Duration;

/**
 * Effect factory interface.
 */
public interface EffectFactory {

    /**
     * Method that return a new effect.
     * @param type the effect type
     * @param duration the duration
     * @param multiplyValue the value to multiply the parameter with
     * @return an effect that describe how to modify the chosen stat.
     */
    Effect createEffect(EffectType type, Duration duration, double multiplyValue);

    // /**
    //  * Method that return a new reproduction range effect.
    //  * @param duration the duration
    //  * @param multiplyValue the value to multiply the parameter with
    //  * @return an effect that describe how to modify the radius range.
    //  */
    // Effect reproductionRangeEffect(Duration duration, double multiplyValue);

    // /**
    //  * Method that return a new fertility effect.
    //  * @param duration the duration
    //  * @param multiplyValue the value to multiply the parameter with
    //  * @return an effect that describe how to modify the fertility.
    //  */
    // Effect fertilityEffect(Duration duration, double multiplyValue);

    // /**
    //  * Method that return a sickness resistence effect.
    //  * @param duration the duration
    //  * @param multiplyValue the value to multiply the parameter with
    //  * @return an effect that describe how to modify the sickness resistence.
    //  */
    // Effect sicknessResistenceEffect(Duration duration, double multiplyValue);
}
