package it.unibo.controller;

import it.unibo.model.chapter.Map;
import it.unibo.model.chapter.MapImpl;
import it.unibo.model.human.Player;
import it.unibo.model.human.PlayerImpl;
import it.unibo.view.screen.Screen;
import it.unibo.view.screen.ScreenImpl;

/**
 * Implementation of the Game engine.
 */
public final class Game implements Runnable {
    private static final int FPS = 60;
    private static final int NANO_IN_SEC = 1_000_000_000;

    private final Thread gameThread;
    private final InputHandler inputHandler;
    private final Screen screen;
    private final Player player;
    private final Map map;

    /**
     * Sets up all the parameters.
     */
    public Game() {
        inputHandler = new InputHandlerImpl();
        final int centerX = (MapImpl.MAP_ROW - 1) * ScreenImpl.TILE_SIZE / 2;
        final int centerY = (MapImpl.MAP_COL - 1) * ScreenImpl.TILE_SIZE / 2;
        player = new PlayerImpl(centerX, centerY);
        screen = new ScreenImpl(inputHandler);
        map = new MapImpl();
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        final double drawInterval = NANO_IN_SEC / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                draw();
                delta--;
            }
        }
    }

    private void update() {
        player.setDirection(inputHandler.getDirection());
        player.move();
        screen.setOffset(player.getPosition().x(), player.getPosition().y());
    }

    private void draw() {
        screen.renderMap(map);
        screen.renderHuman(player);
    }
}
