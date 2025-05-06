package it.unibo.model.malus;

/**
 * Malus interface.
 */
public interface Malus {

    /**
     * 
     * @return the effect's name.
     */
    String getName();

    /**
     * 
     * @return the effect's duration in seconds.
     */
    int getDuration();

    /**
     * 
     * @return if the effect is active or not.
     */
    boolean isActive();

    /**
     * Starts the effect.
     */
    void activate();
}
