package gamesave.abstraction;

import gamesave.storage.SaveResult;
import gamesave.storage.SaveStorage;

/** Refined Abstraction #1: the player names each save. */
public class ManualSaveManager extends SaveManager {
    public ManualSaveManager(SaveStorage storage) {
        super(storage);
    }

    public SaveResult save(String name, String gameData) {
        return storage.save("manual-" + name, gameData);
    }

    public SaveResult load(String name) {
        return storage.load("manual-" + name);
    }
}
