package it.unibo.model.pickable;

import java.time.Clock;
import java.time.Duration;
import java.util.Random;

import it.unibo.common.Position;
import it.unibo.model.effect.Effect;
import it.unibo.model.effect.EffectFactory;
import it.unibo.model.effect.EffectFactoryImpl;
import it.unibo.view.sprite.PickableType;
import it.unibo.view.sprite.Sprite;

/**
 * Pickable factory implementation.
 */
public final class PickableFactoryImpl implements PickableFactory {
    private final EffectFactory effectFactory;
    private static final double MULTIPLY_VALUE = 1.25;
    private static final Duration DURATION_EFFECT_VALUE = Duration.ofSeconds(5);
    private static final Random RANDOM = new Random();

    /**
     * Constructor for pickable factory.
     * @param clock the clock to get the current time.
     */
    public PickableFactoryImpl(final Clock clock) {
        this.effectFactory = new EffectFactoryImpl(clock);
    }

    @Override
    public Pickable speedPickable(final Position spawnPosition, final Duration duration, final double value) {
        return new Pickable() {
            private final double x = spawnPosition.x();
            private final double y = spawnPosition.y();
            private final Effect speedEffect = effectFactory.speedEffect(duration, value);

            @Override
            public Position getPosition() {
                return new Position(x, y);
            }

            @Override
            public Sprite getSprite() {
                return Sprite.getSprite(PickableType.PICKABLE_SPEED);
            }

            @Override
            public Effect getEffect() {
                return this.speedEffect;
            }
        };
    }

    @Override
    public Pickable speedPickable(final Position spawnPosition) {
        return speedPickable(spawnPosition, DURATION_EFFECT_VALUE, MULTIPLY_VALUE);
    }

    @Override
    public Pickable sicknessResistencePickable(final Position spawnPosition, final Duration duration, final double value) {
        return new Pickable() {
            private final double x = spawnPosition.x();
            private final double y = spawnPosition.y();
            private final Effect sicknessResistenceEffect = effectFactory.sicknessResistenceEffect(duration, value);

            @Override
            public Position getPosition() {
                return new Position(x, y);
            }

            @Override
            public Sprite getSprite() {
                return Sprite.getSprite(PickableType.PICKABLE_SICKNESS_RESISTENCE);
            }

            @Override
            public Effect getEffect() {
                return this.sicknessResistenceEffect;
            }
        };
    }

    @Override
    public Pickable sicknessResistencePickable(final Position spawnPosition) {
        return sicknessResistencePickable(spawnPosition, DURATION_EFFECT_VALUE, MULTIPLY_VALUE);
    }

    @Override
    public Pickable reproductionRangePickable(final Position spawnPosition, final Duration duration, final double value) {
        return new Pickable() {
            private final double x = spawnPosition.x();
            private final double y = spawnPosition.y();
            private final Effect reproductionAreaEffect = effectFactory.reproductionRangeEffect(duration, value);

            @Override
            public Position getPosition() {
                return new Position(x, y);
            }

            @Override
            public Sprite getSprite() {
                return Sprite.getSprite(PickableType.PICKABLE_REPRODUCTION_RANGE);
            }

            @Override
            public Effect getEffect() {
                return this.reproductionAreaEffect;
            }
        };
    }

    @Override
    public Pickable reproductionRangePickable(final Position spawnPosition) {
        return reproductionRangePickable(spawnPosition, DURATION_EFFECT_VALUE, MULTIPLY_VALUE);
    }

    @Override
    public Pickable randomPickable(final Position spawnPosition) {
        final int randomPickable = RANDOM.nextInt(0, 3);
        switch (randomPickable) {
            case 0: 
                return speedPickable(spawnPosition);
            case 1: 
                return sicknessResistencePickable(spawnPosition);
            default:
                return reproductionRangePickable(spawnPosition);
        }
    }
}
