package gamesave.abstraction;

import gamesave.storage.SaveStorage;

/** Abstraction: knows HOW the player saves, delegates WHERE to a SaveStorage. */
public abstract class SaveManager {
    protected final SaveStorage storage;

    protected SaveManager(SaveStorage storage) {
        this.storage = storage;
    }
}
