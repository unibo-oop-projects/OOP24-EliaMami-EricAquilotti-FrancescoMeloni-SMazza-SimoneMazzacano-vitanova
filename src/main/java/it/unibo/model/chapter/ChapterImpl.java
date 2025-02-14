package it.unibo.model.chapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
    private final Map map = new MapImpl();
    private final InputHandler inputHandler;
    // The first human is the player.
    // CopyOnWriteArrayList is a thread safe list, if it's too slow we'll change it.
    private final List<Human> humans = new CopyOnWriteArrayList<>();

    /**
     * Sets up all the parameters.
     * @param inputHandler
     */
    public ChapterImpl(final InputHandler inputHandler) {
        this.inputHandler = inputHandler;
        final int centerX = (MapImpl.MAP_ROW - 1) * ScreenImpl.TILE_SIZE / 2;
        final int centerY = (MapImpl.MAP_COL - 1) * ScreenImpl.TILE_SIZE / 2;
        this.humans.add(new PlayerImpl(centerX, centerY));
        this.humans.add(new MaleImpl(centerX + ScreenImpl.TILE_SIZE * 2, centerY + ScreenImpl.TILE_SIZE * 2));
        this.humans.add(new FemaleImpl(centerX - ScreenImpl.TILE_SIZE * 2, centerY - ScreenImpl.TILE_SIZE * 2));
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
                    final Position position = female.getPosition();
                    generated.add(new MaleImpl(position.x() - ScreenImpl.TILE_SIZE * 2,
                                    position.y() - ScreenImpl.TILE_SIZE * 2));
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

}
