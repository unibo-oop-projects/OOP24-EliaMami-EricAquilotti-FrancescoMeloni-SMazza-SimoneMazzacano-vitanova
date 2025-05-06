package it.unibo.model.pickable;

import java.time.Clock;
import java.time.Duration;

import it.unibo.common.CooldownGate;
import it.unibo.common.Position;

public class PickablePowerUpFactoryImpl implements PickablePowerUpFactory{

    private final Clock clock;

    /**
     * 
     * @param clock the clock to get the current time.
     */
    public PickablePowerUpFactoryImpl(final Clock clock) {
        this.clock = clock;
    }

    @Override
    public PickablePowerUp speedBoost(Position spawnPosition, int duration, double boost) {
        return new PickablePowerUp() {
            private final String name = "Speed Boost";
            private final int duration = 30; 
            private CooldownGate cooldown;
            private double x = spawnPosition.x();
            private double y = spawnPosition.y();

            @Override
            public Position getPosition() {
                return new Position(x, y);
            }

            @Override
            public String getName() {
                return this.name;
            }

            @Override
            public int getDuration() {
                return this.duration;
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
    public PickablePowerUp sicknessResistenceBoost(Position spawnPosition, int duration, double boost) {
        return new PickablePowerUp() {
            private final String name = "Sickness Resistence";
            private final int duration = 30; 
            private double x = spawnPosition.x();
            private double y = spawnPosition.y();

            @Override
            public Position getPosition() {
                return new Position(x, y);
            }

            @Override
            public String getName() {
                return this.name;
            }

            @Override
            public int getDuration() {
                return this.duration;
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
    public PickablePowerUp reproductionRangeBoost(Position spawnPosition, int duration, double boost) {
        return new PickablePowerUp() {
            private final String name = "Reproduction Range";
            private final int duration = 30; 
            private double x = spawnPosition.x();
            private double y = spawnPosition.y();

            @Override
            public Position getPosition() {
                return new Position(x, y);
            }

            @Override
            public String getName() {
                return this.name;
            }

            @Override
            public int getDuration() {
                return this.duration;
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
