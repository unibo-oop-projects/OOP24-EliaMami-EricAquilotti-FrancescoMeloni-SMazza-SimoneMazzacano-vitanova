package it.unibo.model.pickable;

import java.time.Clock;

import it.unibo.common.Position;
import it.unibo.view.sprite.PowerUpType;
import it.unibo.view.sprite.Sprite;

/**
 * Pickable power up factory implementation.
 */
public final class PickablePowerUpFactoryImpl implements PickablePowerUpFactory {

    private final Clock clock;

    /**
     * 
     * @param clock the clock to get the current time.
     */
    public PickablePowerUpFactoryImpl(final Clock clock) {
        this.clock = clock;
    }

    @Override
    public PickablePowerUp speedBoost(final Position spawnPosition, final int duration, final double boost) {
        return new PickablePowerUp() {
            private static final String NAME = "Speed Boost";
            private final int powerUpDuration = duration;
            private final double boostValue = boost;
            //private CooldownGate cooldown;
            private final double x = spawnPosition.x();
            private final double y = spawnPosition.y();

            @Override
            public Position getPosition() {
                return new Position(x, y);
            }

            @Override
            public String getName() {
                return NAME;
            }

            @Override
            public int getDuration() {
                return powerUpDuration;
            }

            @Override
            public double getBoostValue() {
                return this.boostValue;
            }

            @Override
            public Sprite getSprite() {
                return Sprite.getPowerUpSprite(PowerUpType.PICKABLE_SPEED_BOOST);
            }

            @Override
            public boolean isActive() {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'isActive'");
            }

            @Override
            public void activate() {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'activate'");
            }
        };
    }

    @Override
    public PickablePowerUp sicknessResistenceBoost(final Position spawnPosition, final int duration, final double boost) {
        return new PickablePowerUp() {
            private static final String NAME = "Sickness Resistence";
            private final int powerUpDuration = duration;
            private final double boostValue = boost;
            private final double x = spawnPosition.x();
            private final double y = spawnPosition.y();

            @Override
            public Position getPosition() {
                return new Position(x, y);
            }

            @Override
            public String getName() {
                return NAME;
            }

            @Override
            public int getDuration() {
                return powerUpDuration;
            }

            @Override
            public double getBoostValue() {
                return this.boostValue;
            }

            @Override
            public Sprite getSprite() {
                return Sprite.getPowerUpSprite(PowerUpType.PICKABLE_SICKNESS_RESISTENCE);
            }

            @Override
            public boolean isActive() {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'isActive'");
            }

            @Override
            public void activate() {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'activate'");
            }
        };
    }

    @Override
    public PickablePowerUp reproductionRangeBoost(final Position spawnPosition, final int duration, final double boost) {
        return new PickablePowerUp() {
            private static final String NAME = "Reproduction Range";
            private final int powerUpDuration = duration;
            private final double boostValue = boost;
            private final double x = spawnPosition.x();
            private final double y = spawnPosition.y();

            @Override
            public Position getPosition() {
                return new Position(x, y);
            }

            @Override
            public String getName() {
                return NAME;
            }

            @Override
            public int getDuration() {
                return powerUpDuration;
            }

            @Override
            public double getBoostValue() {
                return this.boostValue;
            }

            @Override
            public Sprite getSprite() {
                return Sprite.getPowerUpSprite(PowerUpType.PICKABLE_REPRODUCTION_BOOST);
            }

            @Override
            public boolean isActive() {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'isActive'");
            }

            @Override
            public void activate() {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'activate'");
            }
        };
    }
}
