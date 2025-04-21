package it.unibo.model.human;

/**
 * Models stats of a human.
 */
public interface Stats {

    /**
     * 
     * @return the current speed of human.
     */
    int getSpeed();
    
    /**
     * Used to increase speed after finishing the chapter.
     */
    void increaseSpeed();

    /**
     * 
     * @return the radius of the reproduction radius.
     */
    int getReproductionAreaRadius();

    /**
     * Used to increase the reproduction area radius after finishing the chapter.
     */
    void increaseReproductionAreaRadius();

    /**
     * 
     * @return the probability to resiste sickness effect.
     */
    double getResistenceToSickness();

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
     * Use to increase fertility after finishing the chapter.
     */
    void increaseFertility();
}
