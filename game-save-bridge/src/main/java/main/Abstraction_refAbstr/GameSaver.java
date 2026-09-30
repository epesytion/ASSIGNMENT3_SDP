package main.Abstraction_refAbstr;

import main.Target_Implementor.SaveStorage;

//Abstraction in bridge
public abstract class GameSaver {
    protected SaveStorage storage; //reference to implementor

    protected GameSaver(SaveStorage storage){
        this.storage = storage;
    }

    public abstract void save(String id, String data) ;

    public void saveData(String id, String text)  {
        storage.save(id, text);
    }
}
