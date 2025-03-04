package it.unibo.model.human;

import java.util.Arrays;
import java.util.List;

import it.unibo.common.Position;
import it.unibo.model.chapter.map.Map;
import it.unibo.view.sprite.Sprite;

/**
 * Implementation of a male human that provides its sprites.
 */
public final class MaleImpl extends BasicNPC implements Male {

    private static final List<Sprite> VALID_SPRITES =
        Arrays.stream(Sprite.values())
                .filter(s -> s.name().startsWith("MALE_"))
                .toList();

    /**
     * 
     * @param startingPosition the initial position.
     * @param map the chapter's map
     */
    public MaleImpl(final Position startingPosition, final Map map) {
        super(startingPosition, Sprite.MALE_DOWN_1, map);
    }

    @Override
    public void move() {
        super.move();
        updateSprite(VALID_SPRITES);
    }

}
