package main.ConcImplem_Adapters;

import main.Target_Implementor.SaveException;
import main.Target_Implementor.SaveStorage;

//Concrete Implementor1
public class MemoryStorage implements SaveStorage {
    @Override
    public String name() {
        return "memory";
    }

    @Override
    public void save(String id, String data)  {
        System.out.println(id+" Saved at the memory: "+ data);
    }

    @Override
    public void load(String id, String data) throws SaveException {
        System.out.println(id+" Loaded the data from memory: "+ data);
    }
}
