package main.ConcImplem_Adapters;
//Concrete Implementor 2 / Adapter

import main.Target_Implementor.SaveStorage;

public class LegacyAdapter implements SaveStorage {
    LegacySaveAPI legacySaveAPI;
//reference to adaptee
    public LegacyAdapter(LegacySaveAPI legacySaveAPI){
        this.legacySaveAPI = legacySaveAPI;
    }

    @Override
    public String name() {
        return "adapter";
    }

    @Override
    public void save(String id, String data)  {
        legacySaveAPI.saveData(id, data);
    }

    @Override
    public void load(String id, String data) {
        legacySaveAPI.loadData(id, data);
    }
}
