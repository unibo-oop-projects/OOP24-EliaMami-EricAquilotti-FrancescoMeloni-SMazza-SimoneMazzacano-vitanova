package it.unibo.model.human.strategies.reproduction;

import java.time.Clock;
import java.time.Duration;
import java.util.Random;
import java.util.function.Predicate;
import java.util.function.Supplier;

import it.unibo.common.Circle;
import it.unibo.common.CircleImpl;
import it.unibo.common.Position;
import it.unibo.model.chapter.map.MapImpl;
import it.unibo.model.human.Human;
import it.unibo.view.sprite.HumanType;

/**
 * Implementation of a factory of reproduction strategies.
 */
public final class ReproStrategyFactoryImpl implements ReproStrategyFactory {
    // I want the center to be around the legs of the human.
    private static final double CIRCLE_X_OFFSET = MapImpl.TILE_SIZE / 2.0;
    private static final double CIRCLE_Y_OFFSET = MapImpl.TILE_SIZE * 3.0 / 4.0;
    private static final double CIRCLE_RADIUS = MapImpl.TILE_SIZE / 5.0;
    private static final long MIN_COOLDOWN_MILLIS = 1000;
    private static final long MAX_COOLDOWN_MILLIS = 4000;
    private static final Random random = new Random();

    private final Clock clock;

    /**
     * Creates a new factory for reproduction strategies.
     * 
     * @param clock the clock to get the current time.
     */
    public ReproStrategyFactoryImpl(final Clock clock) {
        this.clock = clock;
    }

    @Override
    public ReproStrategy maleReproStrategy(final Position startingPosition) {
        return generalised(h -> false, startingPosition, () -> false);
    }

    @Override
    public ReproStrategy femaleReproStrategy(final Position startingPosition) {
        final CooldownReproductionPredicate reproPredicate = new CooldownReproductionPredicate(
            h -> h.getType() != HumanType.FEMALE,
            Duration.ofMillis(random.nextLong(MIN_COOLDOWN_MILLIS, MAX_COOLDOWN_MILLIS)),
            clock
        );
        return generalised(
            reproPredicate,
            startingPosition,
            reproPredicate::isOnCooldown
        );
    }

    private ReproStrategy generalised(
        final Predicate<Human> canReproduceWith,
        final Position startingPosition,
        final Supplier<Boolean> isOnCooldown
    ) {
        final Circle reproductionArea = new CircleImpl(
            startingPosition.x() + CIRCLE_X_OFFSET,
            startingPosition.y() + CIRCLE_Y_OFFSET,
            CIRCLE_RADIUS
        );

        return new ReproStrategy() {
            @Override
            public void update(final Position humanPosition) {
                centerReproductionArea(humanPosition);
            }

            @Override
            public Circle getReproductionArea() {
                return new CircleImpl(reproductionArea);
            }

            @Override
            public boolean collide(final Human other) {
                return reproductionArea.intersects(other.getStats().getReproductionCircle()) && canReproduceWith.test(other);
            }

            private void centerReproductionArea(final Position humanPosition) {
                reproductionArea.setCenter(humanPosition.x() + CIRCLE_X_OFFSET, humanPosition.y() + CIRCLE_Y_OFFSET);
            }

            @Override
            public void changeReproductionArea(final double changeValue) {
                reproductionArea.setRadius(changeValue);
            }

            @Override
            public boolean isOnCooldown() {
                return isOnCooldown.get();
            }
        };
    }
}
