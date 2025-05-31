package it.unibo.model.human.stats;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import it.unibo.common.Circle;
import it.unibo.common.CircleImpl;
import it.unibo.model.effect.Effect;
import it.unibo.model.human.strategies.reproduction.ReproStrategy;

/**
 * Implementation of human stats that handles all human's stats.
 */
public final class HumanStatsImpl implements HumanStats {
    private static final double SPEED_UPGRADE_VALUE = .5;
    private static final double SICKNESS_RESISTENCE_UPGRADE_VALUE = .05;
    private static final double FERTILITY_UPGRADE_VALUE = .05;
    private static final int RADIUS_UPGRADE_VALUE = 4;
    private ReproStrategy reproStrategy;
    private double speed;
    private double sicknessResistence;
    private double reproductionRadius;
    private double fertility;
    private int speedUpgrade;
    private int sicknessResistenceUpgrade;
    private int reproductionRangeUpgrade;
    private int fertilityUpgrade;
    private boolean hasBeenSick;
    private boolean isSick;
    private final List<Effect> activatedEffects = new CopyOnWriteArrayList<>();

    /**
     * Constructor for human stats.
     * @param speed to inizialize speed value.
     * @param sicknessResistence to inizialize sickness resistence value.
     * @param fertility to inizialize fertility value.
     * @param reproStrategy to inizialize reproduction area value.
     */
    public HumanStatsImpl(
        final double speed, final double sicknessResistence, final double fertility, final ReproStrategy reproStrategy) {
        setSpeed(speed);
        setSicknessResistence(sicknessResistence);
        setFertility(fertility);
        setReproStrategy(reproStrategy); 
    }

    /**
     * Constructor for human stats.
     * @param speed to inizialize speed value.
     * @param sicknessResistence to inizialize sickness resistence value.
     * @param fertility to inizialize fertility value.
     * @param reproStrategy to inizialize reproduction area value.
     * @param upgrade where index 0 refers to speedUpgrade, 
     * index 1 to sicknessResistenceUpgrade, index 2 to reproductionRangeUpgrade and index 3 to fertilityUpgrade.
     */
    public HumanStatsImpl(
        final double speed, final double sicknessResistence, final double fertility, 
        final ReproStrategy reproStrategy, final List<Integer> upgrade) {
        setSpeedUpgrade(upgrade.get(0));
        setSicknessResistenceUpgrade(upgrade.get(1));
        setReproductionRangeUpgrade(upgrade.get(2));
        setFertilityUpgrade(upgrade.get(3));
        setSpeed(speed + speedUpgrade * SPEED_UPGRADE_VALUE);
        setSicknessResistence(sicknessResistence + sicknessResistenceUpgrade * SICKNESS_RESISTENCE_UPGRADE_VALUE);
        setFertility(fertility + fertilityUpgrade * FERTILITY_UPGRADE_VALUE);
        setReproStrategy(reproStrategy);
        setReproductionAreaRadius(getReproductionAreaRadius().getRadius() + reproductionRangeUpgrade * RADIUS_UPGRADE_VALUE);
    }

    private void setSpeedUpgrade(final int value) {
        this.speedUpgrade = value;
    }

    private void setSicknessResistenceUpgrade(final int value) {
        this.sicknessResistenceUpgrade = value;
    }

    private void setReproductionRangeUpgrade(final int value) {
        this.reproductionRangeUpgrade = value;
    }

    private void setFertilityUpgrade(final int value) {
        this.fertilityUpgrade = value;
    }

    @Override
    public double getSpeed() {
        return this.speed;
    }

    private void setSpeed(final double newSpeed) {
        this.speed = newSpeed;
    }

    @Override
    public void increaseSpeed() {
        setSpeed(this.speed + SPEED_UPGRADE_VALUE);
        this.speedUpgrade += 1;
    }

    @Override
    public ReproStrategy getReproStrategy() {
        return this.reproStrategy;
    }

    @Override
    public Circle getReproductionAreaRadius() {
        return new CircleImpl(
            getReproStrategy().getReproductionArea().getCenter().x(), 
            getReproStrategy().getReproductionArea().getCenter().y(), 
            this.reproductionRadius
        );
    }

    @Override
    public double getBaseRadius() {
        return this.baseRadius;
    }

    @Override
    public void setReproStrategy(final ReproStrategy newReproStrategy) {
        this.reproStrategy = newReproStrategy;
        this.reproductionRadius = reproStrategy.getReproductionArea().getRadius();
    }

    private void setReproductionAreaRadius(final double newRadius) {
        getReproStrategy().changeReproductionArea(newRadius);
        this.reproductionRadius = reproStrategy.getReproductionArea().getRadius();
    }

    @Override
    public void increaseReproductionAreaRadius() {
        setReproductionAreaRadius(getReproStrategy().getReproductionArea().getRadius() + RADIUS_UPGRADE_VALUE);
        this.reproductionRangeUpgrade += 1;
    }

    @Override
    public double getSicknessResistence() {
        return this.sicknessResistence;
    }

    private void setSicknessResistence(final double newSicknessResistence) {
        this.sicknessResistence = newSicknessResistence;
    }

    @Override
    public void increaseSicknessResistence() {
        setSicknessResistence(this.sicknessResistence + SICKNESS_RESISTENCE_UPGRADE_VALUE);
        this.sicknessResistenceUpgrade += 1;
    }

    @Override
    public double getFertility() {
        return this.fertility;
    }

    private void setFertility(final double newFertility) {
        this.fertility = newFertility;
    }

    @Override
    public void increaseFertility() {
        setFertility(this.fertility + FERTILITY_UPGRADE_VALUE);
        this.fertilityUpgrade += 1;
    }

    @Override
    public void applyEffect(final Effect effect) {
        activatedEffects.stream().filter(a -> a.getType().equals(effect.getType())).forEach(this::resetEffect);
        activatedEffects.add(effect);
        switch (effect.getType()) {
            case SPEED:
                this.speed = this.speed * effect.getMultiplyValue();
                break;
            case SICKNESS_RESISTENCE:
                this.sicknessResistence = this.sicknessResistence * effect.getMultiplyValue();
                break;
            case REPRODUCTION_RANGE:
                this.reproductionRadius = this.reproductionRadius * effect.getMultiplyValue();
                break;
            default:
                this.fertility = this.fertility * effect.getMultiplyValue();
                break;
        }
    }

    @Override
    public void resetEffect(final Effect effect) {
        activatedEffects.remove(effect);
        switch (effect.getType()) {
            case SPEED:
                this.speed = this.speed / effect.getMultiplyValue();
                break;
            case SICKNESS_RESISTENCE:
                this.sicknessResistence = this.sicknessResistence / effect.getMultiplyValue();
                break;
            case REPRODUCTION_RANGE:
                this.reproductionRadius = this.reproductionRadius / effect.getMultiplyValue();
                break;
            default:
                this.fertility = this.fertility / effect.getMultiplyValue();
                break;
        }
    }

    @Override
    public void resetAllEffect() {
        setSickness(false);
        this.hasBeenSick = false;
        activatedEffects.forEach(this::resetEffect);
    }

    @Override
    public int getSpeedUpgrade() {
        return speedUpgrade;
    }

    @Override
    public int getSicknessResistenceUpgrade() {
        return sicknessResistenceUpgrade;
    }

    @Override
    public int getReproductionRangeUpgrade() {
        return reproductionRangeUpgrade;
    }

    @Override
    public int getFertilityUpgrade() {
        return fertilityUpgrade;
    }

    @Override
    public boolean hasBeenSick() {
        return this.hasBeenSick;
    }

    @Override
    public boolean isSick() {
        return this.isSick;
    }

    @Override
    public void setSickness(final boolean isSick) {
        this.isSick = isSick;
        this.hasBeenSick = this.hasBeenSick || isSick;
    }
}
