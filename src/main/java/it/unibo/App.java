package it.unibo;

import it.unibo.controller.Game;

/**
 * Main class to run the application.
 */
public final class App {

    /**
     * Private constructor to avoid the usage of this class.
     */
    private App() { }

    /**
     * Starts the controller i.e. the game engine.
     * @param args
     */
    public static void main(final String[] args) {
        new Game();
    }
}
