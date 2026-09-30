package main.Target_Implementor;

//Implementator / Target
public interface SaveStorage {
    String name();
    void save(String id, String data);
    void load(String id, String data);
}
