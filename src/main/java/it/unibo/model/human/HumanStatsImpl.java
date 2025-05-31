package it.unibo.model.human;

import java.util.List;

import it.unibo.common.Circle;
import it.unibo.common.CircleImpl;
import it.unibo.model.effect.Effect;
import it.unibo.model.effect.EffectType;
import it.unibo.model.human.strategies.reproduction.ReproStrategy;

/**
 * Implementation of human stats that handles all human's stats.
 */
public final class HumanStatsImpl implements HumanStats {
    private static final double SPEED_UPGRADE_VALUE = .5;
    private static final double SICKNESS_RESISTENCE_UPGRADE_VALUE = .05;
    private static final double FERTILITY_UPGRADE_VALUE = .05;
    private static final int RADIUS_UPGRADE_VALUE = 4;
    private static final int MAX_SPEED_UPGRADE = 5;
    private static final int MAX_SICKNESS_RESISTENCE_UPGRADE = 5;
    private static final int MAX_FERTILITY_UPGRADE = 5;
    private static final int MAX_REPRODUCTION_RANGE_UPGRADE = 5;
    private double baseSpeed;
    private double actualSpeed;
    private double baseSicknessResistence;
    private double actualSicknessResistence;
    private double baseFertility;
    private double actualFertility;
    private boolean hasBeenSick;
    private boolean isSick;
    private transient ReproStrategy reproStrategy;
    private double baseRadius;
    private double actualRadius;
    private int speedUpgrade;
    private int sicknessResistenceUpgrade;
    private int fertilityUpgrade;
    private int reproductionRangeUpgrade;

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
        return this.actualSpeed;
    }

    private void setSpeed(final double newSpeed) {
        this.baseSpeed = newSpeed;
        this.actualSpeed = newSpeed;
    }

    @Override
    public void increaseSpeed() {
        if (this.speedUpgrade < MAX_SPEED_UPGRADE) {
            setSpeed(this.baseSpeed + SPEED_UPGRADE_VALUE);
            this.speedUpgrade += 1;
        }
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
            this.actualRadius
        );
    }

    @Override
    public double getBaseRadius() {
        return this.baseRadius;
    }

    @Override
    public void setReproStrategy(final ReproStrategy newReproStrategy) {
        this.reproStrategy = newReproStrategy;
        this.baseRadius = reproStrategy.getReproductionArea().getRadius();
        this.actualRadius = this.baseRadius;
    }

    private void setReproductionAreaRadius(final double newRadius) {
        getReproStrategy().changeReproductionArea(newRadius);
        this.baseRadius = reproStrategy.getReproductionArea().getRadius();
        this.actualRadius = this.baseRadius;
    }

    @Override
    public void increaseReproductionAreaRadius() {
        if (this.reproductionRangeUpgrade < MAX_REPRODUCTION_RANGE_UPGRADE) {
            setReproductionAreaRadius(getReproStrategy().getReproductionArea().getRadius() + RADIUS_UPGRADE_VALUE);
            this.reproductionRangeUpgrade += 1;
        }
    }

    @Override
    public double getSicknessResistence() {
        return this.actualSicknessResistence;
    }

    private void setSicknessResistence(final double newSicknessResistence) {
        this.baseSicknessResistence = newSicknessResistence;
        this.actualSicknessResistence = newSicknessResistence;
    }

    @Override
    public void increaseSicknessResistence() {
        if (this.sicknessResistenceUpgrade < MAX_SICKNESS_RESISTENCE_UPGRADE) {
            setSicknessResistence(this.baseSicknessResistence + SICKNESS_RESISTENCE_UPGRADE_VALUE);
            this.sicknessResistenceUpgrade += 1;
        }
    }

    @Override
    public double getFertility() {
        return this.actualFertility;
    }

    private void setFertility(final double newFertility) {
        this.baseFertility = newFertility;
        this.actualFertility = newFertility;
    }

    @Override
    public void increaseFertility() {
        if (this.fertilityUpgrade < MAX_FERTILITY_UPGRADE) {
            setFertility(this.baseFertility + FERTILITY_UPGRADE_VALUE);
            this.fertilityUpgrade += 1;
        }
    }

    @Override
    public void resetEffect(final EffectType type) {
        switch (type) {
            case SPEED:
                this.actualSpeed = this.baseSpeed;
                break;
            case SICKNESS_RESISTENCE:
                this.actualSicknessResistence = this.baseSicknessResistence;
                break;
            case REPRODUCTION_RANGE:
                this.actualRadius = this.baseRadius;
                break;
            default:
                this.actualFertility = this.baseFertility;
                break;
        }
    }

    @Override
    public void resetAllEffect() {
        setSickness(false);
        this.hasBeenSick = false;
        this.actualSpeed = this.baseSpeed;
        this.actualSicknessResistence = this.baseSicknessResistence;
        this.actualRadius = this.baseRadius;
        this.actualFertility = this.baseFertility;
    }

    @Override
    public int getActualSpeedUpgrade() {
        return speedUpgrade;
    }

    @Override
    public int getActualSicknessResistenceUpgrade() {
        return sicknessResistenceUpgrade;
    }

    @Override
    public int getActualReproductionRangeUpgrade() {
        return reproductionRangeUpgrade;
    }

    @Override
    public int getActualFertilityUpgrade() {
        return fertilityUpgrade;
    }

    @Override
    public int getMaxSpeedUpgrade() {
        return MAX_SPEED_UPGRADE;
    }

    @Override
    public int getMaxSicknessResistenceUpgrade() {
        return MAX_SICKNESS_RESISTENCE_UPGRADE;
    }

    @Override
    public int getMaxReproductionRangeUpgrade() {
        return MAX_REPRODUCTION_RANGE_UPGRADE;
    }

    @Override
    public int getMaxFertilityUpgrade() {
        return MAX_FERTILITY_UPGRADE;
    }

    @Override
    public void applyEffect(final Effect effect) {
        switch (effect.getType()) {
            case SPEED:
                this.actualSpeed = this.baseSpeed * effect.getMultiplyValue();
                break;
            case SICKNESS_RESISTENCE:
                this.actualSicknessResistence = this.baseSicknessResistence * effect.getMultiplyValue();
                break;
            case REPRODUCTION_RANGE:
                this.actualRadius = this.baseRadius * effect.getMultiplyValue();
                break;
            default:
                this.actualFertility = this.baseFertility * effect.getMultiplyValue();
                break;
        }
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
