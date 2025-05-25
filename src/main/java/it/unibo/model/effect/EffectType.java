package it.unibo.model.effect;

/**
 * Enum to easily get the types of effect.
 */
public enum EffectType {
    /**
     * The effect is a speed effect.
     */
    SPEED,
    /**
     * The effect is a reproduction range effect.
     * This is used to increase or reduce the radius of the reproduction area.
     */
    REPRODUCTION_RANGE,
    /**
     * The effect is a fertility effect.
     * This is used to increase or reduce the probability of having a female child.
     */
    FERTILITY,
    /**
     * The effect is a sickness resistance effect.
     * This is used to increase or reduce the probability of not getting sick after reproduction.
     */
    SICKNESS_RESISTENCE
}
