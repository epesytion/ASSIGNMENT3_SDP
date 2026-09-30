package main.ConcImplem_Adapters;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class FileStorageTest {

    @Test
    void nameShouldReturnFile() {
        FileStorage storage = new FileStorage();

        assertEquals("file", storage.name());
    }

    @Test
    void saveShouldPrintCorrectMessage() {
        FileStorage storage = new FileStorage();

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        storage.save("1", "Player Data");

        assertTrue(
                output.toString().contains("1 Saved by file: Player Data")
        );

        System.setOut(System.out);
    }

    @Test
    void loadShouldPrintCorrectMessage() {
        FileStorage storage = new FileStorage();

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;

        System.setOut(new PrintStream(output));

        storage.load("1", "Player Data");

        System.setOut(originalOut);

        assertTrue(
                output.toString().contains("1 Loaded data from file: Player Data")
        );
    }
}