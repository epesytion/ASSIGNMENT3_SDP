package gamesave.selection;

import gamesave.storage.SaveStorage;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * Dynamic implementor selection: "hdd:", "ram:" or "cloud:player42" picks the storage at runtime.
 * A new storage = one more register(...) call; this class never changes.
 */
public class StorageRegistry {
    private final Map<String, Function<String, SaveStorage>> factories = new HashMap<>();

    public StorageRegistry register(String scheme, Function<String, SaveStorage> factory) {
        factories.put(scheme, factory);
        return this;
    }

    public Optional<SaveStorage> open(String location) {
        int colon = location.indexOf(':');
        String scheme = colon < 0 ? location : location.substring(0, colon);
        String argument = colon < 0 ? "" : location.substring(colon + 1);
        return Optional.ofNullable(factories.get(scheme)).map(factory -> factory.apply(argument));
    }
}
