package it.unibo.model.pickable.manager;

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
import it.unibo.model.chapter.map.MapImpl;
import it.unibo.model.effect.Effect;
import it.unibo.model.human.Human;
import it.unibo.model.pickable.Pickable;
import it.unibo.model.pickable.PickableFactory;
import it.unibo.model.pickable.PickableFactoryImpl;

/**
 * Class that will handle all operations for pickables.
 */
public final class PickableManagerImpl implements PickableManager {
    private static final int SECONDS_TO_SPAWN = 5;
    private final List<Pickable> pickables = new CopyOnWriteArrayList<>();
    private final List<Effect> activatedEffects = new CopyOnWriteArrayList<>();
    private final PickableFactory pickableFactory;
    private CooldownGate spawnPickableRate;
    private Human player;
    private final Map map;
    private final Clock baseClock;

    /**
     * Constructor for PickableManager.
     * @param player is the player.
     * @param baseClock the base clock.
     * @param map the chapter map.
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
            spawningPickables.add(pickableFactory.randomPickable(Position.getRandomWalkablePosition(map)));
            this.pickables.addAll(spawningPickables);
        }
    }

    @Override
    public void solvePickableCollisions() {
        if (player.getStats().isSick()) {
            return;
        }
        final Predicate<Pickable> ifCollide = pickable -> 
                                Math.abs(player.getPosition().x() - pickable.getPosition().x()) <= MapImpl.TILE_SIZE / 2 
                                 && Math.abs(player.getPosition().y() - pickable.getPosition().y()) <= MapImpl.TILE_SIZE / 2;
        pickables.stream()
        .filter(ifCollide)
        .forEach(pickable -> {
            checkAndActivate(pickable.getEffect()); 
        });
        pickables.removeIf(ifCollide);
    }

    private void checkAndActivate(final Effect effect) {
        Optional<Effect> tmp = Optional.empty();
        for (final Effect activated : this.activatedEffects) {
            if (activated.getType().equals(effect.getType())) {
                tmp = Optional.of(activated);
            }
        }
        if (tmp.isPresent()) {
            tmp.get().refresh();
        } else {
            effect.activate();
            this.activatedEffects.add(effect);
            this.player.getStats().applyEffect(effect);
        }
    }

    @Override
    public void resetExpiredEffects() {
        activatedEffects.stream()
        .filter(Effect::isExpired)
        .forEach(a -> {
            player.getStats().resetEffect(a);
        });
        activatedEffects.removeIf(Effect::isExpired);
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
        this.activatedEffects.clear();
    }

    @Override
    public void setSpawnPickableRate() {
        this.spawnPickableRate = new CooldownGate(Duration.ofSeconds(SECONDS_TO_SPAWN), baseClock); 
    }

    @Override
    public void setPlayer(final Human player) {
        this.player = player;
    }
}
