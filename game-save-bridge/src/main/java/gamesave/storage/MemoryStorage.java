package gamesave.storage;

/** Concrete Implementor #2 (guest sessions: lost when the game closes). */
public class MemoryStorage implements SaveStorage {
    @Override
    public SaveResult save(String slot, String gameData) {
        return SaveResult.success("saving game to ram");
    }

    @Override
    public SaveResult load(String slot) {
        return SaveResult.success("loading game from ram");
    }
}
