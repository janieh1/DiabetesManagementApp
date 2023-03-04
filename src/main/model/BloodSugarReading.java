package model;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

// represents a blood sugar reading with a value, date, time, category, and notes.
public class BloodSugarReading {
    private double readingValue;
    private LocalDate date;
    private LocalTime time;
    private String category;
    private String notes;

    // REQUIRES: year is in form YYYY-MM-DD, time is in 24hr format HH:MM
    // MODIFIES: this
    // EFFECTS: constructs a blood sugar reading with specified value and time of day
    public BloodSugarReading(double value, String date, String time, String category) {
        readingValue = value;
        this.date = LocalDate.parse(date);
        this.time = LocalTime.parse(time);
        this.category = category;
        this.notes = "";
    }

    // EFFECTS: retrieves blood sugar reading
    public double getValue() {
        return readingValue;
    }

    // EFFECTS: retrieves time of blood sugar reading
    public String getDate() {
        return date.toString();
    }

    // EFFECTS: retrieves time for blood sugar reading
    public String getTime() {
        return time.toString();
    }

    // EFFECTS: retrieves category of reading
    public String getCategory() {
        return this.category;
    }

    // EFFECTS: retrieves notes associated with a specific reading
    public String getNotes() {
        return notes;
    }

    // MODIFIES: this
    // EFFECTS: adds notes about the reading (notes could be anything, something like after exercise, or if you
    //          were sick at the time, etc)
    public void setNotes(String notesToAdd) {
        this.notes = notesToAdd;
    }


}
