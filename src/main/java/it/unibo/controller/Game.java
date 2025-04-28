package it.unibo.controller;

import java.awt.Color;
import java.time.Duration;
import java.util.Collections;

import it.unibo.common.ChapterState;
import it.unibo.common.Position;
import it.unibo.model.chapter.Chapter;
import it.unibo.model.chapter.ChapterImpl;
import it.unibo.view.menu.GameOverMenu;
import it.unibo.view.menu.Menu;
import it.unibo.view.menu.StartMenu;
import it.unibo.view.screen.Screen;
import it.unibo.view.screen.ScreenImpl;

/**
 * Implementation of the Game engine.
 */
public final class Game implements Runnable {
    private static final int FPS = 60;
    private static final int NANO_IN_SEC = 1_000_000_000;
    private final Thread gameThread = new Thread(this);
    private final InputHandler inputHandler = new InputHandlerImpl();
    private final Screen screen = new ScreenImpl(inputHandler);
    private Chapter chapter = new ChapterImpl(inputHandler, 16, 16);
    private Menu menu = new StartMenu(inputHandler, this);
    private boolean isGameplayStarted;
    private boolean isGameplayPaused;
    /**
     * Starts the game engine.
     */
    public Game() {
        gameThread.start();
    }

    @Override
    public void run() {
        final double drawInterval = NANO_IN_SEC / FPS;
        final Duration fixedGameTimeInterval = Duration.ofNanos((long) drawInterval);
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
                update(fixedGameTimeInterval);
                draw();
                delta--;
                frameCount++;
            }

            // Measure FPS every second
            if (System.currentTimeMillis() - timer >= 1000) {
                final int textSize = 32;
                final Position textPosition = new Position(textSize, textSize);
                final String content = chapter.getPlayer().getPosition() + " FPS: " + frameCount 
                + " Population: " + chapter.getHumans().size() + " Goal: " + chapter.getPopulationGoal() 
                + " Time: " + chapter.getTimerValue().toMinutesPart() + ":" + chapter.getTimerValue().toSecondsPart();
                screen.loadText(content, textPosition, Color.RED, textSize);
                frameCount = 0;
                timer += 1000;
            }
        }
    }

    private void update(final Duration gameDelta) {
        if (chapter.getState() == ChapterState.PLAYER_WON) {
            this.exit();
        }

        if (!isGameplayPaused && chapter.getState() == ChapterState.PLAYER_LOST) {
            this.setMenu(new GameOverMenu(inputHandler, this));
        }

        if (isGameplayStarted && !isGameplayPaused) {
            chapter.update(gameDelta);
        }
        menu.update();
        final Position playerPosition = chapter.getPlayer().getPosition();
        screen.setOffset((int) playerPosition.x(), (int) playerPosition.y());
    }

    private void draw() {
        screen.loadMenu(menu.getText());
        screen.loadMap(chapter.getMap());
        if (isGameplayStarted) {
            screen.loadHumans(chapter.getHumans());
        }
    }

    /**
     * Starts the gameplay.
     */
    public void startGameplay() {
        this.isGameplayStarted = true;
    }

    /**
     * Set current menu.
     * @param menu
     */
    public void setMenu(final Menu menu) {
        this.menu = menu;
    }

    /**
     * Pauses the gameplay.
     */
    public void toggleGameplayState() {
        this.isGameplayPaused = !this.isGameplayPaused;
    }

    /**
     * Exits the game.
     */
    public void exit() {
        System.exit(0);
    }

    /**
     * Restarts the current chapter.
     */
    public void restartCurrentChapter() {
        chapter.restart();
    }

    /**
     * Sets the new chapter and clears the screen.
     */
    public void setNewChapter() {
        this.chapter = new ChapterImpl(inputHandler, 16, 16);
        this.isGameplayStarted = false;
        this.screen.loadHumans(Collections.emptyList());
    }
}
