package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BloodSugarReadingTest {
    BloodSugarReading reading;

    @BeforeEach
    public void setup() {
        reading = new BloodSugarReading(3.0, "2023-02-06", "12:34", "before meal");
    }

    @Test
    public void BloodSugarReadingConstructorTest() {
        assertEquals(3.0, reading.getValue());
        assertEquals("2023-02-06", reading.getDate());
        assertEquals("12:34", reading.getTime());
        assertEquals("before meal", reading.getCategory());
        assertEquals("", reading.getNotes());

    }

    @Test
    public void setNotesTest() {
        assertEquals("", reading.getNotes());
        reading.setNotes("felt low");
        assertEquals("felt low", reading.getNotes());
    }

}