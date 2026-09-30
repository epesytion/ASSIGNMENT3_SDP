package main.ConcImplem_Adapters;

// Adaptee
public class LegacySaveAPI {
    public void saveData(String id, String data) {
         System.out.println(id+" ?Saving...: " + data);
    }
    public void loadData(String id, String data){
        System.out.println(id+" ?Loading...: " + data);
    }
}
