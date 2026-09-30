package gamesave.storage;

/** What every storage returns. Failures are reported here (no exceptions). */
public record SaveResult(boolean ok, String message) {
    public static SaveResult success(String message) {
        return new SaveResult(true, message);
    }

    public static SaveResult failure(String message) {
        return new SaveResult(false, message);
    }
}
