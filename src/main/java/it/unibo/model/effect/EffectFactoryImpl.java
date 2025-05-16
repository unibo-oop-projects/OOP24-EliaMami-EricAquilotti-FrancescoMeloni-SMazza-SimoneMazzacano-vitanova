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
    public Effect speedEffect(final Duration duration, final double multiplyValue) {
        return new Effect() {
            private static final EffectType TYPE = EffectType.SPEED; 
            private final Duration effectDuration = duration;
            private final double effectValue = multiplyValue;
            private Optional<CooldownGate> cooldown = Optional.empty();

            @Override
            public EffectType getType() {
                return TYPE;
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
    public Effect reproductionRangeEffect(final Duration duration, final double multiplyValue) {
        return new Effect() {
            private static final EffectType TYPE = EffectType.REPRODUCTION_RANGE;
            private final Duration effectDuration = duration;
            private final double effectValue = multiplyValue;
            private Optional<CooldownGate> cooldown = Optional.empty();

            @Override
            public EffectType getType() {
                return TYPE;
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
    public Effect fertilityEffect(final Duration duration, final double multiplyValue) {
        return new Effect() {
            private static final EffectType TYPE = EffectType.FERTILITY; 
            private final Duration effectDuration = duration;
            private final double effectValue = multiplyValue;
            private Optional<CooldownGate> cooldown = Optional.empty();

            @Override
            public EffectType getType() {
                return TYPE;
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
    public Effect sicknessResistenceEffect(final Duration duration, final double multiplyValue) {
        return new Effect() {
            private static final EffectType TYPE = EffectType.SICKNESS_RESISTENCE; 
            private final Duration effectDuration = duration;
            private final double effectValue = multiplyValue;
            private Optional<CooldownGate> cooldown = Optional.empty();

            @Override
            public EffectType getType() {
                return TYPE;
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
