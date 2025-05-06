package it.unibo.model.pickable;

import it.unibo.common.Position;

public interface PickablePowerUp {
    /**
     * 
     * @return the current position of the pickable power up.
     */
    Position getPosition();

    /**
     * 
     * @return the power up's name.
     */
    String getName();

    /**
     * 
     * @return the power up's duration in seconds.
     */
    int getDuration();

    /**
     * 
     * @return if the power up is active or not.
     */
    boolean isActive();

    /**
     * Starts the power up's effect.
     */
    void activate();
}
