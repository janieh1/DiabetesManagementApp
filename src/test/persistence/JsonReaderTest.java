package persistence;

import model.BloodSugarReading;
import model.LogBook;
import org.junit.jupiter.api.Test;


import java.io.IOException;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            LogBook book = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyLogBook() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyLogBook.json");
        try {
            LogBook book = reader.read();
            assertEquals("My Log Book", book.getName());
            assertEquals(0, book.getReadings().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralLogBook() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralLogBook.json");
        try {
            LogBook book = reader.read();
            assertEquals("My Log Book", book.getName());
            assertEquals(2, book.getReadings().size());
            assertEquals(new BloodSugarReading(4.5, "2023-03-06", "04:11", "fasting"), book.getReadings().get(0));
            assertEquals(new BloodSugarReading(6.7, "2023-03-06", "12:12", "after meal"), book.getReadings().get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}

