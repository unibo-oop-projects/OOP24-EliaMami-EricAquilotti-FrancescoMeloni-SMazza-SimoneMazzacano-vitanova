package it.unibo.model.chapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import it.unibo.common.Position;
import it.unibo.controller.InputHandler;
import it.unibo.model.human.Human;
import it.unibo.model.human.MaleImpl;
import it.unibo.model.human.Player;
import it.unibo.model.human.PlayerImpl;
import it.unibo.view.screen.ScreenImpl;

/**
 * Implementation of a chapter that handles map and humans movement and
 * collisions.
 */
public final class ChapterImpl implements Chapter {
    private final Map map;
    private final InputHandler inputHandler;
    // The first human will be the player.
    private final List<Human> humans;

    /**
     * Sets up all the parameters.
     * @param inputHandler
     */
    public ChapterImpl(final InputHandler inputHandler) {
        this.inputHandler = inputHandler;
        this.humans = new ArrayList<>();
        final int centerX = (MapImpl.MAP_ROW - 1) * ScreenImpl.TILE_SIZE / 2;
        final int centerY = (MapImpl.MAP_COL - 1) * ScreenImpl.TILE_SIZE / 2;
        this.humans.add(new PlayerImpl(centerX, centerY));
        this.humans.add(new MaleImpl(centerX + ScreenImpl.TILE_SIZE * 2, centerY + ScreenImpl.TILE_SIZE * 2));
        this.map = new MapImpl();
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
        for (int i = 0; i < humans.size() - 1; i++) {
            final Human h1 = humans.get(i);
            for (int j = i + 1; j < humans.size(); j++) {
                final Human h2 = humans.get(j);
                if (h1.collide(h2)) {
                    final Position position = h1.getPosition();
                    // needs to get mutex
                    synchronized(new Object()) {
                        this.humans.add(new MaleImpl(position.x() - ScreenImpl.TILE_SIZE * 2,
                        position.y() - ScreenImpl.TILE_SIZE * 2));
                    }
                }
            }
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
    public Human getPlayer() {
        return humans.get(0);
    }

}
