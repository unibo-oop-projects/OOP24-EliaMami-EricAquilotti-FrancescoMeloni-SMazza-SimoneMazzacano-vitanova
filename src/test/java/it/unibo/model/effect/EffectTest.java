package it.unibo.model.effect;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.utils.MutableClock;

class EffectTest {
    private static final MutableClock MUTABLE_CLOCK = new MutableClock(Instant.now(), ZoneId.systemDefault());
    private static final EffectFactory EFFECT_FACTORY = new EffectFactoryImpl(MUTABLE_CLOCK);
    private static final Duration EFFECT_DURATION = Duration.ofSeconds(5);
    private Effect speed;
    private Effect sicknessResistence;
    private Effect reproductionRange;
    private Effect fertility;

    @BeforeEach
    void createffect() {
        speed = EFFECT_FACTORY.createEffect(EffectType.SPEED, EFFECT_DURATION, 1);
        sicknessResistence = EFFECT_FACTORY.createEffect(EffectType.SICKNESS_RESISTENCE, EFFECT_DURATION, 1);
        reproductionRange = EFFECT_FACTORY.createEffect(EffectType.REPRODUCTION_RANGE, EFFECT_DURATION, 1);
        fertility = EFFECT_FACTORY.createEffect(EffectType.FERTILITY, EFFECT_DURATION, 1);
    }

    @Test
    void testInitialValue() {
        assertEquals(speed.getType(), EffectType.SPEED);
        assertEquals(sicknessResistence.getType(), EffectType.SICKNESS_RESISTENCE);
        assertEquals(reproductionRange.getType(), EffectType.REPRODUCTION_RANGE);
        assertEquals(fertility.getType(), EffectType.FERTILITY);
        assertEquals(speed.getMultiplyValue(), 1);
        assertEquals(sicknessResistence.getMultiplyValue(), 1);
        assertEquals(reproductionRange.getMultiplyValue(), 1);
        assertEquals(fertility.getMultiplyValue(), 1);
        assertEquals(speed.getDuration(), EFFECT_DURATION);
        assertEquals(sicknessResistence.getDuration(), EFFECT_DURATION);
        assertEquals(reproductionRange.getDuration(), EFFECT_DURATION);
        assertEquals(fertility.getDuration(), EFFECT_DURATION);
    }

    @Test
    void testActivate() {
        speed.activate();
        sicknessResistence.activate();
        reproductionRange.activate();
        fertility.activate();
        assertFalse(speed.isExpired());
        assertFalse(sicknessResistence.isExpired());
        assertFalse(reproductionRange.isExpired());
        assertFalse(fertility.isExpired());
    }

    @Test
    void testIsExpired() {
        speed.activate();
        sicknessResistence.activate();
        reproductionRange.activate();
        fertility.activate();
        MUTABLE_CLOCK.advance(EFFECT_DURATION);
        assertTrue(speed.isExpired());
        assertTrue(sicknessResistence.isExpired());
        assertTrue(reproductionRange.isExpired());
        assertTrue(fertility.isExpired());
    }

    @Test 
    void testRefresh() {
        speed.activate();
        sicknessResistence.activate();
        reproductionRange.activate();
        fertility.activate();
        MUTABLE_CLOCK.advance(EFFECT_DURATION);
        speed.refresh();
        sicknessResistence.refresh();
        reproductionRange.refresh();
        fertility.refresh();
        assertFalse(speed.isExpired());
        assertFalse(sicknessResistence.isExpired());
        assertFalse(reproductionRange.isExpired());
        assertFalse(fertility.isExpired());
    }
}
