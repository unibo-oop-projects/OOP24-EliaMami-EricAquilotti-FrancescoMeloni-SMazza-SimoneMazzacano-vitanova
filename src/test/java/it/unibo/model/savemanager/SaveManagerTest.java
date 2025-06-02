package it.unibo.model.savemanager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SaveManagerTest {
    private final SaveManager sv = new SaveManagerImpl();
    private final static File testFile = new File("src/test/java/test.txt");
    private final Integer objectToSave = 1; 

    @BeforeEach
    void setUp() throws IOException {
        sv.saveObj(objectToSave, testFile);
    }

    @Test
    void testIOException() throws ClassNotFoundException, IOException {
        assertThrows(IOException.class, () -> sv.saveObj(objectToSave, new File("/no/such/place")));
        assertThrows(IOException.class, () -> sv.readObj(new File("/no/such/place")));
    }

    @Test
    void testRead() throws ClassNotFoundException, IOException {
        assertEquals(sv.readObj(testFile), objectToSave);
        
    }

    @AfterAll
    static void deleteFile() throws IOException {
        Files.delete(testFile.toPath());
    }
}