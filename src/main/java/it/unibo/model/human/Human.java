package it.unibo.model.human;

import it.unibo.common.Direction;
import it.unibo.common.Position;
import it.unibo.view.sprite.HumanType;
import it.unibo.view.sprite.Sprite;

/**
 * Models a human that can move inside the map.
 */
public interface Human {

    /**
     * 
     * Moves the human by changing the position according to its direction and
     * speed.
     */
    void move();

    /**
     * 
     * @return the current position of the human.
     */
    Position getPosition();

    /**
     * 
     * @return the relative sprite.
     */
    Sprite getSprite();

    /**
     * 
     * @return the current direction of the human.
     */
    Direction getDirection();

    /**
     * 
     * @return the type of the human.
     */
    HumanType getType();

    /**
     * 
     * @return the human's stats.
     */
    Stats getStats();

    /**
     * 
     * @param other the human to collide with.
     * @return true if the collision happened.
     */
    boolean collide(Human other);
}
