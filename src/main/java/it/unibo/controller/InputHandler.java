package it.unibo.controller;

import java.awt.event.KeyListener;

import it.unibo.common.Direction;

/**
 * Models an InputHandler that listens for the keypressed by the user.
 */
public interface InputHandler extends KeyListener {
    /**
     * 
     * @return the current direction based on the key pressed by the user.
     */
    Direction getDirection();
}
