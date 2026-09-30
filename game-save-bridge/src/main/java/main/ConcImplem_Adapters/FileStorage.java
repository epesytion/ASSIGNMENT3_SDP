package main.ConcImplem_Adapters;
//Concrete Implementor

import main.Target_Implementor.SaveStorage;

public class FileStorage implements SaveStorage {
    @Override
    public String name() {
        return "file";
    }

    @Override
    public void save(String id, String data)  {
        System.out.println(id+" Saved by file: " +data);
    }

    @Override
    public void load(String id, String data)
    {
        System.out.println(id+ " Loaded data from file: " + data);
    }
}
