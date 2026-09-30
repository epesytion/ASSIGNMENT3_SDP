package gamesave.abstraction;

import gamesave.storage.SaveResult;
import gamesave.storage.SaveStorage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AutoSaveManagerTest {
    @Mock SaveStorage storage;

    @Test
    void autoSaveAlwaysUsesTheAutosaveSlot() {
        when(storage.save("autosave", "level=8")).thenReturn(SaveResult.success("saving game to ram"));

        SaveResult result = new AutoSaveManager(storage).autoSave("level=8");

        assertEquals("saving game to ram", result.message());
        verify(storage).save("autosave", "level=8");
    }

    @Test
    void loadLatestLoadsTheAutosaveSlot() {
        when(storage.load("autosave")).thenReturn(SaveResult.success("loading game from ram"));

        assertEquals("loading game from ram", new AutoSaveManager(storage).loadLatest().message());
    }
}
