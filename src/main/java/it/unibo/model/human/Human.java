package it.unibo.model.human;

import java.io.Serializable;

import it.unibo.common.Direction;
import it.unibo.common.Position;
import it.unibo.view.sprite.HumanType;
import it.unibo.view.sprite.Sprite;

/**
 * Models a human that can move inside the map.
 */
public interface Human extends Serializable {

    /**
     * Moves the human by changing the position according to its direction and
     * speed.
     */
    void move();

    /**
     * Method that returns the current human's position.
     * @return the current position of the human.
     */
    Position getPosition();

    /**
     * Method that returns the sprite of human.
     * @return the relative sprite.
     */
    Sprite getSprite();

    /**
     * Method that returns the current direction.
     * @return the current direction of the human.
     */
    Direction getDirection();

    /**
     * Method that returns the current human type.
     * @return the type of the human.
     */
    HumanType getType();

    /**
     * Method that returns the stats.
     * @return the human's stats.
     */
    HumanStats getStats();

    /**
     * Method that given a human check if the collision happened.
     * @param other the human to collide with.
     * @return true if the collision happened.
     */
    boolean collide(Human other);
}
