package it.unibo.model.savemanager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Class used to manage the save and read on file.
 */
public final class SaveManager {
    /**
     * Method that taken the file to save in, write the object to save.
     * @param toSave
     * @param saveFile
     * @throws IOException
     */
    public void saveObj(final Object toSave, final File saveFile) throws IOException {
        final FileOutputStream fileOutputStream = new FileOutputStream(saveFile);
        final ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(toSave);
        objectOutputStream.flush();
        objectOutputStream.close();
    }

    /**
     * Method that taken the file to read from, read and return the object.
     * @param readFile
     * @return the read object.
     * @throws ClassNotFoundException
     * @throws IOException
     */
    public Object readObj(final File readFile) throws ClassNotFoundException, IOException {
        final FileInputStream fileInputStream = new FileInputStream(readFile);
        final ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        final Object obj = objectInputStream.readObject();
        objectInputStream.close(); 
        return obj;
    }
}
