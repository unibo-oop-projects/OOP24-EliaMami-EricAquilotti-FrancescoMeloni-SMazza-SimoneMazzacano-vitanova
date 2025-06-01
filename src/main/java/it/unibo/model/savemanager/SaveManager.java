package it.unibo.model.savemanager;

import java.io.File;
import java.io.IOException;

/**
 * Interface that describe how to manage the save and read from file.
 */
public interface SaveManager {

    /**
     * Method that taken the file to save in, write the object to save.
     * @param toSave
     * @param saveFile
     * @throws IOException
     */
    void saveObj(Object toSave, File saveFile) throws IOException;

    /**
     * Method that taken the file to read from, read and return the object.
     * @param readFile
     * @return the read object.
     * @throws ClassNotFoundException
     * @throws IOException
     */
    Object readObj(File readFile) throws ClassNotFoundException, IOException;

}