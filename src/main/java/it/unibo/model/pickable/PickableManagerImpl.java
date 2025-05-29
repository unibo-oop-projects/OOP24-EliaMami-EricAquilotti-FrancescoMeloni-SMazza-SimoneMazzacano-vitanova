package it.unibo.model.pickable;

import java.time.Clock;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

import it.unibo.common.CooldownGate;
import it.unibo.common.Position;
import it.unibo.model.chapter.map.Map;
import it.unibo.model.human.Human;
import it.unibo.view.screen.ScreenImpl;

/**
 * 
 */
public final class PickableManagerImpl implements PickableManager {
    private final List<Pickable> pickables = new CopyOnWriteArrayList<>();
    private final List<Pickable> activatedPickables = new CopyOnWriteArrayList<>();
    private final PickableFactory pickableFactory;
    private CooldownGate spawnPickableRate;
    private final Human player;
    private final Map map;
    private final Clock baseClock;

    /**
     * 
     * @param player
     * @param baseClock
     * @param map
     */
    public PickableManagerImpl(final Human player, final Clock baseClock, final Map map) {
        this.baseClock = baseClock;
        this.pickableFactory = new PickableFactoryImpl(baseClock);
        setSpawnPickableRate();
        this.player = player;
        this.map = map;
    }

    @Override
    public void spawnPickable() {
        if (this.spawnPickableRate.tryActivate()) {
            final List<Pickable> spawningPickables = new ArrayList<>();
            spawningPickables.add(pickableFactory.randomBoost(Position.getRandomWalkablePosition(map)));
            this.pickables.addAll(spawningPickables);
        }
    }

    @Override
    public void solvePickableCollisions() {
        if (player.getStats().isSick()) {
            return;
        }
        for (final Pickable pickable : this.pickables) {
            if (Math.abs(player.getPosition().x() - pickable.getPosition().x()) <= ScreenImpl.TILE_SIZE / 2 
                && Math.abs(player.getPosition().y() - pickable.getPosition().y()) <= ScreenImpl.TILE_SIZE / 2) {
                checkAndActivate(pickable);
                this.pickables.remove(pickable);
            }
        }
    }

    private void checkAndActivate(final Pickable pickable) {
        Optional<Pickable> tmp = Optional.empty();
        for (final Pickable activated : this.activatedPickables) {
            if (activated.getEffect().getType().equals(pickable.getEffect().getType())) {
                tmp = Optional.of(activated);
            }
        }
        if (tmp.isPresent()) {
            tmp.get().getEffect().refresh();
        } else {
            pickable.getEffect().activate();
            this.activatedPickables.add(pickable);
            this.player.getStats().applyEffect(pickable.getEffect());
        }
    }

    @Override
    public void resetExpiredEffects() {
        for (final Pickable activated : this.activatedPickables) {
            if (activated.getEffect().isExpired()) {
                this.player.getStats().resetEffect(activated.getEffect().getType());
                this.activatedPickables.remove(activated);
            }
        }
    } 

    @Override
    public List<Pickable> getPickables() {
        return new CopyOnWriteArrayList<>(this.pickables);
    }

    @Override
    public void resetPickables() {
        this.pickables.clear();
    }

    @Override
    public void resetActivatedPickables() {
        this.activatedPickables.clear();
    }

    @Override
    public void setSpawnPickableRate() {
        this.spawnPickableRate = new CooldownGate(Duration.ofSeconds(3), baseClock); 
    }
}
