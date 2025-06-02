package it.unibo.model.effect;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.utils.MutableClock;

public class EffectTest {
    private static final MutableClock MUTABLE_CLOCK = new MutableClock(Instant.now(), ZoneId.systemDefault());
    private static final EffectFactory EFFECT_FACTORY = new EffectFactoryImpl(MUTABLE_CLOCK);
    private Effect speed;
    private Effect sicknessResistence;
    private Effect reproductionRange;
    private Effect fertility;

    @BeforeEach
    void setUp() {
        speed = EFFECT_FACTORY.speedEffect(Duration.ofSeconds(5), 1);
        sicknessResistence = EFFECT_FACTORY.sicknessResistenceEffect(Duration.ofSeconds(5), 1);
        reproductionRange = EFFECT_FACTORY.reproductionRangeEffect(Duration.ofSeconds(5), 1);
        fertility = EFFECT_FACTORY.fertilityEffect(Duration.ofSeconds(5), 1);
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
        assertEquals(speed.getDuration(), Duration.ofSeconds(5));
        assertEquals(sicknessResistence.getDuration(), Duration.ofSeconds(5));
        assertEquals(reproductionRange.getDuration(), Duration.ofSeconds(5));
        assertEquals(fertility.getDuration(), Duration.ofSeconds(5));
    }

    @Test
    void testActivate() {
        speed.activate();
        sicknessResistence.activate();
        reproductionRange.activate();
        fertility.activate();
        assertTrue(!speed.isExpired());
        assertTrue(!sicknessResistence.isExpired());
        assertTrue(!reproductionRange.isExpired());
        assertTrue(!fertility.isExpired());
    }

    @Test
    void testIsExpired() {
        speed.activate();
        sicknessResistence.activate();
        reproductionRange.activate();
        fertility.activate();
        MUTABLE_CLOCK.advance(Duration.ofSeconds(5));
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
        MUTABLE_CLOCK.advance(Duration.ofSeconds(5));
        speed.refresh();
        sicknessResistence.refresh();
        reproductionRange.refresh();
        fertility.refresh();
        assertTrue(!speed.isExpired());
        assertTrue(!sicknessResistence.isExpired());
        assertTrue(!reproductionRange.isExpired());
        assertTrue(!fertility.isExpired());
    }
}
