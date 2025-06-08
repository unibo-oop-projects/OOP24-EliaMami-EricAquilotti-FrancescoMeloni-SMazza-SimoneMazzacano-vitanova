package it.unibo.model.effect;

import java.time.Duration;

/**
 * Effect factory interface.
 */
public interface EffectFactory {

    /**
     * Method that return a new effect.
     * @param effectType the effect type
     * @param duration the duration
     * @param multiplyValue the value to multiply the parameter with
     * @return an effect that describe how to modify the chosen stat.
     */
    Effect createEffect(EffectType effectType, Duration duration, double multiplyValue);
}
