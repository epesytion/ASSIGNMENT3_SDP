package main.Abstraction_refAbstr;

import main.ConcImplem_Adapters.MemoryStorage;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class AutoSaverTest {

    @Test
    void autoSaverShouldSaveUsingStorage() {
        MemoryStorage storage = new MemoryStorage();
        AutoSaver saver = new AutoSaver(storage);

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;

        System.setOut(new PrintStream(output));

        saver.save("1", "Auto Data");

        System.setOut(originalOut);

        String result = output.toString();

        assertTrue(
                result.contains("Saving data automatically")
        );

        assertTrue(
                result.contains("1 Saved at the memory: Auto Data")
        );
    }
}