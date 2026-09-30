package gamesave.abstraction;

import gamesave.storage.SaveResult;
import gamesave.storage.SaveStorage;

/** Refined Abstraction #2: one automatic slot, overwritten every time. */
public class AutoSaveManager extends SaveManager {
    private static final String SLOT = "autosave";

    public AutoSaveManager(SaveStorage storage) {
        super(storage);
    }

    public SaveResult autoSave(String gameData) {
        return storage.save(SLOT, gameData);
    }

    public SaveResult loadLatest() {
        return storage.load(SLOT);
    }
}
