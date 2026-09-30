package gamesave.selection;

import gamesave.storage.SaveStorage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class StorageRegistryTest {
    @Mock SaveStorage hdd;
    @Mock SaveStorage cloud;

    @Test
    void choosesTheStorageFromTheInputScheme() {
        StorageRegistry registry = new StorageRegistry()
                .register("hdd", path -> hdd)
                .register("cloud", account -> cloud);

        assertEquals(Optional.of(hdd), registry.open("hdd:C:/saves"));
        assertEquals(Optional.of(cloud), registry.open("cloud:player42"));
    }

    @Test
    void passesTheRestOfTheInputToTheFactory() {
        StorageRegistry registry = new StorageRegistry().register("cloud", account -> {
            assertEquals("player42", account);
            return cloud;
        });

        assertTrue(registry.open("cloud:player42").isPresent());
    }

    @Test
    void unknownSchemeGivesEmptyResult() {
        assertTrue(new StorageRegistry().register("hdd", p -> hdd).open("ftp:x").isEmpty());
    }

    @Test
    void newStorageNeedsOnlyANewRegistration() {
        StorageRegistry registry = new StorageRegistry();
        assertTrue(registry.open("nas:home").isEmpty());

        registry.register("nas", share -> hdd); // no existing class was touched

        assertEquals(Optional.of(hdd), registry.open("nas:home"));
    }
}
