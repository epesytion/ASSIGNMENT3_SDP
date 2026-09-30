# Oral defense cheat sheet (not for submission)

**Pitch:** How the player saves (manual / auto) and where the save goes (hdd / ram / cloud) vary independently -> Bridge. The cloud SDK can't implement my interface and can't be edited -> Adapter, placed on the Implementor side.

1. **Where is the Bridge?** `SaveManager.storage` (a `SaveStorage`). Abstraction hierarchy: SaveManager -> Manual/Auto. Implementor hierarchy: SaveStorage -> HardDrive/Memory/CloudVaultStorageAdapter.
2. **Why is the adaptee really incompatible?** Names (`putBlob/getBlob`), reversed params with different types (`byte[] payload, String key`), `int` codes and `null` instead of `SaveResult`, plus an account-scoped key.
3. **What is translated?** `OK` -> success; `QUOTA_EXCEEDED` -> "cloud storage is full"; `NETWORK_DOWN` -> "cloud is unreachable"; unknown code -> generic failure; `null` on load -> "no save found".
4. **Why not only Bridge / only Adapter?** Sections 3 and 4 of the rationale (M+N vs M x N; can't edit vendor code).
5. **Object or class adapter?** Object adapter (composition): Java has no multiple inheritance and it stays mockable/swappable (Lecture 3).
6. **Show OCP.** Add `QuickSaveManager` (subclass) and a `nas` storage (class + one `register` line).
7. **Where does dynamic selection happen?** `StorageRegistry.open(String)`: scheme before `:` picks the factory. Only `Main` knows concrete classes.
8. **Adapter vs Facade / Strategy?** Adapter changes an existing interface to the one expected; Facade simplifies a subsystem; Strategy swaps behaviour behind one constant interface (Lecture 4 table).
9. **Why no exceptions?** Failures are part of the contract as `SaveResult`. Limitation: callers get text, not a typed cause.
10. **Live edit ideas:** add `QuickSaveManager`; map a new vendor code; register a new storage.
