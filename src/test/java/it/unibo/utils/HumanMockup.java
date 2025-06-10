package it.unibo.utils;

import java.time.Instant;
import java.time.ZoneId;

import it.unibo.common.Circle;
import it.unibo.common.Direction;
import it.unibo.common.Position;
import it.unibo.controller.InputHandlerImpl;
import it.unibo.model.chapter.map.Map;
import it.unibo.model.chapter.map.MapImpl;
import it.unibo.model.effect.Effect;
import it.unibo.model.human.Human;
import it.unibo.model.human.HumanFactory;
import it.unibo.model.human.HumanFactoryImpl;
import it.unibo.model.human.stats.HumanStats;
import it.unibo.model.human.stats.StatType;
import it.unibo.model.human.strategies.reproduction.ReproStrategy;
import it.unibo.view.sprite.HumanType;
import it.unibo.view.sprite.Sprite;

/**
 * Utility class to create mock humans for testing purposes.
 */
public final class HumanMockup {
    private static final MutableClock MUTABLE_CLOCK = new MutableClock(Instant.now(), ZoneId.systemDefault());
    private static final HumanFactory HUMAN_FACTORY = new HumanFactoryImpl(MUTABLE_CLOCK);
    private static final Map MAP = new MapImpl(32, 32);
    private HumanMockup() { }
    /**
     * Creates a mock human with the specified type and reproduction area.
     * All other methods return default or null values.
     * @param type the type of the human
     * @param area the reproduction area of the human
     * @return a mock human
     */
    public static Human createEmptyHuman(final HumanType type, final Circle area) {
        return new Human() {
            @Override public HumanType getType() {
                return type;
            }
            @Override
            public void move() {
            }
            @Override
            public Position getPosition() {
                return null;
            }
            @Override
            public Sprite getSprite() {
                return null;
            }
            @Override
            public Direction getDirection() {
                return null;
            }
            @Override
            public boolean collide(final Human other) {
                return false;
            }
            @Override
            public HumanStats getStats() {
                return new HumanStats() {

                    @Override
                    public double getSpeed() {
                        return 0;
                    }

                    @Override
                    public ReproStrategy getReproStrategy() {
                        return null;
                    }

                    @Override
                    public Circle getReproductionCircle() {
                        return area;
                    }

                    @Override
                    public double getSicknessResistence() {
                        return 0;
                    }

                    @Override
                    public double getFertility() {
                        return 0;
                    }

                    @Override
                    public void increaseStat(final StatType type) {
                    }

                    @Override
                    public int getSpeedUpgrade() {
                        return 0;
                    }

                    @Override
                    public int getSicknessResistenceUpgrade() {
                        return 0;
                    }

                    @Override
                    public int getReproductionRangeUpgrade() {
                        return 0;
                    }

                    @Override
                    public int getFertilityUpgrade() {
                        return 0;
                    }

                    @Override
                    public void resetEffect(final Effect effect) {
                    }

                    @Override
                    public void applyEffect(final Effect effect) {
                    }

                    @Override
                    public boolean hasBeenSick() {
                        return false;
                    }

                    @Override
                    public boolean isSick() {
                        return false;
                    }

                    @Override
                    public void setSickness(final boolean isSick) {

                    }

                    @Override
                    public void resetAllEffect() {
                    }
                };
            }
            @Override
            public boolean canReproduce() {
                return false;
            }
        };
    }

    /**
     * Creates a human of the specified type.
     * @param type the type of the human
     * @return a human of the specified type
     */
    public static Human createHuman(final HumanType type) {
        final Position position = new Position(0, 0);
        if (type == HumanType.MALE) {
            return HUMAN_FACTORY.male(position, MAP);
        } else if (type == HumanType.FEMALE) {
            return HUMAN_FACTORY.female(position, MAP);
        } else {
            return HUMAN_FACTORY.player(position, MAP, new InputHandlerImpl());
        }
    }

    /**
     * Creates a sick human of the specified type.
     * The human will have its sickness status set to true.
     * Sickness effects will not be applied.
     * @param type the type of the human
     * @return a sick human of the specified type
     */
    public static Human createSickHuman(final HumanType type) {
        final Human human = createHuman(type);
        human.getStats().setSickness(true);
        return human;
    }
}
