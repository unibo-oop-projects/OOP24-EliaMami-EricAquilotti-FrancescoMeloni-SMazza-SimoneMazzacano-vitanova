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
import it.unibo.model.effect.EffectType;
import it.unibo.model.human.strategies.reproduction.ReproStrategy;
import it.unibo.model.human.strategies.reproduction.ReproStrategyFactory;
import it.unibo.model.human.strategies.reproduction.ReproStrategyFactoryImpl;
import it.unibo.utils.MutableClock;

final class HumanStatsTest {
    private static final MutableClock MUTABLE_CLOCK = new MutableClock(Instant.now(), ZoneId.systemDefault());
    private static final ReproStrategyFactory REPRODUCTION_STRATEGY_FACTORY = new ReproStrategyFactoryImpl(MUTABLE_CLOCK);
    private static final EffectFactory EFFECT_FACTORY = new EffectFactoryImpl(MUTABLE_CLOCK);
    private static final double MULTIPLY_VALUE = 1.25;
    private static final Duration EFFECT_DURATION = Duration.ofSeconds(100);
    private static final List<Integer> UPGRADE = List.of(5, 5, 5, 5);
    private static final double BASE_SPEED = 1;
    private static final double BASE_SICKNESS_RESISTENCE = 0.5;
    private static final double BASE_FERTILITY = 0.1;
    private ReproStrategy baseReproStrategy;
    private double baseRadius;
    private HumanStats stats;
    private Effect speed;
    private Effect sicknessResistence;
    private Effect reproductionRange;
    private Effect fertility;

    @BeforeEach
    void inizializeValues() {
        baseReproStrategy = REPRODUCTION_STRATEGY_FACTORY.maleReproStrategy(new Position(0, 0));
        baseRadius = baseReproStrategy.getReproductionArea().getRadius();
        stats = new HumanStatsImpl(BASE_SPEED, BASE_SICKNESS_RESISTENCE, BASE_FERTILITY, baseReproStrategy);
        speed = EFFECT_FACTORY.createEffect(EffectType.SPEED, EFFECT_DURATION, MULTIPLY_VALUE);
        sicknessResistence = EFFECT_FACTORY.createEffect(EffectType.SICKNESS_RESISTENCE, EFFECT_DURATION, MULTIPLY_VALUE);
        fertility = EFFECT_FACTORY.createEffect(EffectType.FERTILITY, EFFECT_DURATION, MULTIPLY_VALUE);
        reproductionRange = EFFECT_FACTORY.createEffect(EffectType.REPRODUCTION_RANGE, EFFECT_DURATION, MULTIPLY_VALUE);
    }

    @Test
    void testInitialValues() {
        assertEquals(BASE_SPEED, stats.getSpeed());
        assertEquals(BASE_SICKNESS_RESISTENCE, stats.getSicknessResistence());
        assertEquals(BASE_FERTILITY, stats.getFertility());
        assertEquals(baseRadius, stats.getReproductionCircle().getRadius());
    }

    @Test
    void testApplyEffects() {
        stats.applyEffect(speed);
        assertEquals(BASE_SPEED * speed.getMultiplyValue(), stats.getSpeed());
        stats.applyEffect(sicknessResistence);
        assertEquals(BASE_SICKNESS_RESISTENCE * sicknessResistence.getMultiplyValue(), stats.getSicknessResistence());
        stats.applyEffect(fertility);
        assertEquals(BASE_FERTILITY * fertility.getMultiplyValue(), stats.getFertility());
        stats.applyEffect(reproductionRange);
        assertEquals(baseRadius * reproductionRange.getMultiplyValue(), stats.getReproductionCircle().getRadius());
    }

    @Test 
    void testResetEffects() {
        stats.applyEffect(speed);
        stats.resetEffect(speed);
        assertEquals(BASE_SPEED, stats.getSpeed());
        stats.applyEffect(sicknessResistence);
        stats.resetEffect(sicknessResistence);
        assertEquals(BASE_SICKNESS_RESISTENCE, stats.getSicknessResistence());
        stats.applyEffect(fertility);
        stats.resetEffect(fertility);
        assertEquals(BASE_FERTILITY, stats.getFertility());
        stats.applyEffect(reproductionRange);
        stats.resetEffect(reproductionRange);
        assertEquals(baseRadius, stats.getReproductionCircle().getRadius());
    }

    @Test
    void testResetAllEffects() {
        stats.applyEffect(speed);
        stats.applyEffect(sicknessResistence);
        stats.applyEffect(fertility);
        stats.applyEffect(reproductionRange);
        stats.resetAllEffect();
        assertEquals(BASE_SPEED, stats.getSpeed());
        assertEquals(BASE_SICKNESS_RESISTENCE, stats.getSicknessResistence());
        assertEquals(BASE_FERTILITY, stats.getFertility());
        assertEquals(baseRadius, stats.getReproductionCircle().getRadius());
    }

    @Test
    void testIncreaseStats() {
        stats.increaseSpeed();
        stats.increaseSicknessResistence();
        stats.increaseReproductionAreaRadius();
        stats.increaseFertility();
        assertTrue(stats.getSpeed() > BASE_SPEED);
        assertTrue(stats.getSicknessResistence() > BASE_SICKNESS_RESISTENCE);
        assertTrue(stats.getFertility() > BASE_FERTILITY);
        assertTrue(stats.getReproductionCircle().getRadius() > baseRadius);
    }

    @Test 
    void testBuilderWithUpgrade() {
        stats = new HumanStatsImpl(BASE_SPEED, BASE_SICKNESS_RESISTENCE, BASE_FERTILITY, baseReproStrategy, UPGRADE);
        assertTrue(stats.getSpeed() > BASE_SPEED);
        assertTrue(stats.getSicknessResistence() > BASE_SICKNESS_RESISTENCE);
        assertTrue(stats.getFertility() > BASE_FERTILITY);
        assertTrue(stats.getReproductionCircle().getRadius() > baseRadius);
    }
}
