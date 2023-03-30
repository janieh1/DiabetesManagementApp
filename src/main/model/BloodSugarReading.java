package model;

import org.json.JSONObject;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

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

    // EFFECTS: returns true if two readings have the same date and time
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BloodSugarReading that = (BloodSugarReading) o;
        return date.equals(that.date) && time.equals(that.time);
    }


    // EFFECTS: creates hascode for reading
    @Override
    public int hashCode() {
        return Objects.hash(date, time);
    }

    // MODIFIES: json object
    // EFFECTS: writes the data from the reading to json file
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("value", readingValue);
        json.put("date", date);
        json.put("time", time);
        json.put("category", category);
        json.put("notes", notes);
        return json;
    }
}
