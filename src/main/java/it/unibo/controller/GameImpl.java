package it.unibo.controller;

import java.io.File;
import java.io.IOException;
import java.time.Clock;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import it.unibo.common.ChapterState;
import it.unibo.common.PausableClock;
import it.unibo.common.Position;
import it.unibo.model.chapter.Chapter;
import it.unibo.model.chapter.ChapterImpl;
import it.unibo.model.chapter.PopulationCounter;
import it.unibo.model.human.stats.HumanStats;
import it.unibo.model.human.stats.HumanStatsImpl;
import it.unibo.model.savemanager.SaveManager;
import it.unibo.model.savemanager.SaveObject;
import it.unibo.model.skillpoint.SkillPoint;
import it.unibo.view.menu.ErrorMenu;
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
    private final File saveFile = new File("chapterInfo.txt");
    private SaveManager saveManager;
    private final SkillPoint skillPoints = new SkillPoint(3);

    /**
     * Starts the game engine.
     */
    public GameImpl() {
        try {
            final boolean isNewFile = saveFile.createNewFile();
            saveManager = new SaveManager();
            if (isNewFile) {
                chapter = new ChapterImpl(1, inputHandler, baseClock);
            } else {
                final SaveObject saved = (SaveObject) saveManager.readObj(saveFile);
                chapter = new ChapterImpl(saved.getChapterNumber(), inputHandler, baseClock, saved.getPlayerUpgrade());
            }
        } catch (IOException | ClassNotFoundException e) {
            chapter = new ChapterImpl(1, inputHandler, baseClock);
            this.setMenu(errorMenuCall("Lettura del file di gioco andata male"));
        } finally {
            gameThread.start();
        }
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

    private ErrorMenu errorMenuCall(final String subtitle) {
        return new ErrorMenu(inputHandler, this, subtitle);
    }

    /**
     * Starts the gameplay.
     */
    @Override
    public void startGameplay() {
        this.isGameplayStarted = true;
    }

    /**
     * Set current menu.
     * @param menu 
     */
    @Override
    public void setMenu(final Menu menu) {
        this.menu = menu;
    }

    /**
     * Pauses the gameplay.
     * @param paused true if the game is paused, false otherwise.
     */
    @Override
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
    @Override
    public void exit() {
        chapter.getPlayer().getStats().resetAllEffect();
        saveGame();
        System.exit(0);
    }

    /**
     * Restarts the current chapter.
     */
    @Override
    public void restartCurrentChapter() {
        chapter.restart();
    }

    /**
     * Sets the first chapter and clears the screen.
     */
    @Override
    public void setFirstChapter() {
        this.chapter = new ChapterImpl(1, inputHandler, baseClock);
        this.isGameplayStarted = false;
        clearScreen();
    }

    /**
     * Sets the new chapter and clears the screen.
     */
    @Override
    public void setNewChapter() {
        this.chapter = new ChapterImpl(chapter.getChapterNumber(), inputHandler, baseClock, getPlayerStats());
        this.isGameplayStarted = false;
        clearScreen();
    }

    /**
     * Clears the screen by removing everything, except of the map.
     */
    @Override
    public void clearScreen() {
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
    @Override
    public HumanStats getPlayerStats() {
        return chapter.getPlayer().getStats();
    }

    /**
     * Set the next chapter.
     */
    @Override
    public void nextChapter() {
        getPlayerStats().resetAllEffect();
        this.chapter = new ChapterImpl(chapter.getChapterNumber() + 1, inputHandler, baseClock, getPlayerStats());
        saveGame();
        this.isGameplayStarted = true;
        this.skillPoints = Optional.empty();
    }

    /**
     * Save the chapter number and player stats in a file.
     */
    @Override
    public void saveGame() {
        try {
            saveManager.saveObj(new SaveObject(
                chapter.getChapterNumber(), 
                List.of(
                    getPlayerStats().getActualSpeedUpgrade(), 
                    getPlayerStats().getActualSicknessResistenceUpgrade(),
                    getPlayerStats().getActualReproductionRangeUpgrade(),
                    getPlayerStats().getActualFertilityUpgrade()
                    )
                ), 
                saveFile);
        } catch (IOException e) {
            this.setMenu(errorMenuCall("Salvataggio del file di gioco andata male"));
        }
    }

    @Override
    public Chapter getChapter() {
        return this.chapter;
    }
}
