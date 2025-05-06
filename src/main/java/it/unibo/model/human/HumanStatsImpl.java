package it.unibo.model.human;

import it.unibo.common.Circle;
import it.unibo.common.CircleImpl;
import it.unibo.model.human.strategies.reproduction.ReproStrategy;

/**
 * Implementation of human stats that handles all human's stats.
 */
public final class HumanStatsImpl implements HumanStats {
        private double speed;
        private double sicknessResistence;
        private double fertility;
        private Circle reproductionAreaRadius;
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
            setReproductionAreaRadius(reproStrategy.getReproductionArea());
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
            setSpeed(speed + SPEED_UPGRADE_VALUE);
        }

        @Override
        public Circle getReproductionAreaRadius() {
            return new CircleImpl(reproductionAreaRadius);
        }

        private void setReproductionAreaRadius(final Circle reproductionArea) {
            reproductionAreaRadius = new CircleImpl(reproductionArea);
        }

        @Override
        public void increaseReproductionAreaRadius() {
            setReproductionAreaRadius(
                new CircleImpl(
                    this.reproductionAreaRadius.getCenter().x(), 
                    this.reproductionAreaRadius.getCenter().y(), 
                    this.reproductionAreaRadius.getRadius() + RADIUS_UPGRADE_VALUE
                )
            );
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
            setFertility(fertility + FERTILITY_UPGRADE_VALUE);
        }

        @Override
        public void applySpeedModifier(final double moltiplyValue) {
            this.speed = this.speed * moltiplyValue;
        }

        @Override
        public void applyReproductionRangeModifier(final double moltiplyValue) {

        }

        @Override
        public void applySicknessResistenceModifier(final double moltiplyValue) {

        }
}
