package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    public void equalsNullObjectTest() {
        assertFalse(reading.equals(null));
    }

    @Test
    public void equalsNotSameClassTest() {
        assertFalse(reading.equals("Hello"));
    }

    @Test
    public void equalsSameObjectTest () {
        assertTrue(reading.equals(reading));
    }

    @Test
    public void equalsSameDateANotTimeTest() {
        assertFalse(reading.equals(new BloodSugarReading(12.2, "2023-03-06", "12:12", "fasting")));
    }

    @Test
    public void equalsSameTimeANotDateTest() {
        assertFalse(reading.equals(new BloodSugarReading(12.2, "2023-03-05", "12:34", "fasting")));
    }

    @Test
    public void equalsTest() {
        assertFalse(reading.equals(new BloodSugarReading(6.2, "2023-03-06", "12:34", "before meal")));
    }

    @Test
    public void hashCodeTest() {
        assertEquals(1237906720, reading.hashCode());
    }
}