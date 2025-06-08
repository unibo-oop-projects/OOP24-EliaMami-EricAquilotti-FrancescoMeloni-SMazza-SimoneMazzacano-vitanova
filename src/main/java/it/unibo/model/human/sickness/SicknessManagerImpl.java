package it.unibo.model.human.sickness;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import it.unibo.model.effect.Effect;
import it.unibo.model.effect.EffectFactoryImpl;
import it.unibo.model.effect.EffectType;
import it.unibo.model.human.Human;
import it.unibo.view.sprite.HumanType;

/**
 * Implementation of the SicknessManager interface.
 * This class is responsible for managing the sickness status of humans during a chapter.
 */
public final class SicknessManagerImpl implements SicknessManager {
    private static final double SICKNESS_MULTIPLIER = .5;
    private static final double POPULATION_SIZE_REACH = .25;
    private static final Duration DEFAULT_SICKNESS_DURATION = Duration.ofSeconds(20);
    private final Duration sicknessDuration;
    private final EffectFactoryImpl effectFactory;
    private final Map<Human, List<Effect>> sickHumans = new HashMap<>();
    private final int populationGoal;

    /**
     * Constructor to initialize SicknessManagerImpl.
     * @param effectFactory the effect factory used to create effects.
     * @param populationGoal the population goal of the managed chapter. Humans will get sick only if the population
     * size reaches a fixed percentage of the population goal.
     */
    public SicknessManagerImpl(final EffectFactoryImpl effectFactory, final int populationGoal) {
        this(effectFactory, populationGoal, DEFAULT_SICKNESS_DURATION);
    }

    /**
     * Constructor to initialize SicknessManagerImpl.
     * @param effectFactory the effect factory used to create effects.
     * @param populationGoal the population goal of the managed chapter. Humans will get sick only if the population
     * size reaches a fixed percentage of the population goal.
     * @param sicknessDuration the sickness duration
     */
    public SicknessManagerImpl(final EffectFactoryImpl effectFactory, final int populationGoal, final Duration sicknessDuration) {
        this.effectFactory = effectFactory;
        this.populationGoal = populationGoal;
        this.sicknessDuration = sicknessDuration;
    }

    @Override
    public void applyToPlayer(final Human player, final int currentPopulationSize) {
        if (shouldHumansGetSick(currentPopulationSize)
            && player.getType() == HumanType.PLAYER
            && !player.getStats().hasBeenSick()
            && Math.random() >= player.getStats().getSicknessResistence()) {
            applyToHuman(player);
        }
    }

    private List<Effect> createEffects() {
        return List.of(
            effectFactory.createEffect(EffectType.SPEED, sicknessDuration, SICKNESS_MULTIPLIER),
            effectFactory.createEffect(EffectType.REPRODUCTION_RANGE, sicknessDuration, SICKNESS_MULTIPLIER),
            effectFactory.createEffect(EffectType.FERTILITY, sicknessDuration, SICKNESS_MULTIPLIER)
            // effectFactory.speedEffect(sicknessDuration, SICKNESS_MULTIPLIER),
            // effectFactory.reproductionRangeEffect(sicknessDuration, SICKNESS_MULTIPLIER),
            // effectFactory.fertilityEffect(sicknessDuration, SICKNESS_MULTIPLIER)
        );
    }

    /**
     * Applies sickness to a human. Human can get sick only once.
     * @param human the human to apply sickness to.
     */
    private void applyToHuman(final Human human) {
        if (!human.getStats().hasBeenSick()) {
            human.getStats().setSickness(true);
            final List<Effect> effectsToApply = createEffects();
            effectsToApply.forEach(effect -> {
                effect.activate();
                human.getStats().applyEffect(effect);
            });
            sickHumans.put(human, effectsToApply);
        }
    }

    private void removeFromHuman(final Human human) {
        if (!human.getStats().isSick()) {
            return;
        }
        human.getStats().setSickness(false);
        final List<Effect> appliedEffects = sickHumans.get(human);
        appliedEffects.stream().forEach(effect -> human.getStats().resetEffect(effect));
        sickHumans.remove(human);
    }

    @Override
    public void checkStatus(final Human human) {
        if (!human.getStats().isSick()) {
            return;
        }
        final List<Effect> appliedEffects = sickHumans.get(human);
        if (appliedEffects.stream().anyMatch(Effect::isExpired)) {
            removeFromHuman(human);
        }
    }

    /**
     * This method should be called when a human is born.
     * If one of the parents is sick, the child and the other parent will be sick too.
     */
    @Override
    public void solveSpread(final Human male, final Human female, final Human child, final int currentPopulationSize) {
        if (shouldHumansGetSick(currentPopulationSize)
            && (male.getType() == HumanType.MALE || male.getType() == HumanType.PLAYER)
            && (female.getType() == HumanType.FEMALE)) {
                final BiConsumer<Human, Human> checkTransferSickness = (sender, receiver) -> {
                    if (sender.getStats().isSick() && !receiver.getStats().hasBeenSick()) {
                        applyToHuman(receiver);
                    }
                };
                checkTransferSickness.accept(male, female);
                checkTransferSickness.accept(female, male);
                checkTransferSickness.accept(male, child);
                checkTransferSickness.accept(female, child);
        }
    }

    private boolean shouldHumansGetSick(final int currentPopulation) {
        return currentPopulation >= POPULATION_SIZE_REACH * populationGoal;
    }
}

