package main.Abstraction_refAbstr;

import main.Target_Implementor.SaveStorage;

//Refined abstractioin1
public class AutoSaver extends GameSaver{
    public AutoSaver(SaveStorage storage){
        super(storage);
    }

    @Override
    public void save(String id, String data) {
        System.out.println("Saving data automatically");
        saveData(id, data);
    }
}
