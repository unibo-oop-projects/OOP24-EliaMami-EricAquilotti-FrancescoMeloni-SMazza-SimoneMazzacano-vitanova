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
import it.unibo.model.human.stats.StatType;
import it.unibo.model.savemanager.SaveManager;
import it.unibo.model.savemanager.SaveManagerImpl;
import it.unibo.model.saveobject.SaveObject;
import it.unibo.model.saveobject.SaveObjectImpl;
import it.unibo.model.skillpoint.SkillPoint;
import it.unibo.model.skillpoint.SkillPointImpl;
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
    private final SkillPoint skillPoints = new SkillPointImpl(3);
    private HumanStats playerStats;

    /**
     * Starts the game engine.
     */
    public GameImpl() {
        try {
            final boolean isNewFile = saveFile.createNewFile();
            saveManager = new SaveManagerImpl();
            if (isNewFile) {
                chapter = new ChapterImpl(1, inputHandler, baseClock);
                playerStats = chapter.getPlayer().getStats();
            } else {
                final SaveObject saved = (SaveObject) saveManager.readObj(saveFile);
                chapter = new ChapterImpl(saved.getChapterNumber(), inputHandler, baseClock, saved.getPlayerUpgrade());
                playerStats = chapter.getPlayer().getStats();
            }
        } catch (IOException | ClassNotFoundException e) {
            chapter = new ChapterImpl(1, inputHandler, baseClock);
            playerStats = chapter.getPlayer().getStats();
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
            screen.loadPickable(chapter.getPickables());
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
        playerStats.resetAllEffect();
        saveGame();
        System.exit(0);
    }

    @Override
    public void restartCurrentChapter() {
        chapter.restart();
    }

    @Override
    public void setFirstChapter() {
        this.chapter = new ChapterImpl(1, inputHandler, baseClock);
        playerStats = chapter.getPlayer().getStats();
        this.isGameplayStarted = false;
        clearScreen();
    }

    @Override
    public void setNewChapter() {
        playerStats.resetAllEffect();
        this.chapter = new ChapterImpl(chapter.getChapterNumber(), inputHandler, baseClock, playerStats);
        this.isGameplayStarted = false;
        clearScreen();
    }

    @Override
    public void clearScreen() {
        this.skillPoints.reset();
        this.screen.loadHumans(Collections.emptyList());
        this.screen.loadTimer(Optional.empty());
        this.screen.loadPickable(Collections.emptyList());
        this.screen.loadPopulationCounter(Optional.empty());
    }

    @Override
    public void setNextChapter() {
        playerStats.resetAllEffect();
        clearScreen();
        this.chapter = new ChapterImpl(chapter.getChapterNumber() + 1, inputHandler, baseClock, playerStats);
        saveGame();
        this.isGameplayStarted = false;
        this.skillPoints.reset();
    }

    @Override
    public void saveGame() {
        try {
            saveManager.saveObj(new SaveObjectImpl(
                chapter.getChapterNumber(), 
                List.of(
                    playerStats.getSpeedUpgrade(), 
                    playerStats.getSicknessResistenceUpgrade(),
                    playerStats.getReproductionRangeUpgrade(),
                    playerStats.getFertilityUpgrade()
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

    @Override
    public SkillPoint getSkillPoint() {
        return skillPoints;
    }

    @Override
    public void checkAndIncrease(final StatType stat) {
        if (skillPoints.getValue() > 0) {
            playerStats.increaseStat(stat);
        }
        skillPoints.decreaseValue();
    }

    @Override
    public HumanStats getPlayerStats() {
        return new HumanStatsImpl(this.playerStats);
    }
}
