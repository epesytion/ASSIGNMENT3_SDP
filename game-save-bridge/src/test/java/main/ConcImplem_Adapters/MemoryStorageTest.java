package main.ConcImplem_Adapters;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class MemoryStorageTest {

    @Test
    void nameShouldReturnMemory() {
        MemoryStorage storage = new MemoryStorage();

        assertEquals("memory", storage.name());
    }

    @Test
    void saveShouldPrintCorrectMessage() {
        MemoryStorage storage = new MemoryStorage();

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;

        System.setOut(new PrintStream(output));

        storage.save("1", "HP=100");

        System.setOut(originalOut);

        assertTrue(
                output.toString().contains(
                        "1 Saved at the memory: HP=100"
                )
        );
    }

    @Test
    void loadShouldPrintCorrectMessage() {
        MemoryStorage storage = new MemoryStorage();

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;

        System.setOut(new PrintStream(output));

        storage.load("1", "HP=100");

        System.setOut(originalOut);

        assertTrue(
                output.toString().contains(
                        "1 Loaded the data from memory: HP=100"
                )
        );
    }
}