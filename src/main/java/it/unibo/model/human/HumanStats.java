package it.unibo.model.human;

import it.unibo.common.Circle;
import it.unibo.model.human.strategies.reproduction.ReproStrategy;

/**
 * Models stats of a human.
 */
public interface HumanStats {

    /**
     * 
     * @return the current speed of human.
     */
    double getSpeed();

    /**
     * Used to increase speed after finishing the chapter.
     */
    void increaseSpeed();

    /**
     * @param multiplyValue is the value to multiply with.
     * Used to apply power up and malus effects to speed's stat.
     */
    void applySpeedModifier(double multiplyValue);

    /**
     * 
     * @return the reproStrategy.
     */
    ReproStrategy getReproStrategy();

    /**
     * 
     * @return the radius of the reproduction radius.
     */
    Circle getReproductionAreaRadius();

    /**
     * Used to increase the reproduction area radius after finishing the chapter.
     */
    void increaseReproductionAreaRadius();

    /**
     * @param multiplyValue is the value to multiply with.
     * Used to apply power up and malus effects to reproduction range's stat.
     */
    void applyReproductionRangeModifier(double multiplyValue);

    /**
     * 
     * @return the probability to resiste sickness effect.
     */
    double getSicknessResistence();

    /**
     * Used to increase sickness resistence after finishing the chapter.
     */
    void increaseSicknessResistence();

    /**
     * @param multiplyValue is the value to multiply with.
     * Used to apply power up and malus effects to sickness resistence's stat.
     */
    void applySicknessResistenceModifier(double multiplyValue);

    /**
     * 
     * @return the probability of getting more than one child.
     */
    double getFertility(); 

    /**
     * Used to increase fertility after finishing the chapter.
     */
    void increaseFertility();

    /**
     * @param multiplyValue is the value to multiply with.
     * Used to apply power up and malus effects to fertility's stat.
     */
    void applyFertilityModifier(double multiplyValue);

    /**
     * Resets actual speed to base speed.
     */
    void resetToBaseSpeed();

    /**
     * Resets actual sickness resistence to base sickness resistence.
     */
    void resetToBaseSicknessResistence();

    /**
     * Resets actual reproduction range to base reproduction range.
     */
    void resetToBaseReproductionRange();

    /**
     * Resets actual fertility to base fertility.
     */
    void resetToBaseFertility();
}
