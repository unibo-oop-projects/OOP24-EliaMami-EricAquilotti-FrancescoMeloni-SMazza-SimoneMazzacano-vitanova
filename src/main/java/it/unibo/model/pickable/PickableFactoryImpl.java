package it.unibo.model.pickable;

import java.time.Clock;
import java.time.Duration;
import java.util.Random;

import it.unibo.common.Position;
import it.unibo.model.effect.Effect;
import it.unibo.model.effect.EffectFactory;
import it.unibo.model.effect.EffectFactoryImpl;
import it.unibo.view.sprite.PowerUpType;
import it.unibo.view.sprite.Sprite;

/**
 * Pickable power up factory implementation.
 */
public final class PickableFactoryImpl implements PickableFactory {
    private final EffectFactory effectFactory;
    private static final double MULTIPLY_VALUE = 1.25;
    private static final Duration DURATION_EFFECT_VALUE = Duration.ofSeconds(5);
    private static final Random RANDOM = new Random();

    /**
     * 
     * @param clock the clock to get the current time.
     */
    public PickableFactoryImpl(final Clock clock) {
        this.effectFactory = new EffectFactoryImpl(clock);
    }

    @Override
    public Pickable speedBoost(final Position spawnPosition, final Duration duration, final double boost) {
        return new Pickable() {
            private final double x = spawnPosition.x();
            private final double y = spawnPosition.y();
            private final Effect speedBoost = effectFactory.speedEffect(duration, boost);

            @Override
            public Position getPosition() {
                return new Position(x, y);
            }

            @Override
            public Sprite getSprite() {
                return Sprite.getSprite(PowerUpType.PICKABLE_SPEED_BOOST);
            }

            @Override
            public Effect getEffect() {
                return this.speedBoost;
            }
        };
    }

    @Override
    public Pickable speedBoost(final Position spawnPosition) {
        return speedBoost(spawnPosition, DURATION_EFFECT_VALUE, MULTIPLY_VALUE);
    }

    @Override
    public Pickable sicknessResistenceBoost(final Position spawnPosition, final Duration duration, final double boost) {
        return new Pickable() {
            private final double x = spawnPosition.x();
            private final double y = spawnPosition.y();
            private final Effect sicknessResistence = effectFactory.sicknessResistenceEffect(duration, boost);

            @Override
            public Position getPosition() {
                return new Position(x, y);
            }

            @Override
            public Sprite getSprite() {
                return Sprite.getSprite(PowerUpType.PICKABLE_SICKNESS_RESISTENCE);
            }

            @Override
            public Effect getEffect() {
                return this.sicknessResistence;
            }
        };
    }

    @Override
    public Pickable sicknessResistenceBoost(final Position spawnPosition) {
        return sicknessResistenceBoost(spawnPosition, DURATION_EFFECT_VALUE, MULTIPLY_VALUE);
    }

    @Override
    public Pickable reproductionRangeBoost(final Position spawnPosition, final Duration duration, final double boost) {
        return new Pickable() {
            private final double x = spawnPosition.x();
            private final double y = spawnPosition.y();
            private final Effect reproductionAreaEffect = effectFactory.reproductionRangeEffect(duration, boost);

            @Override
            public Position getPosition() {
                return new Position(x, y);
            }

            @Override
            public Sprite getSprite() {
                return Sprite.getSprite(PowerUpType.PICKABLE_REPRODUCTION_BOOST);
            }

            @Override
            public Effect getEffect() {
                return this.reproductionAreaEffect;
            }
        };
    }

    @Override
    public Pickable reproductionRangeBoost(final Position spawnPosition) {
        return reproductionRangeBoost(spawnPosition, DURATION_EFFECT_VALUE, MULTIPLY_VALUE);
    }

    @Override
    public Pickable randomBoost(final Position spawnPosition) {
        final int randomPowerUp = RANDOM.nextInt(0, 3);
        switch (randomPowerUp) {
            case 0: 
                return speedBoost(spawnPosition);
            case 1: 
                return sicknessResistenceBoost(spawnPosition);
            default:
                return reproductionRangeBoost(spawnPosition);
        }
    }
}
