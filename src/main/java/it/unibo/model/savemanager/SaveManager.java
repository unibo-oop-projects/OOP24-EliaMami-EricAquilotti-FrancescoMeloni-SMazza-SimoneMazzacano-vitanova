package it.unibo.model.savemanager;

import java.io.File;
import java.io.IOException;

/**
 * Interface that describe how to manage the save and read from file.
 */
public interface SaveManager {

    /**
     * Method that takes the file to save in, writes the object to save.
     * @param toSave the object you want to save.
     * @param saveFile the file where you want to save your object in.
     * @throws IOException if the path or file doesn't exist.
     */
    void saveObj(Object toSave, File saveFile) throws IOException;

    /**
     * Method that takes the file to read from, reads and return the object.
     * @param readFile the file to read from.
     * @return the read object.
     * @throws ClassNotFoundException if there isn't a definition for the read class.
     * @throws IOException if the file or path doesn't exist.
     */
    Object readObj(File readFile) throws ClassNotFoundException, IOException;

}
