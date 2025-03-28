package it.unibo.model.chapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import it.unibo.common.Circle;
import it.unibo.common.CircleImpl;
import it.unibo.common.Position;
import it.unibo.common.RectangleImpl;
import it.unibo.controller.InputHandler;
import it.unibo.model.human.Human;
import it.unibo.model.human.Male;
import it.unibo.model.human.MaleImpl;
import it.unibo.model.chapter.map.Map;
import it.unibo.model.chapter.map.MapImpl;
import it.unibo.model.chapter.quadtree.Point;
import it.unibo.model.chapter.quadtree.QuadTree;
import it.unibo.model.chapter.quadtree.QuadTreeImpl;
import it.unibo.model.human.Female;
import it.unibo.model.human.FemaleImpl;
import it.unibo.model.human.PlayerImpl;
import it.unibo.model.human.strategies.PlayerMovementStrategy;
import it.unibo.view.screen.ScreenImpl;

/**
 * Implementation of a chapter that handles map and humans movement and
 * collisions.
 */
public final class ChapterImpl implements Chapter {
    private static final int STARTING_FEMALES = 20;
    private static final double MALE_SPAWNING_PROBABILITY = .9;
    private final Map map = new MapImpl();
    // The first human is the player.
    // CopyOnWriteArrayList is a thread safe list, if it's too slow we'll change it.
    private final List<Human> humans = new CopyOnWriteArrayList<>();
    private final Random random = new Random();

    /**
     * Sets up all the parameters.
     * @param inputHandler
     */
    public ChapterImpl(final InputHandler inputHandler) {
        final Position centerPosition = new Position(
            (MapImpl.MAP_ROW - 1) * ScreenImpl.TILE_SIZE / 2,
            (MapImpl.MAP_COL - 1) * ScreenImpl.TILE_SIZE / 2
        );
        this.humans.add(new PlayerImpl(centerPosition, map, new PlayerMovementStrategy(inputHandler)));
        for (int i = 0; i < STARTING_FEMALES; i++) {
            this.humans.add(new FemaleImpl(randomPosition(centerPosition), map));
        }
    }

    @Override
    public void update() {
        for (final Human human : humans) {
            human.move();
        }
        solveCollisions();
    }

    private void solveCollisions() {
        final List<Human> generated = new ArrayList<>();
        final QuadTree tree = createTree();
        for (final Human human : humans) {
            if (!(human instanceof Female)) {
                continue;
            }
            final Female female = (Female) human;
            final Position femalePosition = female.getPosition();
            final Circle range = new CircleImpl(female.reproductionArea());
            range.setRadius(range.getRadius() * 2);
            final List<Male> closeMales = tree.query(range).stream().map(p -> (Male) p.data()).toList();
            for (final Male closeMale : closeMales) {
                if (female.collide(closeMale)) {
                    generated.add(
                        random.nextDouble() < MALE_SPAWNING_PROBABILITY
                            ? new MaleImpl(randomPosition(femalePosition), map)
                            : new FemaleImpl(randomPosition(femalePosition), map)
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
                MapImpl.MAP_ROW * ScreenImpl.TILE_SIZE,
                MapImpl.MAP_COL * ScreenImpl.TILE_SIZE
            )
        );
        fillTree(tree);
        return tree;
    }

    private void fillTree(final QuadTree tree) {
        humans.forEach(h -> {
            if (h instanceof Male) {
                tree.insert(new Point(h.getPosition(), (Male) h));
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
        return new Position(
            (int) Math.floor(
                reference.x()
                    + (random.nextBoolean() ? 1 : -1)
                        * ScreenImpl.TILE_SIZE * 3 * random.nextDouble()
            ),
            (int) Math.floor(
                reference.y()
                    + (random.nextBoolean() ? 1 : -1)
                        * ScreenImpl.TILE_SIZE * 3 * random.nextDouble()
            )
        );
    }
}
