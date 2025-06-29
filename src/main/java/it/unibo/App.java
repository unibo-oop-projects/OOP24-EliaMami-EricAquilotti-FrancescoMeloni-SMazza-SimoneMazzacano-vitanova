package it.unibo;

import it.unibo.controller.GameImpl;

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
     * @param args args that are not used.
     */
    public static void main(final String[] args) {
        new GameImpl();
    }
}
