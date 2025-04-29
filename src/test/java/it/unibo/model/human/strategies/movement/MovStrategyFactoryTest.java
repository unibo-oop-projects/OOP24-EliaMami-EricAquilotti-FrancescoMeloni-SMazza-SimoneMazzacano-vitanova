package it.unibo.model.human.strategies.movement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.event.KeyEvent;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;

import javax.swing.JPanel;

import org.junit.jupiter.api.Test;

import it.unibo.common.Direction;
import it.unibo.common.PausableClock;
import it.unibo.controller.InputHandler;
import it.unibo.controller.InputHandlerImpl;
import it.unibo.utils.MutableClock;

class MovStrategyFactoryTest {
    private static final long MAX_COOLDOWN_MILLIS = 3000;
    private final InputHandler inputHandler = new InputHandlerImpl();
    private final JPanel dummyPanel = new JPanel();
    private final MutableClock mutableClock = new MutableClock(Instant.now(), ZoneId.systemDefault());

    @Test
    void testUserInputMovementReturnsCurrentInputDirection() {
        final PausableClock pausableClock = new PausableClock(mutableClock);
        final MovStrategyFactory factory = new MovStrategyFactoryImpl(pausableClock);
        final MovStrategy strategy = factory.userInputMovement(inputHandler);

        final Direction up = new Direction(true, false, false, false);
        inputHandler.keyPressed(getKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_UP));
        assertEquals(up, strategy.nextDirection());
        inputHandler.keyReleased(getKeyEvent(KeyEvent.KEY_RELEASED, KeyEvent.VK_UP));

        final Direction right = new Direction(false, true, false, false);
        inputHandler.keyPressed(getKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_RIGHT));
        assertEquals(right, strategy.nextDirection());
        inputHandler.keyReleased(getKeyEvent(KeyEvent.KEY_RELEASED, KeyEvent.VK_RIGHT));

        final Direction down = new Direction(false, false, true, false);
        inputHandler.keyPressed(getKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_DOWN));
        assertEquals(down, strategy.nextDirection());
        inputHandler.keyReleased(getKeyEvent(KeyEvent.KEY_RELEASED, KeyEvent.VK_DOWN));

        final Direction left = new Direction(false, false, false, true);
        inputHandler.keyPressed(getKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_LEFT));
        assertEquals(left, strategy.nextDirection());
        inputHandler.keyReleased(getKeyEvent(KeyEvent.KEY_RELEASED, KeyEvent.VK_LEFT));
    }

    @Test
    void testRandomMovementDirectionStaysBeforeCooldown() {
        final PausableClock pausableClock = new PausableClock(mutableClock);
        final MovStrategyFactory factory = new MovStrategyFactoryImpl(pausableClock);
        final MovStrategy strategy = factory.randomMovement();

        final Direction first = strategy.nextDirection();
        // Advance less than minimum cooldown
        mutableClock.advance(Duration.ofMillis(100));
        final Direction second = strategy.nextDirection();

        assertEquals(first, second, "Direction should not change before cooldown");
    }

    @Test
    void testRandomMovementDirectionEventuallyChangesAfterCooldown() {
        final PausableClock pausableClock = new PausableClock(mutableClock);
        final MovStrategyFactory factory = new MovStrategyFactoryImpl(pausableClock);
        final MovStrategy strategy = factory.randomMovement();

        assertTrue(randomMovementDirectionChanged(strategy, 100),
            "Direction should eventually change after multiple cooldowns");
    }

    @Test
    void testNoRandomMovementWhenPaused() {
        final PausableClock pausableClock = new PausableClock(mutableClock);
        final MovStrategyFactory factory = new MovStrategyFactoryImpl(pausableClock);
        final MovStrategy strategy = factory.randomMovement();
        pausableClock.pause();
        assertFalse(randomMovementDirectionChanged(strategy, 100),
            "Direction should not change because of the pause");
    }

    private KeyEvent getKeyEvent(final int id, final int keycode) {
        return new KeyEvent(dummyPanel, id, System.currentTimeMillis(), 0,
                            keycode, KeyEvent.CHAR_UNDEFINED);
    }

    private boolean randomMovementDirectionChanged(final MovStrategy strategy, final int iterations) {
        final Direction original = strategy.nextDirection();
        mutableClock.advance(Duration.ofMillis(MAX_COOLDOWN_MILLIS));

        boolean changed = false;
        // Try several times in case of repeat. Chances are 1/16 each time.
        for (int i = 0; i < iterations; i++) {
            final Direction next = strategy.nextDirection();
            if (!next.equals(original)) {
                changed = true;
                break;
            }
            mutableClock.advance(Duration.ofMillis(MAX_COOLDOWN_MILLIS));
        }
        return changed;
    }
}
