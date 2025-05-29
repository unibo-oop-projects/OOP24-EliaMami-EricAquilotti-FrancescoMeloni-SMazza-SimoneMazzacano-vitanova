package it.unibo.model.skillpoint;

import java.util.Optional;

/**
 * Class to manage skill point after the ending of a chapter.
 */
public class SkillPoint {
    private Optional<Integer> skillPoint = Optional.empty();
    private final int baseValue;

    /**
     * Constuctor of SkillPoint
     * @param value
     */
    public SkillPoint(final int value) {
        this.skillPoint = Optional.of(value);
        this.baseValue = value;
    }

    /**
     * Method that returns the value of skillPoint
     * @return the actual value of skillPoint
     */
    public int getValue() {
        return skillPoint.get();
    }

    /**
     * This method sets skill point's value to hit baseValue if skill points isn't already initialized.
     * @param value the value to initialize skill points to.
     */
    public void resetToBaseValue() {
        skillPoint = skillPoint.or(() -> Optional.of(baseValue));
    }

    /**
     * This method update the variable skill point if skill point is greater than zero.
     */
    public void updateValue() {
        skillPoint = Optional.of(skillPoint.get() > 0 ? (Integer) (skillPoint.get() - 1) : skillPoint.get());
    }

    /**
     * This method resets skillPoint to empty.
     */
    public void reset() {
        skillPoint = Optional.empty();
    }
}
