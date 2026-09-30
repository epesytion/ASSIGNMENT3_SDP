package main.Abstraction_refAbstr;

import main.Target_Implementor.SaveStorage;

//Refined Abstraction 2
public class ManualSaver extends GameSaver{
    private final String checkpoint;

    public ManualSaver(SaveStorage storage, String checkpoint){
        super(storage);
        this.checkpoint = checkpoint;
    }

    @Override
    public void save(String id, String data) {
        System.out.println("YOU REST AT THE BENCH AND SAVED THE GAME: \n" +
                "The checkpoint: "+ checkpoint);
        saveData(id, data);
    }
}

