package model;

import java.util.ArrayList;

// represents a logbook of blood sugar readings
public class LogBook {
    private ArrayList<BloodSugarReading> readings;

    // EFFECTS: creates new logbook with no readings in it
    public LogBook() {
        this.readings = new ArrayList<BloodSugarReading>();
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
    public ArrayList getReadingsFromDay(String date) {
        ArrayList readingsOnDate = new ArrayList<BloodSugarReading>();
        for (BloodSugarReading bsr : readings) {
            if (bsr.getDate().equals(date)) {
                readingsOnDate.add(bsr);
            }
        }
        return readingsOnDate;
    }

    // REQUIRES: category is one of: "before meal" "after meal" or "fasting"
    // EFFECTS: retrieves all reading value(s) with given category
    public ArrayList getReadingValuesFromCategory(String category) {
        ArrayList readingsFromCat = new ArrayList<BloodSugarReading>();
        for (BloodSugarReading bsr : readings) {
            if (bsr.getCategory().equals(category)) {
                readingsFromCat.add(bsr);
            }
        }
        return readingsFromCat;
    }

    // REQUIRES: list of readings is not empty
    // MODIFIES: BloodSugarReading
    // EFFECTS: adds notes to the last reading in the list (the most recently added)
    public void addNotesToLastReading(String notes) {
        BloodSugarReading bsr = readings.get(readings.size() - 1);
        bsr.setNotes(notes);
    }
}
