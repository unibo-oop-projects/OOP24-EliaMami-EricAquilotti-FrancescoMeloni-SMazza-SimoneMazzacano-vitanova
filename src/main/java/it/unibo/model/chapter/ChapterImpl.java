package it.unibo.model.chapter;

import java.time.Clock;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import it.unibo.common.ChapterState;
import it.unibo.common.Circle;
import it.unibo.common.CooldownGate;
import it.unibo.common.Position;
import it.unibo.common.RectangleImpl;
import it.unibo.controller.InputHandler;
import it.unibo.model.human.Human;
import it.unibo.model.human.HumanFactory;
import it.unibo.model.human.HumanFactoryImpl;
import it.unibo.model.timer.Timer;
import it.unibo.model.timer.TimerImpl;
import it.unibo.model.chapter.map.Map;
import it.unibo.model.chapter.map.MapImpl;
import it.unibo.model.chapter.quadtree.Point;
import it.unibo.model.chapter.quadtree.QuadTree;
import it.unibo.model.chapter.quadtree.QuadTreeImpl;
import it.unibo.model.pickable.Pickable;
import it.unibo.model.pickable.PickableFactory;
import it.unibo.model.pickable.PickableFactoryImpl;
import it.unibo.view.screen.ScreenImpl;
import it.unibo.view.sprite.HumanType;

/**
 * Implementation of a chapter that handles map and humans movement and
 * collisions.
 */
public final class ChapterImpl implements Chapter {
    private static final int STARTING_FEMALES = 1;
    private static final double MALE_SPAWNING_PROBABILITY = .9;
    private static final int POPULATION_GOAL = 100;
    private static final double MULTIPLY_VALUE = 1.25;
    private static final Duration DURATION_EFFECT_VALUE = Duration.ofSeconds(5);
    private static final Duration TIMER_VALUE = Duration.ofSeconds(300);
    private CooldownGate spawnPowerupRate;
    private final Map map;
    private final InputHandler inputHandler;
    private final HumanFactory humanFactory;
    private final PickableFactory pickablePowerUpFactory;
    // The first human is the player.
    // CopyOnWriteArrayList is a thread safe list, if it's too slow we'll change it.
    private final List<Human> humans = new CopyOnWriteArrayList<>();
    private final List<Pickable> pickablePowerUps = new CopyOnWriteArrayList<>();
    private final List<Pickable> activatedPowerUps = new CopyOnWriteArrayList<>();
    private final Random random = new Random();
    private final Timer timer;
    private final Clock clock;

    /**
     * Sets up all the parameters.
     * @param inputHandler
     * @param rows the number of rows of the map.
     * @param coloumns the number of coloumns of the map.
     * @param baseClock the clock used for the factories and the timer.
     */
    public ChapterImpl(final InputHandler inputHandler, final int rows, final int coloumns, final Clock baseClock) {
        this.map = new MapImpl(rows, coloumns);
        this.inputHandler = inputHandler;
        this.clock = baseClock;
        this.humanFactory = new HumanFactoryImpl(baseClock);
        this.timer = new TimerImpl(TIMER_VALUE, baseClock);
        this.pickablePowerUpFactory = new PickableFactoryImpl(baseClock);
        this.spawnPowerupRate = new CooldownGate(Duration.ofSeconds(3), baseClock); 
        spawnHumans(inputHandler);
    }

    @Override
    public void update() {
        for (final Human human : humans) {
            human.move();
        }
        solveCollisions();
        if (spawnPowerupRate.tryActivate()) {
            spawnPickablePowerUp(); 
        }
        solvePickablePowerUpCollisions();
        checkPlayerStatusEffect();
    }

    private void spawnPickablePowerUp() {
        final List<Pickable> powerUps = new ArrayList<>();

        final int randomPowerUp = random.nextInt(0, 3);
        switch (randomPowerUp) {
            case 0: 
                powerUps.add(pickablePowerUpFactory.reproductionRangeBoost(
                        Position.getRandomWalkablePosition(map), DURATION_EFFECT_VALUE, MULTIPLY_VALUE));
                break;
            case 1: 
                powerUps.add(pickablePowerUpFactory.sicknessResistenceBoost(
                        Position.getRandomWalkablePosition(map), DURATION_EFFECT_VALUE, MULTIPLY_VALUE));
                break;
            case 2: 
                powerUps.add(pickablePowerUpFactory.speedBoost(
                        Position.getRandomWalkablePosition(map), DURATION_EFFECT_VALUE, MULTIPLY_VALUE));
                break;
            default:
                break;
        }

        this.pickablePowerUps.addAll(powerUps);
    }

    private void solvePickablePowerUpCollisions() {
        for (final Pickable powerUp : pickablePowerUps) {
            if (Math.abs(humans.get(0).getPosition().x() - powerUp.getPosition().x()) <= ScreenImpl.TILE_SIZE / 2 
                && Math.abs(humans.get(0).getPosition().y() - powerUp.getPosition().y()) <= ScreenImpl.TILE_SIZE / 2) {
                checkAndActivate(powerUp);
                pickablePowerUps.remove(powerUp);
            }
        }
    }

    private void checkAndActivate(final Pickable powerUp) {
        Optional<Pickable> tmp = Optional.empty();
        for (final Pickable activatedPowerUp : activatedPowerUps) {
            if (activatedPowerUp.getEffect().getType().equals(powerUp.getEffect().getType())) {
                tmp = Optional.of(activatedPowerUp);
            }
        }
        if (tmp.isPresent()) {
            tmp.get().getEffect().refresh();
        } else {
            powerUp.getEffect().activate();
            activatedPowerUps.add(powerUp);
            switch (powerUp.getEffect().getType()) {
                case SPEED:
                    humans.get(0).getStats().applySpeedModifier(powerUp.getEffect().getMultiplyValue());
                    break;
                case SICKNESS_RESISTENCE:
                    humans.get(0).getStats().applySicknessResistenceModifier(powerUp.getEffect().getMultiplyValue());
                    break;
                case REPRODUCTION_RANGE:
                    humans.get(0).getStats().applyReproductionRangeModifier(powerUp.getEffect().getMultiplyValue());
                    break;
                default:
                    break;
            }
        }
    }

    private void checkPlayerStatusEffect() {
        for (final Pickable powerUp : activatedPowerUps) {
            if (powerUp.getEffect().isExpired()) {
                switch (powerUp.getEffect().getType()) {
                    case SPEED:
                        humans.get(0).getStats().resetToBaseSpeed();
                        break;
                    case SICKNESS_RESISTENCE:
                        humans.get(0).getStats().resetToBaseSicknessResistence();
                        break;
                    case REPRODUCTION_RANGE:
                        humans.get(0).getStats().resetToBaseReproductionRange();
                        break;
                    default:
                        break;
                }
                activatedPowerUps.remove(powerUp);
            }
        }
    } 

    // private void checkIfGetSick(){

    // }

    private boolean gameWon() {
        return this.humans.size() >= POPULATION_GOAL;
    }

    private boolean gameLost() {
        return timer.isOver();
    }

    private void spawnHumans(final InputHandler inputHandler) {
        spawnPlayer(inputHandler);
        for (int i = 0; i < STARTING_FEMALES; i++) {
            this.humans.add(humanFactory.female(Position.getRandomWalkablePosition(map), map));
        }
    }

    private void spawnPlayer(final InputHandler inputHandler) {
        final Position startingPosition = Position.getRandomWalkablePosition(map);
        this.humans.add(humanFactory.player(startingPosition, map, inputHandler));
    }

    private void solveCollisions() {
        final List<Human> generated = new ArrayList<>();
        final QuadTree<Human> tree = createTree();
        humans.stream()
        .filter(h -> h.getType() == HumanType.FEMALE)
        .forEach(female -> {
            final Position femalePosition = female.getPosition();
            final Circle range = female.getStats().getReproductionAreaRadius();
            range.setRadius(range.getRadius() * 2);
            tree.query(range).stream()
            .map(Point::data)
            .filter(female::collide)
            .forEach(male -> generated.add(
                random.nextDouble() < MALE_SPAWNING_PROBABILITY
                    ? humanFactory.male(Position.getRandomWalkableReferencePosition(femalePosition, map), map)
                    : humanFactory.female(Position.getRandomWalkableReferencePosition(femalePosition, map), map)
            ));
        });
        this.humans.addAll(generated);
    }

    private QuadTree<Human> createTree() {
        final QuadTree<Human> tree = new QuadTreeImpl<>(
            new RectangleImpl(
                new Position(0, 0),
                map.getRows() * ScreenImpl.TILE_SIZE,
                map.getColoumns() * ScreenImpl.TILE_SIZE
            )
        );
        fillTree(tree);
        return tree;
    }

    private void fillTree(final QuadTree<Human> tree) {
        humans.forEach(h -> {
            if (h.getType() == HumanType.MALE || h.getType() == HumanType.PLAYER) {
                tree.insert(new Point<>(h.getStats().getReproductionAreaRadius().getCenter(), h));
            }
        });
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
        return Collections.unmodifiableList(pickablePowerUps);
    }

    @Override
    public Human getPlayer() {
        return humans.get(0);
    }

    @Override
    public int getPopulationGoal() {
        return POPULATION_GOAL;
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
        this.pickablePowerUps.clear();
        spawnHumans(this.inputHandler);
        timer.reset();
        this.spawnPowerupRate = new CooldownGate(Duration.ofSeconds(3), clock); 
    }

    @Override
    public Duration getTimerValue() {
        return timer.getRemainingTime();
    }
}
