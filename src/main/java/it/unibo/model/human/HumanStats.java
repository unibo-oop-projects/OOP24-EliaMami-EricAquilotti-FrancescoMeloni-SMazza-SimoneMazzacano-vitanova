package it.unibo.model.human;

import it.unibo.common.Circle;

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
     * 
     * @return the radius of the reproduction radius.
     */
    Circle getReproductionAreaRadius();

    /**
     * Used to increase the reproduction area radius after finishing the chapter.
     */
    void increaseReproductionAreaRadius();

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
     * Used to apply power up and malus effects to speed's stat.
     */
    void applySpeedModifier(double multiplyValue);

    /**
     * @param multiplyValue is the value to multiply with.
     * Used to apply power up and malus effects to reproduction range's stat.
     */
    void applyReproductionRangeModifier(double multiplyValue);

    /**
     * @param multiplyValue is the value to multiply with.
     * Used to apply power up and malus effects to sickness resistence's stat.
     */
    void applySicknessResistenceModifier(double multiplyValue);
}
