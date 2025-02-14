package it.unibo.model.chapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import it.unibo.common.Position;
import it.unibo.controller.InputHandler;
import it.unibo.model.human.Human;
import it.unibo.model.human.Male;
import it.unibo.model.human.MaleImpl;
import it.unibo.model.human.Female;
import it.unibo.model.human.FemaleImpl;
import it.unibo.model.human.Player;
import it.unibo.model.human.PlayerImpl;
import it.unibo.view.screen.ScreenImpl;

/**
 * Implementation of a chapter that handles map and humans movement and
 * collisions.
 */
public final class ChapterImpl implements Chapter {
    private static final int STARTING_FEMALES = 20;
    private final Map map = new MapImpl();
    private final InputHandler inputHandler;
    // The first human is the player.
    // CopyOnWriteArrayList is a thread safe list, if it's too slow we'll change it.
    private final List<Human> humans = new CopyOnWriteArrayList<>();
    private final Random random = new Random();

    /**
     * Sets up all the parameters.
     * @param inputHandler
     */
    public ChapterImpl(final InputHandler inputHandler) {
        this.inputHandler = inputHandler;
        final Position centerPosition = new Position(
            (MapImpl.MAP_ROW - 1) * ScreenImpl.TILE_SIZE / 2,
            (MapImpl.MAP_COL - 1) * ScreenImpl.TILE_SIZE / 2
        );
        this.humans.add(new PlayerImpl(centerPosition));
        for (int i = 0; i < STARTING_FEMALES; i++) {
            this.humans.add(new FemaleImpl(randomPosition(centerPosition)));
        }
    }

    @Override
    public void update() {
        final Player player = (Player) getPlayer();
        player.setDirection(inputHandler.getDirection());
        for (final Human human : humans) {
            human.move();
        }
        solveCollisions();
    }

    private void solveCollisions() {
        final List<Human> generated = new ArrayList<>();
        for (final Human human : humans) {
            if (!(human instanceof Female)) {
                continue;
            }
            final Female female = (Female) human;
            for (final Human human2 : humans) {
                if (!(human2 instanceof Male)) {
                    continue;
                }
                final Male male = (Male) human2;
                if (female.collide(male)) {
                    final Position femalePosition = female.getPosition();
                    generated.add(new MaleImpl(randomPosition(femalePosition)));
                }
            }
        }
        this.humans.addAll(generated);
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
                        * ScreenImpl.TILE_SIZE * 2 * random.nextDouble()
            ),
            (int) Math.floor(
                reference.y()
                    + (random.nextBoolean() ? 1 : -1)
                        * ScreenImpl.TILE_SIZE * 2 * random.nextDouble()
            )
        );
    }
}
