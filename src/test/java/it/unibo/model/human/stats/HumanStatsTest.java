package it.unibo.model.human.stats;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.common.Position;
import it.unibo.model.effect.Effect;
import it.unibo.model.effect.EffectFactory;
import it.unibo.model.effect.EffectFactoryImpl;
import it.unibo.model.human.strategies.reproduction.ReproStrategy;
import it.unibo.model.human.strategies.reproduction.ReproStrategyFactory;
import it.unibo.model.human.strategies.reproduction.ReproStrategyFactoryImpl;
import it.unibo.utils.MutableClock;

public class HumanStatsTest {
    private static final MutableClock MUTABLE_CLOCK = new MutableClock(Instant.now(), ZoneId.systemDefault());
    private static final ReproStrategyFactory REPRODUCTION_STRATEGY_FACTORY = new ReproStrategyFactoryImpl(MUTABLE_CLOCK);
    private static final EffectFactory EFFECT_FACTORY = new EffectFactoryImpl(MUTABLE_CLOCK);
    private ReproStrategy reproStrategy;
    private HumanStats stats;
    private Effect speed;
    private Effect sicknessResistence;
    private Effect reproductionRange;
    private Effect fertility;

    @BeforeEach
    void setUp() {
        reproStrategy = REPRODUCTION_STRATEGY_FACTORY.maleReproStrategy(new Position(0, 0));
        stats = new HumanStatsImpl(1, 1, 1, reproStrategy);
        speed = EFFECT_FACTORY.speedEffect(Duration.ofSeconds(100), 1.25);
        sicknessResistence = EFFECT_FACTORY.sicknessResistenceEffect(Duration.ofSeconds(100), 1.25);
        reproductionRange = EFFECT_FACTORY.reproductionRangeEffect(Duration.ofSeconds(100), 1.25);
        fertility = EFFECT_FACTORY.fertilityEffect(Duration.ofSeconds(100), 1.25);
    }

    @Test
    void testInitialValues() {
        assertEquals(1, stats.getSpeed());
        assertEquals(1, stats.getSicknessResistence());
        assertEquals(1, stats.getFertility());
        assertEquals(16, stats.getReproductionAreaRadius().getRadius());
    }

    @Test
    void testApplyEffects() {
        stats.applyEffect(speed);
        assertEquals(1.25, stats.getSpeed());
        stats.applyEffect(sicknessResistence);
        assertEquals(1.25, stats.getSicknessResistence());
        stats.applyEffect(fertility);
        assertEquals(1.25, stats.getFertility());
        stats.applyEffect(reproductionRange);
        assertEquals(20, stats.getReproductionAreaRadius().getRadius());
    }

    @Test 
    void testResetEffects() {
        stats.applyEffect(speed);
        stats.resetEffect(speed);
        assertEquals(1, stats.getSpeed());
        stats.applyEffect(sicknessResistence);
        stats.resetEffect(sicknessResistence);
        assertEquals(1, stats.getSicknessResistence());
        stats.applyEffect(reproductionRange);
        stats.resetEffect(reproductionRange);
        assertEquals(16.0, stats.getReproductionAreaRadius().getRadius());
        stats.applyEffect(fertility);
        stats.resetEffect(fertility);
        assertEquals(1, stats.getFertility());
    }

    @Test
    void testResetAllEffects() {
        stats.applyEffect(speed);
        stats.applyEffect(sicknessResistence);
        stats.applyEffect(reproductionRange);
        stats.applyEffect(fertility);
        stats.resetAllEffect();
        assertEquals(1, stats.getSpeed());
        assertEquals(1, stats.getSicknessResistence());
        assertEquals(16, stats.getReproductionAreaRadius().getRadius());
        assertEquals(1, stats.getFertility());
    }

    @Test
    void testIncreaseStats() {
        stats.increaseSpeed();
        stats.increaseSicknessResistence();
        stats.increaseReproductionAreaRadius();
        stats.increaseFertility();
        assertTrue(stats.getSpeed() > 1);
        assertTrue(stats.getSicknessResistence() > 1);
        assertTrue(stats.getReproductionAreaRadius().getRadius() > 16);
        assertTrue(stats.getFertility() > 1);
    }

    @Test 
    void testBuilderWithUpgrade() {
        stats = new HumanStatsImpl(1, 1, 1, reproStrategy, List.of(5, 5, 5, 5));
        assertTrue(stats.getSpeed() > 1);
        assertTrue(stats.getSicknessResistence() > 1);
        assertTrue(stats.getReproductionAreaRadius().getRadius() > 16);
        assertTrue(stats.getFertility() > 1);
    }
}
