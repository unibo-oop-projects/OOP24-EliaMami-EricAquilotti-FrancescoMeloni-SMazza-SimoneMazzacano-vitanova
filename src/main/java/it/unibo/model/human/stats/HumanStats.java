package it.unibo.model.human.stats;

import it.unibo.common.Circle;
import it.unibo.model.effect.Effect;
import it.unibo.model.effect.EffectType;
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
     * Apply stats modifier based on the effect activated.
     * @param effect
     */
    void applyEffect(Effect effect);

    /**
     * Resets actual stats to base stats based on the expired effect type.
     * @param type 
     */
    void resetEffect(EffectType type);

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
     * 
     * @return true if the human has been sick, including now.
     */
    boolean hasBeenSick();

    /**
     * 
     * @return true if the human is sick now.
     */
    boolean isSick();

    /**
     * Sets the sick status of the human.
     * @param isSick true if the human is sick.
     */
    void setSickness(boolean isSick);
}
