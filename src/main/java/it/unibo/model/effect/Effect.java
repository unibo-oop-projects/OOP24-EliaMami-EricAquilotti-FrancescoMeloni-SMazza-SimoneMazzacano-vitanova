package it.unibo.model.effect;

import java.time.Duration;

/**
 * Effect interface.
 */
public interface Effect {

    /**
     * Method that return the type of effect.
     * @return the effect's type.
     */
    EffectType getType();

    /**
     * Method that return the duration of effect.
     * @return the effect's duration in seconds.
     */
    Duration getDuration();

    /**
     * Method that return the multiply value of effect.
     * @return the value to multiply the stats with.
     */
    double getMultiplyValue();

    /**
     * Method that return the effect is expired or not.
     * @return if the effect is expired or not.
     */
    boolean isExpired();

    /**
     * Starts the effect's cooldown.
     */
    void activate();

    /**
     * Resets the effect's duration timer.
     */
    void refresh();
}
