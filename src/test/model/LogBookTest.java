package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LogBookTest {
    LogBook b1;
    LogBook b2;

    @BeforeEach
    public void setup() {
        b1 = new LogBook();
        b2 = new LogBook();
        b2.addReading(new BloodSugarReading(3.0, "2023-02-03", "03:30", "fasting"));
        b2.addReading(new BloodSugarReading(4.3, "2023-02-03", "07:30", "before meal"));
        b2.addReading(new BloodSugarReading(5.4, "2023-02-04", "12:40", "before meal"));
        b2.addReading(new BloodSugarReading(5.2, "2023-02-03", "17:30", "before meal"));
    }

    @Test
    public void LogBookConstructorTest() {
        assertEquals(0, b1.getReadings().size());
    }

    @Test
    public void addReadingTest() {
        assertEquals(0, b1.getReadings().size());
        b1.addReading(new BloodSugarReading(4.6, "2023-02-05", "14:34", "after meal"));
        assertEquals(1, b1.getReadings().size());
    }

    @Test
    public void calculateAverageTest() {
        assertEquals((3.0 + 4.3 + 5.4 + 5.2) / 4.0, b2.calculateOverallAverage());
        assertEquals(0.0, b1.calculateOverallAverage());

    }

    @Test
    public void getReadingValuesAndTimeFromDayTestTypical() {
        ArrayList<BloodSugarReading> readingsFromDay = b2.getReadingsFromDay("2023-02-03");
        assertEquals(3, readingsFromDay.size());
        BloodSugarReading firstReading = readingsFromDay.get(0);
        assertEquals(3.0, firstReading.getValue());
        assertEquals("2023-02-03", firstReading.getDate());
        assertEquals("03:30", firstReading.getTime());
        assertEquals("fasting", firstReading.getCategory());

    }

    @Test
    public void getReadingValuesAndTimeFromDayTestNoReadingsOnDay() {
        ArrayList<BloodSugarReading> readingsFromDay = b2.getReadingsFromDay("2023-03-03");
        assertEquals(0, readingsFromDay.size());

    }

    @Test
    public void getReadingValuesFromCategoryTestTypical() {
        ArrayList<BloodSugarReading> readingsFromCat = b2.getReadingValuesFromCategory("before meal");
        assertEquals(3, readingsFromCat.size());
        BloodSugarReading firstReading = readingsFromCat.get(0);
        assertEquals(4.3, firstReading.getValue());
        assertEquals("2023-02-03", firstReading.getDate());
        assertEquals("07:30", firstReading.getTime());
        assertEquals("before meal", firstReading.getCategory());

    }

    @Test
    public void getValuesFromCatTest() {
        ArrayList<Double> valuesFromCat = b2.getValuesInCategory("before meal");
        assertEquals(3, valuesFromCat.size());
        Double firstValue = valuesFromCat.get(0);
        assertEquals(4.3, firstValue);
    }

    @Test
    public void calculateAverageInCategoryTest() {
        assertEquals((4.3 + 5.2 + 5.4) / 3.0, b2.calculateAverageOfCategory("before meal"), 0.0001);
        assertEquals(3.0, b2.calculateAverageOfCategory("fasting"), 0.0001);
        assertEquals(0.0, b2.calculateAverageOfCategory("after meal"), 0.0001);
    }

    @Test
    public void addNotesToMostRecentReadingTest() {
        BloodSugarReading bsr = b2.getReadings().get(b2.getReadings().size() - 1);
        assertEquals("", bsr.getNotes());
        b2.addNotesToLastReading("felt low");
        assertEquals("felt low", bsr.getNotes());


    }
}
