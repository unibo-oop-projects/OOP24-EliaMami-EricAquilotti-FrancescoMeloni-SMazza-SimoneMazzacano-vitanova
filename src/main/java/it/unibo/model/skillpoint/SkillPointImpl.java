package it.unibo.model.skillpoint;

import java.util.Optional;

/**
 * Class to manage skill point after the ending of a chapter.
 */
public final class SkillPointImpl implements SkillPoint {
    private Optional<Integer> actualValue;
    private final int baseValue;

    /**
     * Constuctor of SkillPoint.
     * @param value the value to inizialize skill point with.
     */
    public SkillPointImpl(final int value) {
        this.actualValue = Optional.of(value);
        this.baseValue = value;
    }

    @Override
    public int getValue() {
        return actualValue.get();
    }

    @Override
    public void resetToBaseValue() {
        actualValue = actualValue.or(() -> Optional.of(baseValue));
    }

    @Override
    public void decreaseValue() {
        if (actualValue.get() > 0) {
            actualValue = Optional.of((Integer) (actualValue.get() - 1));
        }
    }

    @Override
    public void reset() {
        actualValue = Optional.empty();
    }
}
