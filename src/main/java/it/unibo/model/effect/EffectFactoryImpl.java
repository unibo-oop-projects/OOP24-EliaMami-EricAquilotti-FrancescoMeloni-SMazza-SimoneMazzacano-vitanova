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
    public Effect speedDecrease(final Duration duration, final double multiplyValue) {
        return new Effect() {
            private static final String NAME = "Speed Decrease"; 
            private final Duration effectDuration = duration;
            private final double effectValue = multiplyValue;
            private Optional<CooldownGate> cooldown = Optional.empty();

            @Override
            public String getName() {
                return NAME;
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

    @Override
    public Effect reproductionRangeDecrease(final Duration duration, final double multiplyValue) {
        return new Effect() {
            private static final String NAME = "Reproduction Range Decrease";
            private final Duration effectDuration = duration;
            private final double effectValue = multiplyValue;
            private Optional<CooldownGate> cooldown = Optional.empty();

            @Override
            public String getName() {
                return NAME;
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

    @Override
    public Effect fertilityDecrease(final Duration duration, final double multiplyValue) {
        return new Effect() {
            private static final String NAME = "Fertility Decrease"; 
            private final Duration effectDuration = duration;
            private final double effectValue = multiplyValue;
            private Optional<CooldownGate> cooldown = Optional.empty();

            @Override
            public String getName() {
                return NAME;
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
