# Design Rationale - Game Save System (Bridge + Adapter)

## 1. Problem
A game must save player progress. Two things vary independently:

- **How the player saves** (abstraction axis): manual saves with a chosen name, or one automatic slot.
- **Where the save goes** (implementation axis): hard drive, memory (guest session), or a cloud vault.

**Complexity module: dynamic implementor selection.** `StorageRegistry.open("cloud:player42")` chooses the storage from the input string. The client never hard-codes a storage class; a new storage is one `register(...)` call.

## 2. Structure
- **Abstraction:** `SaveManager` (holds a `SaveStorage`). **Refined:** `ManualSaveManager`, `AutoSaveManager`.
- **Implementor:** `SaveStorage` (`save`, `load`). Every call returns a `SaveResult(ok, message)`; failures are reported as `SaveResult.failure(...)`, never as vendor codes.
- **Concrete implementors:** `HardDriveStorage`, `MemoryStorage`, `CloudVaultStorageAdapter`.
- **Adaptee:** `CloudVaultClient` (third-party style, not modified).

The abstraction imports only `SaveStorage` and `SaveResult`; it never touches the vendor package.

## 3. Why Bridge alone is not enough
Bridge assumes every storage can implement `SaveStorage`. The cloud SDK cannot: we do not own its code and it must stay unchanged. Without Adapter, the save managers would need cloud-specific calls and error codes, which breaks the bridge.

## 4. Why Adapter alone is not enough
An adapter only makes the cloud SDK look like `SaveStorage`. It does not solve the combinations of save behaviour and storage: without Bridge we would need `ManualHddSave`, `AutoHddSave`, `ManualCloudSave`, ... (M x N classes). Bridge keeps it at M + N.

## 5. Why `CloudVaultClient` is genuinely incompatible
| Aspect | `SaveStorage` | `CloudVaultClient` |
|---|---|---|
| Method names | `save`, `load` | `putBlob`, `getBlob` |
| Parameters | `(String slot, String gameData)` | `(byte[] payload, String key)`: reversed order, different types |
| Result | `SaveResult` status object | `int` result code (save), `byte[]` or `null` (load) |
| Failure mechanism | `SaveResult.failure(message)` | codes `QUOTA_EXCEEDED`, `NETWORK_DOWN`; `null` when missing or offline |
| Key model | plain slot name | needs an account-scoped key (`account/slot`) |

The adapter converts the arguments, maps each code and `null` to a meaningful `SaveResult.failure(...)` message, and returns a generic failure for unknown codes. No vendor constant or code leaves the adapter (tested in `CloudVaultStorageAdapterTest`).

## 6. Open/Closed on both axes
- **New abstraction** (e.g. `QuickSaveManager`): a new subclass of `SaveManager`; nothing existing changes.
- **New implementor** (e.g. NAS): a new `SaveStorage` class plus one `register("nas", ...)` line in `Main`; `StorageRegistry` and the managers stay untouched (`StorageRegistryTest.newStorageNeedsOnlyANewRegistration`).

## 7. Limitation
The storages are simplified: they report messages rather than reading and writing real files, and failures are plain text in `SaveResult`, so callers cannot react to a specific cause (e.g. "storage full" vs "offline") without parsing the message. A richer design would add an error-type field or enum to `SaveResult`.
