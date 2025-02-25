package it.unibo.controller;

import it.unibo.common.Position;
import it.unibo.model.chapter.Chapter;
import it.unibo.model.chapter.ChapterImpl;
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
    private final Chapter chapter;

    /**
     * Sets up all the parameters.
     */
    public Game() {
        inputHandler = new InputHandlerImpl();
        chapter = new ChapterImpl(inputHandler);
        screen = new ScreenImpl(inputHandler);
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        final double drawInterval = NANO_IN_SEC / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        long timer = System.currentTimeMillis();
        int frameCount = 0; // Count frames per second

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                draw();
                delta--;
                frameCount++;
            }

            // Measure FPS every second
            if (System.currentTimeMillis() - timer >= 1000) {
                screen.loadText(chapter.getHumans().size() + " FPS: " + frameCount);
                frameCount = 0;
                timer += 1000;
            }
        }
    }

    private void update() {
        chapter.update();
        final Position playerPosition = chapter.getPlayer().getPosition();
        screen.setOffset((int) playerPosition.x(), (int) playerPosition.y());
    }

    private void draw() {
        screen.loadMap(chapter.getMap());
        screen.loadHumans(chapter.getHumans());
        screen.show();
    }
}
