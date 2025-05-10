package it.unibo.model.pickable;

import it.unibo.common.Position;
import it.unibo.model.effect.Effect;
import it.unibo.view.sprite.Sprite;

/**
 * Pickable power up interface.
 */
public interface Pickable {
    /**
     * 
     * @return the current position of the pickable power up.
     */
    Position getPosition();

    /**
     * 
     * @return the effect associated to the pickable.
     */
    Effect getEffect();

    /**
     * 
     * @return the relative sprite.
     */
    Sprite getSprite();
}
