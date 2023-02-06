package model;

import java.util.ArrayList;

public class BloodSugarReading {
    private double readingValue;
    private String date;
    private String time;

    // REQUIRES: category must be one of: "fasting", "before meal", or "after meal"
    // EFFECTS: constructs a blood sugar reading with specified value and time of day
    public BloodSugarReading(double value, String date, String time) {
        readingValue = value;
        this.date = date;
        this.time = time;

    }

    public double getValue() {
        return readingValue;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    // EFFECTS: retrieves all reading value(s) on a given date
    public ArrayList getReadingValuesAndTimeFromDay(String date) {
        return new ArrayList(); // stub
    }
}
