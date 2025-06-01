package it.unibo.model.human.stats;

import it.unibo.common.Circle;
import it.unibo.model.effect.Effect;
import it.unibo.model.human.strategies.reproduction.ReproStrategy;

/**
 * Models stats of a human.
 */
public interface HumanStats {

    /**
     * Method that returns the current speed.
     * @return the current speed of human.
     */
    double getSpeed();

    /**
     * Used to increase speed of a fixed value.
     */
    void increaseSpeed();

    /**
     * Method that set the new reprostrategy of human.
     * @param newReproStrategy
     */
    void setReproStrategy(ReproStrategy newReproStrategy);

    /**
     * Method that returns the current reprostrategy.
     * @return the reproStrategy.
     */
    ReproStrategy getReproStrategy();

    /**
     * Method that returns the current reproduction area.
     * @return the radius of the reproduction radius.
     */
    Circle getReproductionAreaRadius();

    /**
     * Method that returns the current radius of the reproduction area.
     * @return the radius of the reproduction radius.
     */
    double getBaseRadius();

    /**
     * Used to increase the reproduction area radius of a fixed value.
     */
    void increaseReproductionAreaRadius();

    /**
     * Method that returns the sickness resistence.
     * @return the probability to resist sickness effect.
     */
    double getSicknessResistence();

    /**
     * Used to increase sickness resistence of a fixed value.
     */
    void increaseSicknessResistence();

    /**
     * Method that returns the fertility.
     * @return the probability of getting a female child.
     */
    double getFertility(); 

    /**
     * Used to increase fertility of a fixed value.
     */
    void increaseFertility();

    /**
     * Apply the effect modifier to the stats based on the type effect activated.
     * @param effect 
     */
    void applyEffect(Effect effect);

    /**
     * Resets actual stats to base stats based on the expired effect.
     * @param effect 
     */
    void resetEffect(Effect effect);

    /**
     * Resets all stats to base stats. 
     */
    void resetAllEffect();

    /**
     * This method returns how many speed upgrade the human took. 
     * @return how many upgrade the human took.
     */
    int getSpeedUpgrade();

    /**
     * This method returns how many sickness resistence upgrade the human took. 
     * @return how many upgrade the human took.
     */
    int getSicknessResistenceUpgrade();

    /**
     * This method returns how many reproduction range upgrade the human took. 
     * @return how many upgrade the human took.
     */
    int getReproductionRangeUpgrade();

    /**
     * This method returns how many fertility upgrade the human took. 
     * @return how many upgrade the human took.
     */
    int getFertilityUpgrade();

    /**
     * This method returns if the human has been sick during the chapter. 
     * @return true if the human has been sick, including now.
     */
    boolean hasBeenSick();

    /**
     * This method returns if the human is sick. 
     * @return true if the human is sick now.
     */
    boolean isSick();

    /**
     * Sets the sick status of the human.
     * @param isSick true if the human is sick.
     */
    void setSickness(boolean isSick);
}
