package it.unibo.model.human.strategies.movement;

import java.time.Clock;
import java.time.Duration;
import java.util.Random;
import java.util.function.Supplier;

import it.unibo.common.Direction;
import it.unibo.controller.InputHandler;
import it.unibo.model.human.strategies.CooldownGate;

/**
 * Implementation of a factory that produces movements' strategies for the humans.
 */
public final class MovStrategyFactoryImpl implements MovStrategyFactory {
    private static final long BASE_MILLIS_COOLDOWN = 200;
    private static final long RANGE_MILLIS_COOLDOWN = 2000;
    private final Random random = new Random();
    private final Clock clock;

    /**
     * 
     * @param clock the clock to get the current time.
     */
    public MovStrategyFactoryImpl(final Clock clock) {
        this.clock = clock;
    }

    @Override
    public MovementStrategy userInputMovement(final InputHandler inputHandler) {
        return generalised(inputHandler::getDirection);
    }

    @Override
    public MovementStrategy randomMovement() {
        final CooldownGate directionCooldown = new CooldownGate(
            () -> {
                final long millis = BASE_MILLIS_COOLDOWN + random.nextLong(RANGE_MILLIS_COOLDOWN);
                return Duration.ofMillis(millis); 
            },
            clock
        );

        final Direction[] currDirection = { randomDirection() };
        return generalised(() -> {
            if (directionCooldown.tryActivate()) {
                currDirection[0] = randomDirection();
            }
            return currDirection[0];
        });
    }

    private MovementStrategy generalised(final Supplier<Direction> directionSupplier) {
        return new MovementStrategy() {
            @Override
            public Direction nextDirection() {
                return directionSupplier.get();
            }
        };
    }

    private Direction randomDirection() {
        return new Direction(random.nextBoolean(), random.nextBoolean(),
                            random.nextBoolean(), random.nextBoolean());
    }
}
