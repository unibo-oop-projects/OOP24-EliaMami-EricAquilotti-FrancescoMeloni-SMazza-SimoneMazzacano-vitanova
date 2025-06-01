package it.unibo.model.skillpoint;

public interface SkillPoint {

    /**
     * Method that returns the value of skillPoint.
     * @return the actual value of skillPoint
     */
    int getValue();

    /**
     * This method sets skill point's value to baseValue if skill points isn't already initialized.
     */
    void resetToBaseValue();

    /**
     * This method update the variable skill point if skill point is greater than zero.
     */
    void updateValue();

    /**
     * This method resets skillPoint to empty.
     */
    void reset();

}