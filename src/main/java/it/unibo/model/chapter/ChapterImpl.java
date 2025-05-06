package it.unibo.model.chapter;

import java.time.Clock;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import it.unibo.common.Circle;
import it.unibo.common.Position;
import it.unibo.common.RectangleImpl;
import it.unibo.controller.InputHandler;
import it.unibo.model.human.Human;
import it.unibo.model.human.HumanFactory;
import it.unibo.model.human.HumanFactoryImpl;
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
    private static final int STARTING_FEMALES = 5;
    private static final double MALE_SPAWNING_PROBABILITY = .9;
    private final Map map;
    private final HumanFactory humanFactory = new HumanFactoryImpl();
    private final PickablePowerUpFactory pickablePowerUpFactory = new PickablePowerUpFactoryImpl(null);
    // The first human is the player.
    // CopyOnWriteArrayList is a thread safe list, if it's too slow we'll change it.
    private final List<Human> humans = new CopyOnWriteArrayList<>();
    private final Random random = new Random();


    /**
     * Sets up all the parameters.
     * @param inputHandler
     * @param rows the number of rows of the map.
     * @param coloumns the number of coloumns of the map.
     */
    public ChapterImpl(final InputHandler inputHandler, final int rows, final int coloumns) {
        map = new MapImpl(rows, coloumns);
        final Position startingPosition = Position.getRandomWalkablePosition(map);
        this.humans.add(humanFactory.player(startingPosition, map, inputHandler));
        for (int i = 0; i < STARTING_FEMALES; i++) {
            this.humans.add(humanFactory.female(randomPosition(startingPosition), map));
        }
    }

    @Override
    public void update() {
        for (final Human human : humans) {
            human.move();
        }
        //spawnEffect
        solveCollisions();
        //solveEffectCollisions
        
    }

    private void spawnPickablePowerUp() {
        final List<PickablePowerUp> powerUps = new ArrayList<>();
        for (int i = 0; i < 200; i++){
            int randomPowerUp = random.nextInt(3);
            switch(randomPowerUp){
                case 0: powerUps.add(pickablePowerUpFactory.reproductionRangeBoost(null, "Reproduction Boost", randomPowerUp, randomPowerUp));
                    break;
                case 1: powerUps.add(pickablePowerUpFactory.sicknessResistenceBoost(null, "Sickness Resistence", randomPowerUp, randomPowerUp));
                    break;
                case 2: powerUps.add(pickablePowerUpFactory.speedBoost(null, "Speed Boost", randomPowerUp, randomPowerUp));
                    break;
            }
        }
    }

    private void solveCollisions() {
        final List<Human> generated = new ArrayList<>();
        final QuadTree tree = createTree();
        for (final Human human : humans) {
            if (human.getType() != HumanType.FEMALE) {
                continue;
            }
            final Human female = human;
            final Position femalePosition = female.getPosition();
            final Circle range = female.getStats().getReproductionAreaRadius();
            range.setRadius(range.getRadius() * 2);
            final List<Human> closeMales = tree.query(range).stream().map(p -> (Human) p.data()).toList();
            for (final Human closeMale : closeMales) {
                if (female.collide(closeMale)) {
                    generated.add(
                        random.nextDouble() < MALE_SPAWNING_PROBABILITY
                            ? humanFactory.male(randomPosition(femalePosition), map)
                            : humanFactory.female(randomPosition(femalePosition), map)
                    );
                }
            }
        }
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
}
