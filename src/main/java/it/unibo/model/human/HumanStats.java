package it.unibo.model.human;

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
     * This method returns how many speed upgrade the human took. 
     * @return how many upgrade the human took.
     */
    int getActualSpeedUpgrade();

    /**
     * This method returns how many sickness resistence upgrade the human took. 
     * @return how many upgrade the human took.
     */
    int getActualSicknessResistenceUpgrade();

    /**
     * This method returns how many reproduction range upgrade the human took. 
     * @return how many upgrade the human took.
     */
    int getActualReproductionRangeUpgrade();

    /**
     * This method returns how many fertility upgrade the human took. 
     * @return how many upgrade the human took.
     */
    int getActualFertilityUpgrade();

    /**
     * This method returns the speed upgrade's max number that human can get. 
     * @return the upgrade's max number that human can get.
     */
    int getMaxSpeedUpgrade();

    /**
     * This method returns the sickness resistence upgrade's max number that human can get. 
     * @return the upgrade's max number that human can get.
     */
    int getMaxSicknessResistenceUpgrade();

    /**
     * This method returns the reproduction range upgrade's max number that human can get. 
     * @return the upgrade's max number that human can get.
     */
    int getMaxReproductionRangeUpgrade();

    /**
     * This method returns the fertility upgrade's max number that human can get. 
     * @return the upgrade's max number that human can get.
     */
    int getMaxFertilityUpgrade();
}
