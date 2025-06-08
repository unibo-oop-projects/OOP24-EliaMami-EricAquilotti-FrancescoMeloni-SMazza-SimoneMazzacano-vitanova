package it.unibo.model.pickable;

import java.time.Duration;

import it.unibo.common.Position;
import it.unibo.model.effect.EffectType;

/**
 * Pickable factory.
 */
public interface PickableFactory {

    /**
     * Method that returns a new pickable with a chosen effect associated.
     * @param spawnPosition the spawn position of the pickable
     * @param effectType the effect type to associate to pickable
     * @param duration the duration in seconds
     * @param value the value to multiply the stat with
     * @return a pickable associated with that type of effect.
     */
    Pickable createPickable(Position spawnPosition, EffectType effectType, Duration duration, double value);

    /**
     * Method that returns a new pickable with a chosen effect
     * associated with a fixed duration and a fixed multiplier.
     * @param effectType the effect type to associate to pickable
     * @param spawnPosition the spawn position of the pickable
     * @return a pickable associated with that type of effect.
     */
    Pickable createPickable(Position spawnPosition, EffectType effectType);

    /**
     * Method that returns a new pickable with a random effect
     * associated with a fixed duration and a fixed multiplier.
     * @param spawnPosition the spawn position of the pickable
     * @return a random pickable multiply.
     */
    Pickable randomPickable(Position spawnPosition);
}
