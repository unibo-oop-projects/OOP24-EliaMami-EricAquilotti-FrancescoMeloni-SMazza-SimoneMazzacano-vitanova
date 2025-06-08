package it.unibo.model.chapter;

import java.time.Clock;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

import it.unibo.common.ChapterState;
import it.unibo.common.Position;
import it.unibo.controller.InputHandler;
import it.unibo.model.human.Human;
import it.unibo.model.human.HumanFactory;
import it.unibo.model.human.HumanFactoryImpl;
import it.unibo.model.human.sickness.SicknessManager;
import it.unibo.model.human.sickness.SicknessManagerImpl;
import it.unibo.model.human.stats.HumanStats;
import it.unibo.model.timer.Timer;
import it.unibo.model.timer.TimerImpl;
import it.unibo.model.chapter.collisions.CollisionSolver;
import it.unibo.model.chapter.map.Map;
import it.unibo.model.chapter.map.MapImpl;
import it.unibo.model.effect.EffectFactoryImpl;
import it.unibo.model.pickable.Pickable;
import it.unibo.model.pickable.manager.PickableManager;
import it.unibo.model.pickable.manager.PickableManagerImpl;
import it.unibo.view.sprite.HumanType;

/**
 * Implementation of a chapter that handles map, humans movement and
 * collisions.
 */
public final class ChapterImpl implements Chapter {
    private static final double MALE_SPAWNING_PROBABILITY = .9;
    private static final int STARTING_POPULATION_GOAL = 5;
    private static final Duration STARTING_TIMER_VALUE = Duration.ofSeconds(180);
    private final Map map;
    private final InputHandler inputHandler;
    private final HumanFactory humanFactory;
    private final PickableManager pickableManager;
    private final SicknessManager sicknessManager;
    // The first human is the player.
    // CopyOnWriteArrayList is a thread safe list, if it's too slow we'll change it.
    private final List<Human> humans = new CopyOnWriteArrayList<>();
    private final Timer timer;
    private static final int STARTING_ROWS = 32;
    private static final int STARTING_COLOUMNS = 32;
    private final int chapterNumber;

    /**
     * Sets up all the parameters.
     * @param chapterNumber the current chapter number.
     * @param inputHandler the input handler.
     * @param baseClock the clock used for the factories and the timer.
     */
    public ChapterImpl(final int chapterNumber, final InputHandler inputHandler, final Clock baseClock) {
        this(chapterNumber, inputHandler, baseClock, Optional.empty(), List.of());
    }

    /**
     * Sets up all the parameters.
     * @param chapterNumber the current chapter number.
     * @param inputHandler the input handler.
     * @param baseClock the clock used for the factories and the timer.
     * @param playerStats the player stats.
     */
    public ChapterImpl(final int chapterNumber, final InputHandler inputHandler, 
                        final Clock baseClock, final HumanStats playerStats) {
        this(chapterNumber, inputHandler, baseClock, Optional.of(playerStats), List.of());
    }

    /**
     * Sets up all the parameters.
     * @param chapterNumber the current chapter number.
     * @param inputHandler the input handler.
     * @param baseClock the clock used for the factories and the timer.
     * @param playerUpgrade the upgrade taken by the player.
     */
    public ChapterImpl(final int chapterNumber, final InputHandler inputHandler, 
                        final Clock baseClock, final List<Integer> playerUpgrade) {
        this(chapterNumber, inputHandler, baseClock, Optional.empty(), playerUpgrade);
    }

    private ChapterImpl(final int chapterNumber, final InputHandler inputHandler,
                        final Clock baseClock, final Optional<HumanStats> playerStats, final List<Integer> playerUpgrade) {
        this.chapterNumber = chapterNumber;
        this.map = new MapImpl(STARTING_ROWS + chapterNumber, STARTING_COLOUMNS + chapterNumber);
        this.inputHandler = inputHandler;
        this.humanFactory = new HumanFactoryImpl(baseClock);
        this.timer = new TimerImpl(STARTING_TIMER_VALUE, baseClock);
        this.sicknessManager = new SicknessManagerImpl(new EffectFactoryImpl(baseClock), getPopulationGoal());
        spawnHumans(inputHandler, playerStats, playerUpgrade);
        this.pickableManager = new PickableManagerImpl(getPlayer(), baseClock, map);
    }

    @Override
    public int getChapterNumber() {
        return chapterNumber;
    }

    @Override
    public void update() {
        for (final Human human : humans) {
            human.move();
            sicknessManager.checkStatus(human);
        }
        CollisionSolver.solveCollisions(humans, (h) -> {
            return h.getType().equals(HumanType.PLAYER) 
            ? 1 - h.getStats().getFertility() 
            : MALE_SPAWNING_PROBABILITY; 
        }, map, humanFactory, sicknessManager);
        pickableManager.spawnPickable();
        pickableManager.solvePickableCollisions();
        pickableManager.resetExpiredEffects();
    }

    private boolean gameWon() {
        return this.humans.size() >= getPopulationGoal();
    }

    private boolean gameLost() {
        return timer.isOver();
    }

    private void spawnHumans(final InputHandler inputHandler, final Optional<HumanStats> playerStats, 
                                final List<Integer> playerUpgrade) {
        spawnPlayer(inputHandler, playerStats, playerUpgrade);
        for (int i = 0; i < getChapterNumber(); i++) {
            this.humans.add(humanFactory.female(Position.getRandomWalkablePosition(map), map));
        }
    }

    private void spawnPlayer(final InputHandler inputHandler, final Optional<HumanStats> playerStats, 
                                final List<Integer> playerUpgrade) {
        final Position startingPosition = Position.getRandomCentralWalkablePosition(map);
        if (playerStats.isEmpty()) {
            if (playerUpgrade.isEmpty()) {
                this.humans.add(humanFactory.player(startingPosition, map, inputHandler));
            } else {
                this.humans.add(humanFactory.player(startingPosition, map, inputHandler, playerUpgrade));
            }
        } else {
            this.humans.add(humanFactory.player(startingPosition, map, inputHandler, playerStats.get()));
        }
    }

    @Override
    public Map getMap() {
        return map;
    }

    @Override
    public List<Human> getHumans() {
        return Collections.unmodifiableList(humans);
    }

    @Override
    public List<Pickable> getPickables() {
        return Collections.unmodifiableList(pickableManager.getPickables());
    }

    @Override
    public Human getPlayer() {
        return humans.getFirst();
    }

    @Override
    public int getPopulationGoal() {
        return STARTING_POPULATION_GOAL * getChapterNumber();
    }

    @Override
    public ChapterState getState() {
        if (gameWon()) {
            getPlayer().getStats().resetAllEffect();
            return ChapterState.PLAYER_WON;
        }
        if (gameLost()) {
            getPlayer().getStats().resetAllEffect();
            return ChapterState.PLAYER_LOST;
        }
        return ChapterState.IN_PROGRESS;
    }

    @Override
    public void restart() {
        getPlayer().getStats().resetAllEffect();
        final HumanStats playerStats = getPlayer().getStats();
        this.humans.clear();
        pickableManager.resetPickables();
        pickableManager.resetActivatedPickables();
        spawnHumans(this.inputHandler, Optional.of(playerStats), List.of());
        timer.reset();
    }

    @Override
    public Duration getTimerValue() {
        return timer.getRemainingTime();
    }
}
