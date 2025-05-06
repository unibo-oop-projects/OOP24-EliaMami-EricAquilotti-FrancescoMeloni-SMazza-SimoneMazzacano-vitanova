package it.unibo.model.pickable;

import it.unibo.common.Position;
import it.unibo.view.sprite.Sprite;

/**
 * Pickable power up interface.
 */
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
     * @return the relative sprite.
     */
    Sprite getSprite();

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
