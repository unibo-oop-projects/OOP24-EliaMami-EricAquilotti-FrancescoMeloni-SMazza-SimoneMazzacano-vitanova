package it.unibo.model.savemanager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

final class SaveManagerTest {
    private final SaveManager sv = new SaveManagerImpl();
    private static final File TEST_FILE = new File("src/test/java/test.txt");
    private static final String EXAMPLE_PATH = "no/such/place";
    private static final Integer OBJECT_TO_SAVE = 1; 

    @BeforeEach
    void saveObject() throws IOException {
        sv.saveObj(OBJECT_TO_SAVE, TEST_FILE);
    }

    @Test
    void testIOException() throws ClassNotFoundException, IOException {
        assertThrows(IOException.class, () -> sv.saveObj(OBJECT_TO_SAVE, new File(EXAMPLE_PATH)));
        assertThrows(IOException.class, () -> sv.readObj(new File(EXAMPLE_PATH)));
    }

    @Test
    void testRead() throws ClassNotFoundException, IOException {
        assertEquals(sv.readObj(TEST_FILE), OBJECT_TO_SAVE);
    }

    @AfterAll
    static void deleteFile() throws IOException {
        Files.delete(TEST_FILE.toPath());
    }
}
