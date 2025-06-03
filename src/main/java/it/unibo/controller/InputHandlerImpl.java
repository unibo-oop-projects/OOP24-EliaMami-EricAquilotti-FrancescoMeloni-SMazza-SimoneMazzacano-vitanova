package it.unibo.controller;

import java.awt.event.KeyEvent;

import it.unibo.common.Direction;

/**
 * Implementation of the input handler.
 */
public final class InputHandlerImpl implements InputHandler {

    private final boolean[] keys = new boolean[KeyEvent.class.getFields().length];

    /**
     * Default constructor.
     */
    public InputHandlerImpl() { }

    @Override
    public Direction getDirection() {
        return new Direction(
            keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_W],
            keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D],
            keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S],
            keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_A]
        );
    }

    @Override
    public void keyPressed(final KeyEvent e) {
      keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(final KeyEvent e) {
      keys[e.getKeyCode()] = false;
    }

    @Override
    public void keyTyped(final KeyEvent e) {

    }

    @Override
    public boolean isKeyPressed(final int keyCode) {
      return keys[keyCode];
    }
}
