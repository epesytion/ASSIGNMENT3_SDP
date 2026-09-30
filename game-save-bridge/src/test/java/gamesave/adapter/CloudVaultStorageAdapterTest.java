package gamesave.adapter;

import gamesave.storage.SaveResult;
import gamesave.vendor.CloudVaultClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CloudVaultStorageAdapterTest {
    private static final byte[] DATA = "level=7".getBytes(StandardCharsets.UTF_8);

    @Mock CloudVaultClient client;
    CloudVaultStorageAdapter adapter;

    @BeforeEach
    void setUp() {
        adapter = new CloudVaultStorageAdapter(client, "p1");
    }

    // ---- normal translation ----

    @Test
    void saveCallsPutBlobWithReorderedArgumentsAndAccountKey() {
        when(client.putBlob(DATA, "p1/slot")).thenReturn(CloudVaultClient.OK);

        SaveResult result = adapter.save("slot", "level=7");

        assertEquals(SaveResult.success("saving game to cloud"), result);
        verify(client).putBlob(DATA, "p1/slot");
    }

    @Test
    void loadSucceedsWhenVaultReturnsData() {
        when(client.getBlob("p1/slot")).thenReturn(DATA);

        assertEquals(SaveResult.success("loading game from cloud"), adapter.load("slot"));
    }

    // ---- failure translation: codes and null become SaveResult.failure ----

    @Test
    void quotaCodeBecomesFailureMessage() {
        when(client.putBlob(DATA, "p1/slot")).thenReturn(CloudVaultClient.QUOTA_EXCEEDED);

        assertEquals(SaveResult.failure("cloud storage is full"), adapter.save("slot", "level=7"));
    }

    @Test
    void networkCodeBecomesFailureMessage() {
        when(client.putBlob(DATA, "p1/slot")).thenReturn(CloudVaultClient.NETWORK_DOWN);

        assertEquals(SaveResult.failure("cloud is unreachable"), adapter.save("slot", "level=7"));
    }

    @Test
    void unknownCodeBecomesGenericFailureWithoutLeakingTheCode() {
        when(client.putBlob(DATA, "p1/slot")).thenReturn(-99);

        SaveResult result = adapter.save("slot", "level=7");

        assertFalse(result.ok());
        assertFalse(result.message().contains("99"));
    }

    @Test
    void nullFromVaultBecomesFailureInsteadOfNullPointer() {
        when(client.getBlob("p1/slot")).thenReturn(null);

        SaveResult result = adapter.load("slot");

        assertFalse(result.ok());
        assertTrue(result.message().contains("no save found"));
    }
}
