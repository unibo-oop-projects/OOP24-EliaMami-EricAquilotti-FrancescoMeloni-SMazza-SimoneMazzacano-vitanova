package it.unibo.model.pickable;

import java.time.Clock;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Predicate;

import it.unibo.common.CooldownGate;
import it.unibo.common.Position;
import it.unibo.model.chapter.map.Map;
import it.unibo.model.human.Human;
import it.unibo.view.screen.ScreenImpl;

/**
 * 
 */
public final class PickableManagerImpl implements PickableManager {
    private final int SECONDS_TO_SPAWN = 5;
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
        Predicate<Pickable> ifCollide = p -> Math.abs(player.getPosition().x() - p.getPosition().x()) <= ScreenImpl.TILE_SIZE / 2 
                                 && Math.abs(player.getPosition().y() - p.getPosition().y()) <= ScreenImpl.TILE_SIZE / 2;
        pickables.stream()
        .filter(ifCollide)
        .forEach( p -> {
            checkAndActivate(p); 
            pickables.remove(p);
        });
        // for (final Pickable activated : activatedPickables) {
        //     if (activated.getEffect().isExpired()) {
        //         getPlayer().getStats().resetEffect(activated.getEffect().getType());
        //         activatedPickables.remove(activated);
        //     }
        // }
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
        activatedPickables.stream()
        .filter(a -> a.getEffect().isExpired())
        .forEach( a -> {
            player.getStats().resetEffect(a.getEffect().getType());
            activatedPickables.remove(a);
        });
        // for (final Pickable activated : activatedPickables) {
        //     if (activated.getEffect().isExpired()) {
        //         player.getStats().resetEffect(activated.getEffect().getType());
        //         activatedPickables.remove(activated);
        //     }
        // }
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
        this.spawnPickableRate = new CooldownGate(Duration.ofSeconds(SECONDS_TO_SPAWN), baseClock); 
    }
}
