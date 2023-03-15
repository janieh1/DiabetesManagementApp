package model;

import exceptions.ReadingNotFoundException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

// represents a logbook of blood sugar readings
public class LogBook {
    private String name;
    private ArrayList<BloodSugarReading> readings;

    // EFFECTS: creates new logbook with no readings in it
    public LogBook() {
        this.readings = new ArrayList<BloodSugarReading>();
        this.name = "My Log Book";
    }

    public String getName() {
        return name;
    }

    // EFFECTS: gets list of readings in log book
    public ArrayList<BloodSugarReading> getReadings() {
        return readings;
    }

    // REQUIRES:
    // MODIFIES: this
    // EFFECTS: adds blood sugar reading to logbook
    public void addReading(BloodSugarReading reading) {
        readings.add(reading);
    }

    // REQUIRES: category is one of "before meal", "fasting", or "after meal"
    // EFFECTS: retrieves a list of blood sugar readings with dates and times and notes from given category
    public ArrayList<BloodSugarReading> getReadingsInCategory(String category) {
        ArrayList<BloodSugarReading> readingsInCategory = new ArrayList<BloodSugarReading>();
        for (BloodSugarReading bsr : readings) {
            if (bsr.getCategory().equals(category)) {
                readingsInCategory.add(bsr);
            }
        }
        return readingsInCategory;
    }

    // REQUIRES: category is one of "before meal", "fasting", or "after meal"
    // EFFECTS: retrieves a list of just blood sugar values from given category
    public ArrayList<Double> getValuesInCategory(String category) {
        ArrayList<Double> valuesInCat = new ArrayList<Double>();
        for (BloodSugarReading bsr : getReadingsInCategory(category)) {
            valuesInCat.add(bsr.getValue());
        }
        return valuesInCat;
    }

    // EFFECTS: calculates overall average blood sugar reading
    public double calculateOverallAverage() {
        double overallSum = 0;
        if (readings.size() == 0) {
            return 0.0;
        } else {
            for (BloodSugarReading r : readings) {
                overallSum += r.getValue();
            }
        }
        return (overallSum / readings.size());
    }

    // REQUIRES: category is one of "before meal", "fasting" or "after meal"
    // EFFECTS: calculates average blood sugar in mmol/L in a given category
    public double calculateAverageOfCategory(String category) {
        double sum = 0;
        if (getReadingsInCategory(category).size() == 0) {
            return 0.0;
        } else {
            for (BloodSugarReading bsr : getReadingsInCategory(category)) {
                sum += bsr.getValue();
            }
        }
        return (sum / getReadingsInCategory(category).size());
    }

    // REQUIRES: date is in the form YYYY-MM-DD
    // EFFECTS: retrieves all readings from a given date
    public ArrayList<BloodSugarReading> getReadingsFromDay(String date) {
        ArrayList readingsOnDate = new ArrayList<BloodSugarReading>();
        for (BloodSugarReading bsr : readings) {
            if (bsr.getDate().equals(date)) {
                readingsOnDate.add(bsr);
            }
        }
        return readingsOnDate;
    }

    // REQUIRES: list of readings is not empty
    // MODIFIES: BloodSugarReading
    // EFFECTS: adds notes to the last reading in the list (the most recently added)
    public void addNotesToLastReading(String notes) {
        BloodSugarReading bsr = readings.get(readings.size() - 1);
        bsr.setNotes(notes);
    }

    // MODIFIES: blood sugar reading
    // EFFECTS: adds notes to a reading given the time and date. if no reading exists,
    // throw new ReadingNotFoundException
    public void setNotesOfReadingOnTimeAndDay(String time, String date, String notes) throws ReadingNotFoundException {
        BloodSugarReading reading = null;
        for (BloodSugarReading bsr : readings) {
            if ((bsr.getTime().equals(time)) && bsr.getDate().equals(date)) {
                reading = bsr;
                break;
            }
        }
        if (reading == null) {
            throw new ReadingNotFoundException("No reading at that time and date exists.");
        } else {
            reading.setNotes(notes);
        }
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("readings", readingsToJson());
        return json;
    }

    // EFFECTS: returns things in this workroom as a JSON array
    private JSONArray readingsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (BloodSugarReading bsr : readings) {
            jsonArray.put(bsr.toJson());
        }

        return jsonArray;
    }


}
