package model;

import exceptions.ReadingNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

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
        assertEquals("My Log Book", b1.getName());
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
        ArrayList<BloodSugarReading> readingsFromCat = b2.getReadingsInCategory("before meal");
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

    @Test
    public void setNotesOfReadingOnTimeAndDayTestCorrectDateAndTime() {
        try {
            b2.setNotesOfReadingOnTimeAndDay("03:30","2023-02-03", "sick");
        } catch (ReadingNotFoundException e) {
            fail("Unexpected ReadingNotFoundException");
        }
        assertEquals("sick", b2.getReadings().get(0).getNotes());
    }

    @Test
    public void setNotesOfReadingOnTimeAndDayTestWrongDate() {
        try {
            b2.setNotesOfReadingOnTimeAndDay("03:30","2023-03-03", "sick");
            fail("expected ReadingNotFoundException");
        } catch (ReadingNotFoundException e) {
            // pass
        }
    }

    @Test
    public void setNotesOfReadingOnTimeAndDayTestWrongTime() {
        try {
            b2.setNotesOfReadingOnTimeAndDay("02:30","2023-02-03", "sick");
            fail("expected ReadingNotFoundException");
        } catch (ReadingNotFoundException e) {
            // pass
        }
    }

    @Test
    public void getReadingsAsStringsTest() {
        b2.addReading(new BloodSugarReading(7.2, "2023-02-03", "17:30", "after meal"));
        ArrayList<String> readingsAsStringsAll = b2.getReadingsAsStrings("none");
        assertEquals(5, readingsAsStringsAll.size());
        ArrayList<String> readingsAsStringsFast = b2.getReadingsAsStrings("fasting");
        assertEquals(1, readingsAsStringsFast.size());
        String stringInList = "Value: 3.0 mmol/L\nDate: 2023-02-03\nTime: 03:30\nCategory: fasting\nNotes: ";
        assertTrue(readingsAsStringsFast.contains(stringInList));
        ArrayList<String> readingsAsStringsBefore = b2.getReadingsAsStrings("before meal");
        assertEquals(3, readingsAsStringsBefore.size());
        ArrayList<String> readingsAsStringsAfter = b2.getReadingsAsStrings("after meal");
        assertEquals(1, readingsAsStringsAfter.size());
        stringInList = "Value: 7.2 mmol/L\nDate: 2023-02-03\nTime: 17:30\nCategory: after meal\nNotes: ";
        assertTrue(readingsAsStringsAfter.contains(stringInList));
    }

    @Test
    public void calculateTimeInRangeTest() {
        b2.addReading(new BloodSugarReading(17.2, "2023-02-03", "17:30", "after meal"));
        assertEquals(0.2, b2.calculateTimeInRange().get("high"), 0.0001);
        assertEquals(0.6, b2.calculateTimeInRange().get("in range"), 0.0001);
        assertEquals(0.2, b2.calculateTimeInRange().get("low"), 0.0001);
    }
}
