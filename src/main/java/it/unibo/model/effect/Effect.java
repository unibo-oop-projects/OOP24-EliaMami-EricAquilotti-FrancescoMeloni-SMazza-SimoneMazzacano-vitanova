package it.unibo.model.effect;

import java.time.Duration;

/**
 * Malus interface.
 */
public interface Effect {

    /**
     * 
     * @return the effect's name.
     */
    EffectType getType();

    /**
     * 
     * @return the effect's duration in seconds.
     */
    Duration getDuration();

    /**
     * 
     * @return the value to multiply the stats with.
     */
    double getMultiplyValue();

    /**
     * 
     * @return if the effect is active or not.
     */
    boolean isExpired();

    /**
     * Starts the effect.
     */
    void activate();

    /**
     * Resets the power up's duration timer.
     */
    void refresh();
}
