package it.unibo.model.human;

import it.unibo.common.Position;

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
     * @return the current position of the human
     */
    Position getPosition();
}
