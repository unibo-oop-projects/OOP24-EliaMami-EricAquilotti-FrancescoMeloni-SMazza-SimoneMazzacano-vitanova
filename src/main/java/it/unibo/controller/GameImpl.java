package it.unibo.controller;

import java.awt.Color;
import java.time.Clock;
import java.util.Collections;
import java.util.Optional;

import it.unibo.common.ChapterState;
import it.unibo.common.PausableClock;
import it.unibo.common.Position;
import it.unibo.model.chapter.Chapter;
import it.unibo.model.chapter.ChapterImpl;
import it.unibo.model.chapter.PopulationCounter;
import it.unibo.model.human.HumanStats;
import it.unibo.view.menu.GameOverMenu;
import it.unibo.view.menu.Menu;
import it.unibo.view.menu.StartMenu;
import it.unibo.view.menu.WinAndUpgradeMenu;
import it.unibo.view.screen.Screen;
import it.unibo.view.screen.ScreenImpl;

/**
 * Implementation of the Game engine.
 */
public final class GameImpl implements Runnable, Game {
    private static final int FPS = 60;
    private static final int NANO_IN_SEC = 1_000_000_000;
    private final Thread gameThread = new Thread(this);
    private final InputHandler inputHandler = new InputHandlerImpl();
    private final Screen screen = new ScreenImpl(inputHandler);
    private final PausableClock baseClock = new PausableClock(Clock.systemUTC());
    private Chapter chapter;
    private Menu menu = new StartMenu(inputHandler, this);
    private boolean isGameplayStarted;
    private boolean isGameplayPaused;
    private Optional<Integer> skillPoints = Optional.empty();

    /**
     * Starts the game engine.
     */
    public GameImpl() {
        chapter = new ChapterImpl(1, inputHandler, baseClock);
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
                final int textSize = 32;
                final Position textPosition = new Position(textSize, textSize);
                // We should not show the timer when the chapter is not going.
                final String content = chapter.getPlayer().getPosition() + " FPS: " + frameCount 
                + " Population: " + chapter.getHumans().size() + " Goal: " + chapter.getPopulationGoal()
                + " Timer: " + chapter.getTimerValue().toSecondsPart();
                screen.loadText(content, textPosition, Color.RED, textSize);
                frameCount = 0;
                timer += 1000;
            }
        }
    }

    private void update() {
        if (!isGameplayPaused) {
            if (chapter.getState() == ChapterState.PLAYER_WON) {
                setSkillPoint(3);
                this.setMenu(new WinAndUpgradeMenu(inputHandler, this));
            } else if (chapter.getState() == ChapterState.PLAYER_LOST) {
                this.setMenu(new GameOverMenu(inputHandler, this));
            }
        }
        if (isGameplayStarted && !isGameplayPaused) {
            chapter.update();
        }
        menu.update();
        final Position playerPosition = chapter.getPlayer().getPosition();
        screen.setOffset((int) playerPosition.x(), (int) playerPosition.y());
    }

    private void draw() {
        screen.loadMenu(menu.getContent());
        screen.loadMap(chapter.getMap());
        if (isGameplayStarted) {
            screen.loadHumans(chapter.getHumans());
            screen.loadPickablePowerUp(chapter.getPickablePowerUp());
            screen.loadTimer(Optional.of(chapter.getTimerValue()));
            final int currentPopulation = chapter.getHumans().size();
            final int populationGoal = chapter.getPopulationGoal();
            final PopulationCounter populationCounter = new PopulationCounter(currentPopulation, populationGoal);
            screen.loadPopulationCounter(Optional.of(populationCounter));
        }
    }

    @Override
    public void startGameplay() {
        this.isGameplayStarted = true;
    }

    @Override
    public void setMenu(final Menu menu) {
        this.menu = menu;
    }

    @Override
    public void setGameplayState(final boolean paused) {
        if (paused) {
            baseClock.pause();
        } else {
            baseClock.unpause();
        }
        this.isGameplayPaused = paused;
    }

    @Override
    public void exit() {
        chapter.getPlayer().getStats().resetAllEffect();
        System.exit(0);
    }

    @Override
    public void restartCurrentChapter() {
        chapter.restart();
    }

    @Override
    public void setNewChapter() {
        this.chapter = new ChapterImpl(chapter.getChapterNumber(), inputHandler, baseClock);
        this.isGameplayStarted = false;
        clearScreen();
    }

    @Override
    public void clearScreen() {
        this.skillPoints = Optional.empty();
        this.screen.loadHumans(Collections.emptyList());
        this.screen.loadTimer(Optional.empty());
        this.screen.loadPickablePowerUp(Collections.emptyList());
        this.screen.loadPopulationCounter(Optional.empty());
    }

    @Override
    public HumanStats getPlayerStats() {
        return chapter.getPlayer().getStats();
    }

    @Override
    public void setSkillPoint(final int value) { 
        skillPoints = skillPoints.or(() -> Optional.of(value));
    }

    @Override
    public int getSkillPoint() {
        return skillPoints.get();
    }

    @Override
    public void updateSkillPoint() {
        skillPoints = Optional.of(skillPoints.get() > 0 ? (Integer) (skillPoints.get() - 1) : skillPoints.get());
    }

    @Override
    public void nextChapter() {
        getPlayerStats().resetAllEffect();
        this.chapter = new ChapterImpl(chapter.getChapterNumber() + 1, inputHandler, baseClock, Optional.of(getPlayerStats()));
        this.isGameplayStarted = true;
        this.skillPoints = Optional.empty();
    }

    @Override
    public Chapter getChapter() {
        return chapter;
    }
}
