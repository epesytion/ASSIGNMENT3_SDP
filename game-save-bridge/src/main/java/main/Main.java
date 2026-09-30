package main;

import main.Abstraction_refAbstr.AutoSaver;
import main.Abstraction_refAbstr.GameSaver;
import main.Abstraction_refAbstr.ManualSaver;
import main.ConcImplem_Adapters.FileStorage;
import main.ConcImplem_Adapters.LegacyAdapter;
import main.ConcImplem_Adapters.LegacySaveAPI;
import main.ConcImplem_Adapters.MemoryStorage;
import main.Target_Implementor.SaveStorage;

public class Main {
    public static void main(String[] args) {
        //Adapter

        LegacySaveAPI legacySaveAPI = new LegacySaveAPI();
        SaveStorage storage = new LegacyAdapter(legacySaveAPI);
        storage.save("0", "Saved Data");


        System.out.println();
        System.out.println();
        System.out.println();


        //Bridge
        MemoryStorage storage1 = new MemoryStorage();
        GameSaver saver = new AutoSaver(storage1);
        saver.save("0", "Auto");

        System.out.println();

        FileStorage storage2 = new FileStorage();
        GameSaver gameSaver = new ManualSaver(storage2, "The Kingdom");
        gameSaver.save("1", "Manual");
    }
}
