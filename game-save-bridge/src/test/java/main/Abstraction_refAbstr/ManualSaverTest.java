package main.Abstraction_refAbstr;

import main.ConcImplem_Adapters.FileStorage;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class ManualSaverTest {

    @Test
    void manualSaverShouldSaveUsingFileStorage() {
        FileStorage storage = new FileStorage();

        ManualSaver saver =
                new ManualSaver(storage, "The Kingdom");

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;

        System.setOut(new PrintStream(output));

        saver.save("1", "Manual");

        System.setOut(originalOut);

        String result = output.toString();

        assertTrue(
                result.contains(
                        "YOU REST AT THE BENCH AND SAVED THE GAME"
                )
        );

        assertTrue(
                result.contains(
                        "The checkpoint: The Kingdom"
                )
        );

        assertTrue(
                result.contains(
                        "1 Saved by file: Manual"
                )
        );
    }
}