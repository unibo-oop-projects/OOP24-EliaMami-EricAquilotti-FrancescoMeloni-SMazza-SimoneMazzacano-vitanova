package it.unibo.model.savemanager;

import java.io.Serializable;

import it.unibo.model.human.HumanStats;

public class SaveObject implements Serializable {
    int chapterNumber;
    HumanStats playerStats;

    public SaveObject(int chapterNumber, HumanStats playerStats){
        this.chapterNumber = chapterNumber;
        this.playerStats = playerStats;
    }

    public HumanStats getPlayerStats(){
        return playerStats;
    };

    public int getChapterNumber(){
        return chapterNumber;
    }
}
