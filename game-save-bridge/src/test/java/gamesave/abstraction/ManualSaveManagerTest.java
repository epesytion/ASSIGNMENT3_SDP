package gamesave.abstraction;

import gamesave.storage.SaveResult;
import gamesave.storage.SaveStorage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ManualSaveManagerTest {
    @Mock SaveStorage storage;

    @Test
    void saveDelegatesToStorageWithPrefixedSlot() {
        when(storage.save("manual-boss", "level=7")).thenReturn(SaveResult.success("saving game to hdd"));

        SaveResult result = new ManualSaveManager(storage).save("boss", "level=7");

        assertEquals(SaveResult.success("saving game to hdd"), result);
        verify(storage).save("manual-boss", "level=7");
    }

    @Test
    void loadDelegatesToStorageWithPrefixedSlot() {
        when(storage.load("manual-boss")).thenReturn(SaveResult.success("loading game from hdd"));

        assertEquals("loading game from hdd", new ManualSaveManager(storage).load("boss").message());
    }

    @Test
    void storageFailureIsPassedBackUnchanged() {
        when(storage.save("manual-x", "d")).thenReturn(SaveResult.failure("storage is full"));

        SaveResult result = new ManualSaveManager(storage).save("x", "d");

        assertFalse(result.ok());
        assertEquals("storage is full", result.message());
    }
}
