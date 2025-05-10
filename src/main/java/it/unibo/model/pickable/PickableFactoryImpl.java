package it.unibo.model.pickable;

import java.time.Clock;
import java.time.Duration;
import java.util.Optional;

import it.unibo.common.CooldownGate;
import it.unibo.common.Position;
import it.unibo.model.effect.Effect;
import it.unibo.view.sprite.PowerUpType;
import it.unibo.view.sprite.Sprite;

/**
 * Pickable power up factory implementation.
 */
public final class PickableFactoryImpl implements PickableFactory {

    private final Clock clock;

    /**
     * 
     * @param clock the clock to get the current time.
     */
    public PickableFactoryImpl(final Clock clock) {
        this.clock = clock;
    }

    @Override
    public Pickable speedBoost(final Position spawnPosition, final Duration duration, final double boost) {
        return new Pickable() {
            private final double x = spawnPosition.x();
            private final double y = spawnPosition.y();
            private final Effect speedBoost = new Effect() {
                private static final String NAME = "Speed Boost";
                private final Duration powerUpDuration = duration;
                private final double boostValue = boost;
                private Optional<CooldownGate> cooldown = Optional.empty();
                @Override
                public String getName() {
                    return NAME;
                }

                @Override
                public Duration getDuration() {
                    return powerUpDuration;
                }

                @Override
                public double getMultiplyValue() {
                    return boostValue;
                }

                @Override
                public boolean isExpired() {
                    return cooldown.isEmpty() || cooldown.get().checkStatus();
                }

                @Override
                public void activate() {
                    if (cooldown.isEmpty()) {
                        cooldown = Optional.of(new CooldownGate(powerUpDuration, clock));
                    } else {
                        cooldown.get().tryActivate();
                    }
                }

                @Override
                public void refresh() {
                    cooldown = Optional.of(new CooldownGate(powerUpDuration, clock));
                }
            };

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
    public Pickable sicknessResistenceBoost(final Position spawnPosition, final Duration duration, final double boost) {
        return new Pickable() {
            private final double x = spawnPosition.x();
            private final double y = spawnPosition.y();
            private final Effect sicknessResistence = new Effect() {
                private static final String NAME = "Sickness Resistence";
                private final Duration powerUpDuration = duration;
                private final double boostValue = boost;
                private Optional<CooldownGate> cooldown = Optional.empty();

                @Override
                public String getName() {
                    return NAME;
                }

                @Override
                public Duration getDuration() {
                    return powerUpDuration;
                }

                @Override
                public double getMultiplyValue() {
                    return boostValue;
                }

                @Override
                public boolean isExpired() {
                    return cooldown.isEmpty() || cooldown.get().checkStatus();
                }

                @Override
                public void activate() {
                    if (cooldown.isEmpty()) {
                        cooldown = Optional.of(new CooldownGate(powerUpDuration, clock));
                    } else {
                        cooldown.get().tryActivate();
                    }
                }

                @Override
                public void refresh() {
                    cooldown = Optional.of(new CooldownGate(powerUpDuration, clock));
                }
            };

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
    public Pickable reproductionRangeBoost(final Position spawnPosition, final Duration duration, final double boost) {
        return new Pickable() {
            private final double x = spawnPosition.x();
            private final double y = spawnPosition.y();
            private final Effect reproductionAreaEffect = new Effect() {
                private static final String NAME = "Reproduction Range";
                private final Duration powerUpDuration = duration;
                private final double boostValue = boost;
                private Optional<CooldownGate> cooldown = Optional.empty();

                @Override
                public String getName() {
                    return NAME;
                }

                @Override
                public Duration getDuration() {
                    return powerUpDuration;
                }

                @Override
                public double getMultiplyValue() {
                    return boostValue;
                }

                @Override
                public boolean isExpired() {
                    return cooldown.isEmpty() || cooldown.get().checkStatus();
                }

                @Override
                public void activate() {
                    if (cooldown.isEmpty()) {
                        cooldown = Optional.of(new CooldownGate(powerUpDuration, clock));
                    } else {
                        cooldown.get().tryActivate();
                    }
                }

                @Override
                public void refresh() {
                    cooldown = Optional.of(new CooldownGate(powerUpDuration, clock));
                }
            };

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
}
