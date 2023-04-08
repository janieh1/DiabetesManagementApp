package model;

import exceptions.ReadingNotFoundException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// represents a logbook of blood sugar readings
public class LogBook {
    private String name;
    private ArrayList<BloodSugarReading> readings;

    // MODIFIES: this
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

    // MODIFIES: this
    // EFFECTS: adds blood sugar reading to logbook
    // TODO: log event when new reading is added
    public void addReading(BloodSugarReading reading) {
        readings.add(reading);
        EventLog.getInstance().logEvent(new Event("Added reading to logbook"));
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
    // TODO: log event when notes are added to new reading
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
            EventLog.getInstance().logEvent(new Event("Added notes to reading on " + date + "at " + time
                    + ": " + notes));
        }
    }

    // MODIFIES: json
    // EFFECTS: writes logbook to json array
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("readings", readingsToJson());
        return json;
    }

    // EFFECTS: returns readings in logbook as a JSON array
    private JSONArray readingsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (BloodSugarReading bsr : readings) {
            jsonArray.put(bsr.toJson());
        }

        return jsonArray;
    }

    // EFFECTS: returns the readings in given category in the logbook as one string. if category == "none", all readings
    //          will be shown
    // TODO: log event when we get reading as string from certain category
    public ArrayList<String> getReadingsAsStrings(String category) {
        ArrayList<String> readingsAsStrings = new ArrayList<String>();
        if (category == "none") {
            for (BloodSugarReading bsr : readings) {
                String stringToAdd = "Value: " + bsr.getValue() + " mmol/L\n"
                        + "Date: " + bsr.getDate() + "\n"
                        + "Time: " + bsr.getTime() + "\n" + "Category: " + bsr.getCategory()
                        + "\n" + "Notes: " + bsr.getNotes();
                readingsAsStrings.add(stringToAdd);
            }
        } else {
            ArrayList<BloodSugarReading> readingsFromCat = getReadingsInCategory(category);
            for (BloodSugarReading bsr : readingsFromCat) {
                String stringToAdd = "Value: " + bsr.getValue() + " mmol/L\n"
                        + "Date: " + bsr.getDate() + "\n"
                        + "Time: " + bsr.getTime() + "\n" + "Category: " + bsr.getCategory()
                        + "\n" + "Notes: " + bsr.getNotes();
                readingsAsStrings.add(stringToAdd);
            }
            EventLog.getInstance().logEvent(new Event("Filtered logbook to show readings from " + category
                    + " category only"));
        }

        return readingsAsStrings;
    }

    // EFFECTS: produces a map of each reading value group (high, in range, or low) with their values being the
    // percentage of readings in the book that have that value
    public Map<String, Double> calculateTimeInRange() {
        Map<String, Double> inRange = new HashMap<>();
        inRange.put("low", 0.0);
        inRange.put("in range", 0.0);
        inRange.put("high", 0.0);

        for (BloodSugarReading bsr : readings) {
            if (bsr.getValue() >= 10.1) {
                inRange.put("high", inRange.get("high") + 1);
            } else if (bsr.getValue() >= 4.1) {
                inRange.put("in range", inRange.get("in range") + 1);
            } else {
                inRange.put("low", inRange.get("low") + 1);
            }
        }
        inRange.put("high", inRange.get("high") / readings.size());
        inRange.put("in range", inRange.get("in range") / readings.size());
        inRange.put("low", inRange.get("low") / readings.size());
        return inRange;
    }

}
