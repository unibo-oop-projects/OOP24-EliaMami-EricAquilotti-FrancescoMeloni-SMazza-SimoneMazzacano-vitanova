package it.unibo.model.human;

import it.unibo.common.Circle;
import it.unibo.common.CircleImpl;
import it.unibo.model.human.strategies.reproduction.ReproStrategy;

/**
 * Implementation of human stats that handles all human's stats.
 */
public final class HumanStatsImpl implements HumanStats {
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
        private static final double SPEED_UPGRADE_VALUE = .5;
        private static final double SICKNESS_RESISTENCE_UPGRADE_VALUE = .05;
        private static final double FERTILITY_UPGRADE_VALUE = .05;
        private static final int RADIUS_UPGRADE_VALUE = 5;

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
            baseSpeed = baseSpeed + SPEED_UPGRADE_VALUE;
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
            setReproductionAreaRadius(getReproStrategy().getReproductionArea().getRadius() + RADIUS_UPGRADE_VALUE);
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
            this.baseSicknessResistence = this.baseSicknessResistence + SICKNESS_RESISTENCE_UPGRADE_VALUE;
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
            setFertility(baseFertility + FERTILITY_UPGRADE_VALUE);
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
}
