package it.unibo.model.chapter;

import java.time.Clock;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import it.unibo.common.ChapterState;
import it.unibo.common.Circle;
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
import it.unibo.model.pickable.PickablePowerUp;
import it.unibo.model.pickable.PickablePowerUpFactory;
import it.unibo.model.pickable.PickablePowerUpFactoryImpl;
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
    private static final Duration TIMER_VALUE = Duration.ofSeconds(300);
    private final Map map;
    private final InputHandler inputHandler;
    private final HumanFactory humanFactory;
    private final PickablePowerUpFactory pickablePowerUpFactory = new PickablePowerUpFactoryImpl(null);
    // The first human is the player.
    // CopyOnWriteArrayList is a thread safe list, if it's too slow we'll change it.
    private final List<Human> humans = new CopyOnWriteArrayList<>();
    private final List<PickablePowerUp> pickablePowerUps = new CopyOnWriteArrayList<>();
    private final Random random = new Random();
    private final Timer timer;


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
        this.humanFactory = new HumanFactoryImpl(baseClock);
        this.timer = new TimerImpl(TIMER_VALUE, baseClock);
        spawnHumans(inputHandler);
    }

    int temporary = 0;
    @Override
    public void update() {
        for (final Human human : humans) {
            human.move();
        }
        
        // i use temporary just to try things and don't spawn powerup everytime 
        if(temporary == 0){
            spawnPickablePowerUp();
            temporary = temporary + 1;
        }

        solveCollisions();
        solvePickablePowerUpCollisions();
        
    }

    private void solvePickablePowerUpCollisions(){
        for(PickablePowerUp powerUp : pickablePowerUps){
            if(Math.abs(humans.get(0).getPosition().x() - powerUp.getPosition().x()) <= ScreenImpl.TILE_SIZE/2 &&  
                Math.abs(humans.get(0).getPosition().y()-powerUp.getPosition().y()) <=  ScreenImpl.TILE_SIZE/2){
                
                // still have to apply effects
                pickablePowerUps.clear();
            }
        }
    }

    private void spawnPickablePowerUp() {
        final List<PickablePowerUp> powerUps = new ArrayList<>();
        for (int i = 0; i < (map.getColoumns()*map.getRows())*0.010 ; i++){
            int randomPowerUp = random.nextInt(0,3);
            switch(randomPowerUp){
                case 0: powerUps.add(pickablePowerUpFactory.reproductionRangeBoost(Position.getRandomWalkablePosition(map), 30, 5));
                    break;
                case 1: powerUps.add(pickablePowerUpFactory.sicknessResistenceBoost(Position.getRandomWalkablePosition(map), 30, 5));
                    break;
                case 2: powerUps.add(pickablePowerUpFactory.speedBoost(Position.getRandomWalkablePosition(map), 30, 5));
                    break;
            }
        }

        this.pickablePowerUps.addAll(powerUps);
    }

    private boolean gameWon() {
        return this.humans.size() >= POPULATION_GOAL;
    }

    private boolean gameLost() {
        return timer.isOver();
    }

    private void spawnHumans(final InputHandler inputHandler) {
        final Position startingPosition = spawnPlayer(inputHandler);
        for (int i = 0; i < STARTING_FEMALES; i++) {
            this.humans.add(humanFactory.female(randomPosition(startingPosition), map));
        }
    }

    /**
     * @param inputHandler the input handler.
     * @return the position of the player.
     */
    private Position spawnPlayer(final InputHandler inputHandler) {
        final Position startingPosition = Position.getRandomWalkablePosition(map);
        this.humans.add(humanFactory.player(startingPosition, map, inputHandler));
        return startingPosition;
    }

    private void solveCollisions() {
        final List<Human> generated = new ArrayList<>();
        final QuadTree tree = createTree();
        humans.stream()
        .filter(h -> h.getType() == HumanType.FEMALE)
        .forEach(female -> {
            final Position femalePosition = female.getPosition();
            final Circle range = female.getStats().getReproductionAreaRadius();
            range.setRadius(range.getRadius() * 2);
            tree.query(range).stream()
            .map(p -> (Human) p.data())
            .filter(female::collide)
            .forEach(male -> generated.add(
                random.nextDouble() < MALE_SPAWNING_PROBABILITY
                    ? humanFactory.male(randomPosition(femalePosition), map)
                    : humanFactory.female(randomPosition(femalePosition), map)
            ));
        });
        this.humans.addAll(generated);
    }

    private QuadTree createTree() {
        final QuadTree tree = new QuadTreeImpl(
            new RectangleImpl(
                new Position(0, 0),
                map.getRows() * ScreenImpl.TILE_SIZE,
                map.getColoumns() * ScreenImpl.TILE_SIZE
            )
        );
        fillTree(tree);
        return tree;
    }

    private void fillTree(final QuadTree tree) {
        humans.forEach(h -> {
            if (h.getType() == HumanType.MALE || h.getType() == HumanType.PLAYER) {
                tree.insert(new Point(h.getPosition(), h));
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
    public Human getPlayer() {
        return humans.get(0);
    }

    private Position randomPosition(final Position reference) {
        final Position r = new Position(
            (int) Math.floor(
                reference.x()
                    + (random.nextBoolean() ? 1 : -1)
                        * ScreenImpl.TILE_SIZE * 2 * random.nextDouble()
            ),
            (int) Math.floor(
                reference.y()
                    + (random.nextBoolean() ? 1 : -1)
                        * ScreenImpl.TILE_SIZE * 2 * random.nextDouble()
            )
        );
        return walkablePosition(r) ? r : randomPosition(reference);
    }

    private boolean walkablePosition(final Position position) {
        return map.getTileFromPixel(position.x(), position.y()).isWalkable();
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
    }

    @Override
    public Duration getTimerValue() {
        return timer.getRemainingTime();
    }
}
