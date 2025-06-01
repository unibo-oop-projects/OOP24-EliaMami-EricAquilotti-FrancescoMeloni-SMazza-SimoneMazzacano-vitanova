package it.unibo.model.pickable;

import it.unibo.common.Position;
import it.unibo.model.effect.Effect;
import it.unibo.view.sprite.Sprite;

/**
 * Pickable interface.
 */
public interface Pickable {
    /**
     * Method that return the position of the pickable.
     * @return the current position of the pickable.
     */
    Position getPosition();

    /**
     * Method that return the effect associated to pickable.
     * @return the effect associated to the pickable.
     */
    Effect getEffect();

    /**
     * Method that return the pickable's sprite.
     * @return the relative sprite.
     */
    Sprite getSprite();
}
