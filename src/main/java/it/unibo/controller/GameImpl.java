package it.unibo.controller;

import java.time.Clock;
import java.util.Collections;
import java.util.Optional;

import it.unibo.common.ChapterState;
import it.unibo.common.PausableClock;
import it.unibo.common.Position;
import it.unibo.model.chapter.Chapter;
import it.unibo.model.chapter.ChapterImpl;
import it.unibo.model.chapter.PopulationCounter;
import it.unibo.model.human.stats.HumanStats;
import it.unibo.model.skillpoint.SkillPoint;
import it.unibo.view.menu.GameOverMenu;
import it.unibo.view.menu.Menu;
import it.unibo.view.menu.StartMenu;
import it.unibo.view.menu.WinAndUpgradeMenu;
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
    private final PausableClock baseClock = new PausableClock(Clock.systemUTC());
    private Chapter chapter;
    private Menu menu = new StartMenu(inputHandler, this);
    private boolean isGameplayStarted;
    private boolean isGameplayPaused;
    private final SkillPoint skillPoints = new SkillPoint(3);

    /**
     * Starts the game engine.
     */
    public Game() {
        chapter = new ChapterImpl(1, inputHandler, baseClock);
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
        if (!isGameplayPaused) {
            if (chapter.getState() == ChapterState.PLAYER_WON) {
                skillPoints.resetToBaseValue();
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

    /**
     * Starts the gameplay.
     */
    public void startGameplay() {
        this.isGameplayStarted = true;
        this.chapter = new ChapterImpl(1, inputHandler, baseClock);
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
     * @param paused true if the game is paused, false otherwise.
     */
    public void setGameplayState(final boolean paused) {
        if (paused) {
            baseClock.pause();
        } else {
            baseClock.unpause();
        }
        this.isGameplayPaused = paused;
    }

    /**
     * Exits the game.
     */
    public void exit() {
        chapter.getPlayer().getStats().resetAllEffect();
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
        getPlayerStats().resetAllEffect();
        this.chapter = new ChapterImpl(chapter.getChapterNumber(), inputHandler, baseClock);
        this.isGameplayStarted = false;
        this.skillPoints.reset();
        this.screen.loadHumans(Collections.emptyList());
        this.screen.loadTimer(Optional.empty());
        this.screen.loadPickablePowerUp(Collections.emptyList());
        this.screen.loadPopulationCounter(Optional.empty());
    }

    /**
     * This method gets player from all humans and return his stats.
     * @return player's stats.
     */
    public HumanStats getPlayerStats() {
        return chapter.getPlayer().getStats();
    }

    /**
     * This method returns skillPoints.
     * @return skillPoints.
     */
    public SkillPoint getSkillPoint() {
        return skillPoints;
    }

    /**
     * Start the next chapter.
     */
    public void startNextChapter() {
        setNextChapter();
        this.isGameplayStarted = true;
    }

    /**
     * Set the next chapter.
     */
    public void setNextChapter() {
        this.skillPoints.reset();
        this.screen.loadHumans(Collections.emptyList());
        this.screen.loadTimer(Optional.empty());
        this.screen.loadPickablePowerUp(Collections.emptyList());
        this.screen.loadPopulationCounter(Optional.empty());
        this.chapter = new ChapterImpl(chapter.getChapterNumber() + 1, inputHandler, baseClock, Optional.of(getPlayerStats()));
        this.isGameplayStarted = false;
    }

    /**
     * This method returns the current chapter.
     * @return the current chapter.
     */
    public Chapter getChapter() {
        return chapter;
    }
}
