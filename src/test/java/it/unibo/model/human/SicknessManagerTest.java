package it.unibo.model.human;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;

import it.unibo.model.effect.EffectFactoryImpl;
import it.unibo.model.human.sickness.SicknessManager;
import it.unibo.model.human.sickness.SicknessManagerImpl;
import it.unibo.model.human.stats.HumanStats;
import it.unibo.model.human.stats.HumanStatsImpl;
import it.unibo.utils.HumanMockup;
import it.unibo.utils.MutableClock;
import it.unibo.view.sprite.HumanType;

/**
 * Test for SicknessManager.
 */
class SicknessManagerTest {
    private static final int POPULATION_GOAL = 100;
    private final MutableClock mutableClock = new MutableClock(Instant.now(), ZoneId.systemDefault());
    private final SicknessManager manager = new SicknessManagerImpl(
        new EffectFactoryImpl(mutableClock), POPULATION_GOAL, Duration.ofSeconds(2));

    @Test
    void testSicknessEffects() {
        final Human male = HumanMockup.createHuman(HumanType.MALE);
        final Human female = HumanMockup.createSickHuman(HumanType.FEMALE);
        final Human child = HumanMockup.createHuman(HumanType.MALE);
        final HumanStats initialMaleStats = new HumanStatsImpl(
            male.getStats().getSpeed(), male.getStats().getSicknessResistence(),
            male.getStats().getFertility(), male.getStats().getReproStrategy());
        manager.solveSpread(male, female, child, POPULATION_GOAL);

        assertTrue(male.getStats().isSick(), "Player should be sick");
        assertTrue(male.getStats().getSpeed() < initialMaleStats.getSpeed(), "Player speed should have decreased");
        assertTrue(male.getStats().getFertility() < initialMaleStats.getFertility(), "Player fertility should have decreased");
        assertTrue(
            male.getStats().getReproductionCircle().getRadius() < initialMaleStats.getReproductionCircle().getRadius(),
            "Player reproduction area radius should have decreased"
        );
    }

    @Test
    void testSolveSpreadWithSickParent() {
        final Human male = HumanMockup.createSickHuman(HumanType.MALE);
        final Human female = HumanMockup.createHuman(HumanType.FEMALE);
        final Human child = HumanMockup.createHuman(HumanType.MALE);
        manager.solveSpread(male, female, child, POPULATION_GOAL);

        assertTrue(female.getStats().isSick(), "Female should be sick after reproduction with sick male");
        assertTrue(child.getStats().isSick(), "Child should be sick after borning from sick parents");
        assertTrue(female.getStats().isSick(), "Male should be still sick");
    }

    @Test
    void testSolveSpreadWithoutSickParent() {
        final Human male = HumanMockup.createHuman(HumanType.MALE);
        final Human female = HumanMockup.createHuman(HumanType.FEMALE);
        final Human child = HumanMockup.createHuman(HumanType.MALE);
        manager.solveSpread(male, female, child, POPULATION_GOAL);

        assertFalse(female.getStats().isSick(), "Female shouldn't be sick after reproduction with healty male");
        assertFalse(child.getStats().isSick(), "Child shouldn't be sick after borning from healthy parents");
        assertFalse(female.getStats().isSick(), "Male should be still healty");
    }

    @Test
    void testCheckStatus() {
        final Human male = HumanMockup.createSickHuman(HumanType.MALE);
        final Human female = HumanMockup.createHuman(HumanType.FEMALE);
        final Human child = HumanMockup.createHuman(HumanType.MALE);
        final HumanStats initialFemaleStats = new HumanStatsImpl(
        female.getStats().getSpeed(), female.getStats().getSicknessResistence(),
        female.getStats().getFertility(), female.getStats().getReproStrategy());
        manager.solveSpread(male, female, child, POPULATION_GOAL);

        manager.checkStatus(female);
        assertTrue(female.getStats().isSick(), "Female should be sick right after reproduction");

        mutableClock.advance(Duration.ofSeconds(2));
        manager.checkStatus(female);
        assertFalse(female.getStats().isSick(), "Female should be not be sick after sickness duration elapsed");
        assertEquals(initialFemaleStats.getSpeed(), female.getStats().getSpeed(), "Female speed should be restored");
        assertEquals(initialFemaleStats.getFertility(), female.getStats().getFertility(), "Female fertility should be restored");
        assertEquals(
            initialFemaleStats.getReproductionCircle().getRadius(), female.getStats().getReproductionCircle().getRadius(),
            "Female reproduction area radius should be restored"
        );
    }
}
