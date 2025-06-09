package it.unibo.model.pickable;

import java.time.Clock;
import java.time.Duration;
import java.util.Random;

import it.unibo.common.Position;
import it.unibo.model.effect.Effect;
import it.unibo.model.effect.EffectFactory;
import it.unibo.model.effect.EffectFactoryImpl;
import it.unibo.model.effect.EffectType;
import it.unibo.view.sprite.PickableType;
import it.unibo.view.sprite.Sprite;

/**
 * Pickable factory implementation.
 */
public final class PickableFactoryImpl implements PickableFactory {
    private static final double MULTIPLY_VALUE = 1.25;
    private static final Duration DURATION_EFFECT_VALUE = Duration.ofSeconds(5);
    private static final Random RANDOM = new Random();
    private final EffectFactory effectFactory;

    /**
     * Constructor for pickable factory.
     * @param clock the clock to get the current time.
     */
    public PickableFactoryImpl(final Clock clock) {
        this.effectFactory = new EffectFactoryImpl(clock);
    }

    @Override
    public Pickable createPickable(
        final Position spawnPosition, final EffectType effectType, final Duration duration, final double value) {
        return new Pickable() {
            private final double x = spawnPosition.x();
            private final double y = spawnPosition.y();
            private final Effect effect = effectFactory.createEffect(effectType, duration, value);

            @Override
            public Position getPosition() {
                return new Position(x, y);
            }

            @Override
            public Sprite getSprite() {
                switch (effect.getType()) {
                    case SPEED:
                        return Sprite.getSprite(PickableType.PICKABLE_SPEED);
                    case REPRODUCTION_RANGE:
                        return Sprite.getSprite(PickableType.PICKABLE_REPRODUCTION_RANGE);
                    case FERTILITY:
                        return Sprite.getSprite(PickableType.PICKABLE_FERTILITY);
                    default:
                        return Sprite.getSprite(PickableType.PICKABLE_SICKNESS_RESISTENCE);
                }
            }

            @Override
            public Effect getEffect() {
                return this.effect;
            }
        };
    }

    @Override
    public Pickable createPickable(final Position spawnPosition, final EffectType effectType) {
        return createPickable(spawnPosition, effectType, DURATION_EFFECT_VALUE, MULTIPLY_VALUE);
    }

    @Override
    public Pickable randomPickable(final Position spawnPosition) {
        final int randomPickable = RANDOM.nextInt(0, 4);
        switch (randomPickable) {
            case 0: 
                return createPickable(spawnPosition, EffectType.SPEED);
            case 1: 
                return createPickable(spawnPosition, EffectType.REPRODUCTION_RANGE);
            case 2: 
                return createPickable(spawnPosition, EffectType.FERTILITY);
            default:
                return createPickable(spawnPosition, EffectType.SICKNESS_RESISTENCE);
        }
    }
}
