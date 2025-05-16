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

    void applyEffect(Effect effect);
    /**
     * Resets actual stats to base stats based on the expired effect type.
     */
    void resetEffect(EffectType type);

    int getActualSpeedUpgrade();

    int getActualSicknessResistenceUpgrade();

    int getActualReproductionRangeUpgrade();

    int getActualFertilityUpgrade();

    int getMaxSpeedUpgrade();

    int getMaxSicknessResistenceUpgrade();

    int getMaxReproductionRangeUpgrade();

    int getMaxFertilityUpgrade();
}
