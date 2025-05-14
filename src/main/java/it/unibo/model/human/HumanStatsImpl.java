package it.unibo.model.human;

import it.unibo.common.Circle;
import it.unibo.common.CircleImpl;
import it.unibo.model.human.strategies.reproduction.ReproStrategy;

/**
 * Implementation of human stats that handles all human's stats.
 */
public final class HumanStatsImpl implements HumanStats {
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
    //private boolean isSick = false;
    private ReproStrategy reproStrategy;
    private double baseRadius;
    private double actualRadius;
    private int actualSpeedUpgrade = 0;
    private int actualSicknessResistenceUpgrade = 0;
    private int actualFertilityUpgrade = 0;
    private int actualReproductionRangeUpgrade = 0;

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
        setSicknessResistence(this.actualSpeedUpgrade < MAX_SPEED_UPGRADE ? 
            this.baseSpeed + SPEED_UPGRADE_VALUE : 
            this.baseSpeed);
        this.actualSpeedUpgrade = this.actualSpeedUpgrade < MAX_SPEED_UPGRADE ? this.actualSpeedUpgrade + 1 : this.actualSpeedUpgrade;
    }

    @Override
    public void applySpeedModifier(final double moltiplyValue) {
        this.actualSpeed = this.baseSpeed * moltiplyValue;
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
        setReproductionAreaRadius(this.actualReproductionRangeUpgrade < MAX_REPRODUCTION_RANGE_UPGRADE ? 
            getReproStrategy().getReproductionArea().getRadius() + RADIUS_UPGRADE_VALUE : 
            getReproStrategy().getReproductionArea().getRadius());
        this.actualReproductionRangeUpgrade = this.actualReproductionRangeUpgrade < MAX_REPRODUCTION_RANGE_UPGRADE ? this.actualReproductionRangeUpgrade + 1 : this.actualReproductionRangeUpgrade;
    }

    @Override
    public void applyReproductionRangeModifier(final double moltiplyValue) {
        this.actualRadius = this.baseRadius * moltiplyValue;
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
        setSicknessResistence(this.actualSicknessResistenceUpgrade < MAX_SICKNESS_RESISTENCE_UPGRADE ? 
            this.baseSicknessResistence + SICKNESS_RESISTENCE_UPGRADE_VALUE : 
            this.baseSicknessResistence);
        this.actualSicknessResistenceUpgrade = this.actualSicknessResistenceUpgrade < MAX_SICKNESS_RESISTENCE_UPGRADE ? this.actualSicknessResistenceUpgrade + 1 : this.actualSicknessResistenceUpgrade;
    }

    @Override
    public void applySicknessResistenceModifier(final double moltiplyValue) {
        this.actualSicknessResistence = this.baseSicknessResistence * moltiplyValue;
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
    public void applyFertilityModifier(final double moltiplyValue) {
        this.actualFertility = this.baseFertility * moltiplyValue;
    }

    @Override
    public void increaseFertility() {
        setSicknessResistence(this.actualFertilityUpgrade < MAX_FERTILITY_UPGRADE ? 
            this.baseFertility + FERTILITY_UPGRADE_VALUE : 
            this.baseFertility);
        this.actualFertilityUpgrade = this.actualFertilityUpgrade < MAX_FERTILITY_UPGRADE ? this.actualFertilityUpgrade + 1 : this.actualFertilityUpgrade;
    }

    @Override
    public void resetToBaseSpeed() {
        this.actualSpeed = this.baseSpeed;
    }

    @Override
    public void resetToBaseSicknessResistence() {
        this.actualSicknessResistence = this.baseSicknessResistence;
    }

    @Override
    public void resetToBaseReproductionRange() {
        this.actualRadius = this.baseRadius;
    }

    @Override
    public void resetToBaseFertility() {
        this.actualFertility = this.baseFertility;
    }

    @Override
    public int getActualSpeedUpgrade() {
        return actualSpeedUpgrade;
    }

    @Override
    public int getActualSicknessResistenceUpgrade() {
        return actualSicknessResistenceUpgrade;
    }

    @Override
    public int getActualReproductionRangeUpgrade() {
        return actualReproductionRangeUpgrade;
    }

    @Override
    public int getActualFertilityUpgrade() {
        return actualFertilityUpgrade;
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
}
