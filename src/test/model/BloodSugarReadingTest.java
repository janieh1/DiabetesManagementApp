package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BloodSugarReadingTest {
    BloodSugarReading reading;

    @BeforeEach
    public void setup() {
        reading = new BloodSugarReading(3.0, "02-06-2023", "12:34");
    }

    @Test
    public void BloodSugarReadingConstructorTest() {
        assertEquals(3.0, reading.getValue());
        assertEquals("02-06-2023", reading.getDate());
        assertEquals("12:34", reading.getTime());

    }

    @Test
    public void getReadingValuesAndTimeFromDayTest() {

    }
}