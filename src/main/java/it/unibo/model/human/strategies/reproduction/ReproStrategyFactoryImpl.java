package it.unibo.model.human.strategies.reproduction;

import java.time.Clock;
import java.time.Duration;
import java.util.function.Predicate;

import it.unibo.common.Circle;
import it.unibo.common.CircleImpl;
import it.unibo.common.Position;
import it.unibo.model.human.Human;
import it.unibo.view.sprite.HumanType;

/**
 * Implementation of a factory of reproduction strategies.
 */
public final class ReproStrategyFactoryImpl implements ReproStrategyFactory {
    // I want the center to be around the legs of the human.
    private static final int CIRCLE_X_OFFSET = 16;
    private static final int CIRCLE_Y_OFFSET = 24;
    private static final int CIRCLE_RADIOUS = 20;

    private final Clock clock;

    /**
     * 
     * @param clock the clock to get the current time.
     */
    public ReproStrategyFactoryImpl(final Clock clock) {
        this.clock = clock;
    }

    @Override
    public ReproStrategy maleReproStrategy(final Position startingPosition) {
        return generalised(h -> false, startingPosition);
    }

    @Override
    public ReproStrategy femaleReproStrategy(final Position startingPosition) {
        return generalised(
            new CooldownReproductionPredicate(h -> h.getType() != HumanType.FEMALE, Duration.ofSeconds(2), clock),
            startingPosition
        );
    }

    private ReproStrategy generalised(final Predicate<Human> canReproduceWith, final Position startingPosition) {
        final Circle reproductionArea = new CircleImpl(
            startingPosition.x() + CIRCLE_X_OFFSET,
            startingPosition.y() + CIRCLE_Y_OFFSET,
            CIRCLE_RADIOUS
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
                return canReproduceWith.test(other) && reproductionArea.intersects(other.getStats().getReproductionAreaRadius());
            }

            private void centerReproductionArea(final Position humanPosition) {
                reproductionArea.setCenter(humanPosition.x() + CIRCLE_X_OFFSET, humanPosition.y() + CIRCLE_Y_OFFSET);
            }

            @Override
            public Circle changeReproductionArea(final int changeValue) {
                return new CircleImpl(reproductionArea.getCenter().x(), reproductionArea.getCenter().y(), changeValue);
            }
        };
    }
}
