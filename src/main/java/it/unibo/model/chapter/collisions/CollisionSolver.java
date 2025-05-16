package it.unibo.model.chapter.collisions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import it.unibo.common.Circle;
import it.unibo.common.Position;
import it.unibo.common.RectangleImpl;
import it.unibo.model.chapter.map.Map;
import it.unibo.model.chapter.quadtree.Point;
import it.unibo.model.chapter.quadtree.QuadTree;
import it.unibo.model.chapter.quadtree.QuadTreeImpl;
import it.unibo.model.human.Human;
import it.unibo.model.human.HumanFactory;
import it.unibo.view.screen.ScreenImpl;
import it.unibo.view.sprite.HumanType;

/**
 * Models a Collision Solver that helps solving the collisions between the
 * humans in a chapter. This class has only static methods.
 */
public final class CollisionSolver {
    private CollisionSolver() { }

    /**
     * Solves the collisions and appends the new generated humans to the current
     * population.
     * @param currentPopulation the current humans that are on the map.
     * @param maleSpawiningProbability the probability of the newborn to be a male.
     * @param map the map where to spawn the humans.
     * @param humanFactory the factory used to create the newborns.
     */
    public static void solveCollisions(
            final List<Human> currentPopulation,
            final double maleSpawiningProbability,
            final Map map,
            final HumanFactory humanFactory
    ) {
        final Random random = new Random();
        final List<Human> generated = new ArrayList<>();
        final QuadTree<Human> tree = createTree(currentPopulation, map);
        currentPopulation.stream()
        .filter(h -> h.getType() == HumanType.FEMALE)
        .forEach(female -> {
            final Position femalePosition = female.getPosition();
            final Circle range = female.getStats().getReproductionAreaRadius();
            range.setRadius(range.getRadius() * 2);
            tree.query(range).stream()
            .map(Point::data)
            .filter(female::collide)
            .forEach(male -> generated.add(
                random.nextDouble() < maleSpawiningProbability
                    ? humanFactory.male(Position.getRandomWalkableReferencePosition(femalePosition, map), map)
                    : humanFactory.female(Position.getRandomWalkableReferencePosition(femalePosition, map), map)
            ));
        });
        currentPopulation.addAll(generated);
    }

    private static QuadTree<Human> createTree(final List<Human> currentPopulation, final Map map) {
        final QuadTree<Human> tree = new QuadTreeImpl<>(
            new RectangleImpl(
                new Position(0, 0),
                map.getRows() * ScreenImpl.TILE_SIZE,
                map.getColoumns() * ScreenImpl.TILE_SIZE
            )
        );
        fillTree(tree, currentPopulation);
        return tree;
    }

    private static void fillTree(final QuadTree<Human> tree, final List<Human> currentPopulation) {
        currentPopulation.forEach(h -> {
            if (h.getType() == HumanType.MALE || h.getType() == HumanType.PLAYER) {
                tree.insert(new Point<>(h.getStats().getReproductionAreaRadius().getCenter(), h));
            }
        });
    }
}
