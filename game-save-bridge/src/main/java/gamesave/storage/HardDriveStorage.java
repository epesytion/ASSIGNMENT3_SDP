package gamesave.storage;

/** Concrete Implementor #1. */
public class HardDriveStorage implements SaveStorage {
    @Override
    public SaveResult save(String slot, String gameData) {
        return SaveResult.success("saving game to hdd");
    }

    @Override
    public SaveResult load(String slot) {
        return SaveResult.success("loading game from hdd");
    }
}
