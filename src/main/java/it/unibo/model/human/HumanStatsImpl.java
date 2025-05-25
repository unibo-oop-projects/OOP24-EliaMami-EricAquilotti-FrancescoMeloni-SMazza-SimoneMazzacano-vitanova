package it.unibo.model.human;

import java.io.Serializable;

import it.unibo.common.Circle;
import it.unibo.common.CircleImpl;
import it.unibo.model.effect.Effect;
import it.unibo.model.effect.EffectType;
import it.unibo.model.human.strategies.reproduction.ReproStrategy;

/**
 * Implementation of human stats that handles all human's stats.
 */
public final class HumanStatsImpl implements HumanStats, Serializable {
    private static final long serialVersionUID = 1L;
    private static final double SPEED_UPGRADE_VALUE = .5;
    private static final double SICKNESS_RESISTENCE_UPGRADE_VALUE = .05;
    private static final double FERTILITY_UPGRADE_VALUE = .05;
    private static final int RADIUS_UPGRADE_VALUE = 5;
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
        setSpeed(this.speedUpgrade < MAX_SPEED_UPGRADE 
        ? this.baseSpeed + SPEED_UPGRADE_VALUE 
        : this.baseSpeed);
        this.speedUpgrade = this.speedUpgrade < MAX_SPEED_UPGRADE
        ? this.speedUpgrade + 1
        : this.speedUpgrade;
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

    private void setReproStrategy(final ReproStrategy newReproStrategy) {
        this.reproStrategy = newReproStrategy;
        this.baseRadius = reproStrategy.getReproductionArea().getRadius();
        this.actualRadius = this.baseRadius;
    }

    private void setReproductionAreaRadius(final double newRadius) {
        getReproStrategy().changeReproductionArea(newRadius);
    }

    @Override
    public void increaseReproductionAreaRadius() {
        setReproductionAreaRadius(this.reproductionRangeUpgrade < MAX_REPRODUCTION_RANGE_UPGRADE 
        ? getReproStrategy().getReproductionArea().getRadius() + RADIUS_UPGRADE_VALUE 
        : getReproStrategy().getReproductionArea().getRadius());
        this.reproductionRangeUpgrade = this.reproductionRangeUpgrade < MAX_REPRODUCTION_RANGE_UPGRADE
        ? this.reproductionRangeUpgrade + 1
        : this.reproductionRangeUpgrade;
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
        setSicknessResistence(this.sicknessResistenceUpgrade < MAX_SICKNESS_RESISTENCE_UPGRADE 
        ? this.baseSicknessResistence + SICKNESS_RESISTENCE_UPGRADE_VALUE 
        : this.baseSicknessResistence);
        this.sicknessResistenceUpgrade = this.sicknessResistenceUpgrade < MAX_SICKNESS_RESISTENCE_UPGRADE
        ? this.sicknessResistenceUpgrade + 1
        : this.sicknessResistenceUpgrade;
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
        setFertility(this.fertilityUpgrade < MAX_FERTILITY_UPGRADE 
        ? this.baseFertility + FERTILITY_UPGRADE_VALUE 
        : this.baseFertility);
        this.fertilityUpgrade = this.fertilityUpgrade < MAX_FERTILITY_UPGRADE
        ? this.fertilityUpgrade + 1
        : this.fertilityUpgrade;
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
