package gamesave.adapter;

import gamesave.storage.SaveResult;
import gamesave.storage.SaveStorage;
import gamesave.vendor.CloudVaultClient;

import java.nio.charset.StandardCharsets;

/**
 * Adapter (object adapter): makes CloudVaultClient usable as a SaveStorage.
 * Converts arguments and turns the vendor's int codes / null into SaveResult,
 * so no vendor code ever reaches the abstraction.
 */
public class CloudVaultStorageAdapter implements SaveStorage {
    private final CloudVaultClient client;
    private final String account;

    public CloudVaultStorageAdapter(CloudVaultClient client, String account) {
        this.client = client;
        this.account = account;
    }

    @Override
    public SaveResult save(String slot, String gameData) {
        int code = client.putBlob(gameData.getBytes(StandardCharsets.UTF_8), account + "/" + slot);
        return switch (code) {
            case CloudVaultClient.OK -> SaveResult.success("saving game to cloud");
            case CloudVaultClient.QUOTA_EXCEEDED -> SaveResult.failure("cloud storage is full");
            case CloudVaultClient.NETWORK_DOWN -> SaveResult.failure("cloud is unreachable");
            default -> SaveResult.failure("cloud save failed");
        };
    }

    @Override
    public SaveResult load(String slot) {
        byte[] data = client.getBlob(account + "/" + slot);
        return data == null
                ? SaveResult.failure("no save found in cloud")
                : SaveResult.success("loading game from cloud");
    }
}
