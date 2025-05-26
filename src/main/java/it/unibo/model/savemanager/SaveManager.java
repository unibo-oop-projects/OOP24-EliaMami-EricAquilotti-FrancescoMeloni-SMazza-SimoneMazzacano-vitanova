package it.unibo.model.savemanager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SaveManager {
    public SaveManager() {}

    public void saveObj(final Object toSave, final File saveFile) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(saveFile);
        System.out.println("fileOutputStream: " + fileOutputStream);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(toSave);
        objectOutputStream.flush();
        objectOutputStream.close();
    }

    public Object readObj(final File readFile) throws ClassNotFoundException, IOException {
        FileInputStream fileInputStream = new FileInputStream(readFile);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        Object obj = objectInputStream.readObject();
        objectInputStream.close(); 
        return obj;
    }
}
