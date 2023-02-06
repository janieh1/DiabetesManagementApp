package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LogBookTest {
    LogBook b1;
    LogBook b2;

    @BeforeEach
    public void setup() {
        b1 = new LogBook();
        b2 = new LogBook();
        b2.addReading(new BloodSugarReading(3.0, "02-03-2023", "3:30"));
        b2.addReading(new BloodSugarReading(4.3, "02-03-2023", "7:30"));
        b2.addReading(new BloodSugarReading(5.4, "02-03-2023", "12:40"));
        b2.addReading(new BloodSugarReading(5.2, "02-03-2023", "17:30"));
    }

    @Test
    public void LogBookConstructorTest() {
        assertEquals(0, b1.getReadings().size());
    }

    @Test
    public void addReadingTest() {
        assertEquals(0, b1.getReadings().size());
        b1.addReading(new BloodSugarReading(4.6, "02-05-2023", "14:34"));
        assertEquals(1, b1.getReadings().size());
    }

    @Test
    public void calculateAverageTest() {
        assertEquals((3.0 + 4.3 + 5.4 + 5.2) / 4, b2.calculateAverage());

    }
}
