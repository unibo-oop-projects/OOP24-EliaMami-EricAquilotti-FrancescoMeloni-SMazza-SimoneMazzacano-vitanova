package it.unibo.model.effect;

import java.time.Duration;

/**
 * Malus factory interface.
 */
public interface EffectFactory {

    /**
     * 
     * @param duration the duration in seconds
     * @param multiplyValue the value to multiply the parameter with
     * @return an effect that decrease the speed.
     */
    Effect speedDecrease(Duration duration, double multiplyValue);

    /**
     * 
     * @param duration the duration in seconds
     * @param multiplyValue the value to multiply the parameter with
     * @return an effect that decrease the radius range.
     */
    Effect reproductionRangeDecrease(Duration duration, double multiplyValue);

    /**
     * 
     * @param duration the duration in seconds
     * @param multiplyValue the value to multiply the parameter with
     * @return an effect that decrease the fertility.
     */
    Effect fertilityDecrease(Duration duration, double multiplyValue);
}
