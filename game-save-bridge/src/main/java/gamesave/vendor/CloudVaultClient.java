package gamesave.vendor;

import java.util.HashMap;
import java.util.Map;

/**
 * THIRD-PARTY CODE - do not modify (simulated in memory here).
 * Different from SaveStorage in: method names, parameter order and types (byte[] payload first),
 * and failures - int result codes and null instead of SaveResult.
 */
public class CloudVaultClient {
    public static final int OK = 0;
    public static final int QUOTA_EXCEEDED = -3;
    public static final int NETWORK_DOWN = -7;

    private final Map<String, byte[]> blobs = new HashMap<>();
    private boolean online = true;

    public void setOnline(boolean online) {
        this.online = online;
    }

    public int putBlob(byte[] payload, String key) {
        if (!online) return NETWORK_DOWN;
        if (payload.length > 1000) return QUOTA_EXCEEDED;
        blobs.put(key, payload);
        return OK;
    }

    /** @return the data, or null if the key is unknown or the cloud is unreachable */
    public byte[] getBlob(String key) {
        return online ? blobs.get(key) : null;
    }
}
