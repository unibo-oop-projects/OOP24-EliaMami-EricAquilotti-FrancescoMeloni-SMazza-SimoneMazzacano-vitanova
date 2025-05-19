package it.unibo.model.chapter;

import java.time.Clock;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

import it.unibo.common.ChapterState;
import it.unibo.common.CooldownGate;
import it.unibo.common.Position;
import it.unibo.controller.InputHandler;
import it.unibo.model.human.Human;
import it.unibo.model.human.HumanFactory;
import it.unibo.model.human.HumanFactoryImpl;
import it.unibo.model.timer.Timer;
import it.unibo.model.timer.TimerImpl;
import it.unibo.model.chapter.collisions.CollisionSolver;
import it.unibo.model.chapter.map.Map;
import it.unibo.model.chapter.map.MapImpl;
import it.unibo.model.pickable.Pickable;
import it.unibo.model.pickable.PickableFactory;
import it.unibo.model.pickable.PickableFactoryImpl;
import it.unibo.view.screen.ScreenImpl;

/**
 * Implementation of a chapter that handles map and humans movement and
 * collisions.
 */
public final class ChapterImpl implements Chapter {
    private static final double MALE_SPAWNING_PROBABILITY = .9;
    private static final int STARTING_POPULATION_GOAL = 5;
    private static final Duration STARTING_TIMER_VALUE = Duration.ofSeconds(300);
    private CooldownGate spawnPowerupRate;
    private final Map map;
    private final InputHandler inputHandler;
    private final HumanFactory humanFactory;
    private final PickableFactory pickablePowerUpFactory;
    // The first human is the player.
    // CopyOnWriteArrayList is a thread safe list, if it's too slow we'll change it.
    private final List<Human> humans = new CopyOnWriteArrayList<>();
    private final List<Pickable> pickables = new CopyOnWriteArrayList<>();
    private final List<Pickable> activatedPickables = new CopyOnWriteArrayList<>();
    private final Timer timer;
    private final Clock clock;
    private static final int STARTING_ROWS = 16;
    private static final int STARTING_COLOUMNS = 16;
    private final int chapterNumber;

    /**
     * Sets up all the parameters.
     * @param chapterNumber the current chapter number.
     * @param inputHandler
     * @param baseClock the clock used for the factories and the timer.
     */
    public ChapterImpl(final int chapterNumber, final InputHandler inputHandler, final Clock baseClock) {
        this.chapterNumber = chapterNumber;
        this.map = new MapImpl(STARTING_ROWS * chapterNumber, STARTING_COLOUMNS * chapterNumber);
        this.inputHandler = inputHandler;
        this.clock = baseClock;
        this.humanFactory = new HumanFactoryImpl(baseClock);
        this.timer = new TimerImpl(STARTING_TIMER_VALUE, baseClock);
        this.pickablePowerUpFactory = new PickableFactoryImpl(baseClock);
        this.spawnPowerupRate = new CooldownGate(Duration.ofSeconds(3), baseClock); 
        spawnHumans(inputHandler);
    }

    @Override
    public int getChapterNumber() {
        return chapterNumber;
    }

    @Override
    public void update() {
        for (final Human human : humans) {
            human.move();
        }
        CollisionSolver.solveCollisions(humans, MALE_SPAWNING_PROBABILITY, map, humanFactory);
        if (spawnPowerupRate.tryActivate()) {
            spawnPickablePowerUp(); 
        }
        solvePickablePowerUpCollisions();
        resetExpiredEffects();
    }

    private void spawnPickablePowerUp() {
        final List<Pickable> spawningPickables = new ArrayList<>();
        spawningPickables.add(pickablePowerUpFactory.randomBoost(Position.getRandomWalkablePosition(map)));
        this.pickables.addAll(spawningPickables);
    }

    private void solvePickablePowerUpCollisions() {
        for (final Pickable pickable : pickables) {
            if (Math.abs(getPlayer().getPosition().x() - pickable.getPosition().x()) <= ScreenImpl.TILE_SIZE / 2 
                && Math.abs(getPlayer().getPosition().y() - pickable.getPosition().y()) <= ScreenImpl.TILE_SIZE / 2) {
                checkAndActivate(pickable);
                pickables.remove(pickable);
            }
        }
    }

    private void checkAndActivate(final Pickable pickable) {
        Optional<Pickable> tmp = Optional.empty();
        for (final Pickable activated : activatedPickables) {
            if (activated.getEffect().getType().equals(pickable.getEffect().getType())) {
                tmp = Optional.of(activated);
            }
        }
        if (tmp.isPresent()) {
            tmp.get().getEffect().refresh();
        } else {
            pickable.getEffect().activate();
            activatedPickables.add(pickable);
            getPlayer().getStats().applyEffect(pickable.getEffect());
        }
    }

    private void resetExpiredEffects() {
        for (final Pickable activated : activatedPickables) {
            if (activated.getEffect().isExpired()) {
                getPlayer().getStats().resetEffect(activated.getEffect().getType());
                activatedPickables.remove(activated);
            }
        }
    } 

    private boolean gameWon() {
        return this.humans.size() >= (STARTING_POPULATION_GOAL * getChapterNumber());
    }

    private boolean gameLost() {
        return timer.isOver();
    }

    private void spawnHumans(final InputHandler inputHandler) {
        spawnPlayer(inputHandler);
        for (int i = 0; i < getChapterNumber(); i++) {
            this.humans.add(humanFactory.female(Position.getRandomWalkablePosition(map), map));
        }
    }

    private void spawnPlayer(final InputHandler inputHandler) {
        final Position startingPosition = Position.getRandomWalkablePosition(map);
        this.humans.add(humanFactory.player(startingPosition, map, inputHandler));
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
    public List<Pickable> getPickablePowerUp() {
        return Collections.unmodifiableList(pickables);
    }

    @Override
    public Human getPlayer() {
        return humans.get(0);
    }

    @Override
    public int getPopulationGoal() {
        return STARTING_POPULATION_GOAL * getChapterNumber();
    }

    @Override
    public ChapterState getState() {
        if (gameWon()) {
            return ChapterState.PLAYER_WON;
        }
        if (gameLost()) {
            return ChapterState.PLAYER_LOST;
        }
        return ChapterState.IN_PROGRESS;
    }

    @Override
    public void restart() {
        this.humans.clear();
        this.pickables.clear();
        spawnHumans(this.inputHandler);
        timer.reset();
        this.spawnPowerupRate = new CooldownGate(Duration.ofSeconds(3), clock); 
    }

    @Override
    public Duration getTimerValue() {
        return timer.getRemainingTime();
    }
}
