package gamesave.storage;

/** Implementor: where a game is saved. Failure is reported as SaveResult.failure(...). */
public interface SaveStorage {
    SaveResult save(String slot, String gameData);

    SaveResult load(String slot);
}
