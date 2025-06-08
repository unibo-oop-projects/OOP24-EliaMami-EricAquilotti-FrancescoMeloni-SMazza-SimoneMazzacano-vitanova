package it.unibo.model.pickable;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;

import it.unibo.model.chapter.map.Map;
import it.unibo.model.chapter.map.MapImpl;
import it.unibo.model.pickable.manager.PickableManager;
import it.unibo.model.pickable.manager.PickableManagerImpl;
import it.unibo.utils.HumanMockup;
import it.unibo.utils.MutableClock;
import it.unibo.view.sprite.HumanType;

final class PickableManagerTest {
    private static final MutableClock MUTABLE_CLOCK = new MutableClock(Instant.now(), ZoneId.systemDefault());
    private static final Duration ADVANCE = Duration.ofSeconds(5);
    private final Map map = new MapImpl(16, 16);
    private final PickableManager pickableManager = new PickableManagerImpl(HumanMockup.createHuman(HumanType.PLAYER), 
                                                                    MUTABLE_CLOCK, 
                                                                    map);

    @Test
    void testSpawnPickable() {
        MUTABLE_CLOCK.advance(ADVANCE);
        pickableManager.spawnPickable();
        assertFalse(pickableManager.getPickables().isEmpty());
    }
}
