package it.unibo.model.effect;

import java.time.Clock;
import java.time.Duration;
import java.util.Optional;

import it.unibo.common.CooldownGate;

/**
 * Effect factory implementation.
 */
public final class EffectFactoryImpl implements EffectFactory {
    private final Clock clock;

    /**
     * Constructor to initialize EffectFactoryImpl.
     * @param clock used to calculate the cooldown.
     */
    public EffectFactoryImpl(final Clock clock) {
        this.clock = clock;
    }

    @Override
    public Effect createEffect(final EffectType effectType, final Duration duration, final double multiplyValue) {
        return new Effect() {
            private final EffectType type = effectType; 
            private final Duration effectDuration = duration;
            private final double effectValue = multiplyValue;
            private Optional<CooldownGate> cooldown = Optional.empty();

            @Override
            public EffectType getType() {
                return type;
            }

            @Override
            public Duration getDuration() {
                return effectDuration;
            }

            @Override
            public double getMultiplyValue() {
                return effectValue;
            }

            @Override
            public boolean isExpired() {
                return cooldown.isEmpty() || cooldown.get().checkStatus();
            }

            @Override
            public void activate() {
                if (cooldown.isEmpty()) {
                    cooldown = Optional.of(new CooldownGate(effectDuration, clock));
                } else {
                    cooldown.get().tryActivate();
                }
            }

            @Override
            public void refresh() {
                cooldown = Optional.of(new CooldownGate(duration, clock));
            }
        };
    }
}
