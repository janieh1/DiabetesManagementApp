package persistence;

import model.BloodSugarReading;
import model.LogBook;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest {

    @Test
    void testWriterInvalidFile() {
        try {
            LogBook book = new LogBook();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyLogBook() {
        try {
            LogBook book = new LogBook();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyLogBook.json");
            writer.open();
            writer.write(book);
            writer.close();
        } catch (IOException e) {
            fail("Unexpected IOException thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            LogBook book = new LogBook();
            book.addReading(new BloodSugarReading(4.5, "2023-03-06", "04:11", "fasting"));
            book.addReading(new BloodSugarReading(6.7, "2023-03-06", "12:12", "after meal"));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralLogBook.json");
            writer.open();
            writer.write(book);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralLogBook.json");
            book = reader.read();
            assertEquals("My Log Book", book.getName());
            assertEquals(2, book.getReadings().size());
            assertEquals(new BloodSugarReading(4.5, "2023-03-06", "04:11", "fasting"), book.getReadings().get(0));
            assertEquals(new BloodSugarReading(6.7, "2023-03-06", "12:12", "after meal"), book.getReadings().get(1));

        } catch (IOException e) {
            fail("Unexpected IOException thrown");
        }
    }
}
