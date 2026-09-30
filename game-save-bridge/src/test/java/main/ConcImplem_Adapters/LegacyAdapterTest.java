package main.ConcImplem_Adapters;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class LegacyAdapterTest {

    @Test
    void nameShouldReturnAdapter() {
        LegacySaveAPI legacyAPI = new LegacySaveAPI();
        LegacyAdapter adapter = new LegacyAdapter(legacyAPI);

        assertEquals("adapter", adapter.name());
    }

    @Test
    void saveShouldCallLegacySaveAPI() {
        LegacySaveAPI legacyAPI = new LegacySaveAPI();
        LegacyAdapter adapter = new LegacyAdapter(legacyAPI);

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;

        System.setOut(new PrintStream(output));

        adapter.save("10", "Game Data");

        System.setOut(originalOut);

        assertTrue(
                output.toString().contains(
                        "10 ?Saving...: Game Data"
                )
        );
    }

    @Test
    void loadShouldCallLegacySaveAPI() {
        LegacySaveAPI legacyAPI = new LegacySaveAPI();
        LegacyAdapter adapter = new LegacyAdapter(legacyAPI);

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;

        System.setOut(new PrintStream(output));

        adapter.load("10", "Game Data");

        System.setOut(originalOut);

        assertTrue(
                output.toString().contains(
                        "10 ?Loading...: Game Data"
                )
        );
    }
}