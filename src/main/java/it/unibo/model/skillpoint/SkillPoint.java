package it.unibo.model.skillpoint;

import java.util.Optional;

/**
 * Class to manage skill point after the ending of a chapter.
 */
public class SkillPoint {
    private Optional<Integer> actualValue;
    private final int baseValue;

    /**
     * Constuctor of SkillPoint.
     * @param value
     */
    public SkillPoint(final int value) {
        this.actualValue = Optional.of(value);
        this.baseValue = value;
    }

    /**
     * Method that returns the value of skillPoint.
     * @return the actual value of skillPoint
     */
    public int getValue() {
        return actualValue.get();
    }

    /**
     * This method sets skill point's value to baseValue if skill points isn't already initialized.
     */
    public void resetToBaseValue() {
        actualValue = actualValue.or(() -> Optional.of(baseValue));
    }

    /**
     * This method update the variable skill point if skill point is greater than zero.
     */
    public void updateValue() {
        actualValue = Optional.of(actualValue.get() > 0 ? (Integer) (actualValue.get() - 1) : actualValue.get());
    }

    /**
     * This method resets skillPoint to empty.
     */
    public void reset() {
        actualValue = Optional.empty();
    }
}
