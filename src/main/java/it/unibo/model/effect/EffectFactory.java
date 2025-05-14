package it.unibo.model.effect;

import java.time.Duration;

/**
 * Malus factory interface.
 */
public interface EffectFactory {

    /**
     * 
     * @param duration the duration
     * @param multiplyValue the value to multiply the parameter with
     * @return an effect that describe how to modify the speed.
     */
    Effect speedEffect(Duration duration, double multiplyValue);

    /**
     * 
     * @param duration the duration
     * @param multiplyValue the value to multiply the parameter with
     * @return an effect that describe how to modify the radius range.
     */
    Effect reproductionRangeEffect(Duration duration, double multiplyValue);

    /**
     * 
     * @param duration the duration
     * @param multiplyValue the value to multiply the parameter with
     * @return an effect that describe how to modify the fertility.
     */
    Effect fertilityEffect(Duration duration, double multiplyValue);

    /**
     * 
     * @param duration the duration
     * @param multiplyValue the value to multiply the parameter with
     * @return an effect that describe how to modify the sickness resistence.
     */
    Effect sicknessResistenceEffect(Duration duration, double multiplyValue);
}
