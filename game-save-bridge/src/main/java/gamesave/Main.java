package gamesave;

import gamesave.abstraction.AutoSaveManager;
import gamesave.abstraction.ManualSaveManager;
import gamesave.adapter.CloudVaultStorageAdapter;
import gamesave.selection.StorageRegistry;
import gamesave.storage.HardDriveStorage;
import gamesave.storage.MemoryStorage;
import gamesave.storage.SaveStorage;
import gamesave.vendor.CloudVaultClient;

import java.util.Optional;

/** Client / composition root: the only place that knows the concrete storages. */
public class Main {
    public static void main(String[] args) {
        CloudVaultClient vault = new CloudVaultClient();
        StorageRegistry registry = new StorageRegistry()
                .register("hdd", path -> new HardDriveStorage())
                .register("ram", ignored -> new MemoryStorage())
                .register("cloud", account -> new CloudVaultStorageAdapter(vault, account));

        String[] locations = args.length > 0 ? args : new String[]{"hdd:C:/saves", "ram:", "cloud:player42", "ftp:nowhere"};
        for (String location : locations) {
            System.out.println("== " + location);
            Optional<SaveStorage> storage = registry.open(location); // chosen from the input itself
            if (storage.isEmpty()) {
                System.out.println("unknown storage");
                continue;
            }
            ManualSaveManager manual = new ManualSaveManager(storage.get());
            AutoSaveManager auto = new AutoSaveManager(storage.get());
            System.out.println(manual.save("before-boss", "level=7").message());
            System.out.println(auto.autoSave("level=8").message());
            System.out.println(auto.loadLatest().message());
        }

        System.out.println("== cloud goes offline");
        vault.setOnline(false);
        System.out.println(new ManualSaveManager(registry.open("cloud:player42").get()).save("x", "level=9").message());
    }
}
