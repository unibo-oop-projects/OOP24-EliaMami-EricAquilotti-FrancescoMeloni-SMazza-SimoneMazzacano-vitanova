package it.unibo.model.savemanager;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import it.unibo.model.human.HumanStats;

public class SaveManager {
    FileInputStream fileInputStream;
    FileOutputStream fileOutputStream;
    ObjectInputStream objectInputStream;
    ObjectOutputStream objectOutputStream;

    public SaveManager(FileInputStream inputFile, FileOutputStream outputFile) throws IOException {
        fileOutputStream = outputFile;
        fileInputStream = inputFile;
        objectOutputStream = new ObjectOutputStream(outputFile);
        objectInputStream = new ObjectInputStream(inputFile);
    }

    public void saveObj(Object toSave) throws IOException {
        objectOutputStream.writeObject(toSave);
        objectOutputStream.flush();
        objectOutputStream.close();
    }

    public HumanStats readObj() throws ClassNotFoundException, IOException {
        HumanStats obj = (HumanStats) objectInputStream.readObject();
        objectInputStream.close(); 
        return obj;
    }
}
